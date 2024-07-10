class Nota:

    def __init__(self, id_nota, student, problema_laborator, nota):
        self.__id_nota = id_nota
        self.__student = student
        self.__problema_laborator = problema_laborator
        self.__nota = nota
        self.__sters = False

    def get_id_nota(self):
        return self.__id_nota

    def get_student(self):
        return self.__student

    def get_sters(self):
        return self.__sters

    def get_nota(self):
        return self.__nota

    def sterge(self):
        self.__sters = True