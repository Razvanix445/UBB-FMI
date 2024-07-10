-- Deadlock Transaction 1
CREATE OR ALTER PROCEDURE Deadlock1 AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL SNAPSHOT;
    BEGIN TRAN;
    BEGIN TRY
        UPDATE Filme SET Buget = 1500000 WHERE Titlu = 'Inception';
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Filme', GETDATE());
        WAITFOR DELAY '00:00:05';
        UPDATE Regizori SET NumeRegizor = 'DEADLOCK' WHERE NumeRegizor = 'Quentin';
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Regizori', GETDATE());
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC Deadlock1;

SELECT * FROM Regizori;