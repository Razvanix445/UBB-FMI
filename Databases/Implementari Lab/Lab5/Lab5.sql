USE Filme;

-- ==============================================================================================================
-- TABELE
CREATE TABLE ActoriPrincipaliFilme(
	ActorPrincipalID INT FOREIGN KEY REFERENCES ActoriPrincipali(ActorPrincipalID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(ActorPrincipalID, FilmID),
	NumarActoriPrincipali INT
);

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
);

CREATE TABLE ActoriPrincipali(
	ActorPrincipalID INT PRIMARY KEY IDENTITY(1,1),
	NumeActorPrincipal VARCHAR(30) NOT NULL,
	PrenumeActorPrincipal VARCHAR(30),
	DataNasterii DATE,
	Gen VARCHAR(10)
);
GO;

-- ==============================================================================================================
-- PROCEDURI ActoriPrincipaliFilme

-- INSERT
CREATE OR ALTER PROCEDURE InsertActorPrincipalFilm
	@ActorPrincipalID INT,
	@FilmID INT,
	@NumarActoriPrincipali INT,
	@RowsAffected INT OUTPUT
AS
BEGIN
	INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali)
	VALUES (@ActorPrincipalID, @FilmID, @NumarActoriPrincipali);
	SET @RowsAffected = @@ROWCOUNT;
END;
GO;


-- SEARCH
CREATE PROCEDURE SearchActorPrincipalFilm
AS
BEGIN
	SELECT * FROM ActoriPrincipaliFilme;
END;
GO;


-- SEARCH BY ID
CREATE PROCEDURE SearchActorPrincipalFilmByID
	@ActorPrincipalID INT,
	@FilmID INT
AS
BEGIN
    SELECT * FROM ActoriPrincipaliFilme
	WHERE ActorPrincipalID = @ActorPrincipalID AND FilmID = @FilmID;
END;
GO;


-- REMOVE
CREATE OR ALTER PROCEDURE RemoveActorPrincipalFilm
	@ActorPrincipalID INT,
	@FilmID INT,
	@RowsAffected INT OUTPUT
AS
BEGIN
	DELETE FROM ActoriPrincipaliFilme
	WHERE ActorPrincipalID = @ActorPrincipalID AND FilmID = @FilmID;
	SET @RowsAffected = @@ROWCOUNT;
END;
GO;


-- UPDATE
CREATE OR ALTER PROCEDURE UpdateActorPrincipalFilm
	@ActorPrincipalID INT,
	@FilmID INT,
	@NumarActoriPrincipali INT,
	@RowsAffected INT OUTPUT
AS
BEGIN
	UPDATE ActoriPrincipaliFilme
	SET NumarActoriPrincipali = @NumarActoriPrincipali
	WHERE ActorPrincipalID = @ActorPrincipalID AND FilmID = @FilmID;
	SET @RowsAffected = @@ROWCOUNT;
END;
GO;


-- ==============================================================================================================
-- PROCEDURI Filme

-- INSERT
CREATE OR ALTER PROCEDURE InsertFilm
    @Titlu VARCHAR(30),
    @DataLansare DATE,
    @Descriere VARCHAR(200),
    @ContentRating VARCHAR(30),
    @UserRating FLOAT,
    @RegizorID INT,
    @Buget INT,
    @Incasari INT,
    @StudioID INT,
	@FilmID INT OUTPUT
AS
BEGIN
    INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, RegizorID, Buget, Incasari, StudioID)
    VALUES (@Titlu, @DataLansare, @Descriere, @ContentRating, @UserRating, @RegizorID, @Buget, @Incasari, @StudioID);
	SET @FilmID = SCOPE_IDENTITY();
END;
GO;


-- SEARCH
CREATE PROCEDURE SearchFilm
AS
BEGIN
    SELECT * FROM Filme;
END;
GO;


-- SEARCH BY ID
CREATE PROCEDURE SearchFilmByID
	@FilmID INT
AS
BEGIN
    SELECT * FROM Filme
	WHERE FilmID = @FilmID;
END;
GO;


-- REMOVE
CREATE OR ALTER PROCEDURE RemoveFilm
    @FilmID INT,
	@RowsAffected INT OUTPUT
