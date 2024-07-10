USE Oser;

-- Organizatorii Oser doresc sa construiasca un catalog online pentru a le fi mai usor 
-- clientilor sa gaseasca obiectele de care au nevoie. Fiecare hala (litera, suprafata) are mai multe tarabe 
-- (suprafata, numar). Pe fiecare taraba se pot gasi mai multe categorii de produse. Fiecare categorie de produs
-- are un nume si poate fi gasita pe mai multe tarabe. Fiecare categorie de produse are mai multe produse.
-- Un produs are denumire, pret si poate aparatine unei singure categorii de produse.
 
-- 1. Scrieti un script SQL care creeaza un model relational pentru a reprezenta si stoca datele (4p)
 
-- 2. Un vanzator vrea sa faca bani. Creati o procedura stocata care actualizeaza pretul produselor de pe o taraba data:
-- Daca pretul e < 100, va creste cu 10 lei.
-- Daca pretul e > 200, va creste 50 lei.
-- Daca pretul e intre 100 si 200, va creste 10%. (3p)
 
-- 3. Spre finalul programalui, vanzatorii reduc din pret, dar nu toti. Creati un view care afiseaza toate produsele (denumire si pret) avand
-- pretul redus cu 60% din categoriile haine si vesela din halele "A", "F" si "X" (2p)


-- 1
CREATE TABLE Hale (
	id_hala INT PRIMARY KEY IDENTITY(1,1),
	litera VARCHAR(10),
	suprafata INT
)

CREATE TABLE Tarabe (
	id_taraba INT PRIMARY KEY IDENTITY(1,1),
	suprafata INT,
	numar INT,
	id_hala INT FOREIGN KEY REFERENCES Hale(id_hala)
)

CREATE TABLE Categorii (
	id_categorie INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(50)
)

CREATE TABLE TarabeCategorii (
	id_taraba INT FOREIGN KEY REFERENCES Tarabe(id_taraba),
	id_categorie INT FOREIGN KEY REFERENCES Categorii(id_categorie),
	PRIMARY KEY(id_taraba, id_categorie)
)

CREATE TABLE Produse (
	id_produs INT PRIMARY KEY IDENTITY(1,1),
	denumire VARCHAR(100),
	pret INT,
	id_categorie INT FOREIGN KEY REFERENCES Categorii(id_categorie)
)
GO;
-- 2

-- Un vanzator vrea sa faca bani. Creati o procedura stocata care actualizeaza pretul produselor de pe o taraba data:
-- Daca pretul e < 100, va creste cu 10 lei.
-- Daca pretul e > 200, va creste 50 lei.
-- Daca pretul e intre 100 si 200, va creste 10%.

CREATE OR ALTER PROCEDURE actualizeazaPret1 @id_taraba INT
AS
	UPDATE Produse
	SET pret = 
		CASE
			WHEN pret < 100 THEN pret + 10
			WHEN pret > 200 THEN pret + 50
			WHEN pret >= 100 AND  pret <= 200 THEN pret * 1.1
			ELSE pret
		END
	WHERE id_categorie IN (SELECT id_categorie FROM TarabeCategorii WHERE id_taraba = @id_taraba);
GO;

-- sau

CREATE OR ALTER PROCEDURE actualizeazaPret2 @id_taraba INT
AS
	UPDATE P
	SET P.pret = 
		CASE
			WHEN P.pret < 100 THEN P.pret + 10
			WHEN P.pret > 200 THEN P.pret + 50
			WHEN P.pret >= 100 AND P.pret <= 200 THEN P.pret * 1.1
			ELSE P.pret
		END
	FROM Produse P
	INNER JOIN TarabeCategorii TC ON P.id_categorie = TC.id_categorie
	WHERE TC.id_taraba = @id_taraba;
GO;


-- 3

-- Spre finalul programului, vanzatorii reduc din pret, dar nu toti. Creati un view care afiseaza toate produsele (denumire si pret) avand
-- pretul redus cu 60% din categoriile haine si vesela din halele "A", "F" si "X".
CREATE OR ALTER VIEW Script
AS
	SELECT DISTINCT P.denumire, P.pret * 0.4 AS Pret FROM Produse P
	INNER JOIN Categorii C ON P.id_categorie = C.id_categorie
	INNER JOIN TarabeCategorii TC ON C.id_categorie = TC.id_categorie
	INNER JOIN Tarabe T ON TC.id_taraba = T.id_taraba
	INNER JOIN Hale H ON T.id_hala = H.id_hala
	WHERE C.nume IN ('haine', 'vesela') AND H.litera IN ('A', 'F', 'X');
GO;

-- TESTARE:
-- 2
EXEC actualizeazaPret1 1;
EXEC actualizeazaPret2 1;

SELECT * FROM Produse;

-- 3
SELECT * FROM Script;


-- Introducing data into tables:

-- Fake data for Hale table
INSERT INTO Hale (litera, suprafata) VALUES
('A', 500),
('B', 700),
('C', 600),
('D', 500),
('E', 700),
('F', 600),
('X', 600);

-- Fake data for Tarabe table
INSERT INTO Tarabe (suprafata, numar, id_hala) VALUES
(50, 1, 1),
(40, 2, 5),
(60, 1, 6),
(55, 2, 2),
(45, 1, 7),
(70, 2, 7);

-- Fake data for Categorii table
INSERT INTO Categorii (nume) VALUES
('haine'),
('vesela'),
('Books'),
('Home Appliances');

-- Fake data for TarabeCategorii table
INSERT INTO TarabeCategorii (id_taraba, id_categorie) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 2),
(3, 4),
(4, 4),
(4, 1),
(5, 1),
(5, 2),
(6, 1),
(6, 4);

-- Fake data for Produse table
INSERT INTO Produse (denumire, pret, id_categorie) VALUES
('Smartphone', 120, 1),
('Laptop', 800, 1),
('T-Shirt', 25, 2),
('Jeans', 70, 2),
('Book 1', 15, 3),
('Book 2', 18, 3),
('Refrigerator', 300, 4),
('Microwave', 150, 4),
('Smartwatch', 80, 1),
('Shirt', 80, 2),
('Dress', 120, 2),
('Plate', 15, 4),
('Cup', 8, 4),
('Book', 25, 3),
('Trousers', 100, 2),
('Hat', 30, 2),
('Sweater', 90, 2),
('Mug', 10, 4);

SELECT * FROM Hale;
SELECT * FROM Tarabe;
SELECT * FROM TarabeCategorii;
SELECT * FROM Categorii;
SELECT * FROM Produse;

DROP TABLE Hale;
DROP TABLE Tarabe;
DROP TABLE TarabeCategorii;
DROP TABLE Categorii;
DROP TABLE Produse;