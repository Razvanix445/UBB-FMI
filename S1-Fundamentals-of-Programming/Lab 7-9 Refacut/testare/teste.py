import unittest
from datetime import date

from business.service_note import ServiceNote
from business.service_probleme import ServiceProbleme
from business.service_studenti import ServiceStudenti
from domeniu.nota import Nota
from domeniu.problema import Problema
from domeniu.student import Student
from erori.repository_error import RepoError
from erori.validation_error import ValidError
from infrastructura.file_repo import FileRepoStudenti, FileRepoProbleme, FileRepoNote
from infrastructura.repo_note import RepoNote
from infrastructura.repo_probleme import RepoProbleme
from infrastructura.repo_studenti import RepoStudenti
from validare.validator_nota import ValidatorNota
from validare.validator_problema import ValidatorProblema
from validare.validator_student import ValidatorStudent


class testDomainStudent(unittest.TestCase):

    def setUp(self):
        '''se creeaza un student'''
        self.__id = 1
        self.__nume = "Viorel Stefan"
        self.__grupa = 211
        self.__student = Student(self.__id, self.__nume, self.__grupa)
        '''se creeaza alt student cu nume si grupa diferite'''
        self.__alt_nume = "Vasile Stefan"
        self.__alta_grupa = 212
        self.__student_modificat = Student(self.__id, self.__alt_nume, self.__alta_grupa)
        '''se aleg alt nume si grupa pentru atribuire (set)'''
        self.__nume_nou = "Andrei"
        self.__grupa_noua = 213

    def test_create_student(self):
        self.assertEqual(self.__id, self.__student.get_id())
        self.assertEqual(self.__nume, self.__student.get_nume())
        self.assertAlmostEqual(self.__grupa, self.__student.get_grupa())
        self.assertEqual(self.__student, self.__student_modificat)

    def test_set_student(self):
        self.assertNotEqual(self.__nume_nou, self.__student.get_nume())
        self.__student.set_nume(self.__nume_nou)
        self.assertEqual(self.__nume_nou, self.__student.get_nume())
        self.assertNotEqual(self.__grupa_noua, self.__student.get_grupa())
        self.__student.set_grupa(self.__grupa_noua)
        self.assertEqual(self.__student.get_grupa(), self.__grupa_noua)
        self.assertEqual(str(self.__student), '[1] Studentul Andrei este in grupa 213')
        self.assertEqual(self.__student.string_for_write(), '1,Andrei,213')

class testDomainProblema(unittest.TestCase):

    def setUp(self):
        '''se creeaza o problema'''
        self.__nr = 1
        self.__descriere = "Calculul sumei a doua numere a si b"
        self.__deadline = '2042-12-12'
        self.__problema = Problema(self.__nr, self.__descriere, self.__deadline)
        '''se creeaza alta problema cu descriere si deadline diferite'''
        self.__alta_descriere = "Calculul diferentei dintre numerele a si b"
        self.__alt_deadline = '2052-06-09'
        self.__problema_modificata = Problema(self.__nr, self.__alta_descriere, self.__alt_deadline)
        '''se aleg descriere si deadline noi pentru atribuire (set)'''
        self.__descriere_noua = "Inmultirea a doua numere a si b"
        self.__deadline_nou = '2052-01-01'

    def test_create_problema(self):
        self.assertEqual(self.__nr, self.__problema.get_nr())
        self.assertEqual(self.__descriere, self.__problema.get_descriere())
        self.assertEqual(self.__deadline, self.__problema.get_deadline())
        self.assertEqual(self.__problema, self.__problema_modificata)

    def test_set_problema(self):
        self.assertNotEqual(self.__descriere_noua, self.__problema.get_descriere())
        self.__problema.set_descriere(self.__descriere_noua)
        self.assertEqual(self.__descriere_noua, self.__problema.get_descriere())
        self.assertNotEqual(self.__deadline_nou, self.__problema.get_deadline())
        self.__problema.set_deadline(self.__deadline_nou)
        self.assertEqual(self.__deadline_nou, self.__problema.get_deadline())
        self.assertEqual(str(self.__problema), 'Problema 1: Inmultirea a doua numere a si b; deadline: 2052-01-01')
        self.assertEqual(self.__problema.string_for_write(), '1,Inmultirea a doua numere a si b,2052-01-01')

