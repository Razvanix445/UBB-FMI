

def creeaza_tranzactie(id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie):
    '''
    creeaza o tranzactie cu id-ul intreg id_tranzactie, ziua intreg zi_tranzactie, suma float suma_tranzactie, tipul string tip_tranzactie
    :param id_tranzactie: int
    :param zi_tranzactie: int
    :param suma_tranzactie: float
    :param tip_tranzactie: string
    :return: rezultat: tranzactia cu id-ul intreg id_tranzactie, ziua intreg zi_tranzactie, suma float suma_tranzactie, tipul string tip_tranzactie
    '''
    return [id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie]

def get_id_tranzactie(tranzactie):
    '''
    returneaza id-ul intreg al tranzactiei tranzactie
    :param tranzactie: tranzactie
    :return: rezultat: int - id-ul intreg al tranzactiei tranzactie
    '''
    return tranzactie[0]

def get_zi_tranzactie(tranzactie):
    '''
    returneaza ziua intreg a tranzactiei tranzactie
    :param tranzactie: tranzactie
    :return: rezultat: int - ziua intreg a tranzactiei tranzactie
    '''
    return tranzactie[1]

def get_suma_tranzactie(tranzactie):
    '''
    returneaza suma float a tranzactiei tranzactie
    :param tranzactie: tranzactie
    :return: rezultat: float - suma float a tranzactiei tranzactie
    '''
    return tranzactie[2]

def get_tip_tranzactie(tranzactie):
    '''
    returneaza tipul string al tranzactiei tranzactie
    :param tranzactie: tranzactie
    :return: rezultat: string - tipul string al tranzactiei tranzactie
    '''
    return tranzactie[3]

def set_id_tranzactie(tranzactie, id_tranzactie):
    '''
    seteaza id-ul int al tranzactiei tranzactie la id-ul int id_tranzactie
    :param tranzactie: tranzactie
    :param id_tranzactie: int
    :return: - (tranzactia va avea id-ul int setat la id_tranzactie)
    '''
    tranzactie[0] = id_tranzactie

def set_zi_tranzactie(tranzactie, zi_tranzactie):
    '''
    seteaza ziua int a tranzactiei tranzactie la ziua int zi_tranzactie
    :param tranzactie: tranzactie
    :param zi_tranzactie: int
    :return: - (tranzactia va avea ziua int setata la zi_tranzactie)
    '''
    tranzactie[1] = zi_tranzactie

def set_suma_tranzactie(tranzactie, suma_noua):
    '''
    seteaza suma float a tranzactiei tranzactie la suma float suma_noua
    :param tranzactie: tranzactie
    :return: - (tranzactia va avea suma float setata la suma_noua)
    '''
    tranzactie[2] = suma_noua

def set_tip_tranzactie(tranzactie, tip_tranzactie):
    '''
    seteaza tipul string al tranzactiei tranzactie la tipul string tip_tranzactie
    :param tranzactie: tranzactie
    :return: - (tranzactia va avea tipul string setat la tip_tranzactie)
    '''
    tranzactie[3] = tip_tranzactie

def id_egal_tranzactii(tranzactie_1, tranzactie_2):
    '''
    verifica daca tranzactia tranzactie_1 si tranzactia tranzactie_2 sunt egale prin id-ul lor intreg unic
    :param tranzactie1: tranzactie
    :param tranzactie2: tranzactie
    :return: rezultat: bool - True, daca tranzactie_1 si tranzactie_2 au acelasi id unic
                              False, altfel
    '''
    return get_id_tranzactie(tranzactie_1) == get_id_tranzactie(tranzactie_2)

def zi_egal_tranzactii(tranzactie_1, tranzactie_2):
    '''
    verifica daca tranzactia tranzactie_1 si tranzactia tranzactie_2 sunt egale prin zi
    :param tranzactie1: tranzactie
    :param tranzactie2: tranzactie
    :return: rezultat: bool - True, daca tranzactie_1 si tranzactie_2 au aceeasi zi
                              False, altfel
    '''
    return get_zi_tranzactie(tranzactie_1) == get_zi_tranzactie(tranzactie_2)

def tip_egal_tranzactie(tranzactie, tip):
    '''
    verifica daca tranzactia tranzactie are tipul string tip
    :param tranzactie: tranzactie
    :param tip: tranzactie
    :return: rezultat: bool - True, daca tranzactia tranzactie are tipul string tip
                              False, altfel
    '''
    return get_tip_tranzactie(tranzactie) == tip

def to_string_tranzactie(tranzactie):
    '''
    genereaza string-ul pentru afisarea tranzactiei tranzactie
    :param tranzactie: tranzactie
    :return: rezultat: string
    '''
    return f"[{get_id_tranzactie(tranzactie)}] in ziua {get_zi_tranzactie(tranzactie)}, s-a efectuat o tranzactie cu suma de {get_suma_tranzactie(tranzactie)} lei de tipul {get_tip_tranzactie(tranzactie)}"

def adauga_in_stiva(undolist, operatie, tranzactie):
    '''
    adauga in stiva lista respectiva
    :param cont: lista de tranzactii unic identificabile
    :return: -
    '''
    ultima_operatie = {
        "operatie": operatie,
        "tranzactie": tranzactie
    }
    undolist.append(ultima_operatie)