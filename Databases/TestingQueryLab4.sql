USE Filme;

CREATE OR ALTER PROCEDURE addToTables (@tableName VARCHAR(50)) AS
	IF @tableName IN (SELECT Name FROM Tables)
	BEGIN
		PRINT 'Tabel deja adaugat in Tables';
		RETURN;
	END
	IF @tableName NOT IN (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES)
	BEGIN
		PRINT 'Tabelul nu a fost adaugat in baza de date';
		RETURN;
	END
	INSERT INTO Tables (Name) VALUES (@tableName)
GO

-- ==============================================================================================================
EXEC addToTables 'Genuri'
EXEC addToTables 'Filme'
EXEC addToTables 'GenuriFilme'
EXEC addToTables 'Regizori'


-- ==============================================================================================================
CREATE OR ALTER PROCEDURE addToViews (@viewName VARCHAR(50)) AS
	IF @viewName IN (SELECT Name FROM Views)
	BEGIN
		PRINT 'View deja adaugat in Views';
		RETURN;
	END
	IF @viewName NOT IN (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS)
	BEGIN
		PRINT 'View-ul nu a fost adaugat in baza de date';
		RETURN;
	END
	INSERT INTO Views (Name) VALUES (@viewName);
GO

-- ==============================================================================================================
EXEC addToViews 'TitluriCuDataLansareSiDescriere'
EXEC addToViews 'FilmeCuRegizori'
EXEC addToViews 'NumarFilmeDupaGen'


-- ==============================================================================================================
-- TESTS
CREATE OR ALTER PROCEDURE addToTests (@testName VARCHAR(50)) AS
	IF @testName IN (SELECT Name FROM Tests)
	BEGIN
		PRINT 'Test deja adaugat in Tests';
		RETURN;
	END
	INSERT INTO Tests (Name) VALUES (@testName);
GO
-- ==============================================================================================================
EXEC addToTests 'test1'
EXEC addToTests 'test2'
EXEC addToTests 'test3'


-- ==============================================================================================================
-- ADAUGARE DATE IN TestTables
CREATE OR ALTER PROCEDURE connectTableToTest (@tableName VARCHAR(50), @testName VARCHAR(50), @NoOfRows INT, @Position INT) AS
	If @tableName NOT IN (SELECT Name FROM Tables)
	BEGIN
		PRINT 'Tabel inexistent in tabelul Tables';
		RETURN;
	END
	IF @testName NOT IN (SELECT Name FROM Tests)
	BEGIN
		PRINT 'Teste inexistente in tabelul Tests';
		RETURN;
	END
	IF EXISTS(
		SELECT * FROM TestTables T1
		JOIN Tests T2 ON T1.TestID = T2.TestID
		WHERE T2.Name=@testName AND Position=@Position
		)
	BEGIN
		PRINT 'Pozitia oferita invalida';
		RETURN;
	END
	INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) VALUES (
		(SELECT Tests.TestID FROM Tests WHERE Name=@testName),
		(SELECT Tables.TableID FROM Tables WHERE Name=@tableName),
		@NoOfRows,
		@Position
	)
GO
-- ==============================================================================================================
EXEC connectTableToTest 'Filme', 'test1', 1000, 1
EXEC connectTableToTest 'Filme', 'test2', 1000, 1
EXEC connectTableToTest 'Regizori', 'test2', 1000, 2
EXEC connectTableToTest 'Filme', 'test3', 1000, 1
EXEC connectTableToTest 'Genuri', 'test3', 1000, 2
EXEC connectTableToTest 'GenuriFilme', 'test3', 1000, 3


-- ==============================================================================================================
-- ADAUGARE DATE IN TestViews
CREATE OR ALTER PROCEDURE connectViewToTest (@viewName VARCHAR(50), @testName VARCHAR(50)) AS
	IF @viewName NOT IN (SELECT Name FROM Views)
	BEGIN
		PRINT 'View inexistent in tabelul Views';
		RETURN;
	END
	IF @testName NOT IN (SELECT Name FROM Tests)
	BEGIN
		PRINT 'Teste inexistente in tabelul Tests';
		RETURN;
	END
	INSERT INTO TestViews (TestID, ViewID) VALUES (
		(SELECT Tests.TestID FROM Tests WHERE Name=@testName),
		(SELECT Views.ViewID FROM Views WHERE Name=@viewName)
	)
GO
-- ==============================================================================================================
EXEC connectViewToTest 'TitluriCuDataLansareSiDescriere', 'test1'
EXEC connectViewToTest 'FilmeCuRegizori', 'test2'
EXEC connectViewToTest 'NumarFilmeDupaGen', 'test3'


