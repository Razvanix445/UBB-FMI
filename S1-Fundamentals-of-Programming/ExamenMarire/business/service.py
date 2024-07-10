from domeniu.jucator import Jucator


class Service:

    def __init__(self, validator, file_repo):
        self.__validator = validator
        self.__file_repo = file_repo

    def adaugare(self, nume, prenume, inaltime, post):
        '''
        Se adauga jucatori in aplicatie
        :param nume: string
        :param prenume: string
        :param inaltime: int
        :param post: string
        :return: - Se adauga jucatori in aplicatie
        '''
        id = (nume, prenume)
        jucator = Jucator(nume, prenume, inaltime, post)
        self.__validator.valideaza(jucator)
        self.__file_repo.adaugare(id, jucator)

    def modificare(self, noua_inaltime):
        '''
        Se modifica inaltimea jucatorilor din aplicatie
        :return: - Se va modifica inaltimea jucatorilor din aplicatie adunand sau scazand
        '''
        self.__file_repo.modificare(noua_inaltime)

    def tiparire(self):
        self.__file_repo.tiparire()

    def importare(self, nume_fisier):
        '''
        Se importa nume si prenume din fisier
        :return: - Se afisaeaza numarul de jucatori introdusi
        '''
        return self.__file_repo.importare(nume_fisier)

    def get_all(self):
        '''
        Se returneaza toti jucatorii din aplicatie
        :return: se returneaza toti jucatorii din aplicatie
        '''
        return self.__file_repo.get_all()

    def size(self):
        '''
        marimea dictionarului de jucatori
        :return: returneaza marimea dictionarului de jucatori
        '''
        return self.__file_repo.size()