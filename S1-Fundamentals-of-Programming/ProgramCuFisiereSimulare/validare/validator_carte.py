from erori.validation_error import ValidError


class ValidatorCarte:

    def __init__(self):
        pass

    def valideaza(self, carte):
        erori = ""
        if carte.get_id() < 0:
            erori += "id invalid!\n"
        if carte.get_titlu() == "":
            erori += "titlu invalid!\n"
        if carte.get_descriere() == "":
            erori += "descriere invalida!\n"
        if carte.get_autor() == "":
            erori += "autor invalid!\n"
        if len(erori) > 0:
            raise ValidError(erori)