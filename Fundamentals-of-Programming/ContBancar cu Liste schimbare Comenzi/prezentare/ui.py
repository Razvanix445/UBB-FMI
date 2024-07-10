from business.service_tranzactii import adauga_tranzactie_service, modifica_tranzactie_service, \
    get_all_tranzactii_service, sterge_tranzactie_dupa_zi_service, sterge_tranzactie_dupa_perioada_service, \
    sterge_tranzactie_dupa_tip_service, elimina_mai_mici_suma_dupa_tip_service, elimina_dupa_tip_service, undo
from domeniu.tranzactie import to_string_tranzactie, get_suma_tranzactie, get_zi_tranzactie, get_tip_tranzactie
from infrastructura.repository_tranzactii import numar_tranzactii_cont, cauta_tip, cauta_zi


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

def ui_adauga_tranzactie(cont, params, undolist):
    if len(params) != 4:
        print("numar parametri invalid!\n")
        return
    id = int(params[0])
    zi = int(params[1])
    suma = float(params[2])
    tip = params[3]
    adauga_tranzactie_service(cont, id, zi, suma, tip, undolist)
    print("Tranzactie adaugata cu succes!\n")

def ui_modifica_tranzactie(cont, params, undolist):
    if len(params) != 4:
        print("numar parametri invalid!\n")
        return
    id = int(params[0])
    zi = int(params[1])
    suma = float(params[2])
    tip = params[3]
    modifica_tranzactie_service(cont, id, zi, suma, tip, undolist)
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

