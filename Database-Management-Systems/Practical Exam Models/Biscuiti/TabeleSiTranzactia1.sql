USE Biscuiti;

CREATE TABLE Producatori
(
	Id INT PRIMARY KEY IDENTITY(1, 1),
	Nume VARCHAR(50)
)

CREATE TABLE Biscuiti
(
	Id INT PRIMARY KEY IDENTITY(1, 1),
	Nume VARCHAR(50) NOT NULL,
	Pret INT NOT NULL,
	ProducatorId INT,
	FOREIGN KEY (ProducatorId) REFERENCES Producatori(Id)
)

INSERT INTO Producatori (Nume) VALUES
('Sfasrvr'),
('Rregar'),
('Grvsvs'),
('Brbtsbst'),
('Ptbdstb');

INSERT INTO Biscuiti (Nume, Pret, ProducatorId) VALUES
('Sfasrvr', 12, 1),
('Rregar', 154, 1),
('Grvsvs', 3, 1),
('Brbtsbst', 423, 1),
('Ptbdstb', 31, 2),
('Sfasrvr', 12, 2),
('Rregar', 154, 2),
('Grvsvs', 3, 2),
('Brbtsbst', 423, 3),
('Ptbdstb', 31, 3),
('Sfasrvr', 12, 3),
('Rregar', 154, 3),
('Grvsvs', 3, 4),
('Brbtsbst', 423, 4),
('Ptbdstb', 31, 4),
('Sfasrvr', 12, 4),
('Rregar', 54, 5),
('Grvsvs', 123, 5),
('Brbtsbst', 76, 5),
('Ptbdstb', 12, 5);

-- Tranzactia 1 - Phantom Reads
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
BEGIN TRANSACTION A;
SELECT * FROM Biscuiti;
WAITFOR DELAY '00:00:06';
SELECT * FROM Biscuiti;
COMMIT TRANSACTION A;

-- Index
CREATE INDEX idx_Biscuiti_Pret ON Biscuiti(Pret);

SELECT * FROM Biscuiti WHERE Nume = 'Rregar' ORDER BY Pret;