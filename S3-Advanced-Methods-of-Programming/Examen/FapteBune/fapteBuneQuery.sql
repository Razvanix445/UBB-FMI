CREATE TABLE Persoane (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	nume VARCHAR(100),
	prenume VARCHAR(100),
	username VARCHAR(100),
	parola VARCHAR(100),
	oras VARCHAR(100),
	strada VARCHAR(100),
	numarStrada VARCHAR(100),
	telefon VARCHAR(100)
);

CREATE TABLE Nevoi (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	titlu VARCHAR(100),
	descriere VARCHAR(100),
	deadline TIMESTAMP,
	omInNevoie BIGINT,
	omSalvator BIGINT,
	status VARCHAR(100),
	FOREIGN KEY (omInNevoie) REFERENCES Persoane(id),
	FOREIGN KEY (omSalvator) REFERENCES Persoane(id) ON DELETE SET NULL
);

DROP TABLE Persoane;
DROP TABLE Nevoi;
SELECT * FROM Persoane;
SELECT * FROM Nevoi;

-- Fake data for Persoana table
INSERT INTO Persoane (nume, prenume, username, parola, oras, strada, numarStrada, telefon)
VALUES
    ('Smith', 'John', 'jsmith', 'pass123', 'SATUMARE', 'Street1', '123', '555-1234'),
    ('Doe', 'Jane', 'jdoe', 'pass456', 'DROBETATURNUSEVERIN', 'Street2', '456', '555-5678'),
    ('Johnson', 'Bob', 'bjohnson', 'pass789', 'CONSTANTA', 'Street3', '789', '555-9012'),
    ('Brown', 'Alice', 'abrown', 'passabc', 'BACAU', 'Street4', '101', '555-3456'),
    ('Lee', 'David', 'dlee', 'passdef', 'BACAU', 'Street5', '202', '555-7890'),
    ('svr', 'svr', 'svr', 'svr', 'BACAU', 'Street6', '303', '555-2345'),
    ('Wilson', 'Tom', 'twilson', 'passjkl', 'BACAU', 'Street7', '404', '555-6789'),
    ('Garcia', 'Maria', 'mgarcia', 'passmno', 'BACAU', 'Street8', '505', '555-1234'),
    ('Chen', 'Michael', 'mchen', 'passpqr', 'BACAU', 'Street9', '606', '555-5678');

-- Fake data for Nevoie table
INSERT INTO Nevoi (titlu, descriere, deadline, omInNevoie, status)
VALUES
    ('Help with groceries', 'I need assistance with grocery shopping', '2024-02-15 12:00:00', 1, 'Caut erou!'),
    ('Tutoring', 'Require tutoring for math subject', '2024-03-01 15:30:00', 3, 'Caut erou!'),
    ('Moving help', 'Need help moving furniture', '2024-02-20 10:00:00', 5, 'Caut erou!'),
    ('Dog walking', 'Looking for someone to walk my dog', '2024-02-18 08:00:00', 7, 'Caut erou!'),
    ('Computer repair', 'Computer needs fixing', '2024-02-25 14:00:00', 9, 'Caut erou!'),
    ('Language exchange', 'Seeking language exchange partner', '2024-03-05 17:00:00', 1, 'Caut erou!'),
    ('Gardening help', 'Help needed in the garden', '2024-02-22 11:30:00', 2, 'Caut erou!'),
    ('Car maintenance', 'Car needs servicing', '2024-02-28 09:45:00', 4, 'Caut erou!'),
    ('Painting assistance', 'Assistance required for painting', '2024-03-10 13:15:00', 6, 'Caut erou!'),
    ('Cooking lessons', 'Want to learn how to cook', '2024-02-24 16:30:00', 8, 'Caut erou!');