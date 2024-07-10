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


class RepoNou : public Repo {
private:

	map<string, Film> filme;
	int probabilitate;

public:

	RepoNou(int probabilitate) :Repo(), probabilitate{ probabilitate } {
	}

	virtual void adaugaFilm(const Film& film) override {
		//cout << "Merge!";
		int mergeSauNu = rand() % 100;
		cout << mergeSauNu << "\n";
		if (mergeSauNu < probabilitate) {
			throw ExceptieRepo("Nu vreau!!!\n");
		}
		if (exista(film)) {
			throw ExceptieRepo("Filmul cu titlul " + film.getTitluFilm() + " este deja adaugat!\n");
		}
		filme.insert(make_pair(film.getTitluFilm(), film));
	}

	virtual void stergeFilm(int pozitie) override {
		//cout << "Merge!";
		int mergeSauNu = rand() % 100;
		cout << mergeSauNu << "\n";
		if (mergeSauNu < probabilitate)
			throw ExceptieRepo("Nu vreau!!!\n");
		if (pozitie < 0 || pozitie >= filme.size()) {
			throw ExceptieRepo("Pozitie invalida!\n");
		}
		auto iterator = filme.begin();
		advance(iterator, pozitie);
		filme.erase(iterator);
	}

	virtual void modificaFilm(const Film& filmModificat) override {
		//cout << "Merge!";
		int mergeSauNu = rand() % 100;
		cout << mergeSauNu << "\n";
		if (mergeSauNu < probabilitate)
			throw ExceptieRepo("Nu vreau!!!\n");
		auto iterator = filme.find(filmModificat.getTitluFilm());
		if (iterator == filme.end()) {
			throw ExceptieRepo("Titlul " + filmModificat.getTitluFilm() + " nu a fost gasit!\n");
		}
		iterator->second = filmModificat;
	}

	virtual const Film& cautaFilm(string titluFilm) override {
		//cout << "Merge!";
		auto iterator = filme.find(titluFilm);
		if (iterator == filme.end()) {
			throw ExceptieRepo("Nu s-a gasit filmul cu titlul " + titluFilm + "!\n");
		}
		return iterator->second;
	}

	virtual int cautaPozitieDupaTitlu(const string titluFilm) const override {
		auto iterator = filme.find(titluFilm);
		if (iterator == filme.end()) {
			throw ExceptieRepo("Nu exista film cu titlul " + titluFilm);
		}
		return static_cast<int>(std::distance(filme.begin(), iterator));
	}

	virtual const vector<Film>& getAll() const noexcept override {
		//cout << "Merge!";
		static vector<Film> vectorFilme;
		vectorFilme.clear();
		for (const auto& film : filme) {
			vectorFilme.push_back(film.second);
		}
		return vectorFilme;
	}

	~RepoNou() override {
	}

};
