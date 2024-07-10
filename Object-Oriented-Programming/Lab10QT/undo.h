#pragma once
#include "Film.h"
#include "repo.h"
#include <vector>
#include <functional>
#include <fstream>

class ActiuneUndo {
public:
	virtual void doUndo() = 0;
	virtual ~ActiuneUndo() = default;
};

class UndoAdauga : public ActiuneUndo {
private:
	Film filmDeSters;
	Repo& repository;
public:
	UndoAdauga(const Film film, Repo& repo) : filmDeSters{ film }, repository{ repo } {}
	void doUndo() override {
		repository.stergeFilm(repository.cautaPozitieDupaTitlu(filmDeSters.getTitluFilm()));
	}
};

class UndoSterge :public ActiuneUndo {
private:
	Film filmDeAdaugat;
	Repo& repository;
public:
	UndoSterge(const Film film, Repo& repo) : filmDeAdaugat{ film }, repository{ repo } {}
	void doUndo() override {
		repository.adaugaFilm(filmDeAdaugat);
	}
};

class UndoModifica : public ActiuneUndo {
private:
	Film filmDeModificat;
	Repo& repository;
public:
	UndoModifica(const Film film, Repo& repo) : filmDeModificat{ film }, repository{ repo } {}
	void doUndo() override {
		repository.stergeFilm(repository.cautaPozitieDupaTitlu(filmDeModificat.getTitluFilm()));
		repository.adaugaFilm(filmDeModificat);
	}
};