class testDomainNota(unittest.TestCase):

    def setUp(self):
        '''se creeaza o nota'''
        self.__id_nota = 1
        self.__id_student = 1
        self.__nume_student = "Viorel Stefan"
        self.__grupa_student = 211
        self.__student = Student(self.__id_student, self.__nume_student, self.__grupa_student)
        self.__nr_problema = 1
        self.__descriere_problema = "Calculul sumei a doua numere a si b"
        self.__deadline_problema = '2042-12-12'
        self.__problema = Problema(self.__nr_problema, self.__descriere_problema, self.__deadline_problema)
        self.__nota_nota = 8.5
        self.__nota = Nota(self.__id_nota, self.__student, self.__problema, self.__nota_nota)
        '''se creeaza alta nota cu student, problema si nota diferite'''
        self.__alt_nume_student = "Vasile Stefan"
        self.__alta_grupa_student = 212
        self.__alt_student = Student(self.__id_student, self.__alt_nume_student, self.__alta_grupa_student)
        self.__alta_descriere_problema = "Calculul diferentei dintre numerele a si b"
        self.__alt_deadline_problema = '2052-06-09'
        self.__alta_problema = Problema(self.__nr_problema, self.__alta_descriere_problema, self.__alt_deadline_problema)
        self.__alta_nota_nota = 9.49
        self.__alta_nota = Nota(self.__id_nota, self.__alt_student, self.__alta_problema, self.__alta_nota_nota)
        '''se aleg student, problema si nota noi pentru atribuire (set)'''
        self.__id_student_nou = 2
        self.__nume_nou = "Andrei"
        self.__grupa_noua = 213
        self.__student_nou = Student(self.__id_student_nou, self.__nume_nou, self.__grupa_noua)
        self.__nr_problema_nou = 2
        self.__descriere_noua = "Inmultirea a doua numere a si b"
        self.__deadline_nou = '2052-01-01'
        self.__problema_noua = Problema(self.__nr_problema_nou, self.__descriere_noua, self.__deadline_nou)
        self.__nota_nota_noua = 10

    def test_create_nota(self):
        self.assertEqual(self.__id_nota, self.__nota.get_id())
        self.assertEqual(self.__student, self.__nota.get_student())
        self.assertEqual(self.__problema, self.__nota.get_problema())
        self.assertEqual(self.__nota_nota, self.__nota.get_nota())
        self.assertEqual(self.__nota, self.__alta_nota)

    def test_set_nota(self):
        self.assertNotEqual(self.__student_nou, self.__nota.get_student())
        self.__nota.set_student(self.__student_nou)
        self.assertEqual(self.__student_nou, self.__nota.get_student())
        self.assertNotEqual(self.__problema_noua, self.__nota.get_problema())
        self.__nota.set_problema(self.__problema_noua)
        self.assertEqual(self.__problema_noua, self.__nota.get_problema())
        self.assertNotEqual(self.__nota_nota_noua, self.__nota.get_nota())
        self.__nota.set_nota(self.__nota_nota_noua)
        self.assertEqual(self.__nota_nota_noua, self.__nota.get_nota())
        self.assertEqual(str(self.__nota), 'Studentul Andrei are nota 10 la problema 2')
        self.assertEqual(self.__nota.string_for_write(), '1,2,2,10')

