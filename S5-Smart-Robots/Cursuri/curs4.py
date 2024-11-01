import cv2
import numpy as np


def basicShapes(img):
    # cv2.line(img, pt1, pt2, color[, thickness[, lineType[, shift]]]) -> img
    # result = cv2.line(img, (100, 240), (540, 240), (255, 255, 255))
    
    # cv2.rectangle(img, pt1, pt2, color[, thickness[, lineType[, shift]]]) -> img
    # result = cv2.rectangle(img, (100, 100), (540, 380), (255, 255, 255))
    
    # cv2.circle(img, center, radius, color[, thickness[, lineType[, shift]]]) -> img
    # result = cv2.circle(img, (320, 240), 50, (255, 255, 255))
    
    # cv2.polylines(img, pts, isClosed, color[, thickness[, lineType[, shift]]]) -> img
    # points = np.array([[40, 40], [100, 40], [200, 120], [300, 240], [200, 340]]) # list of all the vertexes of the polygon
    # points = points.reshape((-1, 1, 2))
    # result = cv2.polylines(img, [points], True, (255, 255, 255), thickness=2)
    
    # cv2.putText(img, text, org, fontFace, fontScale, color[, thickness[, lineType[, bottomLeftOrigin]]]) -> img
    result = cv2.putText(img, "My text", (100, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, (255, 255, 255), thickness=2)
    return result
   
def imgProps(img):
    print("Image size: %d" % img.size)
    print("Image shape: %s" % str(img.shape))
    return img
    
def splitMerge(img):
    (b, g, r) = cv2.split(img) # each of the three separate channels can still function as a regular OpenCV grayscale image
    cv2.imshow("R", r) # we can show them
    cv2.imshow("G", g)
    cv2.imshow("B", b)
    return cv2.merge([b, g, r]) # or merge them back
    
def convertColorspace(img):
    # cv2.cvtColor(src, code[, dst[, dstCn[, hint]]]) -> dst
    # result = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    # result = cv2.cvtColor(img, cv2.COLOR_BGR2Lab)
    # result = cv2.cvtColor(img, cv2.COLOR_BGR2YCrCb)
    result = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    # result = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    return result

def resize(img):
    # cv.resize(src, dsize[, dst[, fx[, fy[, interpolation]]]]) -> dst
    # fx scale factor along the horizontal axis; when it equals 0, it is computed as (double)dsize.width/src.cols
    # fy scale factor along the vertical axis; when it equals 0, it is computed as (double)dsize.height/src.rows
    result = cv2.resize(img, (1000, 1000))
    return result
   
def affineTransform(img):
    rows,cols,ch = img.shape
    # pts1 = np.float32([[50,50],[200,50],[50,200]])
    # pts2 = np.float32([[10,100],[200,50],[100,250]])
    # rotate 90 degrees
    pts1 = np.float32([[0,0],[0,640],[480,640]])
    pts2 = np.float32([[0,640],[480,640],[480,0]])
    M = cv2.getAffineTransform(pts1,pts2)
    result = cv2.warpAffine(img,M,(cols,rows))
    return result

def gaussNoise(img):
    # Gaussian noise
    # define a matrix of the same shape as the image, just filled with zeroes
    noise_mat = np.zeros(img.shape, np.uint8)
    # generate random numbers in the noise matrix with a specified mean and standard deviation values -> Gaussian distribution
    # cv2.randn(destination, mean, stddev)
    cv2.randn(noise_mat, 0, 100)
    # add the noise to the original image while forcing the values to remain within [0, 255]
    result = np.clip(img + noise_mat, 0, 255).astype(np.uint8)
    return result

def saltAndPepperNoise(img):
    # Salt and pepper noise
    h, w, d = img.shape
    # let's assume we want approximately 1% of the pixels to be white or black
    noisy_pixels = int(h * w * 0.01)
 
    result = img.copy()
    for x in range(noisy_pixels):
        # randomly select the coordinates of a pixel
        row, col = np.random.randint(0, h), np.random.randint(0, w)
        # 50% to make it fully white, 50% to make it fully black
        if np.random.rand() < 0.5:
            result[row, col] = [0, 0, 0] 
        else:
            result[row, col] = [255, 255, 255]
    return result
    
def randomNoise(img):
    # similar to gaussian noise, we define a matrix for noise
    noise = np.random.randint(-25, 25, img.shape)
    result = np.clip(img + noise, 0, 255).astype(np.uint8)
    return result

def blurring(img):
    # cv2.blur(src, ksize[, dst[, anchor[, borderType]]]	) -> dst
    # result = cv2.blur(img,(5,5))
    # cv2.GaussianBlur(src, ksize, sigmaX[, dst[, sigmaY[, borderType[, hint]]]]	) -> dst
    # result = cv2.GaussianBlur(img,(5,5),0)
    # cv2.medianBlur(src, ksize[, dst]) -> dst
    # result = cv2.medianBlur(img, 5)
    # cv2.bilateralFilter(src, d, sigmaColor, sigmaSpace[, dst[, borderType]]) -> dst
    result = cv2.bilateralFilter(img, 9, 75, 75)
    return result

def morph(img):
    kernel = np.ones((5, 5), np.uint8) 
  
    # The first parameter is the original image, 
    # kernel is the matrix with which image is 
    # convolved and third parameter is the number 
    # of iterations, which will determine how much 
    # you want to erode/dilate a given image. 
    # result = cv2.erode(img, kernel, iterations=1)
    # result = cv2.dilate(img, kernel, iterations=1)
    
    # cv.morphologyEx(src, op, kernel[, dst[, anchor[, iterations[, borderType[, borderValue]]]]]) -> dst
    # opening -> erosion followed by dilation
    # result = cv2.morphologyEx(img, cv2.MORPH_OPEN, kernel)
    # closing -> dilation followed by erosion
    result = cv2.morphologyEx(img, cv2.MORPH_CLOSE, kernel)
    return result

def binarization(img):
    # cv2.threshold(src, thresh, maxval, type[, dst]) -> retval, dst
    result = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    _, result = cv2.threshold(result, 128, 255, cv2.THRESH_BINARY)
    return result
    
def inRange(img):
    # cv2.inRange(src, lowerb, upperb[, dst]) -> dst
    # the following line will print the HSV value of a fully green pixel
    # print(str(cv2.cvtColor(np.uint8([[[0, 255, 0]]]), cv2.COLOR_BGR2HSV)))
    # we take as ranges [H-10, 100,100] and [H+10, 255, 255]
    # it is a simple and rough estimation of what ranges we should take
    result = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    result = cv2.inRange(result, np.array([50, 100, 100]), np.array([70, 255, 255]))
    result = cv2.erode(result, cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3)))
    result = cv2.dilate(result, cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3)))
    return result

def findContours(img):
    mask = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(mask, np.array([50, 100, 100]), np.array([70, 255, 255]))
    mask = cv2.erode(mask, cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3)))
    mask = cv2.dilate(mask, cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3)))
    
    # cv.findContours(image, mode, method[, contours[, hierarchy[, offset]]]) -> contours, hierarchy
    contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
    cv2.drawContours(img, contours, -1, (0,255,0), 3)
    return img
    
if __name__ == "__main__":
    cv2.namedWindow("Camera")
    vc = cv2.VideoCapture(0)

    rval = True
    if not vc.isOpened():
        rval = False

    try:
        while rval:
            rval, frame = vc.read()
            result = findContours(frame)
            cv2.imshow("Camera", result)
            cv2.waitKey(1)
    except KeyboardInterrupt:
        cv2.destroyAllWindows()
        vc.release()