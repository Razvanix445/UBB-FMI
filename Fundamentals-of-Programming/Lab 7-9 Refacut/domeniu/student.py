class Student:

    def __init__(self, id, nume, grupa):
        self.__id = id
        self.__nume = nume
        self.__grupa = grupa

    def get_id(self):
        return self.__id

    def get_nume(self):
        return self.__nume

    def get_grupa(self):
        return self.__grupa

    def set_nume(self, nume):
        self.__nume = nume

    def set_grupa(self, grupa):
        self.__grupa = grupa

    def __eq__(self, other):
        return self.__id == other.__id

    def __str__(self):
        return f'[{self.__id}] Studentul {self.__nume} este in grupa {self.__grupa}'

    def string_for_write(self):
        return f'{self.__id},{self.__nume},{self.__grupa}'