def meniu_adaugare(cont, undolist):
    while True:
        comenzi = {
            "adauga_tranzactie": ui_adauga_tranzactie,
            "modifica_tranzactie": ui_modifica_tranzactie
        }
        print("   Meniu Adaugare:")
        print("1. <adauga_tranzactie>. Adauga tranzactie in cont")
        print("2. <modifica_tranzactie>. Modifica tranzactie")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "0":
                return
            parti = comanda.split()
            nume_comanda = parti[0]
            params = parti[1:]
            for param in params:
                param = param.strip()
            if nume_comanda in comenzi:
                comenzi[nume_comanda](cont, params, undolist)
            else:
                print("Comanda invalida!\n")
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_sterge_tranzactie_dupa_zi(cont, params, undolist):
    if len(params) != 1:
        print("numar parametri invalid!\n")
        return
    ziua_cautata = int(params[0])
    try:
        if cauta_zi(cont, ziua_cautata) == False:
            print("")
            print(f"Nu exista tranzactii efectuate in ziua {ziua_cautata}!")
        else:
            sterge_tranzactie_dupa_zi_service(cont, ziua_cautata, undolist)
            print("Tranzactie stearsa cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def ui_sterge_tranzactie_dupa_perioada(cont, params, undolist):
    if len(params) != 2:
        print("numar parametri invalid!\n")
        return
    zi_inceput = int(params[0])
    zi_sfarsit = int(params[1])
    try:
        ok = False
        for i in range(zi_inceput, zi_sfarsit):
            if cauta_zi(cont, i) == True:
                ok = True
        if ok == False:
            print("")
            print(f"Nu exista tranzactii efectuate in perioada {zi_inceput} - {zi_sfarsit}!")
        else:
            lista = sterge_tranzactie_dupa_perioada_service(cont, zi_inceput, zi_sfarsit, undolist)
            if lista:
                print("Tranzactii sterse cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def ui_sterge_tranzactie_dupa_tip(cont, params, undolist):
    if len(params) != 1:
        print("numar parametri invalid!\n")
        return
    tip_de_sters = params[0]
    try:
        if cauta_tip(cont, tip_de_sters) == False:
            print("")
            print(f"Nu exista tranzactii de tipul {tip_de_sters}!")
        else:
            lista = sterge_tranzactie_dupa_tip_service(cont, tip_de_sters, undolist)
            if lista:
                print("Tranzactie stearsa cu succes!\n")
    except ValueError:
        print("Introduceti un numar!")

def meniu_stergere(cont, undolist):
    while True:
        comenzi = {
            "sterge_dupa_zi": ui_sterge_tranzactie_dupa_zi,
            "sterge_dupa_perioada": ui_sterge_tranzactie_dupa_perioada,
            "sterge_dupa_tip": ui_sterge_tranzactie_dupa_tip,
        }
        print("   Meniu Stergere:")
        print("<sterge_dupa_zi>. Sterge tranzactie dupa zi")
        print("<sterge_dupa_perioada>. Sterge tranzacie dupa perioada")
        print("<sterge_dupa_tip>. Sterge tranzactie dupa tip")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "0":
                return
            parti = comanda.split()
            nume_comanda = parti[0]
            params = parti[1:]
            for param in params:
                param = param.strip()
            if nume_comanda in comenzi:
                comenzi[nume_comanda](cont, params, undolist)
            else:
                print("Comanda invalida!\n")
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_tipareste_sume_mari(cont, params):
    if len(params) != 1:
        print("numar parametri invalid!\n")
        return
    suma_mare = float(params[0])
    try:
        for i in range(len(cont)):
            if get_suma_tranzactie(cont[i]) > suma_mare:
                print(to_string_tranzactie(cont[i]))
        print()
    except ValueError:
        print("Introduceti un numar!")

def ui_tipareste_suma_mari_zile_mici(cont, params):
    if len(params) != 2:
        print("numar parametri invalid!\n")
        return
    zi_mica = int(params[0])
    suma_mare = float(params[1])
    try:
        for i in range(0, len(cont)):
            if get_zi_tranzactie(cont[i]) < zi_mica and get_suma_tranzactie(cont[i]) > suma_mare:
                print(to_string_tranzactie(cont[i]))
        print()
    except ValueError:
        print("Introduceti un numar!")

def ui_tipareste_dupa_tip(cont, params):
    if len(params) != 1:
        print("numar parametri invalid!\n")
        return
    acelasi_tip = params[0]
    try:
        for i in range(0, len(cont)):
            if get_tip_tranzactie(cont[i]) == acelasi_tip:
                print(to_string_tranzactie(cont[i]))
        print()
    except ValueError:
        print("Introduceti un numar!")

def meniu_cautare(cont):
    while True:
        comenzi = {
            "tipareste_sume_mari": ui_tipareste_sume_mari,
            "tipareste_suma_mari_zile_mici": ui_tipareste_suma_mari_zile_mici,
            "tipareste_dupa_tip": ui_tipareste_dupa_tip,
        }
        print("   Meniu Cautare:")
        print("<tipareste_sume_mari>. Tipareste tranzactiile cu sume mai mari decat o suma data")
        print("<tipareste_suma_mari_zile_mici>. Tipareste tranzactiile efectuate inainte de o zi si cu sume mai mari decat o suma data")
        print("<tipareste_dupa_tip>. Tipareste tranzactiile de un anumit tip")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "0":
                return
            parti = comanda.split()
            nume_comanda = parti[0]
            params = parti[1:]
            for param in params:
                param = param.strip()
            if nume_comanda in comenzi:
                comenzi[nume_comanda](cont, params)
            else:
                print("Comanda invalida!\n")
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
            "calculeaza_suma": ui_calculeaza_suma,
            "calculeaza_sold": ui_calculeaza_sold,
            "tipareste_tip_dupa_suma": ui_tipareste_tip_dupa_suma,
        }
        print("   Meniu Rapoarte:")
        print("<calculeaza_suma>. Calculeaza suma totala a tranzactiilor de un anumit tip")
        print("<calculeaza_sold>. Calculeaza soldul contului la o data specificata")
        print("<tipareste_tip_dupa_suma>. Tipareste toate tranzactiile de un anumit tip ordonat dupa suma")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "0":
                return
            parti = comanda.split()
            nume_comanda = parti[0]
            params = parti[1:]
            for param in params:
                param = param.strip()
            if comanda == "calculeaza_suma":
                tip = params[0]
                comenzi[comanda](cont, tip)
                print(ui_calculeaza_suma(cont, tip))
            if comanda == "calculeaza_sold":
                zi = int(params[0])
                comenzi[comanda](cont, zi)
                print(ui_calculeaza_sold(cont, zi))
            if comanda == "tipareste_tip_dupa_suma":
                tip = params[0]
                comenzi[comanda](cont, tip)
                list = ui_tipareste_tip_dupa_suma(cont, tip)
                for i in range(0, len(list)):
                    print(to_string_tranzactie(list[i]))
            else:
                print("Comanda invalida!\n")
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ui_elimina_dupa_tip(cont, params):
    if len(params) != 1:
        print("numar parametri invalid!\n")
        return
    tip = params[0]
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

def ui_elimina_mai_mici_suma_dupa_tip(cont, params):
    if len(params) != 2:
        print("numar parametri invalid!\n")
        return
    suma_mare = float(params[0])
    tip = params[1]
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
            "elimina_dupa_tip": ui_elimina_dupa_tip,
            "elimina_mai_mici_suma_dupa_tip": ui_elimina_mai_mici_suma_dupa_tip,
        }
        print("   Meniu Filtre:")
        print("<elimina_dupa_tip>. Elimina toate tranzactiile de un anumit tip")
        print("<elimina_mai_mici_suma_dupa_tip>. Elimina toate tranzactiile mai mici decat o suma data care au tipul specificat")
        print("0. Inapoi la meniul principal")
        try:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "0":
                return
            parti = comanda.split()
            nume_comanda = parti[0]
            params = parti[1:]
            for param in params:
                param = param.strip()
            if nume_comanda in comenzi:
                comenzi[nume_comanda](cont, params)
            else:
                print("Comanda invalida!\n")
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necunoscuta!")

def ruleaza_ui():
    cont = []
    undolist = []
    while True:
        afiseaza_meniu()
        comenzi = {
            "1": meniu_adaugare,
            "2": meniu_stergere,
            "3": meniu_cautare,
            "4": meniu_rapoarte,
            "5": meniu_filtre,
            "6": ui_afiseaza_cont,
            "7": undo
        }
        try:
            comanda = input(">>>")
            if comanda == "":
                continue
            if comanda == "0":
                return
            if comanda == "6" or comanda == "3" or comanda == "4":
                comenzi[comanda](cont)
            else:
                comenzi[comanda](cont, undolist)
        except ValueError as ve:
            print(ve)
        except KeyError:
            print("Comanda necumoscuta!")