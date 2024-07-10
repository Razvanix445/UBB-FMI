-- Dirty Reads Transaction 1
CREATE OR ALTER PROCEDURE DirtyReads1 AS
BEGIN
	BEGIN TRAN;
	BEGIN TRY
		UPDATE Filme SET Buget = 1600000 WHERE Titlu = 'Inception';
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('UPDATE', 'Filme', GETDATE());
		WAITFOR DELAY '00:00:05';
		ROLLBACK TRAN;
		SELECT 'Transaction successfully rolled back!' AS [Message];
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN;
		SELECT 'Transaction unsuccessfully rolled back!' AS [Message];
	END CATCH;
END;

EXEC DirtyReads1;

SELECT * FROM LoggingTable;