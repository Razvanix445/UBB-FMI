--- Non-Repeatable Reads Transaction 2
CREATE PROCEDURE NonRepeatableReads2 AS
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
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC NonRepeatableReads2;

-- Non-Repeatable Reads Transaction 2 Corrected
CREATE PROCEDURE NonRepeatableReads2Correct AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
	BEGIN TRAN;
    BEGIN TRY
		SELECT * FROM Filme;
        INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
		WAITFOR DELAY '00:00:10';
		SELECT * FROM Filme;
		INSERT INTO LoggingTable(operationType, tableName, executionTime) VALUES ('SELECT', 'Filme', GETDATE());
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;

EXEC NonRepeatableReads2Correct;