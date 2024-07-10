from domeniu.student import Student


class ServiceStudenti:

    def __init__(self, validator_student, file_repo_studenti):
        self.__validator_student = validator_student
        self.__file_repo_studenti = file_repo_studenti

    def adauga_student(self, id, nume, grupa):
        student = Student(id, nume, grupa)
        self.__validator_student.valideaza(student)
        self.__file_repo_studenti.adauga_student(student)

    def modifica_student(self, id, nume, grupa):
        student = Student(id, nume, grupa)
        self.__validator_student.valideaza(student)
        self.__file_repo_studenti.modifica_student(student)

    def sterge_student(self, id):
        self.__file_repo_studenti.sterge_student(id)

    def cauta_student(self, id):
        return self.__file_repo_studenti.cauta_student(id)

    def get_all(self):
        return self.__file_repo_studenti.get_all()

    def size(self):
        return self.__file_repo_studenti.size()

    def get_lista_studenti(self, grupa):
        studenti = self.__file_repo_studenti.get_all()
        return self.numara_studenti_dupa_grupa(studenti, grupa)

    def numara_studenti_dupa_grupa(self, studenti, grupa):
        if len(studenti) == 0:
            return 0
        if studenti[0].get_grupa() == grupa:
            return 1 + self.numara_studenti_dupa_grupa(studenti[1:], grupa)
        else:
            return self.numara_studenti_dupa_grupa(studenti[1:], grupa)

    def get_lista_studenti_alfabetic_dupa_grupa_dupa_nume(self):
        '''
        Se returneaza lista de studenti alfabetic
        :return: lista de studenti alfabetic
        '''
        list = self.__file_repo_studenti.get_all()
        studenti = MergeSort(list, key=lambda x: x[2], key2=lambda y: y[1], reverse=False)
        studenti.sort()
        return studenti

    def get_lista_studenti_invers_alfabetic_dupa_grupa_dupa_nume(self):
        '''
        Se returneaza lista de studenti invers alfabetic
        :return: lista de studenti alfabetic
        '''
        list = self.__file_repo_studenti.get_all()
        studenti = MergeSort(list, key=lambda x: x[2], key2=lambda y: y[1], reverse=True)
        studenti.sort()
        return studenti

    def get_lista_studenti_invers_alfabetic_dupa_id(self):
        '''
        Se returneaza lista de studenti dupa id
        :return: lista de studenti alfabetic
        '''
        list = self.__file_repo_studenti.get_all()
        studenti = BingoSort(list, key=lambda x: x[0], reverse=True)
        studenti.sort()
        return studenti

class MergeSort:
    '''
    Complexitatea algoritmului:
    - Cazul favorabil: elementele sunt deja sortate
    Fiecare jumatate de lista va fi sortata corect, iar algoritmul va trece direct la reunirea celor doua jumatati
    sortate intr-o singura lista sortata. Astfel, numarul de comparatii necesare va fi minim, iar complexitatea
    temporala va fi de O(n).
    - Cazul nefavorabil: elementele sunt sortate in ordine inversa cerintei, iar algoritmul va sorta fiecare jumatate
    de lista in mod corespunzator si va trebui sa efectueze comparatii suplimentare pentru a determina ordinea
    elementelor. Numarul de comparatii va fi maxim, iar complexitatea temporala va fi de O(n * log n).
    - Cazul general: elementele sunt dispuse aleatoriu. Numarul de comparatii necesare va fi aproximativ intre cazul
    favorabil si cel nefavorabil, iar complexitatea temporala va fi de O(n * log n).
    '''

    def __init__(self, lista, key=None, key2=None, reverse=False, cmp=None):
        self.lista = lista
        self.key = key
        self.key2 = key2
        self.reverse = reverse
        self.cmp = cmp

    def sort(self):
        if len(self.lista) > 1:
            # Determinarea mijlocului listei
            mij = len(self.lista) // 2
            # Impartirea listei in doua jumatati
            st = self.lista[:mij]
            dr = self.lista[mij:]
            # Sortarea fiecarei jumatati
            MergeSort(st, key=self.key, key2=self.key, reverse=self.reverse, cmp=self.cmp).sort()
            MergeSort(dr, key=self.key, key2=self.key, reverse=self.reverse, cmp=self.cmp).sort()

            # Iterarea prin fiecare jumatate de lista
            i = j = k = 0
            while i < len(st) and j < len(dr):
                if self.key:
                    if self.reverse:
                        if self.key(st[i]) > self.key(dr[j]):
                            self.lista[k] = st[i]
                            i += 1
                        elif self.key(st[i]) < self.key(dr[j]):
                            self.lista[k] = dr[j]
                            j += 1
                        else:
                            if self.key2(st[i]) > self.key2(dr[j]):
                                self.lista[k] = st[i]
                                i += 1
                            else:
                                self.lista[k] = dr[j]
                                j += 1
                    else:
                        if self.key(st[i]) < self.key(dr[j]):
                            self.lista[k] = st[i]
                            i += 1
                        elif self.key(st[i]) > self.key(dr[j]):
                            self.lista[k] = dr[j]
                            j += 1
                        else:
                            if self.key2(st[i]) < self.key2(dr[j]):
                                self.lista[k] = st[i]
                                i += 1
                            else:
                                self.lista[k] = dr[j]
                                j += 1
                else:
                    if self.reverse:
                        if st[i] > dr[j]:
                            self.lista[k] = st[i]
                            i += 1
                        else:
                            self.lista[k] = dr[j]
                            j += 1
                    else:
                        if st[i] < dr[j]:
                            self.lista[k] = st[i]
                            i += 1
                        else:
                            self.lista[k] = dr[j]
                            j += 1
                k += 1
            # Verificarea elementelor ramase in listele st si dr
            while i < len(st):
                self.lista[k] = st[i]
                i += 1
                k += 1
            while j < len(dr):
                self.lista[k] = dr[j]
                j += 1
                k += 1

class BingoSort:
    def __init__(self, lista, key=None, reverse=False, cmp=None):
        self.lista = lista
        self.size = len(lista)
        self.key = key
        self.reverse = reverse
        self.cmp = cmp

    def sort(self):
        # Cautam cel mai mic element din lista
        minim_bingo = min(self.lista, key=self.key)
        # Cautam cel mai mare element din lista
        urmatorul_bingo = max(self.lista, key=self.key)
        urmatoarea_pozitie = 0
        while minim_bingo < urmatorul_bingo:
            pozitia_de_inceput = urmatoarea_pozitie
            for i in range(pozitia_de_inceput, self.size):
                if self.key(self.lista[i]) == self.key(minim_bingo):
                    self.lista[i], self.lista[urmatoarea_pozitie] = self.lista[urmatoarea_pozitie], self.lista[i]
                    urmatoarea_pozitie += 1
                elif self.key(self.lista[i]) < self.key(urmatorul_bingo):
                    urmatorul_bingo = self.lista[i]
            minim_bingo = urmatorul_bingo
            urmatorul_bingo = max(self.lista, key=self.key)

        if self.reverse:
            self.lista = self.lista[::-1]
        return self.lista
