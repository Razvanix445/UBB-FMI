from erori.repository_error import RepoError


class RepoClienti:

    def __init__(self):
        self.__clienti = {}

    def adauga_client(self, client):
        if client.get_id_client() in self.__clienti:
            raise RepoError("Client existent!")
        self.__clienti[client.get_id_client()] = client

    def modifica_client(self, client):
        if client.get_id_client() not in self.__clienti:
            raise RepoError("Client inexistent!")
        self.__clienti[client.get_id_client()] = client

    def sterge_client(self, id_client):
        if id_client not in self.__clienti:
            raise RepoError("Client inexistent!")
        del self.__clienti[id_client]

    def cauta_client(self, id_client):
        if id_client not in self.__clienti:
            return "Client inexistent!"
        return self.__clienti[id_client]

    def get_all(self):
        clienti = {}
        for id_client in self.__clienti:
            clienti[id_client] = self.__clienti[id_client]
        return clienti