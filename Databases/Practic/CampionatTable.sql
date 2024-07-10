CREATE DATABASE CampionatTable;
USE CampionatTable;

-- La firma Cloudflight, pentru a mentine oamenii fericiti fara a mari salariile, se organizeaza un campionat de table.
-- Angajatii (nume, nr_contract) joaca meciuri cu alti angajati. Un meci se joaca intr-o data, pe o anume masa (numar, culoare)
-- si trebuie sa fie disputat de 2 participanti. Castigatorul meciului va trece mai departe in campionat. Pentru fiecare meci, 
-- se vor salva mutarile participantilor. O mutare are valoare zaruri (2 zaruri - valoare de la 1 la 6), piesa_mutata_de_la, 
-- piesa_mutata_la.
-- Meciurile pot fi vizionate de mai multi spectatori. Spectatorii vor fi tot angajati.

-- 1. Scrieti un script SQL care creeaza un model relational pentru a reprezenta si stoca datele. (4p)

-- 2. Scrieti o procedura stocata care calculeaza premiul unui participant dat ca parametru.
-- Pentru fiecare meci castigat, angajatul va primi 100 de puncte de beneficii.
-- Pentru fiecare spectator la meciurile angajatului, va primi 10 puncte de beneficii.
-- Pentru fiecare meci pe care angajatul NU l-a vazut, i se vor scadea 10 puncte de beneficii.
-- Premiul nu poate fi mai mic decat 0! (3p)

-- 3. Scrieti un view care calcueaza cate duble a dat jucatorul cu nr_contract "CLF3215" in tot campionatul.
-- O dubla inseamna ca valoarea celor 2 zaruri este egala (Ex. 1-1, 3-3, 5-5 etc.). (2p)

-- 1
CREATE TABLE Angajati (
	id_angajat INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(100),
	nr_contract VARCHAR(50),
)

CREATE TABLE Mese (
	id_masa INT PRIMARY KEY IDENTITY(1,1),
	numar INT,
	culoare VARCHAR(50)
)

CREATE TABLE Meciuri (
	id_meci INT PRIMARY KEY IDENTITY(1,1),
	data DATE,
	id_masa INT FOREIGN KEY REFERENCES Mese(id_masa),
	id_angajat1 INT FOREIGN KEY REFERENCES Angajati(id_angajat),
	id_angajat2 INT FOREIGN KEY REFERENCES Angajati(id_angajat),
	id_castigator INT,
)

CREATE TABLE Mutari (
	id_mutare INT PRIMARY KEY IDENTITY(1,1),
	valoare_zar1 INT,
	valoare_zar2 INT,
	piesa_mutata_de_la VARCHAR(20),
	piesa_mutata_la VARCHAR(20),
	id_participant INT FOREIGN KEY REFERENCES Angajati(id_angajat),
	id_meci INT FOREIGN KEY REFERENCES Meciuri(id_meci)
)

CREATE TABLE MeciuriSpectatori (
	id_meci INT FOREIGN KEY REFERENCES Meciuri(id_meci),
	id_angajat INT FOREIGN KEY REFERENCES Angajati(id_angajat),
	PRIMARY KEY(id_meci, id_angajat)
)


-- 2. Scrieti o procedura stocata care calculeaza premiul unui participant dat ca parametru.
-- Pentru fiecare meci castigat, angajatul va primi 100 de puncte de beneficii.
-- Pentru fiecare spectator la meciurile angajatului, va primi 10 puncte de beneficii.
-- Pentru fiecare meci pe care angajatul NU l-a vazut, i se vor scadea 10 puncte de beneficii.
-- Premiul nu poate fi mai mic decat 0! (3p)

