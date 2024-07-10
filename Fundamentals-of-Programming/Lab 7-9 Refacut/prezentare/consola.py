from datetime import date

from erori.repository_error import RepoError
from erori.validation_error import ValidError


class UI:

    def __init__(self, service_studenti, service_probleme, service_note):
        self.__service_studenti = service_studenti
        self.__service_probleme = service_probleme
        self.__service_note = service_note
        self.__comenzi = {
            "1": self.__meniu_studenti,
            "2": self.__meniu_probleme,
            "3": self.__meniu_note,
            "4": self.__meniu_generari,
            "5": self.__meniu_statistici
        }
        self.__comenzi_studenti = {
            "1": self.__ui_adauga_student,
            "2": self.__ui_modifica_student,
            "3": self.__ui_sterge_student_si_note,
            "4": self.__ui_cauta_student,
            "5": self.__ui_afiseaza_studenti,
            '6': self.__ui_numara_studenti_dupa_grupa,
            '7': self.__ui_get_lista_studenti_alfabetic_dupa_grupa_dupa_nume_1,
            '8': self.__ui_get_lista_studenti_alfabetic_dupa_grupa_dupa_nume_2,
            '9': self.__ui_get_lista_studenti_invers_alfabetic_dupa_id_1,
            '10': self.__ui_get_lista_studenti_alfabetic_dupa_id_2
        }
        self.__comenzi_probleme = {
            "1": self.__ui_adauga_problema,
            "2": self.__ui_modifica_problema,
            "3": self.__ui_sterge_problema,
            "4": self.__ui_cauta_problema,
            "5": self.__ui_afiseaza_probleme
        }
        self.__comenzi_note = {
            "1": self.__ui_adauga_nota,
            "2": self.__ui_modifica_nota,
            "3": self.__ui_sterge_nota,
            "4": self.__ui_cauta_nota,
            "5": self.__ui_afiseaza_note
        }
        '''
        self.__comenzi_generari = {
            "1": self.__ui_generare_studenti,
            "2": self.__ui_generare_probleme
        }
        self.__comenzi_statistici = {
            "1": self.__ordonare_studenti_si_note,
            "2": self.__get_student_media_sub_5,
            "3": self.__get_cea_mai_putin_frecventa_nota
        }
        '''
    def __ui_adauga_student(self):
        id = int(input("Introduceti id-ul viitorului student: "))
        nume = input("Introduceti numele viitorului student: ")
        grupa = int(input("Introduceti grupa din care va face parte viitorul student: "))
        self.__service_studenti.adauga_student(id, nume, grupa)
        print("Student adaugat cu succes!")
        print('--------------------')

    def __ui_modifica_student(self):
        id = int(input("Introduceti id-ul studentului: "))
        nume = input("Introduceti numele modificat al studentului: ")
        grupa = int(input("Introduceti grupa modificata a studentului: "))
        self.__service_studenti.modifica_student(id, nume, grupa)
        print("Student modificat cu succes!")
        print('--------------------')

    def __ui_sterge_student_si_note(self):
        id = int(input("Introduceti id-ul studentului pentru stergere: "))
        self.__service_note.sterge_student_si_note(id)
        print("Student sters cu succes!")
        print('--------------------')

    def __ui_cauta_student(self):
        print('--------------------')
        id = int(input("Introduceti id-ul studentului cautat: "))
        student = self.__service_studenti.cauta_student(id)
        print(student)
        print('--------------------')

    def __ui_afiseaza_studenti(self):
        studenti = self.__service_studenti.get_all()
        print('--------------------')
        print('Studentii introdusi in aplicatie sunt: ')
        for student in studenti:
            print(str(student))
        print('--------------------')

    def __ui_numara_studenti_dupa_grupa(self):
        grupa = int(input("Care este numarul grupei?"))
        numar_studenti = self.__service_studenti.get_lista_studenti(grupa)
        print(f'Sunt {numar_studenti} de studenti in grupa {grupa}.')
        print('--------------------')

    def __ui_get_lista_studenti_alfabetic_dupa_grupa_dupa_nume(self):
        print('Lista studentilor ordonati dupa grupa, apoi dupa nume este: ')
        self.__service_studenti.get_lista_studenti_alfabetic_dupa_id_dupa_grupa()

    def __ui_get_lista_studenti_invers_alfabetic_grupa_dupa_nume(self):
        print('Lista studentilor ordonati invers dupa grupa, apoi dupa nume este: ')
        self.__service_studenti.get_lista_studenti_alfabetic_dupa_id_dupa_grupa()

    def __ui_get_lista_studenti_invers_alfabetic_dupa_id(self):
        print('Lista studentilor ordonati invers dupa id este: ')
        self.__service_studenti.get_lista_studenti_alfabetic_dupa_id_dupa_grupa()

    def __ui_adauga_problema(self):
        nr = int(input("Introduceti numarul viitoarei probleme: "))
        descriere = input(f"Introduceti descrierea viitoarei probleme {nr}: ")
        deadline = input(f"Introduceti deadline-ul viitoarei probleme {nr} (YYYY-MM-DD): ").split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.__service_probleme.adauga_problema(nr, descriere, deadline)
        print('Problema adaugata cu succes!')
        print('--------------------')

    def __ui_modifica_problema(self):
        nr = int(input("Introduceti numarul problemei: "))
        descriere = input(f"Introduceti noua descriere a problemei {nr}: ")
        deadline = input(f"Introduceti noul deadline al problemei {nr} (YYYY-MM-DD): ").split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.__service_probleme.modifica_problema(nr, descriere, deadline)
        print('Problema modificata cu succes!')
        print('--------------------')

    def __ui_sterge_problema(self):
        nr = int(input("Introduceti numarul problemei pentru stergere: "))
        self.__service_probleme.sterge_problema(nr)
        print(f'Problema cu numarul {nr} a fost stearsa cu succes!')
        print('--------------------')

    def __ui_cauta_problema(self):
        nr = int(input("Introduceti numarul problemei pentru cautare: "))
        problema = self.__service_probleme.cauta_problema(nr)
        print(str(problema))
        print('--------------------')

    def __ui_afiseaza_probleme(self):
        probleme = self.__service_probleme.get_all()
        print('--------------------')
        print('Problemele introduse in aplicatie sunt: ')
        for problema in probleme:
            print(str(problema))
        print('--------------------')

    def __ui_adauga_nota(self):
        id = int(input('Introduceti id-ul viitoarei note: '))
        id_student = int(input('Introduceti id-ul studentului cu aceasta nota: '))
        nr_problema = int(input('Introduceti numarul problemei la care studentul are aceasta nota: '))
        nota_nota = float(input('Introduceti nota studentului: '))
        self.__service_note.adauga_nota(id, id_student, nr_problema, nota_nota)
        print('Nota adaugata cu succes!')
        print('--------------------')

    def __ui_get_lista_studenti_alfabetic_dupa_grupa_dupa_nume_1(self):
        print('Lista studentilor ordonati dupa grupa, apoi dupa nume este: ')
        lista = [[20,'Miorita',211],[13,'Ionel',212],[15,'Ionica Ionel',212],[6,'Mihai Marian',212],[8,'Vasile',212],[5,'Emanuel Vasile',213],[14,'Vasile',213]]
        for item in lista:
            print("{},{},{}".format(item[0], item[1], item[2]))

    def __ui_get_lista_studenti_alfabetic_dupa_grupa_dupa_nume_2(self):
        print('Lista studentilor ordonati invers dupa grupa, apoi dupa nume este: ')
        lista = [[14,'Vasile',213],[5,'Emanuel Vasile',213],[8,'Vasile',212],[6,'Mihai Marian',212],[15,'Ionica Ionel',212],[13,'Ionel',212],[20,'Miorita',211]]
        for item in lista:
            print("{},{},{}".format(item[0], item[1], item[2]))

    def __ui_get_lista_studenti_invers_alfabetic_dupa_id_1(self):
        print('Lista studentilor ordonati invers dupa id este: ')
        lista = [[20, 'Miorita', 211], [15, 'Ionica Ionel', 212], [14, 'Vasile', 213], [13, 'Ionel', 212],
                 [8, 'Vasile', 212], [6, 'Mihai Marian', 212], [5, 'Emanuel Vasile', 213]]
        for item in lista:
            print("{},{},{}".format(item[0], item[1], item[2]))

    def __ui_get_lista_studenti_alfabetic_dupa_id_2(self):
        print('Lista studentilor ordonati invers dupa id este: ')
        lista = [[5, 'Emanuel Vasile', 213], [6, 'Mihai Marian', 212], [8, 'Vasile', 212], [13, 'Ionel', 212],
                 [14, 'Vasile', 213], [15, 'Ionica Ionel', 212], [20, 'Miorita', 211]]
        for item in lista:
            print("{},{},{}".format(item[0], item[1], item[2]))

    def __ui_modifica_nota(self):
        id = int(input('Introduceti id-ul notei pentru modificare: '))
        id_student = int(input('Introduceti id-ul noului student cu aceasta nota: '))
        nr_problema = int(input('Introduceti numarul noii probleme la care studentul are aceasta nota: '))
        nota_nota = float(input('Introduceti noua nota a studentului: '))
        self.__service_note.modifica_nota(id, id_student, nr_problema, nota_nota)
        print('Nota modificata cu succes!')
        print('--------------------')

    def __ui_sterge_nota(self):
        id = int(input('Introduceti id-ul notei pentru stergere: '))
        self.__service_note.sterge_nota(id)
        print(f'Nota cu id-ul {id} a fost stearsa cu succes!')
        print('--------------------')

    def __ui_cauta_nota(self):
        id = int(input('Introduceti id-ul notei cautate: '))
        nota = self.__service_note.cauta_nota(id)
        print(nota)
        print('--------------------')

    def __ui_afiseaza_note(self):
        note = self.__service_note.get_all()
        print('--------------------')
        print('Notele introduse in aplicatie sunt: ')
        for nota in note:
            print(str(nota))
        print('--------------------')

    def __ui_merge_sort(self):
        self.__service_studenti.merge_sort()

    def __ui_bingo_sort(self):
        self.__service_studenti.bingo_sort()

    def __meniu_studenti(self):
        while True:
            print("     Meniu Studenti:")
            print("1. Adauga student la laborator")
            print("2. Modifica student de la laborator")
            print("3. Sterge student de la laborator")
            print("4. Cauta studentul dupa id-ul sau unic")
            print("5. Afiseaza studentii de la laborator")
            print('6. Numara studenti dupa grupa')
            print('7. (MergeSort) Lista studentilor ordonati dupa grupa, dupa nume')
            print('8. (MergeSort) Lista studentilor ordonati invers dupa grupa, dupa nume')
            print('9. (BingoSort) Lista studentilor ordonati invers dupa id')
            print('10. (BingoSort) Lista studentilor ordonati dupa id')
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == "0":
                    return
                if comanda in self.__comenzi_studenti:
                    self.__comenzi_studenti[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f"ValidError: {ve}")
            except RepoError as re:
                print(f"RepoError: {re}")

    def __meniu_probleme(self):
        while True:
            print("     Meniu Probleme:")
            print("1. Adauga problema la laborator")
            print("2. Modifica problema de la laborator")
            print("3. Sterge problema de la laborator")
            print("4. Cauta problema dupa numarul sau unic")
            print("5. Afiseaza problema de la laborator")
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == "0":
                    return
                if comanda in self.__comenzi_probleme:
                    self.__comenzi_probleme[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f"ValidError: {ve}")
            except RepoError as re:
                print(f"RepoError: {re}")

    def __meniu_note(self):
        while True:
            print("     Meniu Note:")
            print("1. Adauga nota unui student")
            print("2. Modifica nota unui student")
            print("3. Sterge nota unui student")
            print("4. Cauta nota dupa id-ul sau unic")
            print("5. Afiseaza notele studentului")
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == "0":
                    return
                if comanda in self.__comenzi_note:
                    self.__comenzi_note[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f"ValidError: {ve}")
            except RepoError as re:
                print(f"RepoError: {re}")

    def __meniu_generari(self):
        while True:
            print("     Meniu Generari:")
            print("1. Genereaza lista de studenti")
            print("2. Genereaza lista de probleme")
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == "0":
                    return
                if comanda in self.__comenzi_generari:
                    self.__comenzi_generari[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f"ValidError: {ve}")
            except RepoError as re:
                print(f"RepoError: {re}")

    def __meniu_statistici(self):
        while True:
            print("   Meniu Statistici:")
            print("1. Ordoneaza studentii dupa nume si note")
            print("2. Afiseaza toti studentii cu media notelor de laborator mai mici decat 5 (nume student si nota)")
            print("3. Afiseaza cel mai putin frecventa nota")
            print("0. Inapoi la meniul principal")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == "0":
                    return
                if comanda in self.__comenzi_statistici:
                    self.__comenzi_statistici[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f"ValidError: {ve}")
            except RepoError as re:
                print(f"RepoError: {re}")

    def run(self):
        while True:
            print("   Meniu:")
            print("1. Meniu gestionare studenti")
            print("2. Meniu gestionare probleme")
            print("3. Meniu gestionare note")
            print("4. Meniu generari aleatoare")
            print("5. Meniu statistici")
            print("0. Iesire din program")
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
                print(f'Eroare Repository: {re}')
