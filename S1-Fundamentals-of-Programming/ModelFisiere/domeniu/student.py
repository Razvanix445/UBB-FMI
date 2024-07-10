class Student:

    def __init__(self, id_student, nume, pret):
        self.__id_student = id_student
        self.__nume = nume
        self.__pret = pret

    def get_id_student(self):
        return self.__id_student

    def get_nume(self):
        return self.__nume

    def get_pret(self):
        return self.__pret

    def set_nume(self, nume):
        self.__nume = nume

    def set_pret(self, pret):
        self.__pret = pret

    def __eq__(self, other):
        return self.__id_student == other.__id_student

    def __str__(self):
        return f"{self.__id_student}, {self.__nume}, {self.__pret}"