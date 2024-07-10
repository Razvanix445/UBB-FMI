from domeniu.student import Student

class ServiceStudenti:

    def __init__(self, validator_student, repo_studenti):
        self.__validator_student = validator_student
        self.__repo_studenti = repo_studenti

    def adauga_student(self, id_student, nume, grupa):
        student = Student(id_student, nume, grupa)
        self.__validator_student.valideaza(student)
        self.__repo_studenti.adauga_student(student)

    def modifica_student(self, id_student, nume, grupa):
        student = Student(id_student, nume, grupa)
        self.__validator_student.valideaza(student)
        self.__repo_studenti.modifica_student(student)

    def cauta_student(self, id_student):
        return self.__repo_studenti.cauta_student_dupa_id(id_student)

    def get_all_studenti(self):
        return self.__repo_studenti.get_all()