import termcolor as colored


class UI:

    def __init__(self, service):
        self.__service = service
        self.__comenzi = {
            '1': self.__ui_conversie_substitutie_baza_intermediara_impartiri_succesive,
            '2': self.__ui_conversie_rapida,
            '3': self.__ui_adunare,
            '4': self.__ui_scadere,
            '5': self.__ui_inmultire_cu_o_cifra,
            '6': self.__ui_impartire_la_o_cifra
        }

    def __ui_conversie_substitutie_baza_intermediara_impartiri_succesive(self):
        '''
        Cerinta: Se da un numar intr-o anumita baza si baza acestuia si inca o baza in care se va converti numarul dat.
        Se foloseste metoda de conversie prin substitutie pentru convertirea numarului in baza 10 si metoda de conversie
        prin impartiri succesive pentru convertirea numarului din baza 10 in baza dorita. Astfel, se foloseste si metoda
        utilizarii unei baze intermediare, baza 10.
        :return: - Se afiseaza numarul convertit in baza dorita.
        '''
        numar = input(f'Introduceti numarul: ')
        baza_initiala = int(input('Introduceti baza in care va fi numarul citit: '))
        baza_finala = int(input('Introduceti baza in care doriti conversia: '))
        numarul_dorit = self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive(numar, baza_initiala, baza_finala)
        print(f'Numarul {numar} din baza {baza_initiala} are valoarea {numarul_dorit} in baza {baza_finala}!')

    def __ui_conversie_rapida(self):
        '''
        Cerinta: Se dau un numar si baza in care este acesta si o baza in care se doreste convertirea numarului.
        Se folosesc tabelele de conversie rapida pentru utilizarea metodei de conversie rapida dintre bazele 2, 4, 8 si
        16.
        :return: - Se afiseaza numarul convertit in baza dorita.
        '''
        numar = input(f'Introduceti numarul: ')
        baza_initiala = int(input('Introduceti baza in care va fi numarul citit: '))
        baza_finala = int(input('Introduceti baza in care doriti conversia: '))
        numarul_dorit = self.__service.conversie_rapida(numar, baza_initiala, baza_finala)
        print(f'Numarul {numar} din baza {baza_initiala} are valoarea {numarul_dorit} in baza {baza_finala}!')

    def __ui_adunare(self):
        '''
        Cerinta: Se dau doua numere si bazele acestora si o a treia baza in care acestea se vor aduna.
        :return: - Se afiseaza numarul care va fi suma celor doua numere in baza dorita.
        '''
        numar1 = input('Introduceti primul numar: ')
        baza1 = int(input('Introduceti baza primului numar: '))
        numar2 = input('Introduceti al doilea numar: ')
        baza2 = int(input('Introduceti baza celui de-al doilea numar: '))
        baza = int(input('Introduceti baza in care sa se faca adunarea: '))
        numarul_dorit = self.__service.adunare(numar1, baza1, numar2, baza2, baza)
        print(f'Suma numerelor {numar1} si {numar2} in baza {baza} este {numarul_dorit}!')

    def __ui_scadere(self):
        '''
        Cerinta: Se dau doua numere si bazele acestora si o a treia baza in care acestea se vor scadea.
        :return: - Se afiseaza numarul care va fi diferenta celor doua numere in baza dorita.
        '''
        numar1 = input('Introduceti primul numar: ')
        baza1 = int(input('Introduceti baza primului numar: '))
        numar2 = input('Introduceti al doilea numar: ')
        baza2 = int(input('Introduceti baza celui de-al doilea numar: '))
        baza = int(input('Introduceti baza in care sa se faca scaderea: '))
        numarul_dorit = self.__service.scadere(numar1, baza1, numar2, baza2, baza)
        print(f'Diferenta numerelor {numar1} si {numar2} in baza {baza} este {numarul_dorit}!')

    def __ui_inmultire_cu_o_cifra(self):
        '''
        nefacut
        :return:
        '''
        pass

    def __ui_impartire_la_o_cifra(self):
        '''
        nefacut
        :return:
        '''
        pass

    def run(self):
        print('\nCreator aplicatie: Calauz Razvan \n')
        while True:
            print('   Meniu:')
            print('1. Conversia prin metodele: impartiri succesive, substitutie, baza intermediara')
            print('2. Conversia rapida (bazele acceptate sunt 2->4; 2->8; 2->16; 4->8; 4->16; 8->16 si invers)')
            print('3. Operatia de adunare')
            print('4. Operatia de scadere')
            #print('5. Operatia de inmultire cu o cifra')
            #print('6. Operatia de impartire la o cifra')
            print('0. Iesire din aplicatie')
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