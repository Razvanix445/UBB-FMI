from domeniu.carte import Carte
from infrastructura.repo_carti import RepoCarti


class FileRepoCarti(RepoCarti):

    def __init__(self, calea_catre_fisier):
        RepoCarti.__init__(self)
        self.__calea_catre_fisier = calea_catre_fisier

    def __read_all_from_file(self):
        with open(self.__calea_catre_fisier, "r") as f:
            linii = f.readlines()
            self._carti.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(", ")
                    id = int(parti[0])
                    titlu = parti[1]
                    descriere = parti[2]
                    autor = parti[3]
                    carte = Carte(id, titlu, descriere, autor)
                    self._carti[id] = carte

    def __write_all_to_file(self):
        with open(self.__calea_catre_fisier, "w") as f:
            for carte in self._carti.values():
                f.write(str(carte) + '\n')

    def adauga_carte(self, carte):
        self.__read_all_from_file()
        RepoCarti.adauga_carte(self, carte)
        self.__write_all_to_file()

    def cauta_carte(self, id):
        self.__read_all_from_file()
        return RepoCarti.cauta_carte(self, id)

    def get_all(self):
        self.__read_all_from_file()
        return RepoCarti.get_all(self)

    def size(self):
        self.__read_all_from_file()
        return RepoCarti.size(self)