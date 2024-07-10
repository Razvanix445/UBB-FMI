-- Dirty Reads Transaction 2
CREATE PROCEDURE DirtyReads2 AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	BEGIN TRAN;
	BEGIN TRY
		SELECT * FROM Filme;
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
		WAITFOR DELAY '00:00:10';
		SELECT * FROM Filme;
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
		COMMIT TRAN;
		SELECT 'Transaction committed!' AS [Message];
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN;
		SELECT 'Transaction rolled back!' AS [Message];
	END CATCH;
END;

EXEC DirtyReads2;

SELECT * FROM LoggingTable;

-- Dirty Reads Transaction 2 Corrected
CREATE OR ALTER PROCEDURE DirtyReads2Correct AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
	BEGIN TRAN;
	BEGIN TRY
		SELECT * FROM Filme;
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
		WAITFOR DELAY '00:00:10';
		SELECT * FROM Filme;
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
		COMMIT TRAN;
		SELECT 'Transaction committed!' AS [Message];
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN;
		SELECT 'Transaction rolled back!' AS [Message];
	END CATCH;
END;

EXEC DirtyReads2Correct;

SELECT * FROM LoggingTable;