from erori.repository_error import RepoError


class RepoProbleme:

    def __init__(self):
        self._probleme = {}

    def adauga_problema(self, problema):
        if problema.get_nr() in self._probleme:
            raise RepoError("Problema existenta!")
        self._probleme[problema.get_nr()] = problema

    def modifica_problema(self, problema):
        if problema.get_nr() not in self._probleme:
            raise RepoError("Problema inexistenta!")
        self._probleme[problema.get_nr()] = problema

    def sterge_problema(self, nr_problema):
        if nr_problema not in self._probleme:
            raise RepoError("Problema inexistenta!")
        del self._probleme[nr_problema]

    def cauta_problema(self, nr_problema):
        if nr_problema not in self._probleme:
            raise RepoError("Problema inexistenta!")
        return self._probleme[nr_problema]

    def get_all(self):
        if self.size() == 0:
            raise RepoError("Nu exista probleme inregistrate!")
        probleme = []
        for problema_id in self._probleme:
            probleme.append(self._probleme[problema_id])
        return probleme

    def size(self):
        return len(self._probleme)