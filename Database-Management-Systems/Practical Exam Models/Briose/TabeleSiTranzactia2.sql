USE Briose;

CREATE TABLE Cofetarii (
    Id INT PRIMARY KEY IDENTITY(1, 1),
    Nume VARCHAR(255) NOT NULL
);

CREATE TABLE Briose (
    Id INT PRIMARY KEY IDENTITY(1, 1),
    Nume VARCHAR(255) NOT NULL,
	Pret INT NOT NULL,
    CofetarieId INT,
    FOREIGN KEY (CofetarieId) REFERENCES Cofetarii(Id)
);

INSERT INTO Cofetarii (Nume) VALUES 
('Respted'),
('Adtesab'),
('Tenesfse'),
('Posgrgg'),
('Quenfew');

INSERT INTO Briose (Nume, Pret, CofetarieId) VALUES 
('Respted', 14, 1),
('Adtesab', 13, 1),
('Tenesfse', 43, 1),
('Posgrgg', 43, 2),
('Quenfew', 6, 2),
('Respted', 14, 2),
('Adtesab', 13, 3),
('Tenesfse', 43, 4),
('Posgrgg', 43, 3),
('Respted', 14, 4),
('Adtesab', 13, 3),
('Tenesfse', 43, 5),
('Posgrgg', 43, 5);

-- Tranzactia 2 - Dirty Reads
SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRANSACTION B
SELECT Nume FROM Briose WHERE Id = 1
COMMIT TRANSACTION B

-- Index
CREATE INDEX idx_Briose_Nume ON Briose (Nume);

SELECT * FROM Briose WHERE Pret = 43 ORDER BY Nume;
SELECT * FROM Cofetarii WHERE Nume = 'Respted';