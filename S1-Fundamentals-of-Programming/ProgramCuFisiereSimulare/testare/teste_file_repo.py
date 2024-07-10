from domeniu.carte import Carte
from infrastructura.file_repo_carti import FileRepoCarti


class TesteFile:

    def ruleaza_toate_testele(self):
        self.__ruleaza_teste_file_repo()
        print("Teste trecute cu succes!")

    def __goleste_fisier(self, cale_catre_fisier):
        with open(cale_catre_fisier, "w") as f:
            pass

    def __ruleaza_teste_file_repo(self):
        cale_catre_fisier = 'testare/carti_test.txt'
        self.__goleste_fisier(cale_catre_fisier)
        repo = FileRepoCarti(cale_catre_fisier)
        assert repo.size() == 0
        carte = Carte(23, "Alibaba", "Smeker os", "Frumoasa din tara adormita")
        repo.adauga_carte(carte)
        assert repo.size() == 1