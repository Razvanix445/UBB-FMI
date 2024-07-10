from business.service_tranzactii import adauga_tranzactie_service, modifica_tranzactie_service, \
    get_all_tranzactii_service, sterge_tranzactie_dupa_zi_service, sterge_tranzactie_dupa_perioada_service, \
    sterge_tranzactie_dupa_tip_service, elimina_mai_mici_suma_dupa_tip_service, elimina_dupa_tip_service, \
    tipareste_suma_mari_zile_mici_service, tipareste_dupa_tip_service, tipareste_sume_mari_service
from domeniu.tranzactie import to_string_tranzactie, get_suma_tranzactie, get_zi_tranzactie, get_tip_tranzactie, \
    tip_egal_tranzactie
from infrastructura.repository_tranzactii import numar_tranzactii_cont, cauta_tip, tipareste_sume_mari, \
    tipareste_suma_mari_zile_mici, tipareste_dupa_tip, cauta_zi


def afiseaza_meniu():
    print("    Meniu:")
    print("1. Submeniu adaugare")
    print("2. Submeniu stergere")
    print("3. Submeniu cautare")
    print("4. Submeniu rapoarte")
    print("5. Submeniu filtre")
    print("6. Afiseaza lista de tranzactii")
    print("7. Undo")
    print("0. Iesire")

def ui_adauga_tranzactie(cont):
    id = int(input("Introduceti id-ul tranzactiei: "))
    zi = int(input("Introduceti ziua in care a fost facuta tranzactia: "))
    suma = float(input("Introduceti suma tranzactiei: "))
    tip = input("Introduceti tipul tranzactiei: ")
    adauga_tranzactie_service(cont, id, zi, suma, tip)
    print("Tranzactie adaugata cu succes!\n")

def ui_modifica_tranzactie(cont):
    id = int(input("Introduceti id-ul tranzactiei: "))
    zi = int(input("Introduceti ziua in care a fost facuta tranzactia: "))
    suma = float(input("Introduceti suma tranzactiei: "))
    tip = input("Introduceti tipul tranzactiei: ")
    modifica_tranzactie_service(cont, id, zi, suma, tip)
    print("Tranzactie modificata cu succes!\n")

def ui_afiseaza_cont(cont):
    if numar_tranzactii_cont(cont) == 0:
        print("Nu exista tranzactii introduse!")
        return
    else:
        tranzactii = get_all_tranzactii_service(cont)
        for tranzactie in tranzactii:
            print(to_string_tranzactie(tranzactie))
    print()

