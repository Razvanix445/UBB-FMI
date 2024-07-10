from erori.validation_error import ValidError


class ValidatorStudent:

    def __init__(self):
        pass

    def valideaza(self, student):
        erori = ""
        if student.get_id() < 0:
            erori += "id_invalid!\n"
        if student.get_nume() == "":
            erori += "nume invalid!\n"
        if student.get_grupa() <= 0:
            erori += "grupa invalida!\n"
        if len(erori) != 0:
            raise ValidError(erori)