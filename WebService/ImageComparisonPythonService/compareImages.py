import cv2
import numpy
import urllib
import json
import re
import ast
import sys, getopt
import os
from urllib.request import urlopen
from colormath.color_objects import sRGBColor, LabColor
from colormath.color_conversions import convert_color
from colormath.color_diff import delta_e_cie2000
from imageai.Classification import ImageClassification

def classify_image(image):
    prediction = ImageClassification()
    prediction.setModelTypeAsDenseNet121()
    prediction.setModelPath("C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\DenseNet-BC-121-32.h5")
    prediction.loadModel()
    predictions, probabilities = prediction.classifyImage(image, result_count=1)
    return predictions[0]

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

image = "C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\userImage.jpg"

base_img_average = get_average_color_base(image)
product_type = 'roof' if classify_image(image) == 'tile_roof' else 'facade';
product_extension = '/product-texture' if product_type == 'facade' else '/single-product'
country = 'germany' if product_type == 'facade' else 'croatia'
api_path = "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/" + country + "/marketing/photography/productshots/" + product_type + product_extension
images_path = "C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\" + product_type;
max_difference = 15
for image_path in os.listdir(images_path):
    input_path = os.path.join(images_path, image_path)
    average_color = get_average_color(input_path)
    
    result = compare_colors(base_img_average, average_color)
    if result < max_difference:
        print (api_path + '/' + image_path)
