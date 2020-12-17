import numpy as np
import cv2
import skimage.filters
from skimage.filters import threshold_local
from skimage.filters import threshold_yen
from skimage.exposure import rescale_intensity
from skimage import measure
import random

img = cv2.imread('fascades/slika14.jpg')

#downscale the image because Canny tends to work better on smaller images
w, h, c = img.shape
resize_coeff = 0.25
img = cv2.resize(img, (int(resize_coeff*h), int(resize_coeff*w)))

#find edges, then contours
canny = cv2.Canny(img, 50, 150)
contours, _ = cv2.findContours(canny, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

#draw the contours, do morphological close operation
#to close possible small gaps, then find contours again on the result
w, h, c = img.shape
blank = np.zeros((w, h)).astype(np.uint8)
cv2.drawContours(blank, contours, -1, 1, 1)
blank = cv2.morphologyEx(blank, cv2.MORPH_CLOSE, np.ones((3, 3), np.uint8))
contours, _ = cv2.findContours(blank, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

#keep only contours of more or less correct area and perimeter
contours = [c for c in contours if 100 < cv2.contourArea(c)]
contours = [c for c in contours if cv2.arcLength(c, True) < 800]
cv2.drawContours(img, contours, -1, (0, 0, 255), 1)

print(len(contours))

cv2.imshow("contours", img)
cv2.waitKey(0)

