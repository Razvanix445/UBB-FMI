# 10. Considerându-se o matrice cu n x m elemente binare (0 sau 1) sortate crescător pe
# linii, să se identifice indexul liniei care conține cele mai multe elemente de 1.
#
# De ex. în matricea
# [[0,0,0,1,1],
# [0,1,1,1,1],
# [0,0,1,1,1]]
# a doua linie conține cele mai multe elemente 1.

def pb10Eficientizat():
    matrice = [[0, 0, 0, 1, 1], [0, 1, 1, 1, 1], [0, 0, 1, 1, 1]]

    for coloanaIndex in range(len(matrice[0])):
        for linieIndex in range(len(matrice)):
            if matrice[linieIndex][coloanaIndex] == 1:
                return print(linieIndex + 1)

def pb10():
    matrice = [[0, 0, 0, 1, 1], [0, 1, 1, 1, 1], [0, 0, 1, 1, 1]]

    indiceLinieCurenta = 0
    sumaMaxima = 0
    linieMaxima = 0
    for linie in matrice:
        suma = 0
        indiceLinieCurenta += 1
        for numar in linie:
            suma += numar
        if suma > sumaMaxima:
            sumaMaxima = suma
            linieMaxima = indiceLinieCurenta

    return print(linieMaxima)

def pb10CuBot():
    # NU MERGE!!!
    # Probabil nu intelege partea cu listele sortate din matrice.
    matrix = [
        [0, 0, 0, 1, 1],
        [0, 1, 1, 1, 1],
        [0, 0, 1, 1, 1]
    ]

    max_ones = 0
    row_with_max_ones = -1

    for i in range(len(matrix)):
        left, right = 0, len(matrix[i]) - 1
        last_one_index = -1

        while left <= right:
            mid = (left + right) // 2
            if matrix[i][mid] == 1:
                last_one_index = mid
                left = mid + 1
            else:
                right = mid - 1

        if last_one_index != -1:
            count_ones = len(matrix[i]) - last_one_index
            if count_ones > max_ones:
                max_ones = count_ones
                row_with_max_ones = i

    return print(row_with_max_ones)
    # NU MERGE!!!