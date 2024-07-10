from domeniu.tranzactie import id_egal_tranzactii, get_zi_tranzactie, get_tip_tranzactie, get_suma_tranzactie, \
    tip_egal_tranzactie, to_string_tranzactie, adauga_in_stiva


#1.
def adauga_tranzactie_cont(cont, tranzactie, undolist):
    '''
    incearca sa adauge tranzactia tranzactie in contul lista  de tranzactii unic identificabile prin id-ul intreg
    daca nu exista deja o tranzactie cu acelasi id
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param tranzactie: tranzactie
    :return: - (cont' = cont U {tranzactie} daca nu exista tranzactie cu acelasi id in contul lista)
    :raises: ValueError: arunca exceptie de tipul ValueError cu mesajul string "Tranzactie invalida!\n"
    '''
    ok = 0
    for alta_tranzactie in cont:
        if id_egal_tranzactii(alta_tranzactie, tranzactie):
            raise ValueError("tranzactie invalida!\n")
            ok = 1
    if ok == 0:
        cont.append(tranzactie)
    adauga_in_stiva(undolist, "adaugare", tranzactie)

def modifica_tranzactie(cont, tranzactie, undolist):
    '''
    incearca sa modifice tranzactia tranzactie in contul lista de tranzactii unic identificabile prin id-ul intreg
    daca exista o tranzactie cu acelasi id
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param tranzactie: tranzactie
    :return: - (cont = cont U {tranzactie} daca exista tranzactie cu acelasi id in contul lista)
    :raises: ValueError: arunca exceptie de tipul ValueError cu mesajul string "Tranzactie inexistenta!\n"
    '''
    ok = 0
    for tranzactie_din_cont in cont:
        if id_egal_tranzactii(tranzactie_din_cont, tranzactie):
            tranzactie_din_cont[1] = tranzactie[1]
            tranzactie_din_cont[2] = tranzactie[2]
            tranzactie_din_cont[3] = tranzactie[3]
            ok = 1
    if ok == 0:
        raise ValueError("tranzactie inexistenta!\n")


#2.
def cauta_zi(cont, ziua_cautata):
    '''
    cauta in cont o tranzactie care a fost efectuata in ziua ziua_cautata
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param ziua_cautata: int
    :return: boolean: True, daca s-a gasit cel putin o tranzactie efectuata in ziua ziua_cautata
                      False, altfel
    '''
    for i in range(0, len(cont)):
        if get_zi_tranzactie(cont[i]) == ziua_cautata:
            return True
    return False

def sterge_tranzactie_dupa_zi(cont, ziua_cautata, undolist):
    '''
    incearca sa stearga toate tranzactiile din contul lista de tranzactii unic identificabile care au fost efectuate in aceeasi zi
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :return: - (cont' = cont / {tranzactie} daca tranzactia are o zi specificata)
    :raises: ValueError: arunca exceptie de tipul ValueError cu mesajul string "Introduceti un numar!\n"
    '''
    for tranzactie in cont:
        if get_zi_tranzactie(tranzactie) == ziua_cautata:
            cont.remove(tranzactie)
            adauga_in_stiva(undolist, "stergere", tranzactie)

def sterge_tranzactie_dupa_perioada(cont, zi_inceput, zi_sfarsit, undolist):
    '''
    incearca sa stearga toate tranzactiile din contul lista de tranzactii unic identificabile care au fost efectuate intr-o perioada de timp
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param zi_inceput: ziua in care incepe perioada de timp
    :param zi_sfarsit: ziua in care se sfarseste perioada de timp
    :return: - (cont' = cont / {tranzactie} daca tranzactia s-a efectuat in perioada delimitata de zi_inceput si zi_sfarsit)
    :raises: ValueError: arunca exceptie de tipul ValueError cu mesajul string "Introduceti un numar!\n"
    '''
    for tranzactie in cont:
        if get_zi_tranzactie(tranzactie) < zi_inceput or get_zi_tranzactie(tranzactie) > zi_sfarsit:
            cont.remove(tranzactie)
            adauga_in_stiva(undolist, "stergere", tranzactie)

