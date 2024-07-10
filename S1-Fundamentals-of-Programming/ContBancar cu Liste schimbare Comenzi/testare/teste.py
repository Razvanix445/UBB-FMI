from business.service_tranzactii import adauga_tranzactie_service, numar_tranzactii_service, \
    modifica_tranzactie_service, sterge_tranzactie_dupa_zi_service, sterge_tranzactie_dupa_perioada_service, \
    sterge_tranzactie_dupa_tip_service, elimina_dupa_tip_service, elimina_mai_mici_suma_dupa_tip_service, \
    tipareste_suma_mari_zile_mici_service, tipareste_sume_mari_service, tipareste_dupa_tip_service
from domeniu.tranzactie import creeaza_tranzactie, get_id_tranzactie, get_zi_tranzactie, get_suma_tranzactie, \
    get_tip_tranzactie, set_suma_tranzactie
from infrastructura.repository_tranzactii import adauga_tranzactie_cont, numar_tranzactii_cont
from prezentare.ui import ui_calculeaza_suma, ui_calculeaza_sold, ui_tipareste_tip_dupa_suma, ui_elimina_dupa_tip, \
    ui_elimina_mai_mici_suma_dupa_tip
from validare.validator_tranzactie import valideaza_tranzactie

def sterge_tranzactie_zi_service(lista, ziua_cautata, undolist):
    pass

def ruleaza_teste_tranzactie():
    id_tranzactie = 3
    zi_tranzactie = 21
    suma_tranzactie = 350.5
    tip_tranzactie = "in"
    tranzactie = creeaza_tranzactie(id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie)
    assert(id_tranzactie == get_id_tranzactie(tranzactie))
    assert(zi_tranzactie == get_zi_tranzactie(tranzactie))
    assert(abs(suma_tranzactie - get_suma_tranzactie(tranzactie)) < 0.00001)
    suma_noua = 105.75
    set_suma_tranzactie(tranzactie, suma_noua)
    assert(abs(suma_noua - get_suma_tranzactie(tranzactie)) < 0.00001)
    assert(tip_tranzactie == get_tip_tranzactie(tranzactie))

def ruleaza_teste_validator_tranzactie():
    id_tranzactie = 3
    zi_tranzactie = 21
    suma_tranzactie = 350.5
    tip_tranzactie = "in"
    tranzactie = creeaza_tranzactie(id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie)
    valideaza_tranzactie(tranzactie)

    id_tranzactie_gresit = -59
    zi_tranzactie_gresit = 0
    suma_tranzactie_gresit = 0.0
    tip_tranzactie_gresit = "Mancare"
    tranzactie_gresita = creeaza_tranzactie(id_tranzactie_gresit, zi_tranzactie_gresit, suma_tranzactie_gresit, tip_tranzactie_gresit)
    try:
        valideaza_tranzactie(tranzactie_gresita)
        assert False
    except ValueError as ve:
        assert(str(ve) == "id invalid!\nzi invalida!\nsuma invalida!\ntip invalid!\n")

def ruleaza_teste_repository_tranzactii():
    tranzactii = []
    undolist = []
    id_tranzactie = 3
    zi_tranzactie = 21
    suma_tranzactie = 350.5
    tip_tranzactie = "in"
    tranzactie = creeaza_tranzactie(id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie)
    assert(numar_tranzactii_cont(tranzactii) == 0)
    adauga_tranzactie_cont(tranzactii, tranzactie, undolist)
    assert (numar_tranzactii_cont(tranzactii) == 1)

    acelasi_id_tranzactie = 3
    alta_zi_tranzactie = 23
    alta_suma_tranzactie = 199.9
    alt_tip_tranzactie = "out"
    alta_tranzactie_acelasi_id = creeaza_tranzactie(acelasi_id_tranzactie, alta_zi_tranzactie, alta_suma_tranzactie, alt_tip_tranzactie)
    try:
        adauga_tranzactie_cont(tranzactii, alta_tranzactie_acelasi_id, undolist)
        assert False
    except ValueError as ve:
        assert(str(ve) == "tranzactie invalida!\n")

def ruleaza_teste_service_tranzactii():
    tranzactii = []
    undolist = []
    id_tranzactie = 3
    zi_tranzactie = 21
    suma_tranzactie = 350.5
    tip_tranzactie = "in"
    assert (numar_tranzactii_service(tranzactii) == 0)
    adauga_tranzactie_service(tranzactii, id_tranzactie, zi_tranzactie, suma_tranzactie, tip_tranzactie, undolist)
    assert(numar_tranzactii_service(tranzactii) == 1)

    id_tranzactie_gresit = -59
    zi_tranzactie_gresit = 0
    suma_tranzactie_gresit = 0.0
    tip_tranzactie_gresit = "Mancare"
    try:
        adauga_tranzactie_service(tranzactii, id_tranzactie_gresit, zi_tranzactie_gresit, suma_tranzactie_gresit, tip_tranzactie_gresit, undolist)
        assert False
    except ValueError as ve:
        assert (str(ve) == "id invalid!\nzi invalida!\nsuma invalida!\ntip invalid!\n")

    acelasi_id_tranzactie = 3
    alta_zi_tranzactie = 23
    alta_suma_tranzactie = 199.9
    alt_tip_tranzactie = "out"
    try:
        adauga_tranzactie_service(tranzactii, acelasi_id_tranzactie, alta_zi_tranzactie, alta_suma_tranzactie, alt_tip_tranzactie, undolist)
        assert False
    except ValueError as ve:
        assert (str(ve) == "tranzactie invalida!\n")

