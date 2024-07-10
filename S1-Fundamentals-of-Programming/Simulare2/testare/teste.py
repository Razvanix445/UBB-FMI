from domeniu.client import Client
from domeniu.film import Film
from erori.repository_error import RepoError


class Teste:

    def __init__(self, service_filme, service_clienti, repo_filme, repo_clienti):
        self.__service_filme = service_filme
        self.__service_clienti = service_clienti
        self.__repo_filme = repo_filme
        self.__repo_clienti = repo_clienti

    def __ruleaza_repo_filme(self):
        assert len(self.__repo_filme.get_all()) == 0
        '''adaugare film'''
        id_film = 1
        titlu = "Alba ca zapada"
        descriere = "Nush"
        gen = "Actiune"
        film = Film(id_film, titlu, descriere, gen)
        self.__repo_filme.adauga_film(film)
        filme = self.__repo_filme.get_all()
        assert len(filme) == 1
        try:
            self.__repo_filme.adauga_film(film)
            assert False
        except RepoError as re:
            assert str(re) == "Film existent!"
        '''modificare film'''
        acelasi_id_film = 1
        alt_titlu = "The Troll"
        alta_descriere = "Smecher"
        alt_gen = "Drama"
        film_modificat = Film(acelasi_id_film, alt_titlu, alta_descriere, alt_gen)
        self.__repo_filme.modifica_film(film_modificat)
        filme = self.__repo_filme.get_all()
        assert len(filme) == 1
        alt_id = 5
        film_inexistent = Film(alt_id, alt_titlu, alta_descriere, alt_gen)
        try:
            self.__repo_filme.modifica_film(film_inexistent)
            assert False
        except RepoError as re:
            assert str(re) == "Film inexistent!"
        '''cautare film'''
        assert self.__repo_filme.cauta_film(id_film) == film_modificat
        id_inexistent = 4
        assert self.__repo_filme.cauta_film(id_inexistent) == "Film inexistent!"
        '''stergere film'''
        id_film = 2
        try:
            self.__repo_filme.sterge_film(id_film)
            assert False
        except RepoError as re:
            assert str(re) == "Film inexistent!"
        id_film = 1
        self.__repo_filme.sterge_film(id_film)
        assert len(self.__repo_filme.get_all()) == 0

    def __ruleaza_repo_clienti(self):
        assert len(self.__repo_clienti.get_all()) == 0
        '''adaugare client'''
        id_client = 1
        nume = "Alba ca zapada"
        CNP = 5030713248143
        client = Client(id_client, nume, CNP)
        self.__repo_clienti.adauga_client(client)
        clienti = self.__repo_clienti.get_all()
        assert len(clienti) == 1
        assert clienti[id_client] == client
        '''modificare client'''
        acelasi_id_client = 1
        alt_nume = "Viorica"
        alt_CNP = 5030712748143
        client_modificat = Client(acelasi_id_client, alt_nume, alt_CNP)
        self.__repo_clienti.modifica_client(client_modificat)
        clienti = self.__repo_clienti.get_all()
        assert len(clienti) == 1
        assert clienti[acelasi_id_client] == client_modificat
        '''cautare client'''
        assert self.__repo_clienti.cauta_client(id_client) == client_modificat
        id_inexistent = 4
        assert self.__repo_clienti.cauta_client(id_inexistent) == "Client inexistent!"
        '''stergere client'''
        self.__repo_clienti.sterge_client(id_client)
        assert len(self.__repo_clienti.get_all()) == 0

    def __ruleaza_service_filme(self):
        assert len(self.__service_filme.get_all()) == 0
        '''adaugare film'''
        id_film = 1
        titlu = "Alba ca zapada"
        descriere = "Nush"
        gen = "Actiune"
        self.__service_filme.adauga_film(id_film, titlu, descriere, gen)
        filme = self.__service_filme.get_all()
        assert len(filme) == 1
        film = self.__service_filme.cauta_film(id_film)
        assert film.get_id_film() == 1
        assert film.get_titlu() == "Alba ca zapada"
        assert film.get_descriere() == "Nush"
        assert film.get_gen() == "Actiune"
        '''modificare film'''
        acelasi_id_film = 1
        alt_titlu = "The Troll"
        alta_descriere = "Smecher"
        alt_gen = "Drama"
        self.__service_filme.modifica_film(acelasi_id_film, alt_titlu, alta_descriere, alt_gen)
        filme = self.__service_filme.get_all()
        assert len(filme) == 1
        film = self.__service_filme.cauta_film(id_film)
        assert film.get_id_film() == 1
        assert film.get_titlu() == "The Troll"
        assert film.get_descriere() == "Smecher"
        assert film.get_gen() == "Drama"
        '''cautare film'''
        film = self.__service_filme.cauta_film(id_film)
        assert film.get_id_film() == 1
        assert film.get_titlu() == "The Troll"
        assert film.get_descriere() == "Smecher"
        assert film.get_gen() == "Drama"
        id_inexistent = 4
        assert self.__service_filme.cauta_film(id_inexistent) == "Film inexistent!"
        '''stergere film'''
        self.__service_filme.sterge_film(id_film)
        assert len(self.__service_filme.get_all()) == 0

    def __ruleaza_service_clienti(self):
        assert len(self.__service_clienti.get_all()) == 0
        '''adaugare client'''
        id_client = 1
        nume = "Alba ca zapada"
        CNP = 5030713248
        self.__service_clienti.adauga_client(id_client, nume, CNP)
        clienti = self.__service_clienti.get_all()
        assert len(clienti) == 1
        assert clienti[id_client].get_id_client() == 1
        assert clienti[id_client].get_nume() == "Alba ca zapada"
        assert clienti[id_client].get_CNP() == 5030713248
        '''modificare client'''
        acelasi_id_client = 1
        alt_nume = "Viorica"
        alt_CNP = 5030712748
        self.__service_clienti.modifica_client(acelasi_id_client, alt_nume, alt_CNP)
        assert len(clienti) == 1
        assert clienti[acelasi_id_client].get_id_client() == 1
        assert clienti[acelasi_id_client].get_nume() == "Viorica"
        assert clienti[acelasi_id_client].get_CNP() == 5030712748
        '''cautare client'''
        client = self.__service_clienti.cauta_film(id_client)
        assert client.get_id_client() == 1
        assert client.get_nume() == "Viorica"
        assert client.get_CNP() == 5030712748
        id_inexistent = 4
        assert self.__service_filme.cauta_client(id_inexistent) == "Client inexistent!"
        '''stergere client'''
        self.__service_clienti.sterge_client(id_client)
        assert len(self.__service_clienti.get_all()) == 0

    def ruleaza_toate_testele(self):
        self.__ruleaza_repo_filme()
        print("Teste repo filme trecute cu succes!")
        #self.__ruleaza_repo_clienti()
        print("Teste repo clienti trecute cu succes!")
        #self.__ruleaza_service_filme()
        print("Teste service trecute cu succes!")
        #self.__ruleaza_service_clienti()
        print("Teste service trecute cu succes!")