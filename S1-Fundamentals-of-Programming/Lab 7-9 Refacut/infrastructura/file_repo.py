from domeniu.nota import Nota
from domeniu.problema import Problema
from domeniu.student import Student
from infrastructura.repo_note import RepoNote
from infrastructura.repo_probleme import RepoProbleme
from infrastructura.repo_studenti import RepoStudenti


class FileRepoStudenti(RepoStudenti):

    def __init__(self):
        RepoStudenti.__init__(self)

    def return_all_from_file(self, studenti):
        with open('student.txt', 'r') as file:
            linii = file.readlines()
            self._studenti.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(", ")
                    id = int(parti[0])
                    nume = parti[1]
                    grupa = int(parti[2])
                    student = Student(id, nume, grupa)
                    self._studenti[id] = student
                    studenti.append(student)
        return studenti

    def __read_all_from_file(self):
        with open('student.txt', 'r') as file:
            linii = file.readlines()
            self._studenti.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(",")
                    id = int(parti[0])
                    nume = parti[1]
                    grupa = int(parti[2])
                    student = Student(id, nume, grupa)
                    self._studenti[id] = student

    def __write_all_to_file(self):
        with open('student.txt', 'w') as file:
            for student in self._studenti:
                file.write(self._studenti[student].string_for_write() + '\n')

    def adauga_student(self, student):
        self.__read_all_from_file()
        RepoStudenti.adauga_student(self, student)
        self.__write_all_to_file()

    def modifica_student(self, student):
        self.__read_all_from_file()
        RepoStudenti.modifica_student(self, student)
        self.__write_all_to_file()

    def sterge_student(self, id):
        self.__read_all_from_file()
        RepoStudenti.sterge_student(self, id)
        self.__write_all_to_file()

    def cauta_student(self, id):
        self.__read_all_from_file()
        return RepoStudenti.cauta_student(self, id)

    def get_all(self):
        self.__read_all_from_file()
        return RepoStudenti.get_all(self)

    def size(self):
        self.__read_all_from_file()
        return RepoStudenti.size(self)

class FileRepoProbleme(RepoProbleme):

    def __init__(self):
        RepoProbleme.__init__(self)

    def return_all_from_file(self, probleme):
        with open('problema.txt', 'r') as file:
            linii = file.readlines()
            self._probleme.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(",")
                    nr = int(parti[0])
                    descriere = parti[1]
                    deadline = parti[2]
                    problema = Problema(nr, descriere, deadline)
                    self._probleme[nr] = problema
                    probleme.append(problema)
        return probleme

    def __read_all_from_file(self):
        with open('problema.txt', 'r') as file:
            linii = file.readlines()
            self._probleme.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(",")
                    nr = int(parti[0])
                    descriere = parti[1]
                    deadline = parti[2]
                    problema = Problema(nr, descriere, deadline)
                    self._probleme[nr] = problema

    def __write_all_to_file(self):
        with open('problema.txt', 'w') as file:
            for problema in self._probleme:
                file.write(self._probleme[problema].string_for_write() + '\n')

    def adauga_problema(self, problema):
        self.__read_all_from_file()
        RepoProbleme.adauga_problema(self, problema)
        self.__write_all_to_file()

    def modifica_problema(self, problema):
        self.__read_all_from_file()
        RepoProbleme.modifica_problema(self, problema)
        self.__write_all_to_file()

    def sterge_problema(self, nr):
        self.__read_all_from_file()
        RepoProbleme.sterge_problema(self, nr)
        self.__write_all_to_file()

    def cauta_problema(self, nr):
        self.__read_all_from_file()
        return RepoProbleme.cauta_problema(self, nr)

    def get_all(self):
        self.__read_all_from_file()
        return RepoProbleme.get_all(self)

    def size(self):
        self.__read_all_from_file()
        return RepoProbleme.size(self)

class FileRepoNote(RepoNote):

    def __init__(self):
        RepoNote.__init__(self)

    def __return_all_from_file(self, note):
        with open('nota.txt', 'r') as file:
            linii = file.readlines()
            self._note.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(",")
                    id = int(parti[0])
                    id_student = int(parti[1])
                    nr_problema = int(parti[2])
                    nota_nota = int(parti[3])
                    nota = Nota(id, id_student, nr_problema, nota_nota)
                    self._note[id] = nota
                    note.append(nota)
        return note

    def __read_all_from_file(self):
        with open('nota.txt', 'r') as file:
            linii = file.readlines()
            self._note.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(",")
                    id = int(parti[0])
                    id_student = int(parti[1])
                    nr_problema = int(parti[2])
                    nota_nota = float(parti[3])
                    nota = Nota(id, id_student, nr_problema, nota_nota)
                    self._note[id] = nota

    def __write_all_to_file(self):
        with open('nota.txt', 'w') as file:
            for nota in self._note:
                file.write(self._note[nota].string_for_write() + '\n')

    def adauga_nota(self, nota):
        self.__read_all_from_file()
        RepoNote.adauga_nota(self, nota)
        self.__write_all_to_file()

    def modifica_nota(self, nota):
        self.__read_all_from_file()
        RepoNote.modifica_nota(self, nota)
        self.__write_all_to_file()

    def sterge_nota(self, id):
        self.__read_all_from_file()
        RepoNote.sterge_nota(self, id)
        self.__write_all_to_file()

    def cauta_nota(self, id):
        self.__read_all_from_file()
        return RepoNote.cauta_nota(self, id)

    def get_all(self):
        self.__read_all_from_file()
        return RepoNote.get_all(self)

    def size(self):
        self.__read_all_from_file()
        return RepoNote.size(self)