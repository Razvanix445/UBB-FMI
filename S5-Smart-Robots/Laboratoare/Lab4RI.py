#!/usr/bin/env python3
import rospy
import cv2
import numpy as np
from sensor_msgs.msg import Image

ROS_NODE_NAME="image_processing_node"

def img_process(img):
	frame = np.ndarray(shape=(img.height, img.width, 3), dtype=np.uint8, buffer=img.data)
	cv2_img = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)

	hsv = cv2.cvtColor(cv2_img, cv2.COLOR_BGR2HSV)
	mask = cv2.inRange(hsv, np.array([40,40,40]), np.array([80,255,255]))

	contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
	largest_contour = max(contours, key=cv2.contourArea)

	contour_frame = cv2_img.copy()
	#cv2.drawContours(contour_frame, [largest_contour], -1, (0, 255, 0), 2)

	(x, y), radius = cv2.minEnclosingCircle(largest_contour)
	center = (int(x), int(y))
	circle = cv2.circle(contour_frame, center,int(radius), [235, 90, 210], 4)

	#cv2.imshow("Frame", cv2_img)
	cv2.imshow("Object detection Frame", circle)

	cv2.waitKey(1) #this forces the window opened by OpenCV to remain open

def cleanup():
	rospy.loginfo("Shutting down...")
	cv2.destroyWindow("Object detection Frame")

if __name__ == "__main__":
	rospy.init_node(ROS_NODE_NAME, log_level=rospy.INFO)
	rospy.on_shutdown(cleanup)
	rospy.Subscriber("/usb_cam/image_raw", Image, img_process)
	try:
		rospy.spin()
	except KeyboardInterrupt:
		pass