from imageai.Classification import ImageClassification
import os

prediction = ImageClassification()
prediction.setModelTypeAsDenseNet121()
prediction.setModelPath("C:\\Users\\Stipe\\Custom Datasets\\DenseNet-BC-121-32.h5")
prediction.loadModel()
predictions, probabilities = prediction.classifyImage("C:\\AIRprojekt\\AIR2025\\WebService\\ImageComparisonPythonService\\fascades\\userImage.jpg", result_count=1)

for eachPrediction, eachProbability in zip(predictions, probabilities):
    print(eachPrediction , " : " , eachProbability)