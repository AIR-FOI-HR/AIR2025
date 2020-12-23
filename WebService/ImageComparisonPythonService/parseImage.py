from imageai.Prediction import ImagePrediction
import os

IMAGEAI_MODELS_HOME = "C:\\AIRprojekt\\AIR2025\WebService\\ImageComparisonPythonService\\resnet50_coco_best_v2.0.1.h5"
IMAGEAI_MODEL = "C:\\AIRprojekt\\AIR2025\WebService\\ImageComparisonPythonService\\resnet50_weights_tf_dim_ordering_tf_kernels.h5"
MODEL_PATH = os.path.join(IMAGEAI_MODELS_HOME, IMAGEAI_MODEL)
assert os.path.exists(MODEL_PATH)
IMAGE_PATH = "fascades/slika10.jpg"  # full path to sample image
# here are the lines to instantiate the predictor
predictor = ImagePrediction()
predictor.setModelTypeAsResNet()     # -- 1
predictor.setModelPath(MODEL_PATH)   # -- 2
predictor.loadModel()
# get top 10 predictions
prediction, probabilities = predictor.predictImage(
           IMAGE_PATH, result_count=10)

counter = 0
for p in prediction:
    print(f"{p}: {probabilities[counter]}")
    counter += 1