class TesteValidareStudent(unittest.TestCase):

    def setUp(self):
        self.__validator = ValidatorStudent()
        self.__student = Student(1, 'Ionica', 212)
        self.__student_id_gresit = Student(-1, 'Ionica', 212)
        self.__student_nume_gresit = Student(1, '', 212)
        self.__student_grupa_gresita = Student(1, 'Ionica', -1)
        self.__student_gresit = Student(-1, '', -1)

    def test_validare_student_id_invalid(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__student_id_gresit)
        self.assertEqual(str(context.exception), "id_invalid!\n")

    def test_validare_student_nume_invalid(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__student_nume_gresit)
        self.assertEqual(str(context.exception), "nume invalid!\n")

    def test_validare_student_grupa_invalida(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__student_grupa_gresita)
        self.assertEqual(str(context.exception), "grupa invalida!\n")

    def test_validare_student_valori_invalide(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__student_gresit)
        self.assertEqual(str(context.exception), "id_invalid!\n" + "nume invalid!\n" + "grupa invalida!\n")

    def test_validare_student_valori_valide(self):
        try:
            self.__validator.valideaza(self.__student)
        except ValidError:
            self.fail("valideaza raised ValidError unexpectedly!")

class TesteValidareProblema(unittest.TestCase):

    def setUp(self):
        self.__validator = ValidatorProblema()
        deadline = '2042-12-12'.split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        deadline_gresit = '2000-12-12'.split('-')
        an, luna, zi = [int(item) for item in deadline_gresit]
        deadline_gresit = date(an, luna, zi)
        self.__problema = Problema(1, 'Calculul sumei a doua numere a si b', deadline)
        self.__problema_nr_gresit = Problema(-1, 'Calculul diferentei dintre numerele a si b', deadline)
        self.__problema_descriere_gresita = Problema(1, '', deadline)
        self.__problema_deadline_gresit = Problema(1, 'Calculul diferentei dintre numerele a si b', deadline_gresit)
        self.__problema_gresita = Problema(-1, '', deadline_gresit)

    def test_validare_problema_nr_invalid(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__problema_nr_gresit)
        self.assertEqual(str(context.exception), "nr invalid!\n")

    def test_validare_problema_descriere_invalid(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__problema_descriere_gresita)
        self.assertEqual(str(context.exception), "descriere invalida!\n")

    def test_validare_problema_deadline_invalid(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__problema_deadline_gresit)
        self.assertEqual(str(context.exception), "deadline invalid!\n")

    def test_validare_problema_valori_invalide(self):
        with self.assertRaises(ValidError) as context:
            self.__validator.valideaza(self.__problema_gresita)
        self.assertEqual(str(context.exception), "nr invalid!\n" + "descriere invalida!\n" + "deadline invalid!\n")

    def test_validare_problema_valori_valide(self):
        try:
            self.__validator.valideaza(self.__problema)
        except ValidError:
            self.fail("valideaza raised ValidError unexpectedly!")

class TesteValidareNota(unittest.TestCase):
    def setUp(self):
        self.__repo_studenti = RepoStudenti()
        self.__repo_probleme = RepoProbleme()
        self.__validator = ValidatorNota(self.__repo_studenti, self.__repo_probleme)
        self.__student = Student(1, 'Ionica', 212)
        self.__repo_studenti.adauga_student(self.__student)
        deadline = '2042-12-12'.split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.__problema = Problema(1, 'Calculul sumei a doua numere a si b', deadline)
        self.__repo_probleme.adauga_problema(self.__problema)

    def test_valideaza(self):
        nota = Nota(1, 1, 1, 9.5)
        self.__validator.valideaza(1, 1, nota)

    def test_valideaza_id_invalid(self):
        nota = Nota(-1, 1, 1, 9.5)
        with self.assertRaises(ValidError):
            self.__validator.valideaza(1, 1, nota)

    def test_valideaza_student_invalid(self):
        nota = Nota(1, 1, 1, 9.5)
        with self.assertRaises(ValidError):
            self.__validator.valideaza(2, 1, nota)

    def test_valideaza_problema_invalida(self):
        nota = Nota(1, 2, 1, 9.5)
        with self.assertRaises(ValidError):
            self.__validator.valideaza(1, 2, nota)

    def test_valideaza_nota_invalida(self):
        nota = Nota(1, 1, 1, 10.5)
        with self.assertRaises(ValidError):
            self.__validator.valideaza(1, 1, nota)

