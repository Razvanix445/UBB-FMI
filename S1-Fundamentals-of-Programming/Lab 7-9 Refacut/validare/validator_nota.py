from erori.validation_error import ValidError


class ValidatorNota:

    def __init__(self, repo_studenti, repo_probleme):
        self.__repo_studenti = repo_studenti
        self.__repo_probleme = repo_probleme

    def valideaza(self, id_student, nr_problema, nota):
        erori = ""
        if nota.get_id() < 0:
            erori += "id invalid!\n"

        studenti = self.__repo_studenti.get_all()
        exista = False
        for student in studenti:
            if student.get_id() == id_student:
                exista = True
        if exista == False:
            erori += "student invalid!\n"

        probleme = self.__repo_probleme.get_all()
        exista = False
        for problema in probleme:
            if problema.get_nr() == nr_problema:
                exista = True
        if exista == False:
            erori += "problema invalida!\n"

        if nota.get_nota() < 0.0 or nota.get_nota() > 10.0:
            erori += "nota invalida!\n"

        if len(erori) != 0:
            raise ValidError(erori)