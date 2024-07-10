USE Filme;
GO

INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Quentin', 'Tarantino', '1963-03-27');
INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Nolan', 'Christopher', '1970-07-30');
INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Spielberg', 'Steven', '1946-12-18');
INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Eisenstein', 'Serghei', '1898-01-22');
INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Hitchcock', 'Alfred', '1899-04-29');
INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Eastwood', 'Clint', '1930-05-31');
INSERT INTO Regizori (NumeRegizor, PrenumeRegizor, DataNasterii) VALUES ('Cooper', 'Bradley', '1975-01-05');

--DELETE FROM Regizori WHERE NumeRegizor='Quentin'

INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('Reservoir Dogs', '1992-10-08', 'Reservoir Dogs is a story about memory and meaning and how fleeting the human understanding of a chaotic world can be.', 'R', 8.3, 1200000, 2900000);
INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('A Star Is Born', '2018-10-05', 'Seasoned musician Jackson Maine discovers-and falls in love with-struggling artist Ally who has just given up on her dream to make it big as a singer.', 'R', 7.6, 36000000, 215300000);
INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('Schindlers List', '1994-03-18', 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'R', 9.0, 22000000, 96898818);
INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('E.T. the Extra-Terrestrial', '1982-06-11', 'A troubled child summons the courage to help a friendly alien escape from Earth and return to his home planet.', 'PG', 7.9, 10500000, 619000000);
INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('American Sniper', '2015-01-23', 'Chris Kyle takes his sole mission to heart and becomes one of the most lethal snipers in American history.', 'R', 7.3, 59000000, 350100000);
INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('Inception', '2010-07-30', 'The film stars Leonardo DiCaprio as a professional thief who steals information by infiltrating the subconstious of his targets.', 'PG 13', 8.8, 160000000, 839000000);
INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari) VALUES ('Jurrasic Park', '1993-06-11', 'Jurrasic Park is a nature reserve on a remote South American island owned by billionaire businessman John Hammond.', 'PG 13', 8.2, 63000000, 357067947);


INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Keitel', 'Harvey', '1939-05-13', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Cooper', 'Bradley', '1975-01-05', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Germanotta', 'Angelina', '1986-03-28', 'Feminin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Neeson', 'Liam', '1952-06-07', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('DiCaprio', 'Leonardo', '1974-11-11', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Tom', 'Hardy', '1977-09-15', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Buscemi', 'Steve', '1957-12-13', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Madsen', 'Michael', '1957-09-25', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Quentin', 'Tarantino', '1963-03-27', 'Masculin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Wallace', 'Dee', '1948-12-14', 'Feminin');
INSERT INTO ActoriPrincipali (NumeActorPrincipal, PrenumeActorPrincipal, DataNasterii, Gen) VALUES ('Watanabe', 'Ken', '1959-10-21', 'Masculin');


INSERT INTO Studiouri (Denumire, Adresa, NumarAngajati) VALUES ('Warner Bros.', 'Burbank, CA 91505', 8000);
INSERT INTO Studiouri (Denumire, Adresa, NumarAngajati) VALUES ('Universal Pictures', '100 Universal City Plaza Drive, Los Angeles, California', 30000);
INSERT INTO Studiouri (Denumire, Adresa, NumarAngajati) VALUES ('The Sony Pictures', '10202 West Washington Boulevard', 9100);
INSERT INTO Studiouri (Denumire, Adresa, NumarAngajati) VALUES ('Walt Disney', '500 South Buena Vista Street, Burbank', 220000);
INSERT INTO Studiouri (Denumire, Adresa, NumarAngajati) VALUES ('20th Century Fox', '10201 West Pico Boulevard, Century City, Los Angeles, California', 2300);
INSERT INTO Studiouri (Denumire, Adresa, NumarAngajati) VALUES ('Miramax Films', '1901 Avenue of the Stars, Suite 2000, Los Angeles, California', 348);


