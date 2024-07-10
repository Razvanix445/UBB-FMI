from erori.repository_error import RepoError
from erori.validation_error import ValidError


class UI:

    def __init__(self, service):
        self.__service = service
        self.__comenzi = {
            '1': self.__ui_adaugare,
            '2': self.__ui_modificare,
            '3': self.__ui_tiparire,
            '4': self.__ui_importare
        }

    def __ui_adaugare(self):
        '''
        Se adauga jucatori in aplicatie
        :return: - Se adauga jucatori in aplicatie
        '''
        nume = input('Introduceti numele jucatorului: ')
        prenume = input('Introduceti prenumele jucatorului: ')
        inaltime = int(input('Introduceti inaltimea jucatorului: '))
        post = input('Introduceti postul jucatorului: ')
        self.__service.adaugare(nume, prenume, inaltime, post)
        print(f'Jucatorul {nume} {prenume} a fost adaugat cu succes!')

    def __ui_modificare(self):
        '''
        Se modifica inaltimea jucatorilor din aplicatie
        :return: - Se va modifica inaltimea jucatorilor din aplicatie adunand sau scazand
        '''
        noua_inaltime = int(input('Introduceti valoarea inaltimii pentru modificare: '))
        self.__service.modificare(noua_inaltime)
        print(f'Jucatorii au fost modificati cu succes!')

    def __ui_tiparire(self):
        self.__service.tiparire()

    def __ui_importare(self):
        '''
        Se importa nume si prenume din fisier
        :return: - Se afisaeaza numarul de jucatori introdusi
        '''
        nume_fisier = input('Introduceti numele unui fisier existent: ')
        numar_jucatori_adaugati = self.__service.importare(nume_fisier)
        print(numar_jucatori_adaugati)

    def run(self):
        while True:
            print('   Meniu:')
            print('1. Adaugare jucator')
            print('2. Modificare jucator')
            print('3. Tiparire echipa')
            print('4. Importa jucatori din fisier')
            print('0. Iesire')
            try:
                comanda = input('>>>')
                if comanda == '':
                    continue
                if comanda == '0':
                    return
                if comanda in self.__comenzi:
                    self.__comenzi[comanda]()
                else:
                    print('Comanda necunoscuta!')
            except ValueError:
                print('Eroare UI: tip numeric invalid!')
            except RepoError as re:
                print(f'Eroare Repository: {re}')
            except ValidError as ve:
                print(f'Eroare Validare: {ve}')