class TesteRepoStudenti(unittest.TestCase):

    def setUp(self):
        self.__repo_studenti = RepoStudenti()
        self.__student1 = Student(1, 'Ionel', 212)
        self.__student2 = Student(2, 'Ionica', 211)
        self.__student3 = Student(3, 'Mircea', 213)
        self.__student_nou = Student(2, 'Maria-Ioana', 210)
        self.__student_cu_id_inexistent = Student(10, 'Ioana', 215)

    def test_repo_studenti(self):
        '''se realizeaza testele pentru repository-ul de studenti'''
        '''se adauga trei studenti'''
        self.__repo_studenti.adauga_student(self.__student1)
        self.__repo_studenti.adauga_student(self.__student2)
        self.__repo_studenti.adauga_student(self.__student3)
        with self.assertRaises(RepoError) as context:
            self.__repo_studenti.adauga_student(self.__student3)
        self.assertEqual(str(context.exception), 'Student existent!')
        self.assertEqual(self.__repo_studenti.size(), 3)
        '''se sterge un student dupa id'''
        self.__repo_studenti.sterge_student(self.__student3.get_id())
        with self.assertRaises(RepoError) as context:
            self.__repo_studenti.sterge_student(self.__student3.get_id())
        self.assertEqual(str(context.exception), 'Student inexistent!')
        self.assertEqual(self.__repo_studenti.size(), 2)
        '''se modifica un student'''
        self.__repo_studenti.modifica_student(self.__student_nou)
        self.assertEqual(self.__student2.get_id(), self.__student_nou.get_id())
        self.assertNotEqual(self.__student2.get_nume(), self.__student_nou.get_nume())
        self.assertNotEqual(self.__student2.get_grupa(), self.__student_nou.get_grupa())
        with self.assertRaises(RepoError) as context:
            self.__repo_studenti.modifica_student(self.__student_cu_id_inexistent)
        self.assertEqual(str(context.exception), 'Student inexistent!')
        self.assertEqual(self.__repo_studenti.size(), 2)
        '''se cauta un student'''
        student_cautat = self.__repo_studenti.cauta_student(self.__student1.get_id())
        self.assertEqual(student_cautat, self.__student1)
        with self.assertRaises(RepoError) as context:
            self.__repo_studenti.cauta_student(3)
        self.assertEqual(str(context.exception), 'Student inexistent!')
        '''get_all studenti'''
        self.assertEqual(len(self.__repo_studenti.get_all()), 2)
        self.__repo_studenti.sterge_student(self.__student1.get_id())
        self.__repo_studenti.sterge_student(self.__student_nou.get_id())
        self.assertEqual(self.__repo_studenti.size(), 0)
        with self.assertRaises(RepoError) as context:
            self.__repo_studenti.get_all()
        self.assertEqual(str(context.exception), 'Nu exista studenti inregistrati!')