def meniu_adaugare(cont):
    while True:
        comenzi = {
            "1": ui_adauga_tranzactie,
            "2": ui_modifica_tranzactie,
            "3": ui_afiseaza_cont
        }
        print("   Meniu Adaugare:")
        print("1. Adauga tranzactie in cont")
        print("2. Modifica tranzactie")
        print("3. Afiseaza contul")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            else:
                comenzi[comanda](cont)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_sterge_tranzactie_dupa_zi(cont):
    ziua_cautata = int(input("Introduceti ziua cautata: "))
    try:
        if cauta_tip(cont, ziua_cautata) == False:
            print("")
            print(f"Nu exista tranzactii de tipul {ziua_cautata}!")
        else:
            lista = sterge_tranzactie_dupa_zi_service(cont, ziua_cautata)
            if lista:
                print("Tranzactie stearsa cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def ui_sterge_tranzactie_dupa_perioada(cont):
    zi_inceput = int(input("Introduceti ziua de unde se incepe stergerea: "))
    zi_sfarsit = int(input("Introduceti ziua unde se sfarseste stergerea: "))
    try:
        ok = False
        for i in range(zi_inceput, zi_sfarsit):
            if cauta_zi(cont, i) == True:
                ok = True
        if ok == False:
            print("")
            print(f"Nu exista tranzactii efectuate in perioada {zi_inceput} - {zi_sfarsit}!")
        else:
            lista = sterge_tranzactie_dupa_perioada_service(cont, zi_inceput, zi_sfarsit)
            if lista:
                print("Tranzactii sterse cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def ui_sterge_tranzactie_dupa_tip(cont):
    tip_de_sters = input("Introduceti tipul cautat: ")
    try:
        if cauta_tip(cont, tip_de_sters) == False:
            print("")
            print(f"Nu exista tranzactii de tipul {tip_de_sters}!")
        else:
            lista = sterge_tranzactie_dupa_tip_service(cont, tip_de_sters)
            if lista:
                print("Tranzactie stearsa cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def meniu_stergere(cont):
    while True:
        comenzi = {
            "1": ui_sterge_tranzactie_dupa_zi,
            "2": ui_sterge_tranzactie_dupa_perioada,
            "3": ui_sterge_tranzactie_dupa_tip,
            "4": ui_afiseaza_cont
        }
        print("   Meniu Stergere:")
        print("1. Sterge tranzactie dupa zi")
        print("2. Sterge tranzacie dupa perioada")
        print("3. Sterge tranzactie dupa tip")
        print("4. Afiseaza contul")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            else:
                comenzi[comanda](cont)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_tipareste_sume_mari(cont):
    suma_mare = float(input("Introduceti suma ceruta: "))
    try:
        lista = tipareste_sume_mari_service(cont, suma_mare)
        for i in range(0, len(lista)):
            print(lista[i])
        print()
    except ValueError:
        print("Introduceti un numar!")

def ui_tipareste_suma_mari_zile_mici(cont):
    zi_mica = int(input("Introduceti ziua: "))
    suma_mare = float(input("Introduceti suma: "))
    try:
        lista = tipareste_suma_mari_zile_mici_service(cont, zi_mica, suma_mare)
        for i in range(0, len(lista)):
            print(lista[i])
        print()
    except ValueError:
        print("Introduceti un numar!")

def ui_tipareste_dupa_tip(cont):
    acelasi_tip = input("Introduceti tipul: ")
    try:
        lista = tipareste_dupa_tip_service(cont, acelasi_tip)
        for i in range(0, len(lista)):
            print(lista[i])
        print()
    except ValueError:
        print("Introduceti un numar!")

def meniu_cautare(cont):
    while True:
        comenzi = {
            "1": ui_tipareste_sume_mari,
            "2": ui_tipareste_suma_mari_zile_mici,
            "3": ui_tipareste_dupa_tip,
            "4": ui_afiseaza_cont
        }
        print("   Meniu Cautare:")
        print("1. Tipareste tranzactiile cu sume mai mari decat o suma data")
        print("2. Tipareste tranzactiile efectuate inainte de o zi si cu sume mai mari decat o suma data")
        print("3. Tipareste tranzactiile de un anumit tip")
        print("4. Afiseaza contul")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            else:
                comenzi[comanda](cont)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_calculeaza_suma(cont, tip):
    '''
    calculeaza suma tranzactiilor de un anumit tip
    :param cont: lista
    :param tip: string
    :return: suma - float: suma tranzactiilor de un anumit tip
    '''
    suma = 0.0
    for i in range(0, len(cont)):
        if get_tip_tranzactie(cont[i]) == tip:
            suma = suma + get_suma_tranzactie(cont[i])
    return suma

def ui_calculeaza_sold(cont, zi):
    '''
    calculeaza soldul contului la o data specificata
    :param cont: lista
    :param zi: int
    :return: sold - float: soldul contului in ziua specificata
    '''
    sold = 0.0
    for i in range(0, len(cont)):
        if get_tip_tranzactie(cont[i]) == "in" and get_zi_tranzactie(cont[i]) <= zi:
            sold = sold + get_suma_tranzactie(cont[i])
        if get_tip_tranzactie(cont[i]) == "out" and get_zi_tranzactie(cont[i]) <= zi:
            sold = sold - get_suma_tranzactie(cont[i])
    return sold

def ui_tipareste_tip_dupa_suma(cont, tip):
    '''
    sorteaza toate tranzactiile de un anumit tip dupa suma
    :param cont: lista
    :param tip: string
    :return: - (se sorteaza toate tranzactiile de un anumit tip dupa suma)
    '''
    lista_f = []
    for i in range(0, len(cont)):
        for j in range(i + 1, len(cont)):
            if get_suma_tranzactie(cont[i]) > get_suma_tranzactie(cont[j]):
                cont[i], cont[j] = cont[j], cont[i]
    for i in range(0, len(cont)):
        if get_tip_tranzactie(cont[i]) == tip:
            lista_f.append(cont[i])
    return lista_f

def meniu_rapoarte(cont):
    while True:
        comenzi = {
            "1": ui_calculeaza_suma,
            "2": ui_calculeaza_sold,
            "3": ui_tipareste_tip_dupa_suma,
            "4": ui_afiseaza_cont
        }
        print("   Meniu Rapoarte:")
        print("1. Calculeaza suma totala a tranzactiilor de un anumit tip")
        print("2. Calculeaza soldul contului la o data specificata")
        print("3. Tipareste toate tranzactiile de un anumit tip ordonat dupa suma")
        print("4. Afiseaza contul")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            if comanda == "1":
                tip = input("Introduceti tipul: ")
                comenzi[comanda](cont, tip)
            if comanda == "2":
                zi = input("Introduceti ziua: ")
                comenzi[comanda](cont, zi)
            if comanda == "3":
                tip = input("Introduceti tipul: ")
                comenzi[comanda](cont, tip)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_elimina_dupa_tip(cont):
    tip = input("Introduceti tipul cautat pentru eliminarea tranzactiilor de acest tip: ")
    try:
        if cauta_tip(cont, tip) == False:
            print("")
            print(f"Nu exista tranzactii de tipul {tip}!")
        else:
            lista = elimina_dupa_tip_service(cont, tip)
            if lista:
                print("Tranzactii eliminate cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def ui_elimina_mai_mici_suma_dupa_tip(cont):
    suma_mare = float(input("Introduceti suma cautata: "))
    tip = input("Introduceti tipul cautat pentru eliminarea tranzactiilor de acest tip: ")
    try:
        if cauta_tip(cont, tip) == False:
            print("")
            print(f"Nu exista tranzactii de tipul {tip}!")
        else:
            lista = elimina_mai_mici_suma_dupa_tip_service(cont, suma_mare, tip)
            if lista:
                print("Tranzactii eliminate cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def meniu_filtre(cont):
    while True:
        comenzi = {
            "1": ui_elimina_dupa_tip,
            "2": ui_elimina_mai_mici_suma_dupa_tip,
            "3": ui_afiseaza_cont
        }
        print("   Meniu Filtre:")
        print("1. Elimina toate tranzactiile de un anumit tip")
        print("2. Elimina toate tranzactiile mai mici decat o suma data care au tipul specificat")
        print("3. Afiseaza contul")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            else:
                comenzi[comanda](cont)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ruleaza_ui():
    cont = {}
    comenzi = {
        "1": meniu_adaugare,
        "2": meniu_stergere,
        "3": meniu_cautare,
        "4": meniu_rapoarte,
        "5": meniu_filtre,
        "6": ui_afiseaza_cont
        # "7": undo
    }
    while True:
        afiseaza_meniu()
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            else:
                comenzi[comanda](cont)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necumoscuta!")