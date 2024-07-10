from erori.repository_error import RepoError
from erori.validation_error import ValidError


class UI:

    def __init__(self, service_carti):
        self.__service_carti = service_carti
        self.__comenzi = {
            "1": self.__meniu_carti,
            "2": self.__meniu_clienti
        }
        self.__comenzi_carti = {
            "1": self.__ui_adauga_carte,
            "2": self.__ui_cauta_carte
        }
        self.__comenzi_clienti = {
            "1": self.__ui_adauga_client,
            "2": self.__ui_cauta_client
        }

    def __ui_adauga_carte(self):
        id = int(input("Introduceti id-ul: "))
        titlu = input("Introduceti titlul: ")
        descriere = input("Introduceti descrierea: ")
        autor = input("Introduceti autorul: ")
        self.__service_carti.adauga_carte(id, titlu, descriere, autor)
        print("Carte adaugata cu succes!\n")

    def __ui_cauta_carte(self):
        id = int(input("Introduceti id-ul: "))
        carte = self.__service_carti.cauta_carte(id)
        print(carte)

    def __ui_adauga_client(self):
        pass

    def __ui_cauta_client(self):
        pass

    def __meniu_carti(self):
        while True:
            print("   Meniu carti:")
            print("1. Adauga carte")
            print("2. Cauta carte")
            try:
                comanda = input(">>>")
                if comanda == "0":
                    return
                if comanda == "":
                    continue
                for comanda in self.__comenzi_carti:
                    self.__comenzi_carti[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except RepoError as re:
                print(f"Eroare Repository: {re}")
            except ValidError as ve:
                print(f"Eroare Validare: {ve}")

    def __meniu_clienti(self):
        while True:
            print("   Meniu carti:")
            print("1. Adauga carte")
            print("2. Cauta carte")
            try:
                comanda = input(">>>")
                if comanda == "0":
                    return
                if comanda == "":
                    continue
                for comanda in self.__comenzi_clienti:
                    self.__comenzi_clienti[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except RepoError as re:
                print(f"Eroare Repository: {re}")
            except ValidError as ve:
                print(f"Eroare Validare: {ve}")

    def run(self):
        while True:
            print("   Meniu:")
            print("1. Meniu gestionare carti")
            print("2. Meniu gestionare clienti")
            try:
                comanda = input(">>>")
                if comanda == "0":
                    return
                if comanda == "":
                    continue
                for comanda in self.__comenzi:
                    self.__comenzi[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except RepoError as re:
                print(f"Eroare Repository: {re}")
            except ValidError as ve:
                print(f"Eroare Validare: {ve}")