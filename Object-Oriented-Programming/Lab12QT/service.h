#pragma once

#include "Film.h"
#include "repo.h"
#include "validator.h"
#include "CosFilme.h"
#include "undo.h"
#include <string>
#include <functional>
#include <iterator>
#include <fstream>
#include <iostream>
#include <vector>

using namespace std;
using std::vector;

class Service {
private:

	Repo& repository;
	Validator& validator;
	CosInchirieriFilme& cosInchirieri;
	vector<unique_ptr<ActiuneUndo>> ActiuniUndo;

public:

	CosInchirieriFilme& getCosInchirieri() {
		return cosInchirieri;
	}

	Service(Repo& repository, Validator& validator, CosInchirieriFilme& cosInchirieri) noexcept : repository{ repository }, validator{ validator }, cosInchirieri{ cosInchirieri } {}
	//nu permitem copierea de obiecte Service
	Service(const Service& other) = delete;

	/*
	Functia creeaza, valideaza si adauga un nou film in repository
	arunca exceptie daca parametrii filmului nu sunt valizi sau daca exista deja un film cu acelasi titlu
	*/
	void adaugaFilm(const string& titluFilm, const string& genFilm, const int anAparitie, const string& actorPrincipal);

	/*
	Functia sterge un film din repository
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	void stergeFilm(const string& titluFilm);

	/*
	Functia modifica un film din repository
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	void modificaFilm(const string& titluFilm, const string& genFilm, int anAparitie, const string& actorPrincipal);

	/*
	Functia cauta un film in repository dupa titlul acestuia
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	const Film& cautaFilm(const string& titluFilm) const;

	/*
	Functia returneaza toate filmele in ordinea adaugarii lor in repository
	*/
	const vector<Film>& getAll() const;

	vector<Film> filtreazaDupaGen(const string& genFilm);

	vector<Film> filtreazaDupaAnAparitie(int anAparitie);

	vector<Film> sorteaza(int reperSortare, int ordineSortare);


	const vector<Film>& adaugaFilmInCosInchirieri(const string& titluFilm);

	const vector<Film>& stergeFilmDinCosInchirieri(const string& titluFilm);

	const vector<Film>& golesteCosInchirieri();

	const vector<Film>& umpleRandomCosInchirieri(int dimensiune);

	const vector<Film>& getAllCosInchirieri();

	void exportaInCSV(const string& numeFisier, const vector<Film>& filme);

	void exportaInHTML(const string& numeFisier, const vector<Film>& filme);

	void undo();

	//void notifica() {
		//this->cosInchirieri.notifica();
	//}

	~Service();

};