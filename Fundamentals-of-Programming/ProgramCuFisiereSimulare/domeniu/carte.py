class Carte:

    def __init__(self, id, titlu, descriere, autor):
        self.__id = id
        self.__titlu = titlu
        self.__descriere = descriere
        self.__autor = autor

    def get_id(self):
        return self.__id

    def get_titlu(self):
        return self.__titlu

    def get_descriere(self):
        return self.__descriere

    def get_autor(self):
        return self.__autor

    def set_titlu(self, titlu):
        self.__titlu = titlu

    def set_descriere(self, descriere):
        self.__descriere = descriere

    def set_autor(self, autor):
        self.__autor = autor

    def __eq__(self, other):
        return self.__id == other.__id

    def __str__(self):
        return f'{self.__id}, {self.__titlu}, {self.__descriere}, {self.__autor}'