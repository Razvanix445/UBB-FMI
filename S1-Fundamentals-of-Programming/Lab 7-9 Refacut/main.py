from business.service_note import ServiceNote
from business.service_probleme import ServiceProbleme
from business.service_studenti import ServiceStudenti
from infrastructura.file_repo import FileRepoStudenti, FileRepoProbleme, FileRepoNote
from infrastructura.repo_probleme import RepoProbleme
from infrastructura.repo_studenti import RepoStudenti
from prezentare.consola import UI
from validare.validator_nota import ValidatorNota
from validare.validator_problema import ValidatorProblema
from validare.validator_student import ValidatorStudent


def main():
    repo_studenti = RepoStudenti()
    repo_probleme = RepoProbleme()
    file_repo_studenti = FileRepoStudenti()
    file_repo_probleme = FileRepoProbleme()
    file_repo_note = FileRepoNote()
    validator_student = ValidatorStudent()
    validator_problema = ValidatorProblema()
    validator_nota = ValidatorNota(file_repo_studenti, file_repo_probleme)
    service_studenti = ServiceStudenti(validator_student, file_repo_studenti)
    service_probleme = ServiceProbleme(validator_problema, file_repo_probleme)
    service_note = ServiceNote(validator_nota, file_repo_note, file_repo_studenti, file_repo_probleme)
    consola = UI(service_studenti, service_probleme, service_note)
    consola.run()

main()