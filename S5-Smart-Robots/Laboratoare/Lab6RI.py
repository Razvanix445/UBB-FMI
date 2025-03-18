#!/usr/bin/env python3
import rospy
import cv2
import math
import numpy as np
import time
from threading import Thread, Lock
from sensor_msgs.msg import Image
from std_srvs.srv import Empty
from puppy_control.msg import Pose, Gait, Velocity
from puppy_control.srv import SetRunActionName

ROS_NODE_NAME = "cam_control_node"

lock = Lock()
move_th = None

tl = (240, 180)
br = (400, 300)

max_contour = None
contour_center = None
radius = 0

pose_pub = None
gait_pub = None
vel_pub = None

shut = False
pose_msg = Pose(
    roll=math.radians(0),
    pitch=math.radians(0),
    yaw=0,
    height=-10,
    x_shift=0,
    stance_x=0,
    stance_y=0,
    run_time=500,
)
gait_msg = Gait(overlap_time=0.3, swing_time=0.5, clearance_time=0.0, z_clearance=3)
vel_msg = Velocity(x=0, y=0, yaw_rate=math.radians(0))


def filterColor(img):
    img = cv2.cvtColor(img, cv2.COLOR_BGR2LAB)

    mask = cv2.inRange(img, np.array([0, 0, 0]), np.array([255, 255, 102]))
    mask = cv2.dilate(mask, cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5)))
    mask = cv2.erode(mask, cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5)))
    return mask


def getMaxContour(mask, thresh=50):
    contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    max_area = 0
    max_contour = None
    for c in contours:
        c_area = math.fabs(cv2.contourArea(c))
        if c_area > thresh:
            if c_area > max_area:
                max_area = c_area
                max_contour = c
    return max_contour


def drawRectangularBound(img, c):
    x, y, w, h = cv2.boundingRect(c)
    img = cv2.rectangle(img, (x, y), (x + w, y + h), (255, 0, 0), thickness=2)
    return img, (x + w // 2, y + h // 2)


def drawCircularBound(img, c):
    center, radius = cv2.minEnclosingCircle(c)
    img = cv2.circle(
        img, (int(center[0]), int(center[1])), int(radius), (255, 0, 0), thickness=2
    )
    return img, (int(center[0]), int(center[1])), int(radius)


def img_process(img):
    global pose_pub, pose_msg
    global contour_center, radius, max_contour
    global lock, shut
    frame = np.ndarray(
        shape=(img.height, img.width, 3), dtype=np.uint8, buffer=img.data
    )
    cv2_img = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)
    mask = filterColor(cv2_img)
    with lock:
        if not shut:
            max_contour = getMaxContour(mask)
            if max_contour is not None:
                cv2_img, contour_center, radius = drawCircularBound(
                    cv2_img, max_contour
                )
                cv2_img = cv2.circle(
                    cv2_img, contour_center, 5, (0, 0, 255), thickness=5
                )

            cv2_img = cv2.rectangle(cv2_img, tl, br, (255, 255, 255))
    cv2.imshow("Frame", cv2_img)
    cv2.waitKey(1)


def cleanup():
    global shut, lock
    with lock:
        shut = True
    rospy.loginfo("Shutting down...")
    cv2.destroyWindow("Frame")
    rospy.ServiceProxy("/puppy_control/go_home", Empty)()


def move():
    global pose_pub, vel_pub, pose_msg, vel_msg
    global contour_center, radius, tl, br
    global lock, shut

    center_x = (tl[0] + br[0]) // 2
    center_y = (tl[1] + br[1]) // 2

    while True:
        time.sleep(0.2)
        with lock:
            if shut:
                break
            if max_contour is not None:
                x_offset = contour_center[0] - center_x
                y_offset = contour_center[1] - center_y

                pitch_angle = 0
                vel_msg.x = 0

                if 30 <= radius <= 40:
                    if abs(y_offset) > 20:
                        pitch_angle = -math.radians(y_offset / 20)                        
                else:
                    if radius < 30:
                        vel_msg.x = 10
                    elif radius > 40:
                        vel_msg.x = -5

                pose_msg.pitch = pitch_angle
                #pose_pub.publish(pose_msg)
                #time.sleep(0.2)

                if abs(x_offset) > 20:  
                    vel_msg.yaw_rate = -math.radians(x_offset / 10)
                else:
                    vel_msg.yaw_rate = 0

                #vel_pub.publish(vel_msg)
                #time.sleep(0.2)
            else:
                vel_msg.x = 0
                vel_msg.yaw_rate = math.radians(0)
                pose_msg.pitch = 0
                #pose_pub.publish(pose_msg)
                #vel_pub.publish(vel_msg)
                #time.sleep(0.2)
        pose_pub.publish(pose_msg)
        time.sleep(0.5)
        vel_pub.publish(vel_msg)
        time.sleep(1)


if __name__ == "__main__":
    rospy.init_node(ROS_NODE_NAME, log_level=rospy.INFO)
    rospy.on_shutdown(cleanup)
    pose_pub = rospy.Publisher("/puppy_control/pose", Pose, queue_size=1)
    gait_pub = rospy.Publisher("/puppy_control/gait", Gait, queue_size=1)
    vel_pub = rospy.Publisher("/puppy_control/velocity", Velocity, queue_size=1)
    gait_pub.publish(gait_msg)
    rospy.Subscriber("/usb_cam/image_raw", Image, img_process)
    rospy.ServiceProxy("/puppy_control/go_home", Empty)()
    move_th = Thread(target=move, daemon=True)
    move_th.start()
    rospy.spin()



