-- Phantom Reads Transaction 1
CREATE PROCEDURE PhantomReads1 AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        WAITFOR DELAY '00:00:05';
        INSERT INTO Filme (Titlu, Buget) VALUES ('NEW MOVIE', 1000000);
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('INSERT', 'Filme', GETDATE());
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC PhantomReads1;