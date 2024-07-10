from business.service import Service
from infrastructura.file_repo import FileRepo
from infrastructura.repo import Repo
from prezentare.consola import UI
from testare.teste import Teste
from validator.validator import Validare

if __name__ == '__main__':
    validator = Validare()
    repo = Repo()
    cale_catre_fisier = 'jucatori.txt'
    file_repo = FileRepo(cale_catre_fisier)
    service = Service(validator, file_repo)
    consola = UI(service)
    testare = Teste(validator, repo, service)
    testare.ruleaza_toate_testele()
    consola.run()