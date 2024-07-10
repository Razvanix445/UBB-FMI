from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from azure.ai.vision.imageanalysis.models import VisualFeatures
from matplotlib import pyplot as plt
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from msrest.authentication import CognitiveServicesCredentials
import os
import time

# 1. Sa se foloseasca un algoritm de clasificare a imaginilor (etapa de inferenta/testare) si sa se stabileasca
# performanta acestui algoritm de clasificare binara (imagini cu biciclete vs. imagini fara biciclete).

# 2. Pentru imaginile care contin biciclete:
# a. sa se localizeze automat bicicletele in aceste imagini si sa se evidentieze chenarele care incadreaza bicicletele

def detect_bicycle(image_path, computervision_client):
    with open(image_path, "rb") as img:
        result = computervision_client.analyze_image_in_stream(img, visual_features=[VisualFeatureTypes.objects])
        image = plt.imread(image_path)
        plt.imshow(image)
        for ob in result.objects:
            if ob.object_property == "bicycle":
                # Extragem coordonatele bounding box-ului
                x, y, w, h = ob.rectangle.x, ob.rectangle.y, ob.rectangle.w, ob.rectangle.h

                # Desenam bounding box-ul
                plt.gca().add_patch(plt.Rectangle((x, y), w, h, fill=False, edgecolor='red', linewidth=2))

                # Afisam imaginea cu bounding box-ul
                plt.show()
                return True, ob.rectangle
        return False, None

def pb1(computervision_client):
    start_time = time.time()

    images_folder = "bike_images"
    bicycles = []
    for image_name in os.listdir(images_folder):
        image_path = os.path.join(images_folder, image_name)
        has_bicycle, bicycle_location = detect_bicycle(image_path, computervision_client)
        bicycles.append((image_name, has_bicycle, bicycle_location))

    end_time = time.time()
    elapsed_time = end_time - start_time
    print(f"Timp automat: {elapsed_time} secunde") # 17.95 secunde

    # Calculam performanta algoritmului de clasificare binara
    # Folosim un set de date groundTruth cu imagini si un numar binar pentru fiecare imagine (1 daca imaginea contine o bicicleta, 0 altfel)
    ground_truth = {
        "bike1.jpg": 1,
        "bike02.jpg": 1,
        "bike03.jpg": 1,
        "bike04.jpg": 1,
        "bike05.jpg": 1,
        "bike06.jpg": 1,
        "bike07.jpg": 1,
        "bike08.jpg": 1,
        "bike09.jpg": 1,
        "bike10.jpg": 1,
        "traffic01.jpg": 0,
        "traffic02.jpg": 0,
        "traffic03.jpg": 0,
        "traffic04.jpg": 0,
        "traffic05.jpg": 0,
        "traffic06.jpg": 0,
        "traffic07.jpg": 0,
        "traffic08.jpg": 0,
        "traffic09.jpg": 0,
        "traffic10.jpg": 0
    }
    test_labels = [ground_truth[image_name] for image_name, _, _ in bicycles]
    predicted_labels = [1 if has_bicycle else 0 for _, has_bicycle, _ in bicycles]

    accuracy = accuracy_score(test_labels, predicted_labels)     # no. of correct predictions / no. of total predictions
    precision = precision_score(test_labels, predicted_labels)   # no. of true positives / no. of true positives and false positives
    recall = recall_score(test_labels, predicted_labels)         # no. of true positives / no. of true positives and false neagtives
    f1 = f1_score(test_labels, predicted_labels)                 # 2 * (precision * recall) / (precision + recall) - media armonica a precision si recall

    print(f"Accuracy: {accuracy}")
    print(f"Precision: {precision}")
    print(f"Recall: {recall}")
    print(f"F1 Score: {f1}")
    return predicted_labels