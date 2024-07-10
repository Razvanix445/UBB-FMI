from domeniu.problema import Problema


class ServiceProbleme:

    def __init__(self, validator_problema, file_repo_probleme):
        self.__validator_problema = validator_problema
        self.__file_repo_probleme = file_repo_probleme

    def adauga_problema(self, nr, descriere, deadline):
        problema = Problema(nr, descriere, deadline)
        self.__validator_problema.valideaza(problema)
        self.__file_repo_probleme.adauga_problema(problema)

    def modifica_problema(self, nr, descriere, deadline):
        problema = Problema(nr, descriere, deadline)
        self.__validator_problema.valideaza(problema)
        self.__file_repo_probleme.modifica_problema(problema)

    def sterge_problema(self, nr):
        self.__file_repo_probleme.sterge_problema(nr)

    def cauta_problema(self, nr):
        return self.__file_repo_probleme.cauta_problema(nr)

    def get_all(self):
        return self.__file_repo_probleme.get_all()

    def size(self):
        return self.__file_repo_probleme.size()