class TesteRepoProbleme(unittest.TestCase):
    def setUp(self):
        self.__repo_probleme = RepoProbleme()
        self.__problema1 = Problema(1, 'Calculul sumei a doua numere a si b', 2042-12-12)
        self.__problema2 = Problema(2, 'Calculul diferentei dintre numerele a si b', 2052-6-9)
        self.__problema3 = Problema(3, 'Se cere aflarea celui mai mare divizor comun', 2029-5-5)
        self.__problema_noua = Problema(2, 'Sa se imparta numarul a la b si sa se afiseze rezultatul', 2035-2-2)
        self.__problema_cu_id_inexistent = Problema(10, 'Calculati cea mai mare latura a unui patrulater cu latura de trei centimetri', 2040-12-12)

    def test_repo_probleme(self):
        '''se realizeaza testele pentru repository-ul de probleme'''
        '''se adauga trei probleme'''
        self.__repo_probleme.adauga_problema(self.__problema1)
        self.__repo_probleme.adauga_problema(self.__problema2)
        self.__repo_probleme.adauga_problema(self.__problema3)
        with self.assertRaises(RepoError) as context:
            self.__repo_probleme.adauga_problema(self.__problema3)
        self.assertEqual(str(context.exception), 'Problema existenta!')
        self.assertEqual(self.__repo_probleme.size(), 3)
        '''se sterge o problema dupa id'''
        self.__repo_probleme.sterge_problema(self.__problema3.get_nr())
        with self.assertRaises(RepoError) as context:
            self.__repo_probleme.sterge_problema(self.__problema3.get_nr())
        self.assertEqual(str(context.exception), 'Problema inexistenta!')
        self.assertEqual(self.__repo_probleme.size(), 2)
        '''se modifica o problema'''
        self.__repo_probleme.modifica_problema(self.__problema_noua)
        self.assertEqual(self.__problema2.get_nr(), self.__problema_noua.get_nr())
        self.assertNotEqual(self.__problema2.get_descriere(), self.__problema_noua.get_descriere())
        self.assertNotEqual(self.__problema2.get_deadline(), self.__problema_noua.get_deadline())
        with self.assertRaises(RepoError) as context:
            self.__repo_probleme.modifica_problema(self.__problema_cu_id_inexistent)
        self.assertEqual(str(context.exception), 'Problema inexistenta!')
        self.assertEqual(self.__repo_probleme.size(), 2)
        '''se cauta o problema'''
        student_cautat = self.__repo_probleme.cauta_problema(self.__problema1.get_nr())
        self.assertEqual(student_cautat, self.__problema1)
        with self.assertRaises(RepoError) as context:
            self.__repo_probleme.cauta_problema(3)
        self.assertEqual(str(context.exception), 'Problema inexistenta!')
        '''get_all probleme'''
        self.assertEqual(len(self.__repo_probleme.get_all()), 2)
        self.__repo_probleme.sterge_problema(self.__problema1.get_nr())
        self.__repo_probleme.sterge_problema(self.__problema_noua.get_nr())
        self.assertEqual(self.__repo_probleme.size(), 0)
        with self.assertRaises(RepoError) as context:
            self.__repo_probleme.get_all()
        self.assertEqual(str(context.exception), 'Nu exista probleme inregistrate!')

class TesteRepoNote(unittest.TestCase):

    def setUp(self):
        self.__repo_note = RepoNote()
        self.__nota = Nota(1, 1, 1, 9.5)

    def test_adauga_nota(self):
        self.__repo_note.adauga_nota(self.__nota)
        self.assertEqual(self.__repo_note.size(), 1)
        with self.assertRaises(RepoError):
            self.__repo_note.adauga_nota(self.__nota)

    def test_modifica_nota(self):
        self.__repo_note.adauga_nota(self.__nota)
        nota = Nota(1, 1, 1, 9.6)
        self.__repo_note.modifica_nota(nota)
        self.assertEqual(self.__repo_note.cauta_nota(1).get_nota(), 9.6)
        nota_inexistenta = Nota(2, 1, 1, 9.5)
        with self.assertRaises(RepoError):
            self.__repo_note.modifica_nota(nota_inexistenta)

    def test_sterge_nota(self):
        self.__repo_note.adauga_nota(self.__nota)
        self.__repo_note.sterge_nota(1)
        self.assertEqual(self.__repo_note.size(), 0)
        with self.assertRaises(RepoError):
            self.__repo_note.sterge_nota(1)

    def test_cauta_nota(self):
        self.__repo_note.adauga_nota(self.__nota)
        self.assertEqual(self.__repo_note.cauta_nota(1), self.__nota)
        with self.assertRaises(RepoError):
            self.__repo_note.cauta_nota(3)

    def test_get_all(self):
        self.assertEqual(self.__repo_note.get_all(), "Nu exista note inregistrate!")
        nota1 = Nota(1, 1, 1, 9.5)
        nota2 = Nota(2, 1, 2, 8.5)
        self.__repo_note.adauga_nota(nota1)
        self.__repo_note.adauga_nota(nota2)
        self.assertEqual(self.__repo_note.get_all(), [nota1, nota2])

