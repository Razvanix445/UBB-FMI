from domeniu.student import Student
from infrastructura.file_repo_studenti import FileRepoStudenti
from testare.teste_file_repo_studenti import Teste


def main():
    teste = Teste()
    teste.ruleaza_toate_testele()
    cale_studenti = "studenti.txt"
    repo = FileRepoStudenti(cale_studenti)
    for student in repo.get_all():
        print(student)

main()