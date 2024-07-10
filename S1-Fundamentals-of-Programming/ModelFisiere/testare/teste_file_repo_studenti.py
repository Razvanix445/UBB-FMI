from domeniu.student import Student
from infrastructura.file_repo_studenti import FileRepoStudenti


class Teste:

    def ruleaza_toate_testele(self):
        self.__ruleaza_file_repo_teste()

    def __goleste_fisier(self, cale):
        with open(cale, "w") as f:
            pass

    def __ruleaza_file_repo_teste(self):
        cale_test_file = "testare/studenti_test.txt"
        self.__goleste_fisier(cale_test_file)
        repo = FileRepoStudenti(cale_test_file)
        assert repo.size() == 0
        student = Student(23, "Jordan", 9000.1)
        repo.adauga_student(student)
        assert repo.size() == 1