from erori.repository_error import RepoError


class RepoCarti:

    def __init__(self):
        self._carti = {}

    def adauga_carte(self, carte):
        if carte.get_id() in self._carti:
            raise RepoError("Carte existenta!")
        self._carti[carte.get_id()] = carte

    def cauta_carte(self, id):
        if id not in self._carti:
            raise RepoError("Carte inexistenta!")
        return self._carti[id]

    def get_all(self):
        return [x for x in self._carti.values()]

    def size(self):
        return len(self._carti)