def cauta_tip(cont, tip_de_sters):
    '''
        cauta in cont o tranzactie de un anumit tip
        :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
        :param tip_de_sters: string
        :return: boolean: True, daca s-a gasit cel putin o tranzactie de tipul tip_de_sters
                          False, altfel
        '''
    for i in range(0, len(cont)):
        if get_tip_tranzactie(cont[i]) == tip_de_sters:
            return True
    return False

def sterge_tranzactie_dupa_tip(cont, tip_de_sters, undolist):
    '''
    incearca sa stearga toate tranzactiile din contul lista de tranzactii unic identificabile de un anumit tip
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :param tip_de_sters: string - tipul care se cauta pentru stergere
    :return: - (cont' = cont / {tranzactie} daca tranzactia are tipul specificat)
    :raises: ValueError: arunca exceptie de tipul ValueError cu mesajul string "Introduceti un numar!\n"
    '''
    for tranzactie in cont:
        if get_tip_tranzactie(tranzactie) != tip_de_sters:
            cont.remove(tranzactie)
            adauga_in_stiva(undolist, "stergere", tranzactie)

#3.
def tipareste_sume_mari(cont, suma_mare):
    lista = []
    for i in range(0, len(cont)):
        if get_suma_tranzactie(cont[i]) > suma_mare:
            lista.append(to_string_tranzactie(cont[i]))
    return lista

def tipareste_suma_mari_zile_mici(cont, zi_mica, suma_mare):
    lista = []
    for i in range(0, len(cont)):
        if get_zi_tranzactie(cont[i]) < zi_mica and get_suma_tranzactie(cont[i]) > suma_mare:
            lista.append(to_string_tranzactie(cont[i]))
    return lista

def tipareste_dupa_tip(cont, acelasi_tip):
    lista = []
    for i in range(0, len(cont)):
        if get_tip_tranzactie(cont[i]) == acelasi_tip:
            lista.append(to_string_tranzactie(cont[i]))
    return lista

#5.
def elimina_dupa_tip(cont, tip):
    '''
    elimina toate tranzactiile de un anumit tip
    :param cont: lista de tranzactii
    :param tip: string - tipul cautat pentru eliminare tranzactii
    :return: - (cont' = cont / {tranzactie} pentru fiecare tranzactie gasita de tipul specificat)
    '''
    lista_provizorie = []
    for i in range(0, len(cont)):
        if get_tip_tranzactie(cont[i]) != tip:
            lista_provizorie.append(cont[i])
    cont[:] = lista_provizorie

def elimina_mai_mici_suma_dupa_tip(cont, suma_mare, tip):
    '''
    elimina toate tranzactiile de un anumit tip care sunt mai mici decat o suma suma_mare
    :param cont: lista de tranzactii
    :param suma_mare: float
    :param tip: string
    :return: - (cont' = cont / {tranzactie} pentru fiecare tranzactie gasita de tipul specificat cu suma mai mica decat suma_mare)
    '''
    lista_provizorie = []
    for i in range(0, len(cont)):
        if get_suma_tranzactie(cont[i]) >= suma_mare or not tip_egal_tranzactie(cont[i], tip):
            lista_provizorie.append(cont[i])
    cont[:] = lista_provizorie

def numar_tranzactii_cont(cont):
    '''
    returneaza numarul de tranzactii din contul lista de tranzactii unic identificabile prin id-ul lor intreg
    :param cont: lista de tranzactii unic identificabile prin id-ul lor intreg
    :return: rezultat: int - numarul de tranzactii din cont
    '''
    return len(cont)

def get_all_tranzactii_cont(cont):
    '''
    returneaza lista tuturor tranzactiilor
    :param cont: lista de tranzactii
    :return: rezultat: lista de tranzactii
    '''
    return cont[:]