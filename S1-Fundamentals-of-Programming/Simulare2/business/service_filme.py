from domeniu.film import Film


class ServiceFilme:

    def __init__(self, validator_film, repo_filme, file_repo_filme):
        self.__validator_film = validator_film
        self.__repo_filme = repo_filme
        self.__file_repo_filme = file_repo_filme

    def adauga_film(self, id_film, titlu, descriere, gen):
        film = Film(id_film, titlu, descriere, gen)
        self.__validator_film.valideaza(film)
        self.__repo_filme.adauga_film(film)
        self.__file_repo_filme.adauga_film(film)

    def modifica_film(self, id_film, titlu, descriere, gen):
        film = Film(id_film, titlu, descriere, gen)
        self.__validator_film.valideaza(film)
        self.__repo_filme.modifica_film(film)
        self.__file_repo_filme.modifica_film(film)

    def sterge_film(self, id_film):
        self.__file_repo_filme.sterge_film(id_film)

    def cauta_film(self, id_film):
        return self.__file_repo_filme.cauta_film(id_film)

    def get_all(self):
        return self.__file_repo_filme.get_all()
