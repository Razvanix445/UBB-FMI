from domeniu.jucator import Jucator
from infrastructura.repo import Repo


class FileRepo(Repo):

    def __init__(self, cale_catre_fisier):
        Repo.__init__(self)
        self.__cale_catre_fisier = cale_catre_fisier

    def __read_all_from_file(self):
        with open(self.__cale_catre_fisier, 'r') as file:
            linii = file.readlines()
            self._jucatori.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != '':
                    parti = linie.split(',')
                    nume = parti[0]
                    prenume = parti[1]
                    inaltime = int(parti[2])
                    post = parti[3]
                    jucator = Jucator(nume, prenume, inaltime, post)
                    id = (nume, prenume)
                    self._jucatori[id] = jucator

    def __write_all_to_file(self):
        with open(self.__cale_catre_fisier, 'w') as file:
            for jucator_id in self._jucatori:
                file.write(str(self._jucatori[jucator_id]) + '\n')

    def adaugare(self, id, jucator):
        '''
        Se adauga jucatori in aplicatie
        :return: - Se adauga jucatori in aplicatie
        '''
        self.__read_all_from_file()
        Repo.adaugare(self, id, jucator)
        self.__write_all_to_file()

    def modificare(self, noua_inaltime):
        '''
        Se modifica inaltimea jucatorilor din aplicatie
        :return: - Se va modifica inaltimea jucatorilor din aplicatie adunand sau scazand
        '''
        self.__read_all_from_file()
        Repo.modificare(self, noua_inaltime)
        self.__write_all_to_file()

    def tiparire(self):
        self.__read_all_from_file()
        Repo.tiparire(self)

    def importare(self, nume_fisier):
        '''
        :param nume_fisier: numele fisierului in care sunt numele si prenumele
        :return: returneaza numarul de jucatori introdusi
        '''
        return Repo.importare(self, nume_fisier)

    def get_all(self):
        '''
        Se returneaza toti jucatorii din aplicatie
        :return: se returneaza toti jucatorii din aplicatie
        '''
        self.__read_all_from_file()
        return Repo.get_all(self)

    def size(self):
        self.__read_all_from_file()
        Repo.size(self)