#pragma once

#include "Film.h"
#include "observator.h"
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>
#include <ostream>
#include <string>
#include <functional>
#include <fstream>
#include <iostream>

using std::vector;
using std::shuffle;
using std::ostream;

class CosInchirieriFilme : public Observabil {

private:

	vector<Film> cosFilme;

public:

	CosInchirieriFilme();

	void adaugaCosInchirieri(const Film& film);

	void stergeFilmDinCosInchirieri(const string& titluFilm);

	void golesteCosInchirieri();

	void umpleRandomCosInchirieri(size_t dimensiune, vector<Film> listaFilme);

	const vector <Film>& getAllCosInchirieri();

	~CosInchirieriFilme();

};

class ExceptieCosFilme {

	string mesajEroare;

public:

	ExceptieCosFilme(string mesaj) :mesajEroare{ mesaj } {}

	friend ostream& operator<<(ostream& out, const ExceptieCosFilme& exceptie);

	string getMesaj() { return this->mesajEroare; }

};

ostream& operator<<(ostream& out, const ExceptieCosFilme& exceptie);