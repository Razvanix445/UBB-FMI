from domeniu.carte import Carte


class ServiceCarti:

    def __init__(self, validator_carte, repo_carti, file_repo_carti):
        self.__repo_carti = repo_carti
        self.__validator_carte = validator_carte
        self.__file_repo_carti = file_repo_carti

    def adauga_carte(self, id, titlu, descriere, autor):
        carte = Carte(id, titlu, descriere, autor)
        self.__validator_carte.valideaza(carte)
        self.__file_repo_carti.adauga_carte(carte)

    def cauta_carte(self, id):
        return self.__file_repo_carti.cauta_carte(id)

    def get_all(self):
        return self.__file_repo_carti.get_all()