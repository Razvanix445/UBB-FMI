﻿USE Melodii;

-- Tranzacția 1 - Unrepeatable Reads
-- SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
BEGIN TRANSACTION A;
SELECT * FROM Melodii;
WAITFOR DELAY '00:00:05'
SELECT * FROM Melodii;
COMMIT TRANSACTION A;