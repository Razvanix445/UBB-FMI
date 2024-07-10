class Client:

    def __init__(self, id_client, nume, CNP):
        self.__id_client = id_client
        self.__nume = nume
        self.__CNP = CNP

    def get_id_client(self):
        return self.__id_client

    def get_nume(self):
        return self.__nume

    def get_CNP(self):
        return self.__CNP

    def set_nume(self, nume):
        self.__nume = nume

    def set_CNP(self, CNP):
        self.__CNP = CNP

    def __eq__(self, other):
        return self.__id_client == other.__id_client

    def __str__(self):
        return f'[{self.__id_client}] Clientul {self.__nume} are CNP-ul {self.__CNP}'