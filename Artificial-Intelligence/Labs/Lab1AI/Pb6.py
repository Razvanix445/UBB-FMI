# 6. Pentru un șir cu n numere întregi care conține și duplicate, să se determine elementul majoritar
# (care apare de mai mult de n / 2 ori). De ex. 2 este elementul majoritar în șirul [2,8,7,2,2,5,2,3,1,2,2].

def pb6():
    # Initializam sirul de numere si dictionarul de aparitii
    sir = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
    aparitii = {}
    lungimeaSirului = len(sir) / 2

    sir = sorted(sir)
    for i in sir:
        aparitii[i] = aparitii.get(i, 0) + 1
        if (aparitii[i] > lungimeaSirului):
            print(sir[i])


def pb6CuBot():
    sir = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
    majoritar = None
    contor = 0

    # Parcurgem fiecare element din șir
    for element in sir:
        # Dacă contorul este 0, setăm elementul majoritar curent la elementul curent
        if contor == 0:
            majoritar = element
            contor = 1
        # Dacă elementul curent este egal cu elementul majoritar curent, incrementăm contorul
        elif element == majoritar:
            contor += 1
        # Altfel, decrementăm contorul
        else:
            contor -= 1

    return print(majoritar)
