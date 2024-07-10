from domeniu.problema import Problema
from domeniu.student import Student

class Teste:

    def __init__(self, repo_studenti, repo_probleme, service_studenti, service_probleme):
        self.__repo_studenti = repo_studenti
        self.__repo_probleme = repo_probleme
        self.__service_studenti = service_studenti
        self.__service_probleme = service_probleme

    def ruleaza_repo_student(self):
        student1 = Student(1, "Ionel", 212)
        self.__repo_studenti.adauga_student(student1)
        ok = self.__repo_studenti.cauta_student_dupa_id(1)
        assert ok == student1
        ok = self.__repo_studenti.cauta_student_dupa_id(10)
        assert ok == None
        student2 = Student(2, "Vasile Stefan", 213)
        self.__repo_studenti.adauga_student(student2)
        assert len(self.__repo_studenti) == 2
        self.__repo_studenti.sterge_student_dupa_id(student2.get_id_student())
        assert len(self.__repo_studenti) == 1
        student3 = Student(3, "Maria-Ioana", 211)
        self.__repo_studenti.modifica_student(student1)
        assert student1.get_id_student() == student3.get_id_student() & ((student1.get_nume() != student3.get_nume()) | (student1.get_grupa() != student3.get_grupa()))
        self.__repo_studenti.sterge_student_dupa_id(student1.get_id_student())
        assert len(self.__repo_studenti) == 0

    def ruleaza_repo_problema(self):
        problema1 = Problema(1, "Se cere adaugarea unei sume cel mai mic numar divizibil cu 5", 2022-12-12)
        self.__repo_probleme.adauga_problema(problema1)
        problema2 = Problema(2, "Se cere calculul a doua numere citite de la tastatura", 2023-6-9)
        self.__repo_probleme.adauga_problema(problema2)
        assert len(self.__repo_probleme) == 2
        self.__repo_probleme.sterge_problema_dupa_nr(problema2.get_nr_problema())
        assert len(self.__repo_probleme) == 1
        problema3 = Problema(3, "Se cere aflarea celui de-al n termen din sirul lui Fibonacci", 2024-12-30)
        self.__repo_probleme.modifica_problema(problema1)
        assert problema1.get_nr_problema() == problema3.get_nr_problema() & ((problema1.get_descriere() != problema3.get_descriere()) | (problema1.get_deadline() != problema3.get_deadline()))
        self.__repo_probleme.sterge_problema_dupa_nr(problema1.get_nr_problema())
        assert len(self.__repo_probleme) == 0

    '''
    def ruleaza_service_student(self):
        #student1 = Student(1, "Ionel", 212)
        self.__service_studenti.adauga_student(1, "Ionel", 212)
        #student2 = Student(2, "Vasile Stefan", 213)
        self.__service_studenti.adauga_student(2, "Vasile Stefan", 213)
        assert len(self.__service_studenti) == 2
        
        self.__repo_studenti.sterge_student_dupa_id(student2.get_id_student())
        assert len(self.__repo_studenti) == 1
        student3 = Student(3, "Maria-Ioana", 211)
        self.__repo_studenti.modifica_student(student1)
        assert student1.get_id_student() == student3.get_id_student() & (
                    (student1.get_nume() != student3.get_nume()) | (student1.get_grupa() != student3.get_grupa()))
        self.__repo_studenti.sterge_student_dupa_id(student1.get_id_student())
        assert len(self.__repo_studenti) == 0
    '''

    def ruleaza_toate_testele(self):
        Teste.ruleaza_repo_student(self)
        print("teste repo student trecute cu succes!")
        Teste.ruleaza_repo_problema(self)
        print("teste repo problema trecute cu succes!")
        #Teste.ruleaza_service_student(self)
        #print("teste service student trecute cu succes!")
        #Teste.ruleaza_service_problema(self)
        #print("teste service problema trecute cu succes!")