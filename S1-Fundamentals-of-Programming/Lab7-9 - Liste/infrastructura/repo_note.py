from erori.repository_error import RepoError


class RepoNote:

    def __init__(self):
        self.__note = {}

    def adauga_nota(self, nota):
        if nota.get_id_nota() in self.__note:
            raise RepoError("nota existenta!")
        self.__note[nota.get_id_nota()] = nota

    def sterge_nota_dupa_id(self, id_nota):
        if id_nota not in self.__note:
            raise RepoError("nota inexistenta!")
        del self.__note[id_nota]

    def cauta_nota_dupa_id(self, id_nota):
        if id_nota not in self.__note:
            raise RepoError("nota inexistenta!")
        return self.__note[id_nota]

    def modifica_nota(self, nota):
        if nota.get_id_nota() not in self.__note:
            raise RepoError("nota inexistenta!")
        self.__note[nota.get_id_nota()] = nota

    def get_all(self):
        note = []
        for nota_id in self.__note:
            note.append(self.__note[nota_id])
        return note