AS
BEGIN
    DELETE FROM Filme
    WHERE FilmID = @FilmID;
	SET @RowsAffected = @@ROWCOUNT;
END;
GO;


-- UPDATE
CREATE OR ALTER PROCEDURE UpdateFilm
	@FilmID INT,
    @Titlu VARCHAR(30),
    @DataLansare DATE,
    @Descriere VARCHAR(200),
    @ContentRating VARCHAR(30),
    @UserRating FLOAT,
    @RegizorID INT,
    @Buget INT,
    @Incasari INT,
    @StudioID INT,
	@RowsAffected INT OUTPUT
AS
BEGIN
    UPDATE Filme
    SET Titlu = @Titlu,
        DataLansare = @DataLansare,
        Descriere = @Descriere,
        ContentRating = @ContentRating,
        UserRating = @UserRating,
        RegizorID = @RegizorID,
        Buget = @Buget,
        Incasari = @Incasari,
        StudioID = @StudioID
    WHERE FilmID = @FilmID;
	SET @RowsAffected = @@ROWCOUNT;
END;
GO;


-- ==============================================================================================================
-- PROCEDURI ActoriPrincipali

-- INSERT
CREATE OR ALTER PROCEDURE InsertActorPrincipal
    @NumeActorPrincipal VARCHAR(30),
    @PrenumeActorPrincipal VARCHAR(30),
    @DataNasterii DATE,
    @Gen VARCHAR(10),
	@ActorPrincipalID INT OUTPUT
AS
BEGIN
    INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen)
    VALUES (@NumeActorPrincipal, @PrenumeActorPrincipal, @DataNasterii, @Gen);
	SET @ActorPrincipalID = SCOPE_IDENTITY();
END;
GO;


-- SEARCH
CREATE PROCEDURE SearchActoriPrincipali
AS
BEGIN
    SELECT * FROM ActoriPrincipali;
END;
GO;


-- SEARCH BY ID
CREATE PROCEDURE SearchActoriPrincipaliByID
	@ActorPrincipalID INT
AS
BEGIN
    SELECT * FROM ActoriPrincipali
	WHERE ActorPrincipalID = @ActorPrincipalID;
END;
GO;


-- REMOVE
CREATE OR ALTER PROCEDURE RemoveActorPrincipal
    @ActorPrincipalID INT,
	@RowsAffected INT OUTPUT
AS
BEGIN
    DELETE FROM ActoriPrincipali
    WHERE ActorPrincipalID = @ActorPrincipalID;
	SET @RowsAffected = @@ROWCOUNT;
END;
GO;


-- UPDATE
CREATE OR ALTER PROCEDURE UpdateActorPrincipal
    @ActorPrincipalID INT,
    @NumeActorPrincipal VARCHAR(30),
    @PrenumeActorPrincipal VARCHAR(30),
    @DataNasterii DATE,
    @Gen VARCHAR(10),
	@Succes BIT OUTPUT
AS
BEGIN
    UPDATE ActoriPrincipali
    SET
        NumeActorPrincipal = @NumeActorPrincipal,
        PrenumeActorPrincipal = @PrenumeActorPrincipal,
        DataNasterii = @DataNasterii,
        Gen = @Gen
    WHERE ActorPrincipalID = @ActorPrincipalID;

	IF @@ROWCOUNT > 0
		SET @Succes = 1;
	ELSE
		SET @Succes = 0;
END;
GO;


-- ==============================================================================================================
-- FUNCTII VALIDARE

CREATE FUNCTION IsValidDateFormat
    (@DateValue VARCHAR(10))
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;
    IF ISDATE(@DateValue) = 1
        SET @IsValid = 1;
    RETURN @IsValid;
END;
GO;


CREATE FUNCTION IsValidContentRating
    (@ContentRating VARCHAR(30))
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;
    IF @ContentRating IN ('G', 'PG', 'PG-13', 'R', 'NC-17')
        SET @IsValid = 1;
    RETURN @IsValid;
END;
GO;


CREATE FUNCTION IsValidUserRating
    (@UserRating FLOAT)
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;
    IF @UserRating >= 0 AND @UserRating <= 10
        SET @IsValid = 1;
    RETURN @IsValid;
