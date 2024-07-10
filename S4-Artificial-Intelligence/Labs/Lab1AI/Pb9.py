# 9. Considerându-se o matrice cu n x m elemente întregi și o listă cu perechi formate din coordonatele a 2 căsuțe din
# matrice ((p,q) și (r,s)), să se calculeze suma elementelor din sub-matricile identificate de fieare pereche.
#
# De ex, pt matricea
# [[0, 2, 5, 4, 1],
# [4, 8, 2, 3, 7],
# [6, 3, 4, 6, 2],
# [7, 3, 1, 8, 3],
# [1, 5, 7, 9, 4]]
# și lista de perechi ((1, 1) și (3, 3)), ((2, 2) și (4, 4)), suma elementelor din prima sub-matrice este 38,
# iar suma elementelor din a 2-a sub-matrice este 44.

def pb9():
    matrice = [[0, 2, 5, 4, 1], [4, 8, 2, 3, 7], [6, 3, 4, 6, 2], [7, 3, 1, 8, 3], [1, 5, 7, 9, 4]]
    listaPerechi = [((1, 1), (3, 3)), ((2, 2), (4, 4))]
    sume = []

    for pereche in listaPerechi:
        (p, q), (r, s) = pereche
        suma = 0
        for i in range(p, r + 1):
            for j in range(q, s + 1):
                suma += matrice[i][j]
        sume.append(suma)

    return print(sume)

def pb9CuBot():
    matrice = [
        [0, 2, 5, 4, 1],
        [4, 8, 2, 3, 7],
        [6, 3, 4, 6, 2],
        [7, 3, 1, 8, 3],
        [1, 5, 7, 9, 4]
    ]
    liste_de_perechi = [((1, 1), (3, 3)), ((2, 2), (4, 4))]

    # Construim o matrice auxiliara pentru pre-sume
    n = len(matrice)
    m = len(matrice[0])
    pre_sum = [[0] * (m + 1) for _ in range(n + 1)]

    # Calculam pre-sumele pentru fiecare căsuță
    for i in range(1, n + 1):
        for j in range(1, m + 1):
            pre_sum[i][j] = pre_sum[i - 1][j] + pre_sum[i][j - 1] - pre_sum[i - 1][j - 1] + matrice[i - 1][j - 1]

    sume_submatrici = []

    # Calculam suma pentru fiecare pereche de căsuțe
    for pereche in liste_de_perechi:
        (p, q), (r, s) = pereche
        suma_submatrice = pre_sum[r + 1][s + 1] - pre_sum[p][s + 1] - pre_sum[r + 1][q] + pre_sum[p][q]
        sume_submatrici.append(suma_submatrice)

    return print(sume_submatrici)
