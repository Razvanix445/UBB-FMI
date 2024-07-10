from testare.teste import Teste
from validare.validator_student import ValidatorStudent
from validare.validator_problema import ValidatorProblema
from validare.validator_nota import ValidatorNota
from infrastructura.repo_studenti import RepoStudenti
from infrastructura.repo_probleme import RepoProbleme
from infrastructura.repo_note import RepoNote
from business.service_studenti import ServiceStudenti
from business.service_probleme import ServiceProbleme
from business.service_note import ServiceNote
from prezentare.consola import UI

if __name__ == '__main__':
    validator_student = ValidatorStudent()
    validator_problema = ValidatorProblema()
    validator_nota = ValidatorNota()
    repo_studenti = RepoStudenti()
    repo_probleme = RepoProbleme()
    repo_note = RepoNote()
    service_studenti = ServiceStudenti(validator_student, repo_studenti)
    service_probleme = ServiceProbleme(validator_problema, repo_probleme)
    service_note = ServiceNote(validator_nota, repo_note, repo_studenti, repo_probleme)
    consola = UI(service_studenti, service_probleme, service_note)
    teste = Teste(repo_studenti, repo_probleme, service_studenti, service_probleme)
    teste.ruleaza_toate_testele()
    consola.run()