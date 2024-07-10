# 3. Să se determine produsul scalar a doi vectori rari care conțin numere reale.
# Un vector este rar atunci când conține multe elemente nule. Vectorii pot avea oricâte dimensiuni.
# De ex. produsul scalar a 2 vectori unidimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.
import numpy as np


def pb3():
    # Formam vectorii folosind biblioteca NumPy
    v = np.array([1, 0, 2, 0, 3], float)
    w = np.array([1, 2, 0, 3, 1], float)

    # Calculam produsul scalar tot folosind NumPy
    produsScalar = np.dot(v, w)
    print(produsScalar)

def pb3CuBot():
    vector1 = [1, 0, 2, 0, 3]
    vector2 = [1, 2, 0, 3, 1]

    # Asigură că vectorii au aceeași lungime
    if len(vector1) != len(vector2):
        raise ValueError("Vectorii trebuie să aibă aceeași lungime.")

    # Inițializăm suma produselor
    suma_produselor = 0

    # Parcurgem elementele vectorilor și calculăm suma produselor
    for i in range(len(vector1)):
        suma_produselor += vector1[i] * vector2[i]

    print(suma_produselor)