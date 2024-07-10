class Problema:

    def __init__(self, nr_problema, descriere, deadline):
        self.__nr_problema = nr_problema
        self.__descriere = descriere
        self.__deadline = deadline
        self.__sters = False

    def sterge(self):
        self.__sters = True

    def get_nr_problema(self):
        return self.__nr_problema

    def get_descriere(self):
        return self.__descriere

    def get_deadline(self):
        return self.__deadline

    def set_descriere(self, descriere):
        self.__descriere = descriere

    def set_deadline(self, deadline):
        self.__deadline = deadline

    def __eq__(self, other):
        return self.__nr_problema == other.__nr_problema

    def __str__(self):
        return f"Problema {self.__nr_problema}: {self.__descriere}; deadline: {self.__deadline}"