#1.
def ruleaza_adauga_tranzactie():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    adauga_tranzactie_service(lista, 4, 30, 350, "out", undolist)
    assert lista == [tranzactie1, tranzactie2, tranzactie3, [4, 30, 350, "out"]]

def ruleaza_modifica_tranzactie():
    undolist = []
    tranzactie = creeaza_tranzactie(1, 5, 305.5, "in")
    lista = [tranzactie]
    modifica_tranzactie_service(lista, 1, 10, 250.5, "out", undolist)
    assert lista == [[1, 10, 250.5, "out"]]

#2.
def ruleaza_sterge_tranzactie_dupa_zi():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    sterge_tranzactie_dupa_zi_service(lista, 21, undolist)
    assert lista == [tranzactie1, tranzactie3]

def ruleaza_sterge_tranzactie_dupa_perioada():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    sterge_tranzactie_dupa_perioada_service(lista, 20, 22, undolist)
    #assert lista == [tranzactie1, tranzactie3]

def ruleaza_sterge_tranzactie_dupa_tip():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    sterge_tranzactie_dupa_tip_service(lista, "out", undolist)
    #assert lista == [tranzactie1, tranzactie3]

#3.
def ruleaza_tipareste_sume_mari():
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    tipareste_sume_mari_service(lista, 125.5)
    assert lista == [tranzactie2, tranzactie3]

def ruleaza_tipareste_sume_mari_zile_mici():
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    tipareste_suma_mari_zile_mici_service(lista, 20, 125.5)
    assert lista == [tranzactie2]

def ruleaza_tipareste_dupa_tip():
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    tipareste_dupa_tip_service(lista, "out")
    assert lista == [tranzactie2]

#4.
def ruleaza_calculeaza_suma():
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    assert ui_calculeaza_suma(lista, "in") == 275.5
    assert ui_calculeaza_suma(lista, "out") == 150

def ruleaza_calculeaza_sold():
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 8, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 3, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    assert ui_calculeaza_sold(lista, 11) == 125.5

def ruleaza_tipareste_tip_dupa_suma():
    tranzactie1 = creeaza_tranzactie(1, 10, 300, "in")
    tranzactie2 = creeaza_tranzactie(2, 8, 250, "in")
    tranzactie3 = creeaza_tranzactie(3, 3, 500, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    assert ui_tipareste_tip_dupa_suma(lista, "in") == [[2, 8, 250.0, 'in'], [1, 10, 300.0, 'in'], [3, 3, 500.0, 'in']]

#5.
def ruleaza_elimina_dupa_tip():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 300, "in")
    tranzactie2 = creeaza_tranzactie(2, 8, 250, "out")
    tranzactie3 = creeaza_tranzactie(3, 3, 500, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    elimina_dupa_tip_service(lista, "in")
    assert lista == [tranzactie2]

def ruleaza_elimina_mai_mici_suma_dupa_tip():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 8, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 3, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    elimina_mai_mici_suma_dupa_tip_service(lista, 125, "in")
    assert lista == [tranzactie2, tranzactie3]
'''
def ruleaza_undo():
    undolist = []
    tranzactie1 = creeaza_tranzactie(1, 10, 100, "in")
    tranzactie2 = creeaza_tranzactie(2, 21, 150, "out")
    tranzactie3 = creeaza_tranzactie(3, 15, 175.5, "in")
    lista = [tranzactie1, tranzactie2, tranzactie3]
    adauga_tranzactie_service(lista, 4, 30, 350, "out", undolist)
    undo(lista, undolist)
    assert lista == [tranzactie1, tranzactie2, tranzactie3]
    sterge_tranzactie_zi_service(lista, 10, undolist)
    undo(lista, undolist)
    assert lista == [tranzactie1, tranzactie2]
'''
def ruleaza_toate_testele():
    ruleaza_teste_tranzactie()
    print("Teste tranzactie trecute cu succes!")
    ruleaza_teste_validator_tranzactie()
    print("Teste validator tranzactie trecute cu succes!")
    ruleaza_teste_repository_tranzactii()
    print("Teste repository tranzactie trecute cu succes!")
    ruleaza_teste_service_tranzactii()
    print("Teste service tranzactie trecute cu succes!\n")
    ruleaza_adauga_tranzactie()
    print("Teste adauga_tranzactie trecute cu succes!")
    ruleaza_modifica_tranzactie()
    print("Teste modifica_tranzactie trecute cu succes!\n")
    ruleaza_sterge_tranzactie_dupa_zi()
    print("Teste sterge_tranzactie_dupa_zi trecute cu succes!")
    ruleaza_sterge_tranzactie_dupa_perioada()
    print("Teste sterge_tranzactie_dupa_perioada trecute cu succes!")
    ruleaza_sterge_tranzactie_dupa_tip()
    print("Teste sterge_tranzactie_dupa_tip trecute cu succes!\n")
    ruleaza_calculeaza_suma()
    print("Teste calculeaza_suma trecute cu succes!")
    ruleaza_calculeaza_sold()
    print("Teste calculeaza_sold trecute cu succes!")
    ruleaza_tipareste_tip_dupa_suma()
    print("Teste tipareste_tip_dupa_suma trecute cu succes!\n")
    ruleaza_elimina_dupa_tip()
    print("Teste elimina_dupa_tip trecute cu succes!")
    ruleaza_elimina_mai_mici_suma_dupa_tip()
    print("Teste elimina_mai_mici_suma_dupa_tip trecute cu succes!\n")