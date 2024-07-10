# Sa se normalizeze informatiile de la problema 1 si 2 folosind diferite metode de normalizare astfel:
#
# problema 1 - salariul, bonusul, echipa
# problema 2 - valorile pixelilor din imagini
# problema 3 - numarul de aparitii a cuvintelor la nivelul unei propozitii.

import cv2
import os

import numpy as np
from matplotlib import pyplot as plt


def pb4b():
    # Sa se normalizeze informatiile de la problema 2 astfel:
    # problema 2 - valorile pixelilor din imagini
    for image in os.listdir('images'):
        imgPath = os.path.join('images', image)
        img = cv2.imread(imgPath, cv2.IMREAD_GRAYSCALE)
        normalizedImg = cv2.normalize(img, None, alpha=0, beta=1, norm_type=cv2.NORM_MINMAX, dtype=cv2.CV_32F)

        pixelValues = normalizedImg.flatten()
        frequencies, bins = np.histogram(pixelValues, bins=np.linspace(0, 1, 257))

        plt.figure(figsize=(10, 5))
        plt.bar(bins[:-1], frequencies, width=0.8, color='blue')
        plt.title(f'Normalized Pixel Value Frequencies for {image}')
        plt.xlabel('Pixel Value')
        plt.ylabel('Frequency')
        plt.show()