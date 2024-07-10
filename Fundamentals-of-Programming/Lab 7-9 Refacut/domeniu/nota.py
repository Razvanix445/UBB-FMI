class Nota:

    def __init__(self, id, student, problema, nota):
        self.__id = id
        self.__student = student
        self.__problema = problema
        self.__nota = nota

    def get_id(self):
        return self.__id

    def get_student(self):
        return self.__student

    def get_problema(self):
        return self.__problema

    def get_nota(self):
        return self.__nota

    def set_student(self, student):
        self.__student = student

    def set_problema(self, problema):
        self.__problema = problema

    def set_nota(self, nota):
        self.__nota = nota

    def __eq__(self, other):
        return self.__id == other.__id

    def __str__(self):
        return f'Studentul {self.__student.get_nume()} are nota {self.__nota} la problema {self.__problema.get_nr()}'

    def string_for_write(self):
        return f'{self.__id},{self.__student.get_id()},{self.__problema.get_nr()},{self.__nota}'