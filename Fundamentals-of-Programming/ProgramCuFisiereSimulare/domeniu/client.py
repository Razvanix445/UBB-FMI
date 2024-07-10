class Client:

    def __init__(self, id, nume, CNP):
        self.__id = id
        self.__nume = nume
        self.__CNP = CNP

    def get_id(self):
        return self.__id

    def get_nume(self):
        return self.__nume

    def get_CNP(self):
        return self.__CNP

    def set_nume(self, nume):
        self.__nume = nume

    def set_CNP(self, CNP):
        self.__CNP = CNP

    def __eq__(self, other):
        return self.__id == other.__id

    def __str__(self):
        return f'{self.__id}, {self.__nume}, {self.__CNP}'