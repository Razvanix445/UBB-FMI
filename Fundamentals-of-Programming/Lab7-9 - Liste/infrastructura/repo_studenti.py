from erori.repository_error import RepoError


class RepoStudenti:

    def __init__(self):
        self.__studenti = []

    def adauga_student(self, student):
        '''
        functia adauga un student in lista de studenti daca id-ul studentului respectiv este unic
        :param student: student
        :return: - daca id-ul studentului nu gaseste printre id-urile studentilor, se arunca eroare de tipul RepoError cu mesajul "student existent!"
        '''
        if student in self.__studenti:
            raise RepoError("student existent!")
        self.__studenti.append(student)

    def sterge_student_dupa_id(self, id_student):
        '''
        functia sterge un student din dictionarul de studenti daca id-ul studentului se gaseste printre id-urile studentilor
        :param id_student: int
        :return: - daca id-ul studentului nu se gaseste printre id-urile studentilor, se arunca eroare de tipul RepoError cu mesajul "student inexistent!"
        '''
        for student in self.__studenti:
            if student.get_id_student() == id_student:
                self.__studenti.remove(student)


    def cauta_student_dupa_id(self, id_student):
        '''
        functia cauta un student in dictionarul de studenti prin id-ul sau unic
        :param id_student: int
        :return: lista - daca id-ul studentului se gaseste printre id-urile studentilor
                        altfel, se arunca eroare de tipul RepoError cu mesajul "student inexistent!"
        '''
        for student in self.__studenti:
            if student.get_id_student() == id_student:
                return student
        return None
        #raise RepoError("student inexistent!")

    def modifica_student(self, student):
        '''
        functia modifica numele si grupa unui student din dictionarul de studenti
        :param student: student
        :return: - daca nu se gaseste id-ul studentului, se arunca eroare de tipul RepoError cu mesajul "student inexistent!"
        '''
        i = 0
        for student1 in self.__studenti:
            if student1.get_id_student() == student.get_id_student():
                self.__studenti[i] = student
            i = i + 1

    def get_all(self):
        '''
        functia returneaza lista de studenti introdusi la laborator
        :return: studenti - lista de studenti introdusi la laborator
        '''
        studenti = []
        for student in self.__studenti:
            studenti.append(student)
        return studenti

    def __len__(self):
        return len(self.__studenti)