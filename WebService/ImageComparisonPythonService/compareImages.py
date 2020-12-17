import cv2
import numpy
import urllib
import json
import re
import ast
import sys, getopt
from urllib.request import urlopen
from colormath.color_objects import sRGBColor, LabColor
from colormath.color_conversions import convert_color
from colormath.color_diff import delta_e_cie2000

def getImage(imageUrl):
    urllib.request.urlretrieve(imageUrl, "C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\fascades\\nova.jpg")
    return "C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\fascades\\nova.jpg"

def get_average_color(image):
    myimg = cv2.imread(image)
    avg_color_per_row = numpy.average(myimg, axis=0)
    avg_color = numpy.average(avg_color_per_row, axis=0)
    return avg_color
    
def get_average_color_base(image):
    myimg = cv2.imread(image)
    avg_color_per_row = numpy.average(myimg, axis=0)
    avg_color = numpy.average(avg_color_per_row, axis=0)
    return avg_color

def compare_colors(rgb1, rgb2):
    # Red Color
    color1_rgb = sRGBColor(rgb1[2], rgb1[1], rgb1[0])
    # Blue Color
    color2_rgb = sRGBColor(rgb2[2], rgb2[1], rgb2[0])
    # Convert from RGB to Lab Color Space
    color1_lab = convert_color(color1_rgb, LabColor)
    # Convert from RGB to Lab Color Space
    color2_lab = convert_color(color2_rgb, LabColor)
    # Find the color difference
    delta_e = delta_e_cie2000(color1_lab, color2_lab)

    return delta_e

def main(baseImg, testImagesUrls):
    imgPath = baseImg
    image = "C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\fascades\\slika1.jpg"
    base_img_average = get_average_color_base(image)

    max_difference = 15

    for num in range(0, len(testImagesUrls)-1):
        imgPath = testImagesUrls[num]
        img_saved = getImage(imgPath)
        average_color = get_average_color(img_saved)
      
        result = compare_colors(base_img_average, average_color)
        if result < max_difference:
            print (testImagesUrls[num])



baseImg = sys.argv[1]
testImagesUrls = sys.argv[2]

main(baseImg, testImagesUrls.split(','))
