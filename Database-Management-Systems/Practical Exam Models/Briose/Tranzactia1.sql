USE Briose;

-- Tranzactia 1 - Dirty Reads
BEGIN TRANSACTION A
UPDATE Briose SET Nume = 'Noua e' WHERE Id = 1
WAITFOR DELAY '00:00:05' -- Așteptăm 5 secunde pentru a permite tranzacției B să înceapă
ROLLBACK TRANSACTION A