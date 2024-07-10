#pragma once
#include "Film.h"
#include <vector>
#include <string>
#include <ostream>

using std::vector;
using std::string;
using std::ostream;

class Repo {
private:

	vector<Film> filme;

	//verifica daca film a fost introdus deja in repository
	bool exista(const Film& film);

public:

	Repo() = default;
	//Repo() = default;
	//nu permitem copierea de obiecte
	Repo(const Repo& other) = delete;

	/*
	Adauga film in repository
	arunca exceptie daca exista deja un film cu acelasi titlu
	*/
	virtual void adaugaFilm(const Film& film);

	/*
	Sterge film dupa titlu din repository
	arunca exceptie daca nu s-a gasit titlul filmului
	*/
	virtual void stergeFilm(int pozitie);

	/*
	Modifica un film din repository
	arunca exceptie daca nu exista titlul filmului sau noile date sunt invalide
	*/
	virtual void modificaFilm(const Film filmModificat, int pozitie);

	/*
	Cauta film in repository
	arunca exceptie daca nu se gaseste filmul
	*/
	const Film& cautaFilm(string titlu);

	int cautaPozitieDupaTitlu(const string titluFilm) const;

	/*
	Functia getAll returneaza toate filmele din repository
	*/
	const vector<Film>& getAll() const noexcept;

	//virtual ~Repo();
};

class ExceptieRepo {

	string mesajEroare;

public:

	ExceptieRepo(string mesaj) :mesajEroare{ mesaj } {}

	friend ostream& operator<<(ostream& out, const ExceptieRepo& exceptie);

	string getMesaj() { return this->mesajEroare; }

};

ostream& operator<<(ostream& out, const ExceptieRepo& exceptie);
