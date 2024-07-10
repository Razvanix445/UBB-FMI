# Se dau mai multe imagini (salvate in folder-ul "data/images"). Se cere:
#
# sa se vizualizeze una din imagini
# daca imaginile nu aceeasi dimensiune, sa se redimensioneze toate la 128 x 128 pixeli si sa se vizualizeze imaginile intr-un cadru tabelar.
# sa se transforme imaginile in format gray-levels si sa se vizualizeze
# sa se blureze o imagine si sa se afiseze in format "before-after"
# sa se identifice muchiile intr-o imagine si sa se afiseze in format "before-after"

import pandas as pd
from PIL import Image, ImageFilter
import os
import matplotlib.pyplot as plt

def pb2():
    #pb2a()
    #pb2b()
    #pb2c()
    #pb2d()
    pb2e()

def pb2a():
    # sa se vizualizeze una din imagini
    caleFisier = ("images")
    listaImagini = os.listdir(caleFisier)
    caleImagine = os.path.join(caleFisier, listaImagini[2])
    image = Image.open(caleImagine)
    image.show()


def pb2b():
    # daca imaginile nu au aceeasi dimensiune, sa se redimensioneze toate la 128 x 128 pixeli si sa se vizualizeze imaginile intr-un cadru tabelar.
    dimensiune = (128, 128)
    caleFisier = ("images")
    listaImagini = os.listdir(caleFisier)
    imaginiRedimensionate = []

    for imagine in listaImagini:
        caleImagine = os.path.join(caleFisier, imagine)
        image = Image.open(caleImagine)
        image = image.resize(dimensiune)
        imaginiRedimensionate.append(image)

    nrImagini = len(imaginiRedimensionate)
    nrColoane = 2
    nrRanduri = nrImagini // nrColoane

    tabel = pd.DataFrame(columns=[f"Imagine {i+1}" for i in range(nrColoane)])

    fig, ax = plt.subplots(nrRanduri, nrColoane, figsize=(8, 8))
    for i, imagine in enumerate(imaginiRedimensionate):
        ax[i // nrColoane, i % nrColoane].imshow(imagine)
    plt.show()

def pb2c():
    # sa se transforme imaginile in format gray-levels si sa se vizualizeze
    caleFisier = ("images")
    listaImagini = os.listdir(caleFisier)
    imaginiGray = []

    for imagine in listaImagini:
        caleImagine = os.path.join(caleFisier, imagine)
        image = Image.open(caleImagine)
        image = image.convert("L")
        imaginiGray.append(image)

    nrImagini = len(imaginiGray)
    nrColoane = 2
    nrRanduri = nrImagini // nrColoane

    tabel = pd.DataFrame(columns=[f"Imagine {i+1}" for i in range(nrColoane)])

    fig, ax = plt.subplots(nrRanduri, nrColoane, figsize=(8, 8))
    for i, imagine in enumerate(imaginiGray):
        ax[i // nrColoane, i % nrColoane].imshow(imagine, cmap="gray")
    plt.show()

def pb2d():
    # sa se blureze o imagine si sa se afiseze in format "before-after"
    caleFisier = ("images")
    listaImagini = os.listdir(caleFisier)
    imagine = Image.open(os.path.join(caleFisier, listaImagini[5]))
    blurIntensity = 50
    imagineBlured = imagine.filter(ImageFilter.GaussianBlur(blurIntensity))
    fig, ax = plt.subplots(1, 2, figsize=(8, 8))
    ax[0].imshow(imagine)
    ax[0].set_title("Imagine originala")
    ax[1].imshow(imagineBlured)
    ax[1].set_title("Imagine blurata")
    plt.show()

def pb2e():
    # sa se identifice muchiile intr-o imagine si sa se afiseze in format "before-after"
    caleFisier = ("images")
    listaImagini = os.listdir(caleFisier)
    imagine = Image.open(os.path.join(caleFisier, listaImagini[5]))
    imagineEdges = imagine.filter(ImageFilter.FIND_EDGES)
    fig, ax = plt.subplots(1, 2, figsize=(8, 8))
    ax[0].imshow(imagine)
    ax[0].set_title("Imagine originala")
    ax[1].imshow(imagineEdges)
    ax[1].set_title("Imagine cu muchii")
    plt.show()