-- ==============================================================================================================
-- RULARE TESTE
CREATE OR ALTER PROCEDURE runTest (@testName VARCHAR(50)) AS
	SET NOCOUNT ON;
	IF @testName NOT IN (SELECT Name FROM Tests)
	BEGIN
		PRINT 'Test inexistent in tabelul Tests';
		RETURN;
	END
	DECLARE @command VARCHAR(100), @testStartTime DATETIME2, @startTime DATETIME2, @endTime DATETIME2;
	DECLARE @table VARCHAR(50), @NoOfRows INT, @Position INT, @view VARCHAR(50), @testID INT, @testEndTime DATETIME2;
	SELECT @testID=TestID FROM Tests WHERE Name=@testName
	DECLARE @testRunID INT
	SET @testRunID = (SELECT MAX(TestRunID) + 1 FROM TestRuns)
	IF @testRunID IS NULL
		SET @testRunID = 0
	DECLARE tableCursor CURSOR SCROLL FOR
		SELECT T1.Name, T2.NoOfRows, T2.Position FROM Tables T1
		JOIN TestTables T2 ON T1.TableID = T2.TableID WHERE T2.TestID=@testID
		ORDER BY T2.Position
	DECLARE viewCursor CURSOR FOR
		SELECT V.Name FROM Views V
		JOIN TestViews TV ON V.ViewID = TV.ViewID WHERE TV.TestID = @testID

	SET @testStartTime = SYSDATETIME()

	OPEN tableCursor
	FETCH LAST FROM tableCursor INTO @table, @NoOfRows, @Position
	WHILE @@FETCH_STATUS = 0
	BEGIN
		EXEC ('DELETE FROM ' + @table)
		FETCH PRIOR FROM tableCursor INTO @table, @NoOfRows, @Position
	END
	CLOSE tableCursor

	OPEN tableCursor
	SET IDENTITY_INSERT TestRuns ON
	INSERT INTO TestRuns (TestRunID, Description, StartAt) VALUES (@testRunID, 'Rezultate teste pentru: ' + @testName, @testStartTime)
	SET IDENTITY_INSERT TestRuns OFF
	FETCH tableCursor INTO @table, @NoOfRows, @Position
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @command = 'populateTable' + @table
		IF @command NOT IN (SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES)
		BEGIN
			PRINT @command + ' nu exista';
			RETURN;
		END
		SET @startTime = SYSDATETIME()
		EXEC @command @NoOfRows
		SET @endTime = SYSDATETIME()
		INSERT INTO TestRunTables (TestRunID, TableId, StartAt, EndAt) VALUES (@testRunID, (SELECT TableID FROM Tables WHERE Name=@table), @startTime, @endTime)
		PRINT 'Performanta inserarii in tabelul ' + @table + ' este de ' + CAST(DATEDIFF(MILLISECOND, @startTime, @endTime) AS NVARCHAR(20)) + ' milisecunde!';
		FETCH tableCursor INTO @table, @NoOfRows, @Position
	END
	CLOSE tableCursor
	DEALLOCATE tableCursor

	OPEN viewCursor
	FETCH viewCursor INTO @view
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @command = 'SELECT * FROM ' + @view
		SET @startTime = SYSDATETIME()
		EXEC (@command)
		SET @endTime = SYSDATETIME()
		INSERT INTO TestRunViews (TestRunID, ViewID, StartAt, EndAt) VALUES (@testRunID, (SELECT ViewID FROM Views WHERE Name=@view), @startTime, @endTime)
		PRINT 'Performanta view-ului ' + @view + ' este de ' + CAST(DATEDIFF(MILLISECOND, @startTime, @endTime) AS NVARCHAR(20)) + ' milisecunde!';
		FETCH viewCursor INTO @view
	END
	CLOSE viewCursor
	DEALLOCATE viewCursor

	UPDATE TestRuns
	SET EndAt = SYSDATETIME()
	WHERE TestRunID = @testRunID

	SET @testEndTime = SYSDATETIME()

	PRINT 'Testul s-a terminat in ' + CAST(DATEDIFF(MILLISECOND, @testStartTime, @testEndTime) AS NVARCHAR(20)) + ' milisecunde!';
GO
-- ==============================================================================================================
EXEC runTest 'test1'
EXEC runTest 'test2'
EXEC runTest 'test3'
-- ==============================================================================================================
SELECT * FROM Tests;
SELECT * FROM TestTables;
SELECT * FROM TestViews;
SELECT * FROM TestRuns;
SELECT * FROM TestRunTables;
SELECT * FROM TestRunViews;

DELETE FROM GenuriFilme;
DELETE FROM Genuri;
DELETE FROM Filme;