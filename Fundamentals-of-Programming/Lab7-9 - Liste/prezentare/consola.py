from datetime import date

from erori.repository_error import RepoError
from erori.validation_error import ValidError
class UI():

    def __init__(self, service_studenti, service_probleme, service_note):
        self.__service_studenti = service_studenti
        self.__service_probleme = service_probleme
        self.__service_note = service_note
        self.__comenzi = {
            "1": self.__ui_adauga_student,
            "2": self.__ui_modifica_student,
            "3": self.__ui_sterge_student_si_note,
            "4": self.__ui_afiseaza_studenti,
            "5": self.__ui_cauta_student,
            "6": self.__ui_adauga_problema,
            "7": self.__ui_modifica_problema,
            "8": self.__ui_sterge_problema,
            "9": self.__ui_afiseaza_probleme,
            "10": self.__ui_cauta_problema
        }

    def __ui_adauga_problema(self):
        nr_problema = int(input("Introduceti numarul problemei: "))
        descriere = input("Introduceti o descriere a problemei: ")
        deadline = input("Introduceti deadline-ul problemei (YYYY-MM-DD): ").split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.__service_probleme.adauga_problema(nr_problema, descriere, deadline)
        print("Problema adaugata cu succes!")

    def __ui_modifica_problema(self):
        nr_problema = int(input("Introduceti numarul problemei: "))
        descriere = input("Introduceti o descriere a problemei: ")
        deadline = input("Introduceti deadline-ul problemei (YYYY-MM-DD): ").split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.__service_probleme.modifica_problema(nr_problema, descriere, deadline)
        print("Problema modificata cu succes!")

    def __ui_sterge_problema(self):
        nr_problema = int(input("Introduceti numarul problemei care va urma sa fie stearsa:"))
        self.__service_probleme.sterge_problema(nr_problema)
        print(f"Problema cu numarul {nr_problema} a fost stearsa cu succes!")

    def __ui_afiseaza_probleme(self):
        probleme = self.__service_probleme.get_all_probleme()
        if len(probleme) == 0:
            print("Nu exista studenti in aplicatie!")
            return
        for problema in probleme:
            print(problema.__str__())

    def __ui_cauta_problema(self):
        nr_problema = int(input("Introduceti numarul problemei cautate: "))
        problema = self.__service_probleme.cauta_problema(nr_problema)
        print(problema.__str__())

    def __ui_adauga_student(self):
        id_student = int(input("Introduceti id-ul studentului: "))
        nume = input("Introduceti numele studentului: ")
        grupa = int(input("Introduceti grupa din care face parte studentul: "))
        self.__service_studenti.adauga_student(id_student, nume, grupa)
        print("Student adaugat cu succes!")

    def __ui_modifica_student(self):
        id_student = int(input("Introduceti id-ul studentului: "))
        nume = input("Introduceti numele studentului: ")
        grupa = int(input("Introduceti grupa din care face parte studentul: "))
        self.__service_studenti.modifica_student(id_student, nume, grupa)
        print("Student modificat cu succes!")

    def __ui_sterge_student_si_note(self):
        id_student = int(input("Introduceti id-ul studentului care va fi sters:"))
        self.__service_note.sterge_student_si_note(id_student)
        print(f"Studentul cu id-ul {id_student} si notele lui au fost sterse cu succes!")

    def __ui_afiseaza_studenti(self):
        studenti = self.__service_studenti.get_all_studenti()
        if len(studenti) == 0:
            print("Nu exista studenti in aplicatie!")
            return
        for student in studenti:
            print(student.__str__())

    def __ui_cauta_student(self):
        id_student = int(input("Introduceti id-ul studentului cautat:"))
        student = self.__service_studenti.cauta_student(id_student)
        print(student.__str__())

    def run(self):
        while True:
            print("   Meniu:")
            print("1. Adauga student la laborator")
            print("2. Modifica student de la laborator")
            print("3. Sterge student de la laborator")
            print("4. Afiseaza studentii de la laborator")
            print("5. Cauta studentul dupa id-ul sau")
            print("6. Adauga problema la laborator")
            print("7. Modifica problema de la laborator")
            print("8. Sterge problema de la laborator")
            print("9. Afiseaza problema de la laborator")
            print("10. Cauta problema dupa numarul sau")
            try:
                comanda = input(">>>")
                if comanda == "":
                    continue
                if comanda == "exit":
                    return
                if comanda in self.__comenzi:
                    self.__comenzi[comanda]()
                else:
                    print("Comanda necunoscuta!")
            except ValueError:
                print("Eroare UI: tip numeric invalid!")
            except ValidError as ve:
                print(f"ValidError: {ve}")
            except RepoError as re:
                print(f"RepoError: {re}")