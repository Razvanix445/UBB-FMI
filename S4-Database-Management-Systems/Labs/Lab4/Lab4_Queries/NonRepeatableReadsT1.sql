-- Non-Repeatable Reads Transaction 1
CREATE PROCEDURE NonRepeatableReads1 AS
BEGIN
	BEGIN TRAN;
    BEGIN TRY
        WAITFOR DELAY '00:00:05';
        UPDATE Filme SET Buget = 120000 WHERE Titlu = 'Inception';
        INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('UPDATE', 'Filme', GETDATE());
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC NonRepeatableReads1;