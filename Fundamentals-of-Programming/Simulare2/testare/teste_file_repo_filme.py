from domeniu.film import Film
from infrastructura.file_repo_filme import FileRepoFilme


class FileTeste:

    def ruleaza_toate_testele(self):
        self.__ruleaza_teste_file_repo()

    def __goleste_fisier(self, cale):
        with open(cale, "w") as f:
            pass

    def __ruleaza_teste_file_repo(self):
        cale_test_file = "testare/filme_test.txt"
        self.__goleste_fisier(cale_test_file)
        repo = FileRepoFilme(cale_test_file)
        assert repo.size() == 0
        film = Film(23, "Alba ca zapada", "Smeker", "Drama romantica")
        repo.adauga_film(film)
        assert repo.size() == 1