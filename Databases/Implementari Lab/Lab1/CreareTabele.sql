USE Filme;

--sall

CREATE TABLE Regizori(
	RegizorID INT PRIMARY KEY IDENTITY(1,1),
	NumeRegizor VARCHAR(30) NOT NULL,
	PrenumeRegizor VARCHAR(30),
	DataNasterii DATE
)

CREATE TABLE ActoriPrincipali(
	ActorPrincipalID INT PRIMARY KEY IDENTITY(1,1),
	NumeActorPrincipal VARCHAR(30) NOT NULL,
	PrenumeActorPrincipal VARCHAR(30),
	DataNasterii DATE,
	Gen VARCHAR(10)
)

CREATE TABLE Genuri(
	GenuriID INT PRIMARY KEY IDENTITY(1,1),
	Gen VARCHAR(30)
)

CREATE TABLE Studiouri(
	StudioID INT PRIMARY KEY IDENTITY(1,1),
	Denumire VARCHAR(30),
	Adresa VARCHAR(100),
	NumarAngajati INT
)

CREATE TABLE Filme(
	FilmID INT PRIMARY KEY IDENTITY(1,1),
	Titlu VARCHAR(30),
	DataLansare DATE,
	Descriere VARCHAR(200),
	ContentRating VARCHAR(30),
	UserRating FLOAT(3),
	RegizorID INT FOREIGN KEY REFERENCES Regizori(RegizorID),
	Buget INT,
	Incasari INT,
	StudioID INT FOREIGN KEY REFERENCES Studiouri(StudioID)
)

CREATE TABLE GenuriFilme(
	GenuriID INT FOREIGN KEY REFERENCES Genuri(GenuriID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(GenuriID, FilmID)
)

CREATE TABLE Evenimente (
	EvenimentID INT PRIMARY KEY IDENTITY(1,1),
	NumeEveniment VARCHAR(100)
)

CREATE TABLE Nominalizari(
	NominalizareID INT PRIMARY KEY IDENTITY(1,1),
	Categorie VARCHAR(100),
	EvenimentID INT FOREIGN KEY REFERENCES Evenimente(EvenimentID)
)

CREATE TABLE NominalizariFilme(
	NominalizariID INT FOREIGN KEY REFERENCES Nominalizari(NominalizareID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(NominalizariID, FilmID),
	An INT,
	Castigat BIT
)

CREATE TABLE Tari(
	TaraID INT PRIMARY KEY IDENTITY(1,1),
	Nume VARCHAR(30) UNIQUE NOT NULL,
	Capitala VARCHAR(30),
	LimbaOficiala VARCHAR(30)
)

CREATE TABLE TariFilme(
	TaraID INT FOREIGN KEY REFERENCES Tari(TaraID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(TaraID, FilmID)
)

CREATE TABLE ActoriPrincipaliFilme(
	ActorPrincipalID INT FOREIGN KEY REFERENCES ActoriPrincipali(ActorPrincipalID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	PRIMARY KEY(ActorPrincipalID, FilmID),
	NumarActoriPrincipali INT
)

CREATE TABLE PlatformeDeStreaming(
	PlatformaID INT PRIMARY KEY IDENTITY(1,1),
	NumePlatforma VARCHAR(30) UNIQUE,
	AnInfiintare INT,
	PretLunarAbonare INT
)

CREATE TABLE PlatformeDeStreamingFilme(
	PlatformaID INT FOREIGN KEY REFERENCES PlatformeDeStreaming(PlatformaID),
	FilmID INT FOREIGN KEY REFERENCES Filme(FilmID),
	NumarMinute INT,
	PRIMARY KEY(PlatformaID, FilmID)
)

CREATE TABLE Scenaristi(
	ScenaristID INT PRIMARY KEY IDENTITY(1,1),
	NumeScenarist VARCHAR(30) NOT NULL,
	PrenumeScenarist VARCHAR(30),
	DataNasterii DATE,
	ScenaristPentru INT
)


CREATE TABLE Versiuni(
	Versiune INT
)