END;
GO;


CREATE FUNCTION IsNotNullOrEmpty
    (@Value NVARCHAR(MAX))
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;
    IF @Value IS NOT NULL AND LTRIM(RTRIM(@Value)) <> ''
        SET @IsValid = 1;
    RETURN @IsValid;
END;
GO;


CREATE FUNCTION IsValidValue
    (@Value INT)
RETURNS BIT
AS
BEGIN
    DECLARE @IsValid BIT = 0;
    IF @Value >= 0
        SET @IsValid = 1;
    RETURN @IsValid;
END;
GO;


-- ==============================================================================================================
-- ALTER TABLE ADDING CONSTRAINTS

ALTER TABLE Filme
ADD CONSTRAINT CK_TitleNotNullOrEmpty CHECK (dbo.IsNotNullOrEmpty(Titlu) = 1),
	CONSTRAINT CK_UserRating CHECK (dbo.IsValidUserRating(UserRating) = 1),
    CONSTRAINT CK_ContentRating CHECK (dbo.IsValidContentRating(ContentRating) = 1),
    CONSTRAINT CK_Buget CHECK (dbo.IsValidValue(Buget) = 1),
    CONSTRAINT CK_Incasari CHECK (dbo.IsValidValue(Incasari) = 1);

ALTER TABLE ActoriPrincipali
ADD CONSTRAINT CK_NumeActorPrincipalNotNullOrEmpty CHECK (dbo.IsNotNullOrEmpty(NumeActorPrincipal) = 1),
	CONSTRAINT CK_Gen CHECK (Gen IS NOT NULL);

ALTER TABLE ActoriPrincipaliFilme
ADD CONSTRAINT CK_ActorPrincipalIDNotNull CHECK (ActorPrincipalID IS NOT NULL),
    CONSTRAINT CK_FilmIDNotNull CHECK (FilmID IS NOT NULL),
	CONSTRAINT CK_NumarActoriPrincipali CHECK (NumarActoriPrincipali >= 0);
GO;


-- ==============================================================================================================
-- CREATE VIEWS

CREATE VIEW FilmActorSummaryView AS
SELECT
    F.FilmID,
    F.Titlu AS FilmTitlu,
    F.DataLansare,
    COUNT(APF.ActorPrincipalID) AS NumarActoriPrincipali
FROM
    Filme F
LEFT JOIN
    ActoriPrincipaliFilme APF ON F.FilmID = APF.FilmID
GROUP BY
    F.FilmID, F.Titlu, F.DataLansare
HAVING
    COUNT(APF.ActorPrincipalID) > 0;
GO;


CREATE OR ALTER VIEW MainActorsView AS
SELECT
    F.FilmID,
    F.Titlu AS NumeFilm,
    AP.ActorPrincipalID,
    AP.NumeActorPrincipal,
    AP.PrenumeActorPrincipal,
    AP.DataNasterii AS DataNasteriiActor,
    AP.Gen AS GenActor,
    APF.NumarActoriPrincipali
FROM
    Filme F
JOIN
    ActoriPrincipaliFilme APF ON F.FilmID = APF.FilmID
JOIN
    ActoriPrincipali AP ON APF.ActorPrincipalID = AP.ActorPrincipalID
WHERE
    APF.NumarActoriPrincipali > 2;
GO;
DROP VIEW MainActorsView;

-- ==============================================================================================================
-- CREATE NON-CLUSTERED INDEXES

CREATE NONCLUSTERED INDEX IX_Filme_Titlu
ON Filme (Titlu);

CREATE NONCLUSTERED INDEX IX_ActorPrincipalFilm_ActorPrincipalID
ON ActoriPrincipaliFilme (NumarActoriPrincipali);

DROP INDEX IX_Filme_Titlu ON Filme;
DROP INDEX IX_Filme_DataLansare ON Filme;
DROP INDEX IX_ActorPrincipalFilmID ON ActoriPrincipaliFilme;
DROP INDEX IX_ActorPrincipalFilm_ActorPrincipalID ON ActoriPrincipaliFilme;

-- ==============================================================================================================
-- EXEC

