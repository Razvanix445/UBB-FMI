CREATE DATABASE RestaurantSamsara;
USE RestaurantSamsara;
-- de nota 6.5
-- 1

CREATE TABLE Clienti (
	id_client INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(50),
	nr_telefon INT
)

CREATE TABLE Comenzi (
	id_comanda INT PRIMARY KEY IDENTITY(1,1),
	adresa_de_livrare VARCHAR(50),
	data DATE,
	id_client INT FOREIGN KEY REFERENCES Clienti(id_client)
)

CREATE TABLE Preparate (
	id_preparat INT PRIMARY KEY IDENTITY(1,1),
	denumire VARCHAR(50),
	cantitate INT,
	pret INT
)

CREATE TABLE ComenziPreparate(
	id_comanda INT FOREIGN KEY REFERENCES Comenzi(id_comanda),
	id_preparat INT FOREIGN KEY REFERENCES Preparate(id_preparat),
	PRIMARY KEY (id_comanda, id_preparat)
)

CREATE TABLE Ingrediente (
	id_ingredient INT PRIMARY KEY IDENTITY(1,1),
	denumire VARCHAR(50),
	calorii INT
)

CREATE TABLE IngredientePreparate (
	id_ingredient INT FOREIGN KEY REFERENCES Ingrediente(id_ingredient),
	id_preparat INT FOREIGN KEY REFERENCES Preparate(id_preparat),
	PRIMARY KEY (id_ingredient, id_preparat)
)

-- 2

CREATE OR ALTER PROCEDURE StergeIngredient @numeIngredient VARCHAR(30)
AS
	DECLARE @nr_comenzi_de_sters INT;
	SELECT @nr_comenzi_de_sters = COUNT(*) FROM Comenzi Co
	--DELETE Co FROM Comenzi Co
	INNER JOIN Clienti Cl ON Co.id_client = Cl.id_client
	INNER JOIN ComenziPreparate CP ON Co.id_comanda = CP.id_comanda
	INNER JOIN IngredientePreparate IPP ON CP.id_preparat = IPP.id_preparat
	INNER JOIN Ingrediente I ON IPP.id_ingredient = I.id_ingredient
	WHERE I.denumire = @numeIngredient;
	PRINT 'Numar comenzi de sters: ' + CAST(@nr_comenzi_de_sters AS VARCHAR(10));
	RETURN @@ROWCOUNT;
GO;

EXEC StergeIngredient 'Ingredient1';

-- 3

CREATE OR ALTER VIEW AfisareComenzi
AS
	SELECT Co.data, 
	(SELECT SUM(P.pret) AS Suma FROM Preparate P
	INNER JOIN ComenziPreparate CP ON P.id_preparat = CP.id_preparat
	INNER JOIN Comenzi Co ON CP.id_comanda = Co.id_comanda
	INNER JOIN Clienti Cl ON Co.id_client = Cl.id_client
	WHERE Cl.nume = 'Client1') AS Suma
	FROM Comenzi Co
	INNER JOIN Clienti Cl ON Co.id_client = Cl.id_client
	WHERE Cl.nume = 'Client1';
GO;

SELECT * FROM AfisareComenzi;

-- POPULARE DATE:
INSERT INTO Clienti (nume, nr_telefon) VALUES
('Client1', 0720010024),
('Client2', 0720010233),
('Client3', 0720012023),
('Client4', 0720010023),
('Client5', 0724510023);

INSERT INTO Comenzi (adresa_de_livrare, data, id_client) VALUES
('Adresa1', '2003-12-12', 1),
('Adresa2', '2004-12-12', 1),
('Adresa3', '2001-12-12', 2),
('Adresa4', '2002-12-12', 2),
('Adresa5', '2003-01-12', 3),
('Adresa6', '2003-02-12', 3),
('Adresa7', '2006-12-12', 4),
('Adresa8', '2003-01-12', 5);

INSERT INTO Preparate (denumire, cantitate, pret) VALUES
('Preparat1', 12, 10),
('Preparat2', 14, 15),
('Preparat3', 10, 9),
('Preparat4', 11, 12),
('Preparat5', 12, 19),
('Preparat6', 20, 17),
('Preparat7', 4, 12),
('Preparat8', 3, 20);

INSERT INTO ComenziPreparate (id_comanda, id_preparat) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(3, 2),
(3, 5),
(3, 6),
(3, 7),
(4, 1),
(5, 8),
(6, 5),
(7, 6),
(8, 7);

INSERT INTO Ingrediente (denumire, calorii) VALUES
('Ingredient1', 120),
('Ingredient2', 130),
('Ingredient3', 220),
('Ingredient4', 100),
('Ingredient5', 90);

INSERT INTO IngredientePreparate (id_ingredient, id_preparat) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(1, 3),
(2, 3),
(1, 4),
(5, 4),
(2, 5),
(4, 5);

SELECT * FROM Clienti;
SELECT * FROM Comenzi;