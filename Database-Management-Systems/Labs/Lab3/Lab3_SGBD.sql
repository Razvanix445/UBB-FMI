USE Filme;

-- ==============================================================================================================
-- TABELE
CREATE TABLE ActoriPrincipali(
	ActorPrincipalID INT PRIMARY KEY IDENTITY(1,1),
	NumeActorPrincipal VARCHAR(30) NOT NULL,
	PrenumeActorPrincipal VARCHAR(30),
	DataNasterii DATE,
	Gen VARCHAR(10)
)

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

CREATE TABLE ActoriPrincipaliFilme(
	ActorPrincipalID INT FOREIGN KEY REFERENCES ActoriPrincipali(ActorPrincipalID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(ActorPrincipalID, FilmID),
	NumarActoriPrincipali INT
)
-- ==============================================================================================================
-- SISTEM DE LOGARE

CREATE TABLE LoggingTable
(
	LoggingTableID INT PRIMARY KEY IDENTITY(1, 1),
	operationType VARCHAR(50),
	tableName VARCHAR(50),
	executionTime DATETIME
);

-- ==============================================================================================================
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
GO


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
-- ==============================================================================================================
-- VALIDATION FOR ACTORIPRINCIPALI TABLE
CREATE FUNCTION ValidateActoriPrincipaliParameters
(
	@NumeActorPrincipal VARCHAR(30),
	@PrenumeActorPrincipal VARCHAR(30),
	@DataNasterii DATE,
	@Gen VARCHAR(10)
)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = ''

	IF (dbo.IsNotNullOrEmpty(@NumeActorPrincipal) = 0) 
		SET @errorMessage = @errorMessage + 'Nume Actor Principal Invalid! '

	IF (dbo.IsNotNullOrEmpty(@PrenumeActorPrincipal) = 0) 
		SET @errorMessage = @errorMessage + 'Prenume Actor Principal Invalid! '

	IF (dbo.IsValidDateFormat(CONVERT(VARCHAR, @DataNasterii, 23)) = 0)
		SET @errorMessage = @errorMessage + 'Data de Nastere Invalida! '

    IF (dbo.IsNotNullOrEmpty(@Gen) = 0 OR @Gen NOT IN ('Masculin', 'Feminin'))
        SET @errorMessage = @errorMessage + 'Gen Invalid!'

	RETURN @errorMessage
END;

-- ==============================================================================================================
-- VALIDATION FOR FILME TABLE
CREATE FUNCTION ValidateFilmeParameters
(
	@Titlu VARCHAR(30),
	@DataLansare DATE,
	@Descriere VARCHAR(200),
	@ContentRating VARCHAR(30),
	@UserRating FLOAT,
	@RegizorID INT,
	@Buget INT,
	@Incasari INT,
	@StudioID INT
)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = ''

	IF (dbo.IsNotNullOrEmpty(@Titlu) = 0)
		SET @errorMessage = @errorMessage + 'Titlu Invalid! '

	IF (dbo.IsValidDateFormat(CONVERT(VARCHAR, @DataLansare, 23)) = 0)
		SET @errorMessage = @errorMessage + 'Data de Lansare Invalida! '

	IF (dbo.IsNotNullOrEmpty(@Descriere) = 0)
		SET @errorMessage = @errorMessage + 'Descriere Invalida! '

	IF (dbo.IsValidContentRating(@ContentRating) = 0)
		SET @errorMessage = @errorMessage + 'Content Rating Invalid! '

	IF (dbo.IsValidUserRating(@UserRating) = 0)
		SET @errorMessage = @errorMessage + 'User Rating Invalid! '
		
	IF (NOT EXISTS(SELECT RegizorID FROM Regizori WHERE RegizorID = @RegizorID))
        SET @errorMessage = @errorMessage + 'Regizor Negasit! '

	IF (dbo.IsValidValue(@Buget) = 0)
		SET @errorMessage = @errorMessage + 'Buget Invalid! '

	IF (dbo.IsValidValue(@Incasari) = 0)
		SET @errorMessage = @errorMessage + 'Incasari Invalide! '

	IF (NOT EXISTS(SELECT StudioID FROM Studiouri WHERE StudioID = @StudioID))
        SET @errorMessage = @errorMessage + 'Studio Negasit!'

	RETURN @errorMessage
END;

-- ==============================================================================================================
-- VALIDATION FOR ACTORIPRINCIPALIFILME TABLE
CREATE FUNCTION ValidateActoriPrincipaliFilmeParameters
(
	@ActorPrincipalID INT,
	@FilmID INT
)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @errorMessage VARCHAR(200)
	SET @errorMessage = ''

	IF (NOT EXISTS(SELECT ActorPrincipalID FROM ActoriPrincipali WHERE ActorPrincipalID = @ActorPrincipalID))
		SET @errorMessage = @errorMessage + 'Actor Principal Negasit! '

	IF (NOT EXISTS(SELECT FilmID FROM Filme WHERE FilmID = @FilmID))
		SET @errorMessage = @errorMessage + 'Film Negasit! '

	IF (EXISTS(SELECT ActorPrincipalID, FilmID FROM ActoriPrincipaliFilme WHERE ActorPrincipalID = @ActorPrincipalID AND FilmID = @FilmID))
        SET @errorMessage = @errorMessage + 'Relatie Existenta!'

	RETURN @errorMessage
END;

-- ==============================================================================================================
-- ==============================================================================================================
CREATE OR ALTER PROCEDURE InsertDataIntoTables
(
	@Titlu VARCHAR(30),
	@DataLansare DATE,
	@Descriere VARCHAR(200),
	@ContentRating VARCHAR(30),
	@UserRating FLOAT,
	@RegizorID INT,
	@Buget INT,
	@Incasari INT,
	@StudioID INT,
	@NumeActorPrincipal VARCHAR(30),
	@PrenumeActorPrincipal VARCHAR(30),
	@DataNasterii DATE,
	@Gen VARCHAR(10),
	@NumarActoriPrincipali INT
)
AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		DECLARE @errorMessage VARCHAR(200)

		-- VALIDATION AND INSERT FOR FILME TABLE
		SET @errorMessage = dbo.ValidateFilmeParameters(@Titlu, @DataLansare, @Descriere, @ContentRating, @UserRating, @RegizorID, @Buget, @Incasari, @StudioID)
		IF (@errorMessage != '')
		BEGIN
			PRINT 'Error Filme: ' + @errorMessage
			RAISERROR(@errorMessage, 14, 1)
		END

		INSERT INTO Filme(Titlu, DataLansare, Descriere, ContentRating, UserRating, RegizorID, Buget, Incasari, StudioID)
		VALUES (@Titlu, @DataLansare, @Descriere, @ContentRating, @UserRating, @RegizorID, @Buget, @Incasari, @StudioID)
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('INSERT', 'Filme', CURRENT_TIMESTAMP)


		-- VALIDATION AND INSERT FOR ACTORIPRINCIPALI TABLE
		SET @errorMessage = dbo.ValidateActoriPrincipaliParameters(@NumeActorPrincipal, @PrenumeActorPrincipal, @DataNasterii, @Gen)
        IF (@errorMessage != '')
        BEGIN
			PRINT 'Error ActoriPrincipali: ' + @errorMessage
            RAISERROR(@errorMessage, 14, 1)
        END

		INSERT INTO ActoriPrincipali(NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen)
        VALUES (@NumeActorPrincipal, @PrenumeActorPrincipal, @DataNasterii, @Gen)
        INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('INSERT', 'ActoriPrincipali', CURRENT_TIMESTAMP)
        
		DECLARE @FilmID INT, @ActorPrincipalID INT
        SET @FilmID = (SELECT MAX(FilmID) FROM Filme)
        SET @ActorPrincipalID = (SELECT MAX(ActorPrincipalID) FROM ActoriPrincipali)

		-- VALIDATION AND INSERT FOR ACTORIPRINCIPALIFILME TABLE
        SET @errorMessage = dbo.ValidateActoriPrincipaliFilmeParameters(@ActorPrincipalID, @FilmID)
        IF (@errorMessage != '')
        BEGIN
			PRINT 'Error ActoriPrincipaliFilme: ' + @errorMessage
            RAISERROR(@errorMessage, 14, 1)
        END

		INSERT INTO ActoriPrincipaliFilme(ActorPrincipalID, FilmID, NumarActoriPrincipali)
        VALUES (@ActorPrincipalID, @FilmID, @NumarActoriPrincipali)
        INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('INSERT', 'ActoriPrincipaliFilme', CURRENT_TIMESTAMP)
        
        COMMIT TRAN
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION COMMITTED', 'Tables', CURRENT_TIMESTAMP)
        SELECT 'Transaction committed'
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION ROLLBACKED', 'Tables', CURRENT_TIMESTAMP)
        SELECT 'Transaction rollbacked'
    END CATCH
END
GO

-- ==============================================================================================================
-- CAZURI DE EXECUTIE CU SUCCES
EXEC InsertDataIntoTables 
    @Titlu = 'Inception',
    @DataLansare = '2010-07-16',
    @Descriere = 'A thief who enters the dreams of others to steal their secrets from their subconscious.',
    @ContentRating = 'PG-13',
    @UserRating = 8.8,
    @RegizorID = 1,
    @Buget = 160000000,
    @Incasari = 829895144,
    @StudioID = 1,
    @NumeActorPrincipal = 'DiCaprio',
    @PrenumeActorPrincipal = 'Leonardo',
    @DataNasterii = '1974-11-11',
    @Gen = 'Masculin',
    @NumarActoriPrincipali = 1;
SELECT * FROM Filme
SELECT * FROM ActoriPrincipali
SELECT * FROM ActoriPrincipaliFilme
SELECT * FROM LoggingTable

-- CAZURI DE EXECUTIE CU EROARE
EXEC InsertDataIntoTables 
    @Titlu = 'Inception',
    @DataLansare = '2010-07-16',
    @Descriere = 'A thief who enters the dreams of others to steal their secrets from their subconscious.',
    @ContentRating = 'PG-1',
    @UserRating = 8.8,
    @RegizorID = 1,
    @Buget = 160000000,
    @Incasari = 829895144,
    @StudioID = 1,
    @NumeActorPrincipal = 'DiCaprio',
    @PrenumeActorPrincipal = '',
    @DataNasterii = '1974-11-11',
    @Gen = 'Masculin',
    @NumarActoriPrincipali = 1;
SELECT * FROM Filme
SELECT * FROM ActoriPrincipali
SELECT * FROM ActoriPrincipaliFilme
SELECT * FROM LoggingTable


-- ==============================================================================================================
-- ==============================================================================================================
CREATE OR ALTER PROCEDURE InsertDataIntoTablesWithPartialRollback
(
	@Titlu VARCHAR(30),
	@DataLansare DATE,
	@Descriere VARCHAR(200),
	@ContentRating VARCHAR(30),
	@UserRating FLOAT,
	@RegizorID INT,
	@Buget INT,
	@Incasari INT,
	@StudioID INT,
	@NumeActorPrincipal VARCHAR(30),
	@PrenumeActorPrincipal VARCHAR(30),
	@DataNasterii DATE,
	@Gen VARCHAR(10),
	@NumarActoriPrincipali INT
)
AS
BEGIN
	DECLARE @errorNo INT
	SET @errorNo = 0

	-- VALIDATION AND INSERT FOR FILME TABLE
	BEGIN TRAN
	BEGIN TRY
		DECLARE @errorMessage VARCHAR(200)
		SET @errorMessage = dbo.ValidateFilmeParameters(@Titlu, @DataLansare, @Descriere, @ContentRating, @UserRating, @RegizorID, @Buget, @Incasari, @StudioID)
		IF (@errorMessage != '')
		BEGIN
			PRINT 'Error Filme: ' + @errorMessage
			RAISERROR(@errorMessage, 14, 1)
		END

		INSERT INTO Filme(Titlu, DataLansare, Descriere, ContentRating, UserRating, RegizorID, Buget, Incasari, StudioID)
		VALUES (@Titlu, @DataLansare, @Descriere, @ContentRating, @UserRating, @RegizorID, @Buget, @Incasari, @StudioID)
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('INSERT', 'Filme', CURRENT_TIMESTAMP)

		COMMIT TRAN
		SELECT 'Transaction commited for Filme table'
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION COMMITED', 'Filme', CURRENT_TIMESTAMP)
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked for Filme table'
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION ROLLBACKED', 'Filme', CURRENT_TIMESTAMP)
		SET @errorNo = 1
	END CATCH

	-- VALIDATION AND INSERT FOR ACTORIPRINCIPALI TABLE
	BEGIN TRAN
	BEGIN TRY
		SET @errorMessage = dbo.ValidateActoriPrincipaliParameters(@NumeActorPrincipal, @PrenumeActorPrincipal, @DataNasterii, @Gen)
        IF (@errorMessage != '')
        BEGIN
			PRINT 'Error ActoriPrincipali: ' + @errorMessage
            RAISERROR(@errorMessage, 14, 1)
        END

		INSERT INTO ActoriPrincipali(NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen)
        VALUES (@NumeActorPrincipal, @PrenumeActorPrincipal, @DataNasterii, @Gen)
        INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('INSERT', 'ActoriPrincipali', CURRENT_TIMESTAMP)
        
		COMMIT TRAN
		SELECT 'Transaction commited for ActoriPrincipali table'
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION COMMITED', 'ActoriPrincipali', CURRENT_TIMESTAMP)
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked for Filme table'
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION ROLLBACKED', 'ActoriPrincipali', CURRENT_TIMESTAMP)
		SET @errorNo = 1
	END CATCH

	IF (@errorNo != 0)
		RETURN

	-- VALIDATION AND INSERT FOR ACTORIPRINCIPALIFILME TABLE
	BEGIN TRAN
	BEGIN TRY
		DECLARE @FilmID INT, @ActorPrincipalID INT
        SET @FilmID = (SELECT MAX(FilmID) FROM Filme)
        SET @ActorPrincipalID = (SELECT MAX(ActorPrincipalID) FROM ActoriPrincipali)
		
        SET @errorMessage = dbo.ValidateActoriPrincipaliFilmeParameters(@ActorPrincipalID, @FilmID)
        IF (@errorMessage != '')
        BEGIN
			PRINT 'Error ActoriPrincipaliFilme: ' + @errorMessage
            RAISERROR(@errorMessage, 14, 1)
        END

		INSERT INTO ActoriPrincipaliFilme(ActorPrincipalID, FilmID, NumarActoriPrincipali)
        VALUES (@ActorPrincipalID, @FilmID, @NumarActoriPrincipali)
        INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('INSERT', 'ActoriPrincipaliFilme', CURRENT_TIMESTAMP)
        
        COMMIT TRAN
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION COMMITTED', 'ActoriPrincipaliFilme', CURRENT_TIMESTAMP)
        SELECT 'Transaction committed for ActoriPrincipaliFilme table'
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('TRANSACTION ROLLBACKED', 'ActoriPrincipaliFilme', CURRENT_TIMESTAMP)
        SELECT 'Transaction rollbacked for ActoriPrincipaliFilme table'
		SET @errorNo = 1
    END CATCH
END
GO

-- ==============================================================================================================
-- CAZURI DE EXECUTIE CU SUCCES
EXEC InsertDataIntoTablesWithPartialRollback
    @Titlu = 'Inception',
    @DataLansare = '2010-07-16',
    @Descriere = 'A thief who enters the dreams of others to steal their secrets from their subconscious.',
    @ContentRating = 'PG-13',
    @UserRating = 8.8,
    @RegizorID = 1,
    @Buget = 160000000,
    @Incasari = 829895144,
    @StudioID = 1,
    @NumeActorPrincipal = 'DiCaprio',
    @PrenumeActorPrincipal = 'Leonardo',
    @DataNasterii = '1974-11-11',
    @Gen = 'Masculin',
    @NumarActoriPrincipali = 1;
SELECT * FROM Filme
SELECT * FROM ActoriPrincipali
SELECT * FROM ActoriPrincipaliFilme
SELECT * FROM LoggingTable

-- CAZURI DE EXECUTIE CU EROARE
EXEC InsertDataIntoTablesWithPartialRollback
    @Titlu = '',
    @DataLansare = '2010-07-16',
    @Descriere = 'A thief who enters the dreams of others to steal their secrets from their subconscious.',
    @ContentRating = 'PG-13',
    @UserRating = 8.8,
    @RegizorID = 1,
    @Buget = 160000000,
    @Incasari = 829895144,
    @StudioID = 1,
    @NumeActorPrincipal = '',
    @PrenumeActorPrincipal = 'Leonardo',
    @DataNasterii = '1974-11-11',
    @Gen = 'Masculin',
    @NumarActoriPrincipali = 1;
SELECT * FROM Filme
SELECT * FROM ActoriPrincipali
SELECT * FROM ActoriPrincipaliFilme
SELECT * FROM LoggingTable