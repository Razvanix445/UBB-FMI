from domeniu.problema import Problema

class ServiceProbleme:

    def __init__(self, validator_problema, repo_probleme):
        self.__validator_problema = validator_problema
        self.__repo_probleme = repo_probleme

    def adauga_problema(self, nr_problema, descriere, deadline):
        problema = Problema(nr_problema, descriere, deadline)
        self.__validator_problema.valideaza(problema)
        self.__repo_probleme.adauga_problema(problema)

    def modifica_problema(self, nr_problema, descriere, deadline):
        problema = Problema(nr_problema, descriere, deadline)
        self.__validator_problema.valideaza(problema)
        self.__repo_probleme.modifica_problema(problema)

    def sterge_problema(self, nr_problema):
        self.__repo_probleme.sterge_problema_dupa_nr(nr_problema)

    def cauta_problema(self, nr_problema):
        return self.__repo_probleme.cauta_problema_dupa_nr(nr_problema)

    def get_all_probleme(self):
        return self.__repo_probleme.get_all()