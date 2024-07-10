from business.service_clienti import ServiceClienti
from business.service_filme import ServiceFilme
from infrastructura.file_repo_filme import FileRepoFilme
from infrastructura.repo_clienti import RepoClienti
from infrastructura.repo_filme import RepoFilme
from prezentare.consola import UI
from testare.teste import Teste
from testare.teste_file_repo_filme import FileTeste
from validare.validator_client import ValidatorClient
from validare.validator_film import ValidatorFilm

if __name__ == '__main__':
    file_teste = FileTeste()
    file_teste.ruleaza_toate_testele()
    cale_filme = "filme.txt"
    repo = FileRepoFilme(cale_filme)
    #for film in repo.get_all():
        #print(film)

    validator_film = ValidatorFilm()
    validator_client = ValidatorClient()
    repo_filme = RepoFilme()
    repo_clienti = RepoClienti()
    file_repo_filme = FileRepoFilme(cale_filme)
    service_filme = ServiceFilme(validator_film, repo_filme, file_repo_filme)
    service_clienti = ServiceClienti(validator_client, repo_clienti)
    teste = Teste(service_filme, service_clienti, repo_filme, repo_clienti)
    consola = UI(service_filme, service_clienti)
    teste.ruleaza_toate_testele()
    consola.run()

    #13, Jordan, Smeker de rupe, Drama Romantica
    #20, Mirciulica, Frumos, Romanesc