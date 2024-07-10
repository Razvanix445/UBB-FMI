from erori.repository_error import RepoError
from erori.validation_error import ValidError


class UI:

    def __init__(self, service_filme, service_clienti):
        self.__service_filme = service_filme
        self.__service_clienti = service_clienti
        self.__comenzi = {
            "1": self.__meniu_filme,
            "2": self.__meniu_clienti
        }
        self.__comenzi_filme = {
            "1": self.__ui_adauga_film,
            "2": self.__ui_modifica_film,
            "3": self.__ui_sterge_film,
            "4": self.__ui_cauta_film,
            "5": self.__ui_afiseaza_filmele
        }
        self.__comenzi_clienti = {
            "1": self.__ui_adauga_client,
            "2": self.__ui_modifica_client,
            "3": self.__ui_sterge_client,
            "4": self.__ui_cauta_client,
            "5": self.__ui_afiseaza_clientii
        }

    def __ui_adauga_film(self):
        id_film = int(input("Introduceti id-ul filmului: "))
        titlu = input("Introduceti titlul filmului: ")
        descriere = input("Introduceti descrierea filmului: ")
        gen = input("Introduceti genul filmului: ")
        self.__service_filme.adauga_film(id_film, titlu, descriere, gen)
        print("Film adaugat cu succes!\n")

    def __ui_modifica_film(self):
        id_film = int(input("Introduceti id-ul filmului: "))
        titlu_nou = input("Introduceti titlul nou al filmului: ")
        descriere_noua = input("Introduceti descrierea noua a filmului: ")
        gen_nou = input("Introduceti genul nou al filmului: ")
        self.__service_filme.modifica_film(id_film, titlu_nou, descriere_noua, gen_nou)
        print("Film modificat cu succes!\n")

    def __ui_sterge_film(self):
        id_film = int(input("Introduceti id-ul filmului pentru stergere: "))
        self.__service_filme.sterge_film(id_film)
        print("Film sters cu succes!\n")

    def __ui_cauta_film(self):
        id_film = int(input("Introduceti id-ul filmului cautat: "))
        film = self.__service_filme.cauta_film(id_film)
        print(film)

    def __ui_afiseaza_filmele(self):
        filme = self.__service_filme.get_all()
        if len(filme) == 0:
            print("Nu exista filme introduse!\n")
            return
        for id_film in filme:
            print(filme[id_film])

    def __ui_adauga_client(self):
        id_client = int(input("Introduceti id-ul clientului: "))
        nume = input("Introduceti numele clientului: ")
        CNP = input("Introduceti CNP-ul clientului: ")
        self.__service_clienti.adauga_client(id_client, nume, CNP)
        print("Client adaugat cu succes!\n")

    def __ui_modifica_client(self):
        id_client = int(input("Introduceti id-ul clientului: "))
        nume_nou = input("Introduceti numele nou al clientului: ")
        CNP_nou = input("Introduceti CNP-ul nou clientului: ")
        self.__service_clienti.modifica_client(id_client, nume_nou, CNP_nou)
        print("Client modificat cu succes!\n")

    def __ui_sterge_client(self):
        id_client = int(input("Introduceti id-ul clientului: "))
        self.__service_clienti.sterge_client(id_client)
        print("Client sters cu succes!\n")

    def __ui_cauta_client(self):
        id_client = int(input("Introduceti id-ul clientului: "))
        client = self.__service_clienti.cauta_client(id_client)
        print(client)

    def __ui_afiseaza_clientii(self):
        clienti = self.__service_clienti.get_all()
        if len(clienti) == 0:
            print("Nu exista clienti introdusi!\n")
            return
        for id_client in clienti:
            print(clienti[id_client])

    def __meniu_filme(self):
        while True:
            print("   Meniu filme: ")
            print("1. Adauga film")
            print("2. Modifica film")
            print("3. Sterge film")
            print("4. Cauta film")
            print("5. Afiseaza filmele")
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == '0':
                    return
                if comanda in self.__comenzi_filme:
                    self.__comenzi_filme[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f'Eroare Validare: {ve}')
            except RepoError as re:
                print(f"Eroare Repository: {re}")

    def __meniu_clienti(self):
        while True:
            print("   Meniul clienti: ")
            print("1. Adauga client")
            print("2. Modifica client")
            print("3. Sterge client")
            print("4. Cauta client")
            print("5. Afiseaza clientii")
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == '0':
                    return
                if comanda in self.__comenzi_clienti:
                    self.__comenzi_clienti[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f'Eroare Validare: {ve}')
            except RepoError as re:
                print(f"Eroare Repository: {re}")

    def run(self):
        while True:
            print("   Meniul principal: ")
            print("1. Meniu gestionare filme")
            print("2. Meniu gestionare clienti")
            print("0. Iesi din program")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == '0':
                    return
                if comanda in self.__comenzi:
                    self.__comenzi[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f'Eroare Validare: {ve}')
            except RepoError as re:
                print(f"Eroare Repository: {re}")