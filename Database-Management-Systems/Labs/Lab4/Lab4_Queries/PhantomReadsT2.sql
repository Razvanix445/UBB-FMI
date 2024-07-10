-- Phantom Reads Transaction 2
CREATE PROCEDURE PhantomReads2 AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    BEGIN TRANSACTION;
    BEGIN TRY
        SELECT * FROM Filme;
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
        WAITFOR DELAY '00:00:10';
        SELECT * FROM Filme;
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
        COMMIT TRANSACTION;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC PhantomReads2;

-- Phantom Reads Transaction 2 Corrected
CREATE PROCEDURE PhantomReads2Correct AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    BEGIN TRANSACTION;
    BEGIN TRY
        SELECT * FROM Filme;
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
        WAITFOR DELAY '00:00:10';
        SELECT * FROM Filme;
        INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
        COMMIT TRANSACTION;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC PhantomReads2Correct;