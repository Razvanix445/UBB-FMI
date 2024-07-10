USE Filme;
GO


-- V1 Modificare Tip Coloana
CREATE PROCEDURE SeteazaTipColoanaNumeTabelScenaristi
AS
	ALTER TABLE Scenaristi
		ALTER COLUMN NumeScenarist NVARCHAR(50)
GO


-- V1 Redo Modificare Tip Coloana
CREATE PROCEDURE ReseteazaTipColoanaNumeTabelScenaristi
AS
	ALTER TABLE Scenaristi
		ALTER COLUMN NumeScenarist VARCHAR(30)
GO


-- V2 Adaugare Constrangere "Valoare Implicita" la un Camp
CREATE PROCEDURE AdaugaDefaultNumeScenaristTabelScenaristi
AS
	ALTER TABLE Scenaristi
		ADD CONSTRAINT DF_NumeScenarist DEFAULT 'Ion' FOR NumeScenarist
GO


-- V2 Stergere Constrangere "Valoare Implicita" de la un Camp
CREATE PROCEDURE StergeDefaultNumeScenaristTabelScenaristi
AS
	ALTER TABLE Scenaristi
		DROP CONSTRAINT DF_NumeScenarist
GO


-- V3 Creare Tabel
CREATE PROCEDURE CreeazaTabelSeriale
AS
	CREATE TABLE Seriale(
		SerialID INT PRIMARY KEY IDENTITY(1,1),
		NumeSerial VARCHAR(50)
	)
GO


-- V3 Stergere Tabel
CREATE PROCEDURE StergeTabelSeriale
AS
	DROP TABLE Seriale
GO


-- V4 Adaugare Camp Nou
CREATE PROCEDURE AdaugaCampSalariuScenaristi
AS
	ALTER TABLE Scenaristi
		ADD Salariu INT
GO


-- V4 Stergere Camp
CREATE PROCEDURE StergeCampSalariuScenaristi
AS
	ALTER TABLE Scenaristi
		DROP COLUMN Salariu
GO


-- V5 Creare Constrangere de Cheie Straina
CREATE PROCEDURE CreeazaFKTabelScenaristiFilme
AS
	ALTER TABLE Scenaristi
		ADD CONSTRAINT FK_ScenaristiFilme FOREIGN KEY (ScenaristPentru) REFERENCES Filme(FilmID)
GO


-- V5 Stergere Constrangere de Cheie Straina
CREATE PROCEDURE StergeFKTabelScenaristiFIlme
AS
	ALTER TABLE Scenaristi
		DROP CONSTRAINT FK_ScenaristiFilme
GO


-- Procedura de aducere a bazei de date la o varianta specifica
CREATE PROCEDURE GoToVersion @versiuneFinala INT
AS
	DECLARE @versiuneCurenta INT;
	SELECT @versiuneCurenta = Versiune FROM Versiuni;

	WHILE @versiuneCurenta < @versiuneFinala
		IF @versiuneCurenta = 0
			EXEC SeteazaTipColoanaNumeTabelScenaristi;
		ELSE IF @versiuneCurenta = 1
			EXEC AdaugaDefaultNumeScenaristTabelScenaristi;
		ELSE IF @versiuneCurenta = 2
			EXEC CreeazaTabelSeriale;
		ELSE IF @versiuneCurenta = 3
			EXEC AdaugaCampSalariuScenaristi;
		ELSE IF @versiuneCurenta = 4
			EXEC CreeazaFKTabelScenaristiFilme;
		SET @versiuneCurenta = @versiuneCurenta + 1;

	WHILE @versiuneCurenta > @versiuneFinala
		IF @versiuneCurenta = 1
			EXEC ReseteazaTipColoanaNumeTabelScenaristi;
		ELSE IF @versiuneCurenta = 2
			EXEC StergeDefaultNumeScenaristTabelScenaristi;
		ELSE IF @versiuneCurenta = 3
			EXEC StergeTabelSeriale;
		ELSE IF @versiuneCurenta = 4
			EXEC StergeCampSalariuScenaristi;
		ELSE IF @versiuneCurenta = 5
			EXEC StergeFKTabelScenaristiFIlme;
		SET @versiuneCurenta = @versiuneCurenta - 1;
	UPDATE Versiuni SET Versiune = @versiuneFinala;
GO

SELECT * FROM Versiuni;
EXEC GoToVersion @versiuneFinala = 1;

/*
SeteazaTipColoanaNumeTabelScenaristi
ReseteazaTipColoanaNumeTabelScenaristi
AdaugaDefaultNumeScenaristTabelScenaristi
StergeDefaultNumeScenaristTabelScenaristi
CreeazaTabelSeriale
StergeTabelSeriale
AdaugaCampSalariuScenaristi
StergeCampSalariuScenaristi
CreeazaFKTabelScenaristiFilme
StergeFKTabelScenaristiFIlme
*/