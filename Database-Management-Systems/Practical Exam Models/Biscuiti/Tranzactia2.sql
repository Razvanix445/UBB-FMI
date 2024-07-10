USE Biscuiti;

-- Tranzactia 2 - Phantom Reads

BEGIN TRANSACTION B;
INSERT INTO Biscuiti (Nume, Pret) VALUES ('Biscuit', 10);
COMMIT TRANSACTION B;