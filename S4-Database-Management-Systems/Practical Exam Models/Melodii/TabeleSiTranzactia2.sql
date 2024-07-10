USE Melodii;

CREATE TABLE Artisti 
(
	Id INT PRIMARY KEY IDENTITY(1, 1),
	Nume VARCHAR(40) NOT NULL,
)

CREATE TABLE Melodii
(
	Id INT PRIMARY KEY IDENTITY(1, 1),
	Nume VARCHAR(50) NOT NULL,
	NrVizionari INT NOT NULL,
	ArtistId INT NOT NULL,
	FOREIGN KEY (ArtistId) REFERENCES Artisti(Id)
)

INSERT INTO Artisti (Nume) VALUES
('Arsfe'),
('Fgrsgb'),
('Ternd'),
('Gepitbd'),
('Bersefse');

INSERT INTO Melodii (Nume, NrVizionari, ArtistId) VALUES
('Arsfe', 19134, 1),
('Fgrsgb', 154352, 1),
('Ternd', 1234, 1),
('Gepitbd', 51345, 1),
('Bersefse', 1345, 1),
('Nesfbsr', 47563, 1),
('Arsfe', 19134, 2),
('Fgrsgb', 154352, 2),
('Ternd', 1234, 2),
('Gepitbd', 51345, 2),
('Bersefse', 1345, 3),
('Nesfbsr', 47563, 3),
('Arsfe', 19134, 3),
('Fgrsgb', 154352, 3),
('Ternd', 1234, 4),
('Gepitbd', 51345, 4),
('Bersefse', 1345, 4),
('Nesfbsr', 47563, 4),
('Arsfe', 19134, 5),
('Fgrsgb', 154352, 5),
('Ternd', 1234, 5),
('Gepitbd', 51345, 5),
('Bersefse', 1345, 5),
('Nesfbsr', 47563, 5);

-- Tranzacția 2 - Unrepeatable Reads
BEGIN TRANSACTION B;
UPDATE Melodii SET NrVizionari = 1401 WHERE Id = 1;
COMMIT TRANSACTION B;

-- Index
CREATE INDEX idx_Melodii_NrVizionari ON Melodii (NrVizionari);

SELECT * FROM Melodii WHERE Nume = 'Ternd' ORDER BY NrVizionari;