INSERT INTO Genuri (Gen) VALUES ('Mystery');
INSERT INTO Genuri (Gen) VALUES ('Thriller');
INSERT INTO Genuri (Gen) VALUES ('Horror');
INSERT INTO Genuri (Gen) VALUES ('Romance');
INSERT INTO Genuri (Gen) VALUES ('Western');
INSERT INTO Genuri (Gen) VALUES ('Action');
INSERT INTO Genuri (Gen) VALUES ('Adventure');
INSERT INTO Genuri (Gen) VALUES ('Comedy');
INSERT INTO Genuri (Gen) VALUES ('Drama');
INSERT INTO Genuri (Gen) VALUES ('Science Fiction');
INSERT INTO Genuri (Gen) VALUES ('Musical');
INSERT INTO Genuri (Gen) VALUES ('Fantasy');
INSERT INTO Genuri (Gen) VALUES ('Sports');
INSERT INTO Genuri (Gen) VALUES ('History');
INSERT INTO Genuri (Gen) VALUES ('War');


INSERT INTO Evenimente (NumeEveniment) VALUES ('ACADEMY AWARDS');
INSERT INTO Evenimente (NumeEveniment) VALUES ('BRITISH ACADEMY OF FILM AND TELEVISION ARTS');
INSERT INTO Evenimente (NumeEveniment) VALUES ('GOLDEN GLOBES');
INSERT INTO Evenimente (NumeEveniment) VALUES ('OSCAR');
INSERT INTO Evenimente (NumeEveniment) VALUES ('BAFTA');
INSERT INTO Evenimente (NumeEveniment) VALUES ('SATELLITE');
INSERT INTO Evenimente (NumeEveniment) VALUES ('CRITICS CHOICE');


INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('Netflix', 1997, 7);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('Amazon Prime Video', 2016, 15);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('HBO Max', 2020, 5);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('Hulu', 2007, 8);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('Disney+', 2019, 10);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('Apple TV+', 2019, 7);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('YouTube TV', 2005, 73);
INSERT INTO PlatformeDeStreaming (NumePlatforma, AnInfiintare, PretLunarAbonare) VALUES ('Paramount+', 2017, 6);


INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('India', 'New Delhi', 'English');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Nigeria', 'Abuja', 'English');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('United States', 'Washington, D.C.', 'English');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Italy', 'Rome', 'Italian');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('France', 'Paris', 'French');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Germany', 'Berlin', 'German');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Spain', 'Madrid', 'Spanish');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('United Kingdom', 'London', 'English');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Poland', 'Warsaw', 'Polish');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Israel', 'Jerusalem', 'Hebrew');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Morocco', 'Rabat', 'Arabic');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Japan', 'Tokyo', 'Japanese');
INSERT INTO Tari (Nume, Capitala, LimbaOficiala) VALUES ('Hawaii', 'Honolulu', 'Hawaiian');


INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (1, 7, 8);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (7, 7, 8);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (8, 7, 8);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (9, 7, 8);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (2, 8, 2);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (3, 8, 2);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (4, 9, 6);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (10, 10, 5);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (2, 11, 2);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (6, 12, 9);
INSERT INTO ActoriPrincipaliFilme (ActorPrincipalID, FilmID, NumarActoriPrincipali) VALUES (5, 12, 9);


INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (1, 7);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (2, 7);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (7, 7);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (9, 7);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (4, 8);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (9, 8);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (11, 8);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (9, 9);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (14, 9);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (15, 9);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (6, 10);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (7, 10);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (9, 10);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (10, 10);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (6, 11);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (7, 11);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (9, 11);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (15, 11);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (2, 12);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (6, 12);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (7, 12);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (9, 12);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (10, 12);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (6, 13);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (10, 13);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (7, 13);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (12, 13);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (1, 13);
INSERT INTO GenuriFilme (GenuriID, FilmID) VALUES (2, 13);


INSERT INTO TariFilme (TaraID, FilmID) VALUES (3, 7);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (3, 8);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (8, 8);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (9, 9);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (10, 9);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (3, 10);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (3, 11);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (11, 11);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (3, 12);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (5, 12);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (8, 12);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (11, 12);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (12, 12);
INSERT INTO TariFilme (TaraID, FilmID) VALUES (13, 13);


INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (1, 7, 99);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (2, 7, 99);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (1, 8, 136);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (2, 8, 136);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (3, 8, 136);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (2, 9, 195);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (6, 9, 195);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (8, 9, 195);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (1, 10, 114);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (2, 10, 114);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (1, 11, 132);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (2, 11, 132);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (3, 11, 132);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (4, 11, 132);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (1, 12, 148);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (2, 12, 148);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (3, 12, 148);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (6, 12, 148);
INSERT INTO PlatformeDeStreamingFilme (PlatformaID, FilmID, NumarMinute) VALUES (7, 12, 148);


INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (18, 8, 2019, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (12, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (15, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (17, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (20, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (11, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (13, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (28, 8, 2019, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (21, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (27, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (26, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (22, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (35, 8, 2019, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (38, 8, 2019, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (31, 8, 2019, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (15, 9, 1994, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (11, 9, 1994, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (13, 9, 1994, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (16, 9, 1994, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (20, 9, 1994, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (18, 9, 1994, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (51, 9, 1994, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (53, 9, 1994, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (57, 9, 1994, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (15, 10, 1983, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (16, 10, 1983, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (20, 10, 1983, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (18, 10, 1983, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (57, 10, 1983, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (60, 10, 1983, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (36, 10, 1983, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (15, 11, 2015, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (17, 11, 2015, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (11, 11, 2015, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (20, 11, 2015, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (15, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (17, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (20, 12, 2011, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (36, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (37, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (38, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (56, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (57, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (58, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (60, 12, 2011, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (66, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (61, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (64, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (67, 12, 2011, 0);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (68, 12, 2011, 1);
INSERT INTO NominalizariFilme (NominalizariID, FilmID, An, Castigat) VALUES (70, 12, 2011, 0);


SELECT * FROM Regizori;
SELECT * FROM Filme;
SELECT * FROM Nominalizari;
SELECT * FROM Evenimente;
SELECT * FROM Tari;
SELECT * FROM ActoriPrincipali;
SELECT * FROM Studiouri;
SELECT * FROM Genuri;
SELECT * FROM PlatformeDeStreaming;


INSERT INTO Versiuni VALUES (0)
SELECT * FROM Versiuni;


--SELECT * FROM ActoriPrincipaliFilme;
--SELECT * FROM GenuriFilme;
--SELECT * FROM TariFilme;
--SELECT * FROM PlatformeDeStreamingFilme;
--SELECT * FROM NominalizariFilme;

--UPDATE Filme SET StudioID = 6 WHERE Titlu = 'Reservoir Dogs';
--UPDATE Filme SET StudioID = 1 WHERE Titlu = 'A Star Is Born';
--UPDATE Filme SET StudioID = 2 WHERE Titlu = 'Schindlers List';
--UPDATE Filme SET StudioID = 2 WHERE Titlu = 'E.T. the Extra-Terrestrial';
--UPDATE Filme SET StudioID = 1 WHERE Titlu = 'American Sniper';
--UPDATE Filme SET StudioID = 1 WHERE Titlu = 'Inception';
--UPDATE Filme SET StudioID = 2 WHERE Titlu = 'Jurrasic Park';
--UPDATE Filme SET RegizorID = 3 WHERE Titlu = 'Jurrasic Park';

--UPDATE Filme SET ContentRating = 'R' WHERE Titlu = 'Reservoir Dogs';
--UPDATE Filme SET ContentRating = 'R' WHERE Titlu = 'A Star Is Born';
--UPDATE Filme SET ContentRating = 'R' WHERE Titlu = 'Schindlers List';
--UPDATE Filme SET ContentRating = 'PG' WHERE Titlu = 'E.T. the Extra-Terrestrial';
--UPDATE Filme SET ContentRating = 'R' WHERE Titlu = 'American Sniper';
--UPDATE Filme SET ContentRating = 'PG-13' WHERE Titlu = 'Inception';