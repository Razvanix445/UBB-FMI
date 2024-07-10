#pragma once

#include "Film.h"
#include "observator.h"
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>

using std::vector;
using std::shuffle;

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