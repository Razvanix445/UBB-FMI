from domeniu.jucator import Jucator
from erori.validation_error import ValidError


class Teste:

    def __init__(self, validator, repo, service):
        self.__validator = validator
        self.__repo = repo
        self.__service = service

    def __ruleaza_validator(self):
        nume = 'Viorel'
        prenume = 'Ghita'
        inaltime = 145
        post = 'Pivot'
        nume_gresit = ''
        prenume_gresit = ''
        inaltime_gresita = 120
        post_gresit = 'Mijlocas'
        jucator = Jucator(nume, prenume, inaltime, post)
        self.__validator.valideaza(jucator)
        jucator_gresit = Jucator(nume_gresit, prenume_gresit, inaltime_gresita, post_gresit)
        try:
            self.__validator.valideaza(jucator_gresit)
            assert False
        except:
            ValidError('nume invalid!\nprenume invalid!\ninaltime invalida!\npost invalid!\n')
        jucator_nume_gresit_inaltime_gresita = Jucator(nume_gresit, prenume, inaltime_gresita, post)
        try:
            self.__validator.valideaza(jucator_nume_gresit_inaltime_gresita)
            assert False
        except:
            ValidError('nume invalid!\ninaltime invalida!\n')

    def __ruleaza_repo(self):
        jucator = Jucator('Vasile', 'Ionica', 143, 'Pivot')
        jucator_gresit = Jucator('Vasile', '', 129, 'Mijlocas')
        assert self.__repo.size() == 0
        self.__repo.adaugare('Vasile Ionica', jucator)
        assert self.__repo.size() == 1
        try:
            self.__repo.adaugare('Vasile  ', jucator_gresit)
            assert False
        except:
            assert True
        assert self.__repo.size() == 2

        inaltime = 10
        assert jucator.get_inaltime() == 143
        self.__repo.modificare(inaltime)
        assert jucator.get_inaltime() == 153

        nume_fisier = 'testare/nume_prenume_teste.txt'
        nr = self.__repo.importare(nume_fisier)
        assert nr == 0

        with open('testare/teste_jucatori.txt', 'w') as file:
            pass

    def __ruleaza_file_repo(self):
        jucator = Jucator('Vasile', 'Ionica', 143, 'Pivot')
        jucator_gresit = Jucator('Vasile', '', 129, 'Mijlocas')
        assert self.__repo.size() == 0
        self.__repo.adaugare('Vasile Ionica', jucator)
        assert self.__repo.size() == 1
        try:
            self.__repo.adaugare('Vasile  ', jucator_gresit)
            assert False
        except:
            assert True
        assert self.__repo.size() == 2

        inaltime = 10
        assert jucator.get_inaltime() == 143
        self.__repo.modificare(inaltime)
        assert jucator.get_inaltime() == 153

        nume_fisier = 'testare/nume_prenume_teste.txt'
        nr = self.__repo.importare(nume_fisier)
        assert nr == 0

        with open('testare/teste_jucatori.txt', 'w') as file:
            pass

    def __ruleaza_service(self):
        assert len(self.__service.get_all()) == len(self.__service.get_all())
        assert len(self.__service.get_all()) == len(self.__service.get_all())
        try:
            self.__service.adaugare('Vasile', '', 129, 'Mijlocas')
            assert False
        except ValidError as ve:
            pass
        assert len(self.__service.get_all()) == len(self.__service.get_all())
        
        inaltime = 10
        self.__repo.modificare(inaltime)

        nume_fisier = 'testare/nume_prenume_teste.txt'
        nr = self.__service.importare(nume_fisier)
        assert nr == 0

        with open('testare/teste_jucatori.txt', 'w') as file:
            pass

    def ruleaza_toate_testele(self):
        self.__ruleaza_validator()
        self.__ruleaza_repo()
        self.__ruleaza_file_repo()
        self.__ruleaza_service()