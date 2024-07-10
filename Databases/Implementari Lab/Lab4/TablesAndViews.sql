USE Filme;

-- ==============================================================================================================
-- TABELE
-- tabel cu cheie primară și nicio cheie străină (GenuriID - PK)
CREATE TABLE Genuri(
	GenuriID INT PRIMARY KEY IDENTITY(1,1),
	Gen VARCHAR(30)
)

-- tabel cu cheie primară și cel puțin o cheie străină (PK - FilmID, FK - RegizorID)
CREATE TABLE Filme(
	FilmID INT PRIMARY KEY IDENTITY(1,1),
	Titlu VARCHAR(30),
	DataLansare DATE,
	Descriere VARCHAR(200),
	ContentRating VARCHAR(30),
	UserRating FLOAT(3),
	RegizorID INT FOREIGN KEY REFERENCES Regizori(RegizorID),
	Buget INT,
	Incasari INT,
	StudioID INT FOREIGN KEY REFERENCES Studiouri(StudioID)
)

-- tabel cu două chei primare (FK - GenuriID, FilmID)
CREATE TABLE GenuriFilme(
	GenuriID INT FOREIGN KEY REFERENCES Genuri(GenuriID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(GenuriID, FilmID)
)

CREATE TABLE Regizori(
	RegizorID INT PRIMARY KEY IDENTITY(1,1),
	NumeRegizor VARCHAR(30) NOT NULL,
	PrenumeRegizor VARCHAR(30),
	DataNasterii DATE
)

-- ==============================================================================================================
-- VIEWS
-- view ce conține o comandă SELECT pe o tabelă
CREATE VIEW TitluriCuDataLansareSiDescriere AS
SELECT Titlu, DataLansare, Descriere
FROM Filme;

-- view ce conține o comandă SELECT aplicată pe cel puțin două tabele
CREATE VIEW FilmeCuRegizori AS
SELECT Filme.Titlu, Regizori.NumeRegizor
FROM Filme
JOIN Regizori ON Filme.RegizorID = Regizori.RegizorID;

-- view ce conține o comandă SELECT aplicată pe cel puțin două tabele și având o clauză GROUP BY
CREATE VIEW NumarFilmeDupaGen AS
SELECT Genuri.Gen, COUNT(Filme.FilmID) AS NumarFilme
FROM Genuri
JOIN GenuriFilme ON Genuri.GenuriID = GenuriFilme.GenuriID
JOIN Filme ON GenuriFilme.FilmID = Filme.FilmID
GROUP BY Genuri.Gen;


-- ==============================================================================================================
-- PROCEDURA DE POPULARE TABEL GENURI
CREATE OR ALTER PROCEDURE populateTableGenuri (@rows int) AS
	SET IDENTITY_INSERT Genuri ON;
	WHILE @rows > 0
	BEGIN
		DECLARE @randomString NVARCHAR(30);
		SET @randomString = LEFT(CONVERT(NVARCHAR(255), NEWID()), 30);
		INSERT INTO Genuri(GenuriID, Gen) VALUES (@rows, @randomString);
		SET @rows = @rows - 1;
	END
	SET IDENTITY_INSERT Genuri OFF;
GO

DECLARE @rows INT
SET @rows = 10
EXEC populateTableGenuri @rows;

-- ==============================================================================================================
-- PROCEDURA DE POPULARE TABEL FILME
CREATE OR ALTER PROCEDURE populateTableFilme (@rows INT) AS
BEGIN
    DECLARE @randomTitlu NVARCHAR(30), @randomDescriere NVARCHAR(200), @randomContentRating NVARCHAR(30);
    DECLARE @randomUserRating FLOAT, @randomRegizorID INT, @randomBuget INT, @randomIncasari INT, @randomStudioID INT;

    WHILE @rows > 0
    BEGIN
        SET @randomTitlu = LEFT(CONVERT(NVARCHAR(255), NEWID()), 30);
        SET @randomDescriere = LEFT(CONVERT(NVARCHAR(255), NEWID()), 200);
        SET @randomContentRating = LEFT(CONVERT(NVARCHAR(255), NEWID()), 30);
        SET @randomUserRating = RAND() * 10;
        SET @randomRegizorID = (SELECT TOP 1 RegizorID FROM Regizori ORDER BY NEWID());
        SET @randomBuget = CAST(RAND() * 1000000 AS INT);
        SET @randomIncasari = CAST(RAND() * 10000000 AS INT);
        SET @randomStudioID = (SELECT TOP 1 StudioID FROM Studiouri ORDER BY NEWID());

        INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, RegizorID, Buget, Incasari, StudioID)
        VALUES (@randomTitlu, GETDATE(), @randomDescriere, @randomContentRating, @randomUserRating, @randomRegizorID, @randomBuget, @randomIncasari, @randomStudioID);

        SET @rows = @rows - 1;
    END
