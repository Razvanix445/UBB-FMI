from domeniu.client import Client


class ServiceClienti:

    def __init__(self, validator_client, repo_clienti):
        self.__validator_client = validator_client
        self.__repo_clienti = repo_clienti

    def adauga_client(self, id_client, nume, CNP):
        client = Client(id_client, nume, CNP)
        self.__validator_client.valideaza(client)
        self.__repo_clienti.adauga_client(client)

    def modifica_client(self, id_client, nume, CNP):
        client = Client(id_client, nume, CNP)
        self.__validator_client.valideaza(client)
        self.__repo_clienti.modifica_client(client)

    def sterge_client(self, id_client):
        self.__repo_clienti.sterge_client(id_client)

    def cauta_client(self, id_client):
        return self.__repo_clienti.cauta_client(id_client)

    def get_all(self):
        return self.__repo_clienti.get_all()