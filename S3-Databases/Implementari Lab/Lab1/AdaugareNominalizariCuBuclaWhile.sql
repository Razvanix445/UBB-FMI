USE Filme;
GO

DECLARE @NumarEvenimente INT
SELECT @NumarEvenimente = COUNT(*) FROM Evenimente;
DECLARE @EvenimentID int
SET @EvenimentID = 1

WHILE @EvenimentID <= @NumarEvenimente
BEGIN
	INSERT INTO Nominalizari (Categorie, EvenimentID)
	VALUES
		('Best Actor', @EvenimentID),
		('Best Actress', @EvenimentID),
		('Best Supporting Actor', @EvenimentID),
		('Best Supporting Actress', @EvenimentID),
		('Best Picture', @EvenimentID),
		('Best Director', @EvenimentID),
		('Best Screenplay', @EvenimentID),
		('Best Original Song', @EvenimentID),
		('Best Animated Feature', @EvenimentID),
		('Best Sound', @EvenimentID)

	SET @EvenimentID = @EvenimentID + 1
END

SELECT * FROM Nominalizari;

--DELETE FROM Nominalizari
--WHERE EvenimentID = 4;