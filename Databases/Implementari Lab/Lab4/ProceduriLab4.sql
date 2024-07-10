USE Filme;

CREATE PROCEDURE ExecuteTestWithoutFK
	@TestID INT
AS
BEGIN
	-- Declarez variabilele necesare
	DECLARE @TableName NVARCHAR(50)

	-- Obtin numele tabelului asociat testului
	SELECT @TableName - Tables.TableName
	FROM TestTables
	WHERE TestTables.TestID = @TestID
	ORDER BY TestTables.Position DESC

	-- 1. Sterg datele din tabel in ordinea data de Position
	DECLARE @DeleteQuery NVARCHAR(MAX)
	SET @DeleteQuery = 'DELETE FROM ' + @TableName
	EXEC sp_executesql @DeleteQuery

	-- 2. Inserez inregistrarile in tabel in ordinea inversa data de Position
	DECLARE @InsertQuery NVARCHAR(MAX)
	SET @InsertQuery = 'INSERT INTO ' + @TableName + ' SELECT TOP ' + CONVERT(NVARCHAR(10), NoOfRows) + ' * FROM ' + @TableName + ' ORDER BY ' + 'DESC'
	EXEC sp_executesql @InsertQuery

	-- 3. Evaluez timpul de executie al view-urilor
	DECLARE @StartTime DATETIME
	DECLARE @EndTime DATETIME
	DECLARE @ElapsedTime INT

	-- Exemplu: Evaluez timpul pentru un view specific
	SELECT @StartTime = GETDATE()
	EXEC sp_executesql N'SELECT * FROM YourExampleView'
	SELECT @EndTime = GETDATE()

	-- Calculez timpul in milisecunde
	SET@ElapsedTime = DATEDIFF(MILLISECOND, @StartTime, @EndTime)

	-- Pun rezultatele in TestRunViews
	INSERT INTO TestRunViews(TestID, ViewName, ExecutionTime)
	VALUES (@TestID, 'YourExampleView', @ElapsedTime)
END
GO

EXEC ExecuteTestWithoutForeignKey @TestID = 1
EXEC ExecuteTestWithForeignKey @TestID = 2
EXEC ExecuteTestWithTwoPrimaryKeys @TestID = 3
EXEC ExecuteTestOnView @TestID = 4