import random

from domeniu.jucator import Jucator
from erori.repository_error import RepoError


class Repo:

    def __init__(self):
        self._jucatori = {}

    def adaugare(self, id, jucator):
        '''
        Se adauga jucatori in aplicatie
        :return: - Se adauga jucatori in aplicatie
        '''
        if id in self._jucatori:
            raise RepoError('Jucator existent!')
        self._jucatori[id] = jucator

    def modificare(self, noua_inaltime):
        '''
        Se modifica inaltimea jucatorilor din aplicatie
        :return: - Se va modifica inaltimea jucatorilor din aplicatie adunand sau scazand
        '''
        for jucator_id in self._jucatori:
            self._jucatori[jucator_id].set_inaltime(self._jucatori[jucator_id].get_inaltime() + noua_inaltime)

    def tiparire(self):
        pass

    def get_all(self):
        '''
        Se returneaza toti jucatorii din aplicatie
        :return: se returneaza toti jucatorii din aplicatie
        '''
        jucatori = {}
        for jucator_id in self._jucatori:
            jucatori[jucator_id] = self._jucatori[jucator_id]
        return jucatori

    def size(self):
        return len(self._jucatori)

    def importare(self, nume_fisier):
        '''
        :param nume_fisier: numele fisierului in care sunt numele si prenumele
        :return: returneaza numarul de jucatori introdusi
        '''
        nr = self.__read_all_from_file(nume_fisier)
        return nr

    def __read_all_from_file(self, nume_fisier):
        jucatori = {}
        with open('jucatori.txt', 'r') as file:
            linii = file.readlines()
            self._jucatori.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != '':
                    parti = linie.split(',')
                    nume = parti[0]
                    prenume = parti[1]
                    inaltime = int(parti[2])
                    post = parti[3]
                    jucator = Jucator(nume, prenume, inaltime, post)
                    id = (nume, prenume)
                    jucatori[id] = jucator

        nr = 0
        with open(nume_fisier, 'r') as file:
            linii = file.readlines()
            self._jucatori.clear()
            for linie in linii:
                linie = linie.strip()
                if linie != '':
                    parti = linie.split(' ')
                    nume = parti[0]
                    prenume = parti[1]
                    inaltime = random.randint(141, 220)
                    post = random.randint(1, 3)
                    if post == 1:
                        post = 'Fundas'
                    if post == 2:
                        post = 'Pivot'
                    if post == 3:
                        post = 'Extrema'
                    id = (nume, prenume)
                    jucator = Jucator(nume, prenume, inaltime, post)
                    if id not in jucatori:
                        nr += 1
                        self._jucatori[id] = jucator
        with open('jucatori.txt', 'w') as file:
            for jucator_id in jucatori:
                file.write(str(jucatori[jucator_id]) + '\n')
            for jucator_id in self._jucatori:
                file.write(str(self._jucatori[jucator_id]) + '\n')
        return nr