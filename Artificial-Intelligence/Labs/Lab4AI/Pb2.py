import os
import time

import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from sklearn.metrics import precision_score, recall_score, f1_score, accuracy_score
from Pb1 import pb1

# b. sa se eticheteze (fara ajutorul algoritmilor de AI) aceste imagini cu chenare care sa incadreze
# cat mai exact bicicletele. Care task dureaza mai mult (cel de la punctul a sau cel de la punctul b)?
# c. sa se determine performanta algoritmului de la punctul a avand in vedere etichetarile realizate
# la punctul b (se vor folosi cel putin 2 metrici).

def pb2(computervision_client):
    #display_bounding_boxes('BikeImages-export.csv')
    predicted_labels = pb1(computervision_client)
    actual_labels = get_actual_labels('BikeImages-export.csv')
    print("Predicted labels: ", predicted_labels)
    print("Actual labels: ", actual_labels)
    calculate_metrics(predicted_labels, actual_labels)

def display_bounding_boxes(csv_file):
    start_time = time.time()

    # Citim continutul fisierului CSV
    df = pd.read_csv(csv_file)
    grouped = df.groupby('image')

    # Iteram peste fiecare grup
    for image_name, group in grouped:
        image = plt.imread(image_name)
        plt.imshow(image)

        # Iteram peste fiecare rand din grup
        for _, row in group.iterrows():
            # Extragem coordonatele chenarului si desenam chenarul
            xmin, ymin, xmax, ymax, _ = row[1:]
            rect = patches.Rectangle((xmin, ymin), xmax - xmin, ymax - ymin, linewidth=1, edgecolor='b',
                                     facecolor='none')
            plt.gca().add_patch(rect)

        plt.show()
    end_time = time.time()
    elapsed_time = end_time - start_time
    print(f"Timp manual: {elapsed_time} secunde") # 1.143 secunde


def calculate_metrics(predicted_labels, actual_labels):
    # Calculeaza acuratetea
    accuracy = accuracy_score(actual_labels, predicted_labels)
    print(f'Accuracy: {accuracy}')

    # Calculeaza precizia
    precision = precision_score(actual_labels, predicted_labels)
    print(f'Precision: {precision}')

    # Calculeaza recall
    recall = recall_score(actual_labels, predicted_labels)
    print(f'Recall: {recall}')

    # Calculeaza F1 Score
    f1 = f1_score(actual_labels, predicted_labels)
    print(f'F1 Score: {f1}')

    # Calculate IoU
    iou = calculate_iou(predicted_labels, actual_labels)
    print(f'IoU: {iou}')

def calculate_iou(predicted_boxes, actual_boxes):
    # Calculeaza coordonatele chenarului de intersectie
    x1 = max(predicted_boxes[0], actual_boxes[0])
    y1 = max(predicted_boxes[1], actual_boxes[1])
    x2 = min(predicted_boxes[2], actual_boxes[2])
    y2 = min(predicted_boxes[3], actual_boxes[3])

    # Calculeaza aria de intersectie
    intersection = max(0, x2 - x1 + 1) * max(0, y2 - y1 + 1)

    # Calculeaza aria reuniunii
    predicted_area = (predicted_boxes[2] - predicted_boxes[0] + 1) * (predicted_boxes[3] - predicted_boxes[1] + 1)
    actual_area = (actual_boxes[2] - actual_boxes[0] + 1) * (actual_boxes[3] - actual_boxes[1] + 1)
    union = predicted_area + actual_area - intersection

    # Calculeaza IoU
    iou = intersection / union

    return iou

def get_actual_labels(csv_file):
    df = pd.read_csv(csv_file)
    image_files = os.listdir('bike_images')

    actual_labels = []
    for image_file in image_files:
        if df['image'].str.contains(image_file).any():
            actual_labels.append(1)
        else:
            actual_labels.append(0)

    return actual_labels