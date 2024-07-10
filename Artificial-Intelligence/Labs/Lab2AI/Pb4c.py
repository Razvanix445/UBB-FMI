# Sa se normalizeze informatiile de la problema 1 si 2 folosind diferite metode de normalizare astfel:
#
# problema 1 - salariul, bonusul, echipa
# problema 2 - valorile pixelilor din imagini
# problema 3 - numarul de aparitii a cuvintelor la nivelul unei propozitii.

from matplotlib import pyplot as plt
from nltk import word_tokenize, sent_tokenize
from nltk.probability import FreqDist

def pb4c():
    with open("texts.txt", "r", encoding="utf-8") as f:
        text = f.read()
    normalizarePb3(text)
    f.close()

def normalizarePb3(text):
    # Sa se normalizeze informatiile de la problema 3 astfel:
    # problema 3 - numarul de aparitii a cuvintelor la nivelul unei propozitii.
    propozitii = sent_tokenize(text)
    for propozitie in propozitii:
        print(f'\nPropozitie: {propozitie}')
        cuvinte = word_tokenize(propozitie)
        tokensWithoutPunctuation = [word for word in cuvinte if word.isalnum() and not word.isdigit()]
        frequencyDistance = FreqDist(tokensWithoutPunctuation)
        totalWords = len(tokensWithoutPunctuation)
        listaCuvinte = []
        listaFrecvente = []
        for cuvant, frequency in frequencyDistance.items():
            normalizedFrequency = frequency / totalWords * 100
            listaCuvinte.append(cuvant)
            listaFrecvente.append(normalizedFrequency)
            print(f'{cuvant}: {frequency}/{totalWords} ({round(normalizedFrequency, 2)}%)')

        plt.figure(figsize=(10, 5))
        plt.bar(listaCuvinte, listaFrecvente)
        plt.title(propozitie)
        plt.xlabel('Cuvant')
        plt.ylabel('Frecventa')
        plt.xticks(rotation=90)
        plt.show()