CREATE OR ALTER PROCEDURE PremiuParticipant @id_participant INT
AS
	DECLARE @puncteMeciCastigat INT;
	SELECT @puncteMeciCastigat = COUNT(*) FROM Meciuri M
	WHERE M.id_castigator = @id_participant;
	
	PRINT 'Meciuri Castigate: ' + CAST(@puncteMeciCastigat AS VARCHAR(10));

	DECLARE @puncteSpectator INT;
	SELECT @puncteSpectator = COUNT(*) FROM MeciuriSpectatori MS, Meciuri M
	WHERE M.id_angajat1 = @id_participant AND M.id_meci = MS.id_meci OR M.id_angajat2 = @id_participant AND M.id_meci = MS.id_meci;

	PRINT 'Numar Spectatori: ' + CAST(@puncteSpectator AS VARCHAR(10));

	DECLARE @puncteMeciuriNevazute INT;
	SELECT @puncteMeciuriNevazute = COUNT(*) FROM MeciuriSpectatori MS
	WHERE MS.id_angajat = @id_participant

	DECLARE @totalMeciuri INT;
	SELECT @totalMeciuri = COUNT(*) FROM Meciuri M
	SET @puncteMeciuriNevazute = @totalMeciuri - @puncteMeciuriNevazute;

	PRINT 'Meciuri Nevazute: ' + CAST(@puncteMeciuriNevazute AS VARCHAR(10));

	DECLARE @totalPuncteAngajat INT;
	SET @totalPuncteAngajat = @puncteMeciCastigat * 100 + @puncteSpectator * 10 - @puncteMeciuriNevazute;

	PRINT 'Total Puncte Angajat: ' + CAST(@totalPuncteAngajat AS VARCHAR(10));
GO;

EXEC PremiuParticipant 1;


-- 3. Scrieti un view care calcueaza cate duble a dat jucatorul cu nr_contract "CLF3215" in tot campionatul.
-- O dubla inseamna ca valoarea celor 2 zaruri este egala (Ex. 1-1, 3-3, 5-5 etc.). (2p)

CREATE OR ALTER VIEW NumarDuble
AS
	SELECT COUNT(*) AS ROW_COUNT FROM Mutari Mut
	INNER JOIN Meciuri Mec ON Mut.id_meci = Mec.id_meci
	INNER JOIN Angajati A ON Mut.id_participant = A.id_angajat
	
	WHERE A.nr_contract = 'CLF3215' AND Mut.valoare_zar1 = Mut.valoare_zar2;
GO;

SELECT * FROM NumarDuble;

INSERT INTO Angajati (nume, nr_contract) VALUES
('Angajat1', 'CLF3215'),
('Angajat2', 'CLF3217'),
('Angajat3', 'CLF3216'),
('Angajat4', 'CLF3214'),
('Angajat5', 'CLF3213'),
('Angajat6', 'CLF3212'),
('Angajat7', 'CLF3211');

INSERT INTO Mese (numar, culoare) VALUES
(1, 'verde'),
(2, 'albastru'),
(3, 'gri'),
(4, 'galben');

INSERT INTO Meciuri (data, id_masa, id_angajat1, id_angajat2, id_castigator) VALUES
('2003-12-12', 1, 1, 2, 1),
('2004-12-12', 2, 2, 3, 2),
('2003-12-01', 3, 3, 4, 3),
('2003-11-11', 4, 4, 5, 4),
('2002-12-12', 1, 1, 3, 1),
('2005-07-07', 4, 4, 1, 4);

INSERT INTO Mutari (valoare_zar1, valoare_zar2, piesa_mutata_de_la, piesa_mutata_la, id_participant, id_meci) VALUES
(1, 1, '12', '13', 1, 1),
(2, 3, '13', '10', 2, 1),
(1, 3, '12', '13', 1, 1),
(2, 2, '13', '10', 2, 1),
(2, 4, '13', '10', 3, 2),
(5, 2, '13', '10', 2, 2),
(6, 6, '13', '10', 3, 2),
(1, 1, '12', '13', 3, 3),
(2, 3, '13', '10', 4, 3),
(1, 3, '12', '13', 4, 4),
(2, 2, '13', '10', 5, 4),
(4, 4, '13', '10', 1, 5),
(5, 2, '13', '10', 3, 5),
(6, 6, '13', '10', 4, 6);

INSERT INTO MeciuriSpectatori (id_meci, id_angajat) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 7),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 7),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 7),
(4, 1),
(4, 2),
(5, 3),
(5, 5),
(5, 7),
(6, 2),
(6, 3),
(6, 6),
(6, 7);


DROP TABLE Mutari;
SELECT * FROM Mutari;