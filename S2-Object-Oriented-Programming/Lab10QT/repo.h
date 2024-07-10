#pragma once
#include "Film.h"
#include <vector>
#include <map>
#include <string>
#include <ostream>
#include <functional>
#include <fstream>
#include <iostream>

using namespace std;
using std::vector;
using std::map;
using std::string;
using std::ostream;

class Repo {
protected:

	vector<Film> filme;

	//verifica daca film a fost introdus deja in repository
	bool exista(const Film& film);

public:

	Repo() = default;
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
	virtual void modificaFilm(const Film& filmModificat);

	/*
	Cauta film in repository
	arunca exceptie daca nu se gaseste filmul
	*/
	virtual const Film& cautaFilm(string titlu);

	virtual int cautaPozitieDupaTitlu(const string titluFilm) const;

	/*
	Functia getAll returneaza toate filmele din repository
	*/
	virtual const vector<Film>& getAll() const noexcept;

	virtual ~Repo();
};

class ExceptieRepo {

	string mesajEroare;

public:

	ExceptieRepo(string mesaj) :mesajEroare{ mesaj } {}

	friend ostream& operator<<(ostream& out, const ExceptieRepo& exceptie);

	string getMesaj() { return this->mesajEroare; }

};

ostream& operator<<(ostream& out, const ExceptieRepo& exceptie);


class RepoFile :public Repo {
private:

	void readAllFromFile();
	void writeAllToFile();
	string numeFisier;
	void clearFile() {
		std::ofstream fin(numeFisier, std::ios::trunc);
		fin.close();
	}

public:

	RepoFile(string numeFisier) :Repo(), numeFisier{ numeFisier } {
		readAllFromFile();
	}

	void adaugaFilm(const Film& film) override {
		Repo::adaugaFilm(film);
		writeAllToFile();
	}

	void modificaFilm(const Film& film) override {
		Repo::modificaFilm(film);
		writeAllToFile();
	}

	void stergeFilm(int pozitie) override {
		Repo::stergeFilm(pozitie);
		writeAllToFile();
	}

	~RepoFile() override {
	}
};