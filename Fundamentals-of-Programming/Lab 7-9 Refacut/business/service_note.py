from domeniu.nota import Nota


class ServiceNote:

    def __init__(self, validator_nota, file_repo_note, file_repo_studenti, file_repo_probleme):
        self.__validator_nota = validator_nota
        self.__file_repo_note = file_repo_note
        self.__file_repo_studenti = file_repo_studenti
        self.__file_repo_probleme = file_repo_probleme

    def adauga_nota(self, id, id_student, nr_problema, nota_nota):
        student = self.__file_repo_studenti.cauta_student(id_student)
        problema = self.__file_repo_probleme.cauta_problema(nr_problema)
        nota = Nota(id, student, problema, nota_nota)
        self.__validator_nota.valideaza(id_student, nr_problema, nota)
        self.__file_repo_note.adauga_nota(nota)

    def modifica_nota(self, id, id_student, nr_problema, nota_nota):
        student = self.__file_repo_studenti.cauta_student(id_student)
        problema = self.__file_repo_probleme.cauta_problema(nr_problema)
        nota = Nota(id, student, problema, nota_nota)
        self.__validator_nota.valideaza(id_student, nr_problema, nota)
        self.__file_repo_note.modifica_nota(nota)

    def sterge_student_si_note(self, id):
        student = self.__file_repo_studenti.cauta_student(id)
        ok = False
        if self.__file_repo_note.get_all() != "Nu exista note inregistrate!":
            ok = True
        note_student = []
        if ok == True:
            note = self.__file_repo_note.get_all()
            for nota in note:
                if nota.get_student() == student:
                    note_student.append(nota)
            for nota_student in note_student:
                self.__file_repo_note.sterge_nota(nota_student.get_id())
        self.__file_repo_studenti.sterge_student(id)

    def sterge_nota(self, id):
        self.__file_repo_note.sterge_nota(id)

    def cauta_nota(self, id):
        return self.__file_repo_note.cauta_nota(id)

    def get_all(self):
        return self.__file_repo_note.get_all()

    def size(self):
        return self.__file_repo_note.size()