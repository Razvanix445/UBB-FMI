from domeniu.tranzactie import get_id_tranzactie, get_zi_tranzactie, get_tip_tranzactie, get_suma_tranzactie


def valideaza_tranzactie(tranzactie):
    '''
    verifica daca tranzactia are id-ul int >=0, ziua int >0 si <=31, suma float >0.0 si tipul "in" sau "out"
    :param tranzactie: tranzactie
    :return: - (tranzactia este valida)
    :raises: ValueError - daca id-ul int al tranzactiei este <0, se concateneaza mesajul string "id invalid\n" la codul de eroare
                        - daca ziua int a tranzactiei este <=0 sau >=31, se concateneaza mesajul string "zi invalida!\n" la codul de eroare
                        - daca suma float a tranzactiei este <=0.0, se concateneaza mesajul string "suma invalida!\n" la codul de eroare
                        - daca tipul string al tranzactiei este diferit de "in" sau "out", se concateneaza mesajul string "tip invalid!\n" la codul de eroare
                        - daca cel putin unul dintre atributele tranzactiei este invalid, se arunca exceptie de tipul ValueError cu mesajul codului de eroare
    '''
    erori = ""
    if get_id_tranzactie(tranzactie) < 0:
        erori += "id invalid!\n"
    if get_zi_tranzactie(tranzactie) <= 0 or get_zi_tranzactie(tranzactie) >= 31:
        erori += "zi invalida!\n"
    if get_suma_tranzactie(tranzactie) <= 0.0:
        erori += "suma invalida!\n"
    if get_tip_tranzactie(tranzactie) != "out" and get_tip_tranzactie(tranzactie) != "in":
        erori += "tip invalid!\n"
    if len(erori) > 0:
        raise ValueError(erori)