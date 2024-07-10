from erori.repository_error import RepoError


class RepoNote:

    def __init__(self):
        self._note = {}

    def adauga_nota(self, nota):
        if nota.get_id() in self._note:
            raise RepoError("Nota existenta!")
        self._note[nota.get_id()] = nota

    def modifica_nota(self, nota):
        if nota.get_id() not in self._note:
            raise RepoError("Nota inexistenta!")
        self._note[nota.get_id()] = nota

    def sterge_nota(self, id_nota):
        if id_nota not in self._note:
            raise RepoError("Nota inexistenta!")
        del self._note[id_nota]

    def cauta_nota(self, id_nota):
        if id_nota not in self._note:
            raise RepoError("Nota inexistenta!")
        return self._note[id_nota]

    def get_all(self):
        if self.size() == 0:
            return "Nu exista note inregistrate!"
        note = []
        for nota_id in self._note:
            note.append(self._note[nota_id])
        return note

    def size(self):
        return len(self._note)