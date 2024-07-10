import datetime
from erori.validation_error import ValidError


class ValidatorProblema:

    def __init__(self):
        pass

    def valideaza(self, problema):
        erori = ""
        if problema.get_nr() < 1:
            erori += "nr invalid!\n"
        if problema.get_descriere() == "":
            erori += "descriere invalida!\n"
        if problema.get_deadline() < datetime.date.today():
            erori += "deadline invalid!\n"
        if len(erori) != 0:
            raise ValidError(erori)