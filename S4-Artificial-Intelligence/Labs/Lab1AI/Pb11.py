# 11. Considerându-se o matrice cu n x m elemente binare (0 sau 1), să se înlocuiască cu 1 toate aparițiile
# elementelor egale cu 0 care sunt complet înconjurate de 1.
#
# De ex. matricea:
# [[1,1,1,1,0,0,1,1,0,1],
# [1,0,0,1,1,0,1,1,1,1],
# [1,0,0,1,1,1,1,1,1,1],
# [1,1,1,1,0,0,1,1,0,1],
# [1,0,0,1,1,0,1,1,0,0],
# [1,1,0,1,1,0,0,1,0,1],
# [1,1,1,0,1,0,1,0,0,1],
# [1,1,1,0,1,1,1,1,1,1]]
# *devine *
# [[1,1,1,1,0,0,1,1,0,1],
# [1,1,1,1,1,0,1,1,1,1],
# [1,1,1,1,1,1,1,1,1,1],
# [1,1,1,1,1,1,1,1,0,1],
# [1,1,1,1,1,1,1,1,0,0],
# [1,1,1,1,1,1,1,1,0,1],
# [1,1,1,0,1,1,1,0,0,1],
# [1,1,1,0,1,1,1,1,1,1]].

def pb11():
    matrice = [
        [1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
        [1, 0, 0, 1, 1, 0, 1, 1, 1, 1],
        [1, 0, 0, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
        [1, 0, 0, 1, 1, 0, 1, 1, 0, 0],
        [1, 1, 0, 1, 1, 0, 0, 1, 0, 1],
        [1, 1, 1, 0, 1, 0, 1, 0, 0, 1],
        [1, 1, 1, 0, 1, 1, 1, 1, 1, 1]
    ]

    # Parcurgem cadranul matricei si cautam elemente de 0. Daca gasim, marcam pozitia cu 2 si verificam daca are vecini care au tot valoarea 0.
    # Daca se gasesc vecini cu valoarea 0, se vor marca si acestia.
    for i in range(len(matrice)):
        for j in range(len(matrice[0])):
            # Verificam doar elementele cu valoarea 0
            if matrice[i][j] == 0:
                # Verificam daca elementul este pe margine
                if i == 0 or j == 0 or i == len(matrice) - 1 or j == len(matrice[0]) - 1:
                    inlocuireVecini(matrice, i, j)

    # Inlocuim toate elementele marcate cu 0
    for i in range(len(matrice)):
        for j in range(len(matrice[0])):
            if matrice[i][j] == 2:
                matrice[i][j] = 0
            else:
                matrice[i][j] = 1

    for linie in matrice:
        print(linie)

def inlocuireVecini(matrice, i, j):
    # Marcam elementul curent si ii verificam vecinii
    if matrice[i][j] == 0:
        matrice[i][j] = 2
        # Definim o lista de pozitii care duc la noi linii si coloane (vecinii pozitiei curente)
        directii = [(0, 1), (1, 0), (0, -1), (-1, 0)]
        for directieLinie, directieColoana in directii:
            linieNoua, coloanaNoua = i + directieLinie, j + directieColoana
            # Daca pozitia este una valida (in interiorul matricei) si elementul de pe pozitia noua are valoarea 0, se va modifica in 2.
            if 0 <= linieNoua < len(matrice) and 0 <= coloanaNoua < len(matrice[0]) and matrice[linieNoua][coloanaNoua] == 0:
                inlocuireVecini(matrice, linieNoua, coloanaNoua)

def pb11CuBot():
    #NU MERGE!!!
    matrix = [
        [1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
        [1, 0, 0, 1, 1, 0, 1, 1, 1, 1],
        [1, 0, 0, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 0, 0, 1, 1, 0, 1],
        [1, 0, 0, 1, 1, 0, 1, 1, 0, 0],
        [1, 1, 0, 1, 1, 0, 0, 1, 0, 1],
        [1, 1, 1, 0, 1, 0, 1, 0, 0, 1],
        [1, 1, 1, 0, 1, 1, 1, 1, 1, 1]
    ]

    rows = len(matrix)
    cols = len(matrix[0])

    # Parcurgem toate celulele matricei
    for i in range(rows):
        for j in range(cols):
            # Dacă găsim un zero înconjurat de 1, îl înlocuim cu 1
            if is_surrounded_by_ones(matrix, i, j):
                matrix[i][j] = 1

    for row in matrix:
        print(row)

def is_surrounded_by_ones(matrix, i, j):
    rows = len(matrix)
    cols = len(matrix[0])

    # Verificăm dacă celula este în interiorul matricei și este 0
    if i > 0 and i < rows - 1 and j > 0 and j < cols - 1 and matrix[i][j] == 0:
        # Verificăm dacă toate celulele adiacente sunt 1
        return all(matrix[i + di][j + dj] == 1 for di, dj in [(-1, 0), (1, 0), (0, -1), (0, 1)])
    return False
    #NU MERGE!!!