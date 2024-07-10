from erori.repository_error import RepoError


class RepoStudenti:

    def __init__(self):
        self._studenti = {}

    def adauga_student(self, student):
        if student.get_id() in self._studenti:
            raise RepoError("Student existent!")
        self._studenti[student.get_id()] = student

    def modifica_student(self, student):
        if student.get_id() not in self._studenti:
            raise RepoError("Student inexistent!")
        self._studenti[student.get_id()] = student

    def sterge_student(self, id_student):
        if id_student not in self._studenti:
            raise RepoError("Student inexistent!")
        del self._studenti[id_student]

    def cauta_student(self, id_student):
        if id_student not in self._studenti:
            raise RepoError("Student inexistent!")
        return self._studenti[id_student]

    def get_all(self):
        if self.size() == 0:
            raise RepoError("Nu exista studenti inregistrati!")
        studenti = []
        for student_id in self._studenti:
            studenti.append(self._studenti[student_id])
        return studenti

    def size(self):
        return len(self._studenti)