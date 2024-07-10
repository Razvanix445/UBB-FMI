# 5. Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1} astfel încât o singură valoare se
# repetă de două ori, să se identifice acea valoare care se repetă. De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori.

def pb5():
    # Initializam sirul de numere
    sir = [1, 2, 3, 4, 2]

    # Sortam sirul si cautam elementul care este egal cu elementul de pe pozitia urmatoare
    sir = sorted(sir)
    for i in range(len(sir)):
        if sir[i] == sir[i + 1]:
            return print(sir[i])


def pb5CuBot():
    # Exemplu de utilizare
    șir = [1, 2, 3, 4, 2]

    # Inițializăm un set pentru a urmări valorile
    valori_văzute = set()

    # Parcurgem fiecare element din șir
    for valoare in șir:
        # Dacă valoarea este deja în set, atunci aceasta este valoarea repetată
        if valoare in valori_văzute:
            return print(valoare)
        # Altfel, adăugăm valoarea în set
        else:
            valori_văzute.add(valoare)