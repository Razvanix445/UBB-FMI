class Problema:

    def __init__(self, nr, descriere, deadline):
        self.__nr = nr
        self.__descriere = descriere
        self.__deadline = deadline

    def get_nr(self):
        return self.__nr

    def get_descriere(self):
        return self.__descriere

    def get_deadline(self):
        return self.__deadline

    def set_descriere(self, descriere):
        self.__descriere = descriere

    def set_deadline(self, deadline):
        self.__deadline = deadline

    def __eq__(self, other):
        return self.__nr == other.__nr

    def __str__(self):
        return f'Problema {self.__nr}: {self.__descriere}; deadline: {self.__deadline}'

    def string_for_write(self):
        return f'{self.__nr},{self.__descriere},{self.__deadline}'