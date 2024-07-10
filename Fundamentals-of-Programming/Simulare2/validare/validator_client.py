from erori.validation_error import ValidError


class ValidatorClient:

    def __init__(self):
        pass

    def valideaza(self, client):
        erori = ""
        if client.get_id_client() < 0:
            erori += "id invalid!\n"
        if client.get_nume() == "":
            erori += "nume invalid!\n"
        if int(client.get_CNP()) < 1000000000000 or int(client.get_CNP()) > 9999999999999:
            erori += "CNP invalid!\n"
        if len(erori) > 0:
            raise ValidError(erori)