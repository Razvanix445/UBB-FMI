from business.service_carti import ServiceCarti
from domeniu.carte import Carte
from infrastructura.file_repo_carti import FileRepoCarti
from infrastructura.repo_carti import RepoCarti
from prezentare.consola import UI
from testare.teste_file_repo import TesteFile
from validare.validator_carte import ValidatorCarte


def main():
    teste_file = TesteFile()
    teste_file.ruleaza_toate_testele()
    cale_carti = "carti.txt"
    repo = FileRepoCarti(cale_carti)
    #carte = Carte(1, "este", "ma", "doe")
    #for carte in repo.get_all():
        #print(carte)

    validator_carte = ValidatorCarte()
    repo_carti = RepoCarti()
    file_repo_carti = FileRepoCarti(cale_carti)
    service_carti = ServiceCarti(validator_carte, repo_carti, file_repo_carti)
    consola = UI(service_carti)
    consola.run()

main()