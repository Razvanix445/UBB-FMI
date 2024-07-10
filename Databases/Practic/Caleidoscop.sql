-- Caleidoscop este un serial Netflix. Are 9 episoade. Fiecare episod are durata,
-- nume si ordine cronologica. Fiecare utilizator (nume, email) vizioneaza epsisoadele
-- intr-o ordine aleatorie, mai putin episodul 9 care va fi vizionat ultimul.
-- Intr-un episod joaca mai multi actori. Fiecare actor are un nume si mai multe replici
-- intr-un episod. O replica contine un text si e spus la un moment dat in episod.
-- Un actor poate sa apara in mai multe episoade.
--  
-- 1. Scrieti un script SQL care creeaza un model relational pentru a reprezenta si stoca datele specifice serialului Caleidoscop (4p)
--  
-- 2. Creati o procedura care "da play" la urmatorul episod pentru un utilizator dat. (3p)
-- Puteti folosi functiile RAND() (intoarce un nr in (0,1)) si FLOOR(x) (intoarce cel mai mic intreg fata de x)
--  
-- 3. Creati un view care genereaza scriptul serialului (toate replicile) in format "NUME_ACTOR: TEXT_REPLICA" (2p)

USE Caleidoscop;

CREATE TABLE Episoade (
	id_episod INT PRIMARY KEY IDENTITY (1,1),
	durata INT,
	nume VARCHAR(50),
	ordine_cronologica INT
)

CREATE TABLE Utilizatori (
	id_user INT PRIMARY KEY IDENTITY (1,1),
	nume VARCHAR(50),
	email VARCHAR(50)
)

CREATE TABLE Actori (
	id_actor INT PRIMARY KEY IDENTITY (1,1),
	nume VARCHAR(50)
)

CREATE TABLE EpisoadeActori (
	id_episod INT FOREIGN KEY REFERENCES Episoade(id_episod),
	id_actor INT FOREIGN KEY REFERENCES Actori(id_actor),
	PRIMARY KEY (id_episod, id_actor)
)

CREATE TABLE Replici (
	id_replica INT PRIMARY KEY IDENTITY (1,1),
	text VARCHAR(50),
	timp INT,
	id_actor INT,
	id_episod INT,
	FOREIGN KEY (id_actor, id_episod) REFERENCES EpisoadeActori(id_actor, id_episod)
)

CREATE TABLE Vizionari (
	id_user INT FOREIGN KEY REFERENCES Utilizatori(id_user),
	id_episod INT FOREIGN KEY REFERENCES Episoade(id_episod),
	PRIMARY KEY (id_user, id_episod)
)

-- 2
CREATE PROCEDURE Play @id_user INT
AS
	DECLARE @episoade_vizionate INT;
	SELECT @episoade_vizionate = COUNT(*) FROM Vizionari
	WHERE id_user = @id_user

	-- Daca utilizatorul a vazut 8 episoade, inseram al 9-lea si break
	IF @episoade_vizionate = 8
	BEGIN
		INSERT INTO Vizionari VALUES (@id_user, 9)
		RETURN 0
	END
	DECLARE @episod_urmator INT;
	-- Trebuie sa dam un numar intre 1 si 8
	SET @episod_urmator = FLOOR(RAND() * 8) + 1;
	-- sau SET @episod_urmator = (CAST(RAND() * 8 AS INT)) + 1;
	WHILE EXISTS (SELECT 1 FROM Vizionari WHERE 
		id_user = @id_user AND id_episod = @episod_urmator)
	BEGIN
		SET @episod_urmator = FLOOR(RAND() * 8) + 1;
	END
	INSERT INTO Vizionari VALUES (@id_user, @episod_urmator)
GO

-- 3
CREATE VIEW Script AS
    SELECT A.nume + ': ' + R.text AS ScriptLine
    FROM Replici R
    INNER JOIN Actori A ON R.id_actor = A.id_actor
    INNER JOIN Episoade E ON E.id_episod = R.id_episod
GO

-- TESTARE:
-- 2
EXEC Play 1;

SELECT * FROM Vizionari WHERE id_user = 1;
DELETE FROM Vizionari WHERE id_user = 1;


-- 3
SELECT * FROM Script;


-- Introducing data into tables:

-- Insert fake data into Episoade table
INSERT INTO Episoade (durata, nume, ordine_cronologica) VALUES
(45, 'Episode 1', 1),
(50, 'Episode 2', 2),
(42, 'Episode 3', 3),
(48, 'Episode 4', 4),
(55, 'Episode 5', 5),
(40, 'Episode 6', 6),
(52, 'Episode 7', 7),
(47, 'Episode 8', 8),
(60, 'Episode 9', 9);

-- Insert fake data into Utilizatori table
INSERT INTO Utilizatori (nume, email) VALUES
('User1', 'user1@example.com'),
('User2', 'user2@example.com'),
('User3', 'user3@example.com');

-- Insert fake data into Actori table
INSERT INTO Actori (nume) VALUES
('Actor1'),
('Actor2'),
('Actor3'),
('Actor4'),
('Actor5');

-- Insert fake data into EpisoadeActori table
INSERT INTO EpisoadeActori (id_episod, id_actor) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 3),
(3, 4),
(4, 4),
(4, 5),
(5, 5),
(5, 1),
(6, 1),
(6, 2),
(7, 2),
(7, 3),
(8, 3),
(8, 4),
(9, 4),
(9, 5),
(9, 1);

-- Insert fake data into Replici table
INSERT INTO Replici (text, timp, id_actor, id_episod) VALUES
('Line 1', 1, 1, 1),
('Line 2', 2, 2, 1),
('Line 3', 3, 2, 2),
('Line 4', 4, 3, 2),
('Line 5', 5, 3, 3),
('Line 6', 6, 4, 3),
('Line 7', 7, 4, 4),
('Line 8', 8, 5, 4),
('Line 9', 9, 5, 5),
('Line 10', 10, 1, 5),
('Line 11', 11, 1, 6),
('Line 12', 12, 2, 6),
('Line 13', 13, 2, 7),
('Line 14', 14, 3, 7),
('Line 15', 15, 3, 8),
('Line 16', 16, 4, 8),
('Line 17', 17, 4, 9),
('Line 18', 18, 5, 9),
('Line 19', 19, 1, 9);

-- Insert fake data into Vizionari table
INSERT INTO Vizionari (id_user, id_episod) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(3, 1),
(3, 2),
(3, 3);