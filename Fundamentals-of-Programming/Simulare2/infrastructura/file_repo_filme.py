from domeniu.film import Film
from infrastructura.repo_filme import RepoFilme


class FileRepoFilme(RepoFilme):

    def __init__(self, calea_catre_fisier):
        RepoFilme.__init__(self)
        self.__calea_catre_fisier = calea_catre_fisier

    def __read_all_from_file(self):
        with open(self.__calea_catre_fisier, "r") as f:
            linii = f.readlines()
            self._filme.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(", ")
                    id_film = int(parti[0])
                    titlu = parti[1]
                    descriere = parti[2]
                    gen = parti[3]
                    film = Film(id_film, titlu, descriere, gen)
                    self._filme[id_film] = film

    def __write_all_to_file(self):
        with open(self.__calea_catre_fisier, "w") as f:
            for film in self._filme.values():
                f.write(str(film) + '\n')

    def adauga_film(self, film):
        self.__read_all_from_file()
        RepoFilme.adauga_film(self, film)
        self.__write_all_to_file()

    def modifica_film(self, film):
        self.__read_all_from_file()
        RepoFilme.modifica_film(self, film)
        self.__write_all_to_file()

    def sterge_film(self, id_film):
        self.__read_all_from_file()
        RepoFilme.sterge_film(self, id_film)
        self.__write_all_to_file()

    def cauta_film(self, id_film):
        self.__read_all_from_file()
        return RepoFilme.cauta_film(self, id_film)

    def get_all(self):
        self.__read_all_from_file()
        return RepoFilme.get_all(self)

    def size(self):
        self.__read_all_from_file()
        return RepoFilme.size(self)