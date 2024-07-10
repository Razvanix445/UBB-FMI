-- Deadlock Transaction 1 For .NET App
CREATE OR ALTER PROCEDURE DeadlockT1 AS
BEGIN
    BEGIN TRAN
    UPDATE Filme SET Buget = 1110000 WHERE Titlu = 'Reservoir Dogs';
    INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Filme', GETDATE());
    WAITFOR DELAY '00:00:05';
    UPDATE Regizori SET PrenumeRegizor = 'DEADLOCK' WHERE NumeRegizor = 'Hitchcock';
    INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Regizori', GETDATE());
    COMMIT TRAN
END;

-- Deadlock Transaction 2 For .NET App
CREATE OR ALTER PROCEDURE DeadlockT2 AS
BEGIN
    SET DEADLOCK_PRIORITY HIGH;
    BEGIN TRAN
    UPDATE Regizori SET PrenumeRegizor = 'DEADLOCK' WHERE NumeRegizor = 'Eisenstein';
    INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Regizori', GETDATE());
    WAITFOR DELAY '00:00:05';
    UPDATE Filme SET Buget = 11110000 WHERE Titlu = 'Inception';
    INSERT INTO LoggingTable (operationType, tableName, executionTime) VALUES ('UPDATE', 'Filme', GETDATE());
    COMMIT TRAN
END;

SELECT * FROM Regizori;
SELECT * FROM Filme;

SELECT * FROM LoggingTable;