END
GO

DECLARE @rows INT
SET @rows = 10
EXEC populateTableFilme @rows;

-- ==============================================================================================================
-- PROCEDURA DE POPULARE TABEL GENURIFILME
CREATE OR ALTER PROCEDURE populateTableGenuriFilme (@rows INT) AS
BEGIN
	WHILE @rows > 0
	BEGIN
		DECLARE @randomGenuriID INT;
		DECLARE @randomFilmID INT;

		SET @randomGenuriID = (SELECT TOP 1 GenuriID FROM Genuri ORDER BY NEWID());
		SET @randomFilmID = (SELECT TOP 1 FilmID FROM Filme ORDER BY NEWID());

		INSERT INTO GenuriFilme (GenuriID, FilmID)
		VALUES (@randomGenuriID, @randomFilmID);

		SET @rows = @rows - 1;
	END
END
GO

DECLARE @rows INT
SET @rows = 10
EXEC populateTableGenuriFilme @rows;

-- ==============================================================================================================
-- PROCEDURA DE POPULARE TABEL REGIZORI
CREATE OR ALTER PROCEDURE populateTableRegizori (@rows int) AS
BEGIN
    WHILE @rows > 0
    BEGIN
        DECLARE @randomNumeRegizor NVARCHAR(30);
        DECLARE @randomPrenumeRegizor NVARCHAR(30);
        DECLARE @randomDataNasterii DATE;

        SET @randomNumeRegizor = LEFT(CONVERT(NVARCHAR(255), NEWID()), 30);
        SET @randomPrenumeRegizor = LEFT(CONVERT(NVARCHAR(255), NEWID()), 30);
        SET @randomDataNasterii = DATEADD(DAY, -CAST(RAND() * 365 * 50 AS INT), GETDATE());

        INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii)
        VALUES (@randomNumeRegizor, @randomPrenumeRegizor, @randomDataNasterii);

        SET @rows = @rows - 1;
    END
END
GO

DECLARE @rows INT
SET @rows = 10
EXEC populateTableRegizori @rows;


-- SELECT * FROM Studiouri;
-- SELECT * FROM Filme;
-- SELECT * FROM Genuri;
-- SELECT * FROM GenuriFilme;
-- SELECT * FROM Regizori;
-- DELETE FROM Filme;
-- DELETE FROM Genuri;
-- DELETE FROM GenuriFilme;

SELECT * FROM Tables;
SELECT * FROM Views;
SELECT * FROM Tests;
SELECT * FROM TestTables;
SELECT * FROM TestViews;

SELECT * FROM TestRuns;

DELETE FROM TestTables;





ALTER TABLE Filme
ADD CONSTRAINT FK_Filme_RegizorID
FOREIGN KEY (RegizorID)
REFERENCES Regizori(RegizorID)
ON DELETE CASCADE;


ALTER TABLE GenuriFilme
ADD CONSTRAINT FK_GenuriFilme_FilmID
FOREIGN KEY (FilmID)
REFERENCES Filme(FilmID)
ON DELETE CASCADE;


CREATE OR ALTER PROCEDURE populateTableStudiouri (@rows INT) AS
BEGIN
    DECLARE @randomDenumire NVARCHAR(30), @randomAdresa NVARCHAR(100), @randomNumarAngajati INT;

    SET IDENTITY_INSERT Studiouri ON;

    WHILE @rows > 0
    BEGIN
        SET @randomDenumire = LEFT(CONVERT(NVARCHAR(255), NEWID()), 30);
        SET @randomAdresa = LEFT(CONVERT(NVARCHAR(255), NEWID()), 100);
        SET @randomNumarAngajati = CAST(RAND() * 1000 AS INT);

        INSERT INTO Studiouri (StudioID, Denumire, Adresa, NumarAngajati)
        VALUES (@rows, @randomDenumire, @randomAdresa, @randomNumarAngajati);

        SET @rows = @rows - 1;
    END

    SET IDENTITY_INSERT Studiouri OFF;
END
GO

DECLARE @rows INT
SET @rows = 10
EXEC populateTableStudiouri @rows;
