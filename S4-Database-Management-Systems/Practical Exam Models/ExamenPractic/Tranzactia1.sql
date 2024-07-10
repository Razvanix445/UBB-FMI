USE Examen;

CREATE TABLE Tipuri
(
	Id INT PRIMARY KEY IDENTITY(1, 1),
	Nume VARCHAR(30)
)

CREATE TABLE Tablouri
(
	Id INT PRIMARY KEY IDENTITY(1, 1),
	Nume VARCHAR(30),
	Pret INT,
	TablouId INT,
	FOREIGN KEY (TablouId) REFERENCES Tipuri(Id)
)

-- Tranzactia 2 Deadlock Evitare
BEGIN TRANSACTION A;
UPDATE Tipuri SET Nume = 'TitluUpdatat' WHERE Id = 1;
UPDATE Tablouri SET Nume = 'TablouUpdatat' WHERE Id = 1;
COMMIT TRANSACTION A;

-- Tranzactia 2 Deadlock
-- SET DEADLOCK_PRIORITY HIGH;
BEGIN TRANSACTION B;
UPDATE Tipuri SET Nume = 'TitluUpdatat' WHERE Id = 1;
WAITFOR DELAY '00:00:03'
UPDATE Tablouri SET Nume = 'TablouModificat' WHERE Id = 1;
COMMIT TRANSACTION B;

-- Index
CREATE INDEX idx_Tablouri_Nume ON Tablouri(Nume);

SELECT * FROM Tablouri;

SELECT * FROM Tablouri WHERE Pret > 53 ORDER BY Nume;