class TesteServiceStudenti(unittest.TestCase):

    def setUp(self):
        self.__validator_student = ValidatorStudent()
        self.__file_repo_studenti = FileRepoStudenti()
        self.__service_studenti = ServiceStudenti(self.__validator_student, self.__file_repo_studenti)
        self.__repo_studenti = RepoStudenti()

    def test_service_studenti(self):
        '''se realizeaza testele pentru service studenti'''
        '''se adauga doi studenti'''
        self.__service_studenti.adauga_student(1,"Ionel",212)
        self.__service_studenti.adauga_student(2,"Vasile Stefan",213)
        with self.assertRaises(RepoError) as context:
            self.__service_studenti.adauga_student(1,"Ionel",212)
        self.assertEqual(str(context.exception), 'Student existent!')
        self.assertEqual(self.__file_repo_studenti.size(), 2)
        '''se modifica student'''
        self.__service_studenti.modifica_student(1,"Mirel",210)
        student_cautat = self.__service_studenti.cauta_student(1)
        self.assertEqual(student_cautat.get_id(), 1)
        self.assertEqual(student_cautat.get_nume(), 'Mirel')
        self.assertEqual(student_cautat.get_grupa(), 210)
        with self.assertRaises(RepoError) as context:
            self.__service_studenti.modifica_student(3,"Ionel",212)
        self.assertEqual(str(context.exception), 'Student inexistent!')
        self.assertEqual(self.__file_repo_studenti.size(), 2)
        '''se cauta student'''
        student_cautat = self.__service_studenti.cauta_student(1)
        self.assertEqual(student_cautat.get_id(), 1)
        self.assertEqual(student_cautat.get_nume(), 'Mirel')
        self.assertEqual(student_cautat.get_grupa(), 210)
        with self.assertRaises(RepoError) as context:
            self.__service_studenti.cauta_student(3)
        self.assertEqual(str(context.exception), 'Student inexistent!')
        '''se sterg studentii'''
        self.assertEqual(len(self.__service_studenti.get_all()), 2)
        self.__service_studenti.sterge_student(1)
        self.__service_studenti.sterge_student(2)
        with self.assertRaises(RepoError) as context:
            self.__service_studenti.sterge_student(3)
        self.assertEqual(str(context.exception), 'Student inexistent!')
        self.assertEqual(self.__repo_studenti.size(), 0)
        with self.assertRaises(RepoError) as context:
            self.__service_studenti.get_all()
        self.assertEqual(str(context.exception), 'Nu exista studenti inregistrati!')

