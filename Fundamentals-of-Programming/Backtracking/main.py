#Se dă o listă de numere întregi a1,...,an. Generați toate sub-secvențele cu proprietatea că suma numerelor este
#divizibila cu N dat.

import copy

'''
Descrierea soluției backtracking:

Rezolvare generare subsecvente cu proprietatea ca suma numerelor este divizibila cu N dat

soluție candidat:
x = (x0, x1,. .. , xk), xi ∈ lista

condiție consistent:
x = (x0, x1,. .. , xk) este consistent dacă  len(x) <= len(lista), unde x este secventa creata, iar lista este lista
initiala de elemente

condiție soluție:
x = (x0, x1,. .. , xk) este soluţie dacă este consistent şi (x0 + x1 + ... + xk) % N (suma elementelor din lista x
poate fi divizibila cu N) si solutia x nu se afla in solutii
'''

def este_solutie(x, N):
    """
    returneaza True sau False daca suma elementelor din lista x poate fi divizibila cu N
    """
    suma = 0
    for element in x:
        suma += element
    return suma % N == 0

def este_consistenta(x, lista):
    """
    verifica daca secventa creata este mai mare (in lungime decat lista insasi)
    este consistent daca lungimea secventei create este mai mare decat lungimea listei initiale
    in acest caz, afiseaza True, altfel returneaza False
    """
    if(len(x) > len(lista)):
        return False
    return True

def subsecventa_recursiv(lista, n, x, poz, solutii):
    """
    Conditie de baza: Verifica daca lungimea listei este egala
    cu pozitia ultimului element, atunci goleste lista
    initiala pentru a putea continua
    """

    for i in range(poz, len(lista)):
        x.append(0)
        x[-1] = lista[i]
        if este_consistenta(x, lista):
            if este_solutie(x, n) and x not in solutii:
                solutie = copy.deepcopy(x)
                solutii.append(solutie)
            subsecventa_recursiv(lista, n, x, i + 1, solutii)
    x.clear()

print("Varianta recursiva:")

lista = [1, 20, 35, 24, 56, 16]
n = 3
x = []
solutii = []
subsecventa_recursiv(lista, n, x, 0, solutii)
print(solutii)

print()
print("Varianta iterativa:")

def subsecventa_iterativ(lista, N):
    """
    Folosesc un for pentru a itera prin toate elementele din lista si pentru a crea subsecvente.
    Primul for itereaza prin elementele din lista si al doilea itereaza prin subsecventele posibile incepand de la
    elementul curent din primul for.
    """
    solutii = []
    for i in range(len(lista)):
        for j in range(i, len(lista)):
            subsecventa = lista[i:j+1]
            if sum(subsecventa) % N == 0 and subsecventa not in solutii:
                solutii.append(subsecventa)
    return solutii

lista = [1, 2, 3, 4, 5, 6]
N = 3
print(subsecventa_iterativ(lista, N))