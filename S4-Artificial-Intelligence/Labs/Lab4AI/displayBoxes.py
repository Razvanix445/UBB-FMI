import pandas as pd
import os

from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from matplotlib import pyplot as plt, patches


def display_bounding_boxes(image_dir, csv_file, computervision_client):
    df = pd.read_csv(csv_file)

    for image_name in os.listdir(image_dir):
        image_path = os.path.join(image_dir, image_name)

        with open(image_path, "rb") as img:
            result = computervision_client.analyze_image_in_stream(img, visual_features=[VisualFeatureTypes.objects])
            image = plt.imread(image_path)
            plt.imshow(image)

            # Desenam chenarele detectate de AI in rosu
            for ob in result.objects:
                if ob.object_property == "bicycle":
                    x, y, w, h = ob.rectangle.x, ob.rectangle.y, ob.rectangle.w, ob.rectangle.h
                    plt.gca().add_patch(patches.Rectangle((x, y), w, h, fill=False, edgecolor='red', linewidth=2))

            # Desenam chenarele din fisierul CSV in albastru
            if image_name in df['image'].values:
                for _, row in df[df['image'] == image_name].iterrows():
                    xmin, ymin, xmax, ymax = row[1:5]
                    plt.gca().add_patch(patches.Rectangle((xmin, ymin), xmax - xmin, ymax - ymin, fill=False, edgecolor='blue', linewidth=2))

            plt.show()