class TesteServiceProbleme(unittest.TestCase):

    def setUp(self):
        self.__validator_problema = ValidatorProblema()
        self.__file_repo_probleme = FileRepoProbleme()
        self.__service_probleme = ServiceProbleme(self.__validator_problema, self.__file_repo_probleme)
        self.__repo_probleme = RepoProbleme()

    def test_service_probleme(self):
        '''se realizeaza testele pentru service probleme'''
        '''se adauga doua probleme'''
        self.__service_probleme.adauga_problema(1, "Calculul sumei a doua numere a si b", date(2042,12,12))
        self.__service_probleme.adauga_problema(2, "Calculul diferentei dintre numerele a si b", date(2052,6,9))
        with self.assertRaises(RepoError) as context:
            self.__service_probleme.adauga_problema(1, "Calculul sumei a doua numere a si b", date(2042,12,12))
        self.assertEqual(str(context.exception), 'Problema existenta!')
        self.assertEqual(self.__file_repo_probleme.size(), 2)
        '''se modifica problema'''
        self.__service_probleme.modifica_problema(1, "Inmultirea a doua numere a si b", date(2052,1,1))
        problema_cautata = self.__service_probleme.cauta_problema(1)
        self.assertEqual(problema_cautata.get_nr(), 1)
        self.assertEqual(problema_cautata.get_descriere(), 'Inmultirea a doua numere a si b')
        deadline = problema_cautata.get_deadline().split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.assertEqual(deadline, date(2052,1,1))
        with self.assertRaises(RepoError) as context:
            self.__service_probleme.modifica_problema(3, "Inmultirea a doua numere a si b", date(2052,1,1))
        self.assertEqual(str(context.exception), 'Problema inexistenta!')
        self.assertEqual(self.__file_repo_probleme.size(), 2)
        '''se cauta problema'''
        problema_cautata = self.__service_probleme.cauta_problema(1)
        self.assertEqual(problema_cautata.get_nr(), 1)
        self.assertEqual(problema_cautata.get_descriere(), 'Inmultirea a doua numere a si b')
        deadline = problema_cautata.get_deadline().split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.assertEqual(deadline, date(2052,1,1))
        with self.assertRaises(RepoError) as context:
            self.__service_probleme.cauta_problema(3)
        self.assertEqual(str(context.exception), 'Problema inexistenta!')
        '''se sterg problemele'''
        self.assertEqual(len(self.__service_probleme.get_all()), 2)
        self.__service_probleme.sterge_problema(1)
        self.__service_probleme.sterge_problema(2)
        with self.assertRaises(RepoError) as context:
            self.__service_probleme.sterge_problema(3)
        self.assertEqual(str(context.exception), 'Problema inexistenta!')
        self.assertEqual(self.__repo_probleme.size(), 0)
        with self.assertRaises(RepoError) as context:
            self.__service_probleme.get_all()
        self.assertEqual(str(context.exception), 'Nu exista probleme inregistrate!')

class TesteServiceNote(unittest.TestCase):

    def setUp(self):
        self.__repo_studenti = RepoStudenti()
        self.__repo_probleme = RepoProbleme()
        self.__validator_student = ValidatorStudent()
        self.__validator_problema = ValidatorProblema()
        self.__validator_nota = ValidatorNota(self.__repo_studenti, self.__repo_probleme)
        self.__file_repo_studenti = FileRepoStudenti()
        self.__file_repo_probleme = FileRepoProbleme()
        self.__file_repo_note = FileRepoNote()
        self.__service_studenti = ServiceStudenti(self.__validator_student, self.__file_repo_studenti)
        self.__service_probleme = ServiceProbleme(self.__validator_problema, self.__file_repo_probleme)
        self.__service_note = ServiceNote(self.__validator_nota, self.__file_repo_note, self.__file_repo_studenti, self.__file_repo_probleme)
        #self.__service_studenti.adauga_student(1, "Ionel", 212)
        self.__student = Student(1, 'Ionel', 212)
        self.__file_repo_studenti.adauga_student(self.__student)
        deadline = '2042-12-12'.split('-')
        an, luna, zi = [int(item) for item in deadline]
        deadline = date(an, luna, zi)
        self.__problema = Problema(1, 'Calculul sumei a doua numere a si b', deadline)
        self.__file_repo_probleme.adauga_problema(self.__problema)
        #self.__service_probleme.adauga_problema(1, "Calculul sumei a doua numere a si b", deadline)

    def test_adauga_nota(self):
        '''se realizeaza testele pentru service note'''
        #self.__service_note.adauga_nota(1, 1, 1, 9)
        #self.assertEqual(self.__file_repo_note.size(), 1)
        #with self.assertRaises(RepoError):
            #self.__service_note.adauga_nota(2, 1, 1, 10)