# 4. Să se determine cuvintele unui text care apar exact o singură dată în acel text.
# De ex. cuvintele care apar o singură dată în ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'.

def pb4():
    # Initializam textul
    text = "ana are ana are mere rosii ana"

    # Impartim textul in cuvinte si le sortam in ordine alfabetica
    cuvinteSortate = sorted(text.split())

    # Initializam o lista pentru cuvintele unice
    cuvinteUnice = []

    # Daca textul este format dintr-un cuvant sau din doua cuvinte diferite
    if len(cuvinteSortate) == 1 or (len(cuvinteSortate) == 2 and cuvinteSortate[0] != cuvinteSortate[1]):
        print(cuvinteSortate)
        return

    # Parcurgem cuvintele sortate si verificam daca cuvantul curent este unic
    for i in range(len(cuvinteSortate)):
        # Daca suntem la inceputul listei
        if i == 0 and cuvinteSortate[i] != cuvinteSortate[i + 1]:
            cuvinteUnice.append(cuvinteSortate[i])
        # Daca suntem la finalul listei
        elif i == len(cuvinteSortate) - 1 and cuvinteSortate[i] != cuvinteSortate[i - 1]:
            cuvinteUnice.append(cuvinteSortate[i])
        # Daca cuvantul curent este diferit de cel anterior si de cel urmator
        elif cuvinteSortate[i] != cuvinteSortate[i - 1] and cuvinteSortate[i] != cuvinteSortate[i + 1]:
            cuvinteUnice.append(cuvinteSortate[i])

    print(cuvinteUnice)

def pb4CuBot():
    # Exemplu de utilizare
    text = "ana are ana are mere rosii ana"

    # Împărțim textul în cuvinte
    cuvinte = text.split()

    # Inițializăm un dicționar pentru a număra aparițiile cuvintelor
    frecventa_cuvinte = {}

    # Parcurgem fiecare cuvânt și actualizăm frecvența
    for cuvant in cuvinte:
        frecventa_cuvinte[cuvant] = frecventa_cuvinte.get(cuvant, 0) + 1

    # Returnăm cuvintele care apar o singură dată
    print([cuvant for cuvant, frecventa in frecventa_cuvinte.items() if frecventa == 1])
