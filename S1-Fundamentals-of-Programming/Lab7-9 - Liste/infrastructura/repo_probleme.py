from erori.repository_error import RepoError


class RepoProbleme:

    def __init__(self):
        self.__probleme = {}

    def adauga_problema(self, problema):
        '''
        functia adauga o problema in dictionarul de probleme daca numarul problemei respective este unic
        :param problema: problema
        :return: - daca numarul problemei nu gaseste printre numerele problemelor, se arunca eroare de tipul RepoError cu mesajul "problema existenta!"
        '''
        if problema.get_nr_problema() in self.__probleme:
            raise RepoError("problema existenta!")
        self.__probleme[problema.get_nr_problema()] = problema

    def sterge_problema_dupa_nr(self, nr_problema):
        '''
        functia sterge o problema din dictionarul de probleme daca numarul problemei se gaseste printre numerele problemelor
        :param nr_problema: int
        :return: - daca numarul problemei nu se gaseste printre numerele problemelor, se arunca eroare de tipul RepoError cu mesajul "problema inexistenta!"
        '''
        if nr_problema not in self.__probleme:
            raise RepoError("problema inexistenta!")
        del self.__probleme[nr_problema]

    def cauta_problema_dupa_nr(self, nr_problema):
        '''
        functia cauta o problema in dictionarul de probleme prin numarul sau unic
        :param nr_problema: int
        :return: lista - daca numarul problemei se gaseste printre numerele problemelor
                        altfel, se arunca eroare de tipul RepoError cu mesajul "problema inexistenta!"
        '''
        if nr_problema not in self.__probleme:
            raise RepoError("problema inexistenta!")
        return self.__probleme[nr_problema]

    def modifica_problema(self, problema):
        '''
        functia modifica descrierea si deadline-ul unei probleme din dictionarul de probleme
        :param problema: problema
        :return: - daca nu se gaseste numarul problemei, se arunca eroare de tipul RepoError cu mesajul "problema inexistenta!"
        '''
        if problema.get_nr_problema() not in self.__probleme:
            raise RepoError("problema inexistenta!")
        self.__probleme[problema.get_nr_problema()] = problema

    def get_all(self):
        '''
        functia returneaza lista de probleme introduse la laborator
        :return: probleme - lista de probleme introduse la laborator
        '''
        probleme = []
        for problema_nr in self.__probleme:
            probleme.append(self.__probleme[problema_nr])
        return probleme

    def __len__(self):
        return len(self.__probleme)