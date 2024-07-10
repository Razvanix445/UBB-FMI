--Creati 10 interogari pe baza de date creata pentru primul laborator. Este necesar ca interogarile sa contina cel putin urmatoarele:
--  5 interogari ce folosesc WHERE
--  3 interogari ce folosesc GROUP BY
--  2 interogari ce folosesc DISTINCT
--  2 interogari cu HAVING
--  7 interogari ce extrag informatii din mai mult de 2 tabele
--  2 interogari pe tabele aflate in relatie m-n
--Interogarile trebuie sa fie relevante pentru tema bazei de date si vor returna informatii utile unui potential utilizator.

USE Filme;
GO

--Mi-a placut foarte mult un film si vreau sa vad mai multe filme de la regizorul respectiv.
SELECT F.Titlu FROM Filme F
JOIN Regizori R ON F.RegizorID = R.RegizorID
WHERE R.NumeRegizor = 'Spielberg';


--Vreau sa vizionez cele mai bune 3 filme (dupa UserRating) mai recente de anul 2010.
SELECT TOP 3 Titlu, DataLansare, UserRating
FROM Filme
WHERE YEAR(DataLansare) >= 2010
ORDER BY UserRating DESC;


--Mi-a placut cum a jucat un anumit actor intr-un anumit film de un anumit gen si vreau sa mai vad astfel de filme de acelasi gen, 
--asa ca vreau o lista de actori de actiune de unde sa aleg.
SELECT F.Titlu, STRING_AGG(AP.NumeActorPrincipal + ' ' + AP.PrenumeActorPrincipal, ', ') AS ListaActoriPrincipali
FROM ActoriPrincipali AP
JOIN ActoriPrincipaliFilme APF ON AP.ActorPrincipalID = APF.ActorPrincipalID
JOIN Filme F ON APF.FilmID = F.FilmID
JOIN GenuriFilme GF ON F.FilmID = GF.FilmID
JOIN Genuri G ON GF.GenuriID = G.GenuriID
WHERE G.Gen = 'Action'
GROUP BY F.Titlu;


--Vreau sa vad numele si prenumele regizorilor care sunt experimentati, adica au regizat cel putin 3 filme.
SELECT R.NumeRegizor, R.PrenumeRegizor
FROM Regizori R
JOIN Filme F ON R.RegizorID = F.RegizorID
GROUP BY R.NumeRegizor, R.PrenumeRegizor
HAVING COUNT(F.FilmID) >= 3;


--Vreau sa vad filmele de genul 'Drama' disponibile pe Netflix.
SELECT F.Titlu
FROM Filme F
JOIN PlatformeDeStreamingFilme PSF ON F.FilmID = PSF.FilmID
JOIN PlatformeDeStreaming PS ON PSF.PlatformaID = PS.PlatformaID
JOIN GenuriFilme GF ON F.FilmID = GF.FilmID
JOIN Genuri G ON GF.GenuriID = G.GenuriID
WHERE PS.NumePlatforma = 'Netflix' AND G.Gen = 'Drama';


--Vreau sa ma uit la un film de un anumit regizor premiate cu cel putin doua premii Oscar inainte de anul 2016.
DECLARE @RegizorID INT
SELECT @RegizorID = RegizorID
FROM Regizori WHERE NumeRegizor = 'Spielberg';

SELECT F.Titlu
FROM Filme F
JOIN Regizori R ON F.RegizorID = R.RegizorID
JOIN NominalizariFilme NF ON F.FilmID = NF.FilmID
JOIN Nominalizari N ON NF.NominalizariID = N.NominalizareID
JOIN Evenimente E ON N.EvenimentID = E.EvenimentID
WHERE R.RegizorID = @RegizorID AND E.NumeEveniment = 'OSCAR ACADEMY AWARDS' AND NF.An < 2016
GROUP BY F.Titlu
HAVING COUNT(DISTINCT N.Categorie) >= 2;


--Vreau sa vad un film american de drama cu un actor principal care s-a nascut inainte de anul 1980.
SELECT DISTINCT F.Titlu FROM Filme F
JOIN ActoriPrincipaliFilme APF ON F.FilmID = APF.FilmID
JOIN ActoriPrincipali AP ON APF.ActorPrincipalID = AP.ActorPrincipalID
JOIN GenuriFilme GF ON F.FilmID = GF.FilmID
JOIN Genuri G ON GF.GenuriID = G.GenuriID
JOIN TariFilme TF ON F.FilmID = TF.FilmID
JOIN Tari T ON T.TaraID = T.TaraID
WHERE G.Gen = 'Drama' AND T.Nume = 'United States' AND YEAR(AP.DataNasterii) < 1980;


--Vreau sa vad un film musical pe HBO MAX scurt (care dureaza mai putin de 140 de minute).
SELECT DISTINCT F.Titlu FROM Filme F
JOIN GenuriFilme GF ON F.FilmID = GF.FilmID
JOIN Genuri G ON GF.GenuriID = G.GenuriID
JOIN PlatformeDeStreamingFilme PSF ON F.FilmID = PSF.FilmID
JOIN PlatformeDeStreaming PS ON PSF.PlatformaID = PS.PlatformaID
WHERE G.Gen = 'Musical' AND PS.NumePlatforma = 'HBO MAX' AND PSF.NumarMinute < 140;


--Vreau sa vad cele mai profitabile 3 filme (incasari - buget).
SELECT TOP 3
Titlu AS NumeFilm, Incasari - Buget AS Profit
FROM Filme
ORDER BY Profit DESC;


--Vreau sa gasesc studiourile cu rata de profesionalism mare, data de aprecierea a trei sferturi din persoanele care au vizionat filmul.
SELECT DISTINCT S.Denumire
FROM Studiouri S
JOIN Filme F ON S.StudioID = F.StudioID WHERE F.UserRating >= 7.5;


--Cauta filme care au castigat premiul BAFTA in care regizorul sa fi fost mai in varsta decat majoritatea (media) varstei actorilor principali, dar sa aiba peste 40 de ani.
WITH MediaVarsteiActorilor AS (
	SELECT F.FilmID, AVG(DATEDIFF(YEAR, AP.DataNasterii, F.DataLansare)) AS MediaVarsta
	FROM Filme F
	JOIN ActoriPrincipaliFilme APF ON F.FilmID = APF.FilmID
	JOIN ActoriPrincipali AP ON APF.ActorPrincipalID = AP.ActorPrincipalID
	GROUP BY F.FilmID
)
SELECT F.Titlu AS NumeFilm
FROM Filme F
JOIN MediaVarsteiActorilor MVA ON F.FilmID = MVA.FilmID
JOIN Regizori R ON F.RegizorID = R.RegizorID
WHERE DATEDIFF(YEAR, R.DataNasterii, F.DataLansare) >= MVA.MediaVarsta
AND DATEDIFF(YEAR, R.DataNasterii, F.DataLansare) >= 40
AND F.FilmID IN (
	SELECT DISTINCT NF.FilmID
	FROM NominalizariFilme NF
	JOIN Nominalizari N ON NF.NominalizariID = N.NominalizareID
	JOIN Evenimente E ON N.EvenimentID = E.EvenimentID
	WHERE E.NumeEveniment = 'BAFTA' AND NF.Castigat = 1
);

--