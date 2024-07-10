from domeniu.tranzactie import creeaza_tranzactie, get_zi_tranzactie, adauga_in_stiva
from infrastructura.repository_tranzactii import adauga_tranzactie_cont, numar_tranzactii_cont, modifica_tranzactie, \
    get_all_tranzactii_cont, sterge_tranzactie_dupa_perioada, sterge_tranzactie_dupa_tip, \
    elimina_dupa_tip, elimina_mai_mici_suma_dupa_tip, tipareste_sume_mari, tipareste_suma_mari_zile_mici, \
    tipareste_dupa_tip, sterge_tranzactie_dupa_zi
from validare.validator_tranzactie import valideaza_tranzactie


#1.
def adauga_tranzactie_service(cont, id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie, undolist):
    '''
    pe baza id-ului intreg id_tranzactie, a zilei intreg zi_tranzactie, a sumei float suma si a tipului string
    tip_tranzactie, va crea o tranzactie, va incerca sa o valideze si daca este valida, va incerca sa o adauge in lista
    cont de tranzactii unic identificabile prin id-ul lor intreg daca nu exista deja o tranzactie cu acelasi id in lista
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param id_tranzactie: int
    :param zi_tranzactie: int
    :param suma_tranzactie: float
    :param tip_tranzactie: string
    :return: - (daca tranzactia este valida si nu exista deja o alta tranzactie cu acelasi id in lista cont)
    :raises: ValueError - daca id-ul int al tranzactiei este <0, se concateneaza mesajul string "id invalid\n" la codul de eroare
                        - daca ziua int a tranzactiei este <=0 sau >=31, se concateneaza mesajul string "zi invalida!\n" la codul de eroare
                        - daca suma float a tranzactiei este <=0.0, se concateneaza mesajul string "suma invalida!\n" la codul de eroare
                        - daca tipul string al tranzactiei este diferit de "in" sau "out", se concateneaza mesajul string "tip invalid!\n" la codul de eroare
                        - daca cel putin unul dintre atributele tranzactiei este invalid, se arunca exceptie de tipul ValueError cu mesajul codului de eroare
                        - daca exista o tranzactie cu id-ul id_tranzactie in lista cont, atunci arunca exceptie de tipul ValueError cu mesajul string "tranzactie invalida!\n"
    '''
    tranzactie = creeaza_tranzactie(id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie)
    valideaza_tranzactie(tranzactie)
    adauga_tranzactie_cont(cont, tranzactie, undolist)

def numar_tranzactii_service(cont):
    '''
    returneaza numarul de tranzactii din contul lista de tranzactii unic identificabile prin id-ul lor intreg
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :return: rezultat: int - numarul de tranzactii din cont
    '''
    return numar_tranzactii_cont(cont)

def modifica_tranzactie_service(cont, id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie, undolist):
    '''
    incearca sa modifice tranzactia tranzactie in contul lista de tranzactii unic identificabile prin id-ul intreg
    daca exista o tranzactie cu acelasi id
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param id_tranzactie: int
    :param zi_tranzactie: int
    :param suma_tranzactie: float
    :param tip_tranzactie: string
    :return: - (cont = cont U {tranzactie} daca exista tranzactie cu acelasi id in contul lista)
    :raises: ValueError: arunca exceptie de tipul ValueError cu mesajul string "Tranzactie inexistenta!\n"
    :return:
    '''
    tranzactie = creeaza_tranzactie(id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie)
    valideaza_tranzactie(tranzactie)
    modifica_tranzactie(cont, tranzactie, undolist)

#2.
def sterge_tranzactie_dupa_zi_service(cont, ziua_cautata, undolist):
    sterge_tranzactie_dupa_zi(cont, ziua_cautata, undolist)

def sterge_tranzactie_dupa_perioada_service(cont, zi_inceput, zi_sfarsit, undolist):
    sterge_tranzactie_dupa_perioada(cont, zi_inceput, zi_sfarsit, undolist)

def sterge_tranzactie_dupa_tip_service(cont, tip_de_sters, undolist):
    sterge_tranzactie_dupa_tip(cont, tip_de_sters, undolist)

#3.
def tipareste_sume_mari_service(cont, suma_mare):
    tipareste_sume_mari(cont, suma_mare)

def tipareste_suma_mari_zile_mici_service(cont, zi_mica, suma_mare):
    tipareste_suma_mari_zile_mici(cont, zi_mica, suma_mare)

def tipareste_dupa_tip_service(cont, acelasi_tip):
    tipareste_dupa_tip(cont, acelasi_tip)

#4.
def elimina_dupa_tip_service(cont, tip):
    elimina_dupa_tip(cont, tip)

def elimina_mai_mici_suma_dupa_tip_service(cont, suma_mare, tip):
    elimina_mai_mici_suma_dupa_tip(cont, suma_mare, tip)

def get_all_tranzactii_service(cont):
    '''
    returneaza lista tuturor tranzactiilor
    :param cont: lista de tranzactii
    :return: rezultat: lista de tranzactii
    '''
    return get_all_tranzactii_cont(cont)

#7.
def sterge_tranzactie(cont, tranzactie):
    cont.remove(tranzactie)

def undo(cont, undolist):
    '''
    face undo ultimei modificari a listei
    :param cont: lista de tranzactii
    :return: - (undo va intoarce programul la ultima interactiune cu lista)
    '''
    if len(undolist) == 0:
        raise(ValueError("Nu s-au efectuat tranzactii in cont!"))
    ultima_operatie = undolist.pop()
    operatie = ultima_operatie["operatie"]
    tranzactie = ultima_operatie["tranzactie"]
    if operatie == "adaugare":
        sterge_tranzactie(cont, tranzactie)
    if operatie == "stergere":
        adauga_tranzactie_cont(cont, tranzactie, undolist)