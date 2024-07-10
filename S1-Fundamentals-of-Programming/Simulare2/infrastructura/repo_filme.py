from erori.repository_error import RepoError


class RepoFilme:

    def __init__(self):
        self._filme = {}

    def adauga_film(self, film):
        if film.get_id_film() in self._filme:
            raise RepoError("Film existent!")
        self._filme[film.get_id_film()] = film

    def modifica_film(self, film):
        if film.get_id_film() not in self._filme:
            raise RepoError("Film inexistent!")
        self._filme[film.get_id_film()] = film

    def sterge_film(self, id_film):
        if id_film not in self._filme:
            raise RepoError("Film inexistent!")
        del self._filme[id_film]

    def cauta_film(self, id_film):
        if id_film not in self._filme:
            return "Film inexistent!"
        return self._filme[id_film]

    def get_all(self):
        return [film for film in self._filme.values()]

    def size(self):
        return len(self._filme)