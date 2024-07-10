# 8. Să se genereze toate numerele (în reprezentare binară) cuprinse între 1 și n. De ex. dacă n = 4, numerele sunt: 1, 10, 11, 100.

def pb8():
    n = 4
    sirNumereBinar = []

    # Parcurgem numerele de la 1 la n
    for i in range(1, n + 1):
        p = 0
        reprezentareBinara = 0
        # Convertim numarul i in reprezentare binara
        while i > 0:
            reprezentareBinara = i % 2 * 10 ** p + reprezentareBinara
            p += 1
            i //= 2
        # Adaugam reprezentarea binara la lista de numere binare
        sirNumereBinar.append(reprezentareBinara)

    return print(sirNumereBinar)


def pb8CuBot():
    n = 4
    binary_numbers = []

    # Parcurgem numerele de la 1 la n
    for i in range(1, n + 1):
        binary_repr = ""
        # Convertim numărul i în reprezentare binară manual
        while i > 0:
            binary_repr = str(i % 2) + binary_repr
            i //= 2
        # Adăugăm reprezentarea binară la lista de numere binare
        binary_numbers.append(int(binary_repr))

    return print(binary_numbers)
