import datetime
from erori.validation_error import ValidError


class ValidatorProblema:

    def __init__(self):
        pass

    def valideaza(self, problema):
        erori = ""
        if problema.get_nr_problema() <= 0:
            erori += "problema invalida!\n"
        if problema.get_deadline() < datetime.date.today():
            erori += "deadline invalid!\n"
        if len(erori) > 0:
            raise ValidError(erori)