DECLARE @InsertActorPrincipalRowsAffected INT;
EXEC InsertActorPrincipal 'John', 'Doe', '1990-01-01', 'Male', @InsertActorPrincipalRowsAffected OUTPUT;
PRINT 'Rows affected by InsertActorPrincipal: ' + CAST(@InsertActorPrincipalRowsAffected AS NVARCHAR(10));
EXEC SearchActoriPrincipali;
EXEC SearchActoriPrincipaliByID 12;
DECLARE @RemoveActorPrincipalRowsAffected INT;
EXEC RemoveActorPrincipal 22, @RemoveActorPrincipalRowsAffected OUTPUT;
PRINT 'Rows affected by RemoveActorPrincipal: ' + CAST(@RemoveActorPrincipalRowsAffected AS NVARCHAR(10));
DECLARE @UpdateActorPrincipalRowsAffected INT;
EXEC UpdateActorPrincipal 21, 'Updated John', 'Updated Doe', '1990-01-02', 'Male', @UpdateActorPrincipalRowsAffected OUTPUT;
PRINT 'Rows affected by UpdateActorPrincipal: ' + CAST(@UpdateActorPrincipalRowsAffected AS NVARCHAR(10));


DECLARE @InsertFilmRowsAffected INT;
EXEC InsertFilm 'Movie Title', '2023-01-01', 'Description', 'PG-13', 8.5, 1, 1000000, 5000000, 1, @InsertFilmRowsAffected OUTPUT;
PRINT 'Rows affected by InsertFilm: ' + CAST(@InsertFilmRowsAffected AS NVARCHAR(10));
EXEC SearchFilm;
EXEC SearchFilmByID 23025;
DECLARE @RemoveFilmRowsAffected INT;
EXEC RemoveFilm 17, @RemoveFilmRowsAffected OUTPUT;
PRINT 'Rows affected by RemoveFilm: ' + CAST(@RemoveFilmRowsAffected AS NVARCHAR(10));
DECLARE @UpdateFilmRowsAffected INT;
EXEC UpdateFilm 17, 'Updated Title', '2023-01-02', 'Updated Description', 'R', 7.5, 2, 1500000, 8000000, 2, @UpdateFilmRowsAffected OUTPUT;
PRINT 'Rows affected by UpdateFilm: ' + CAST(@UpdateFilmRowsAffected AS NVARCHAR(10));


DECLARE @InsertActorPrincipalFilmRowsAffected INT;
EXEC InsertActorPrincipalFilm 1, 3, 2, @InsertActorPrincipalFilmRowsAffected OUTPUT;
PRINT 'Rows affected by InsertActorPrincipalFilm: ' + CAST(@InsertActorPrincipalFilmRowsAffected AS NVARCHAR(10));
EXEC SearchActorPrincipalFilm;
EXEC SearchActorPrincipalFilmByID 1, 23025;
DECLARE @RemoveActorPrincipalFilmRowsAffected INT;
EXEC RemoveActorPrincipalFilm 1, 3, @RemoveActorPrincipalFilmRowsAffected OUTPUT;
PRINT 'Rows affected by RemoveActorPrincipalFilm: ' + CAST(@RemoveActorPrincipalFilmRowsAffected AS NVARCHAR(10));
DECLARE @UpdateActorPrincipalFilmRowsAffected INT;
EXEC UpdateActorPrincipalFilm 1, 3, 3, @UpdateActorPrincipalFilmRowsAffected OUTPUT;
PRINT 'Rows affected by UpdateActorPrincipalFilm: ' + CAST(@UpdateActorPrincipalFilmRowsAffected AS NVARCHAR(10));


SELECT * FROM FilmActorSummaryView;
SELECT * FROM MainActorsView;




SELECT * FROM Filme ORDER BY Filme.Titlu;
SELECT * FROM ActoriPrincipali;
SELECT * FROM ActoriPrincipaliFilme;
SELECT * FROM Regizori;

-- DBCC CHECKIDENT ('Filme', RESEED, 0);
-- DBCC CHECKIDENT ('ActoriPrincipaliFilme', RESEED, 0);
-- DBCC CHECKIDENT ('ActoriPrincipali', RESEED, 0);

DELETE FROM Filme;
DELETE FROM ActoriPrincipaliFilme;
DELETE FROM ActoriPrincipali;