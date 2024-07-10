from erori.repository_error import RepoError


class RepoStudenti:
    def __init__(self):
        self._studenti = {}

    def adauga_student(self, student):
        if student.get_id_student() in self._studenti:
            raise RepoError("Student existent!")
        self._studenti[student.get_id_student()] = student

    def modifica_student(self, student):
        if student.get_id_student() not in self._studenti:
            raise RepoError("Student inexistent!")
        self._studenti[student.get_id_student()] = student

    def sterge_student(self, id_student):
        if id_student not in self._studenti:
            raise RepoError("Student inexistent!")
        del self._studenti[id_student]

    def cauta_student(self, id_student):
        if id_student not in self._studenti:
            return "Student inexistent!"
        return self._studenti[id_student]

    def get_all(self):
        return [student for student in self._studenti.values()]

    def size(self):
        return len(self._studenti)