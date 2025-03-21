#include "CosFilme.h"

CosInchirieriFilme::CosInchirieriFilme() {

}

void CosInchirieriFilme::adaugaCosInchirieri(const Film& film) {
	this->cosFilme.push_back(film);
}

void CosInchirieriFilme::golesteCosInchirieri() {
	this->cosFilme.clear();
}

void CosInchirieriFilme::umpleRandomCosInchirieri(size_t dimensiune, vector<Film> listaFilme) {
	vector<Film> listaFilmeAuxiliara;
	shuffle(listaFilme.begin(), listaFilme.end(), std::default_random_engine(std::random_device{}()));
	while ((this->cosFilme.size() < dimensiune) && (listaFilme.size() > 0)) {
		this->cosFilme.push_back(listaFilme.back());
		listaFilmeAuxiliara.push_back(listaFilme.back());
		listaFilme.pop_back();
		if (listaFilme.size() == 0 && cosFilme.size() < dimensiune) {
			listaFilme = listaFilmeAuxiliara;
			listaFilmeAuxiliara.clear();
			shuffle(listaFilme.begin(), listaFilme.end(), std::default_random_engine(std::random_device{}()));
		}
	}
}

const vector <Film>& CosInchirieriFilme::getAllCosInchirieri() {
	return this->cosFilme;
}

CosInchirieriFilme::~CosInchirieriFilme() {

}