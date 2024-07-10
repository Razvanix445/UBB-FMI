-- Deadlock Transaction 2
CREATE OR ALTER PROCEDURE Deadlock2 AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL SNAPSHOT;
    BEGIN TRAN;
    BEGIN TRY
        UPDATE Regizori SET NumeRegizor = 'DEADLOCK' WHERE NumeRegizor = 'Cooper';
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Regizori', GETDATE());
        WAITFOR DELAY '00:00:05';
        UPDATE Filme SET Buget = 1700000 WHERE Titlu = 'A Star Is Born';
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Filme', GETDATE());
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC Deadlock2;

SELECT * FROM Filme;
SELECT * FROM Regizori;

SELECT * FROM LoggingTable;