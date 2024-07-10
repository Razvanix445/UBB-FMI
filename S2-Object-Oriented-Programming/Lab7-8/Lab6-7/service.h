#pragma once

#include "TemplateVectorDinamic.h"
#include "Film.h"
#include "repo.h"
#include "validator.h"
#include <string>
#include <vector>
#include <functional>

using std::vector;
using std::function;

class Service {
private:

	Repo& repository;
	Validator& validator;

public:

	Service(Repo& repository, Validator& validator) noexcept: repository{ repository }, validator{ validator } {}
	//nu permitem copierea de obiecte Service
	//Service(const Service& other) = delete;

	/*
	Functia creeaza, valideaza si adauga un nou film in repository
	arunca exceptie daca parametrii filmului nu sunt valizi sau daca exista deja un film cu acelasi titlu
	*/
	void adaugaFilm(const string titluFilm, const string genFilm, int anAparitie, const string actorPrincipal);

	/*
	Functia sterge un film din repository
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	void stergeFilm(const string titluFilm);

	/*
	Functia modifica un film din repository
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	void modificaFilm(const string titluFilm, const string genFilm, int anAparitie, const string actorPrincipal);

	/*
	Functia cauta un film in repository dupa titlul acestuia
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	const Film& cautaFilm(const string& titluFilm) const;

	/*
	Functia returneaza toate filmele in ordinea adaugarii lor in repository
	*/
	const VectorDinamic<Film>& getAll() const;

	VectorDinamic<Film> filtreazaDupaGen(const string genFilm);

	VectorDinamic<Film> filtreazaDupaAnAparitie(int anAparitie);

	VectorDinamic<Film> sorteaza(int reperSortare, int ordineSortare);

	~Service();

};