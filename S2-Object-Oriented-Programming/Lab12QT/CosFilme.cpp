#include "CosFilme.h"

CosInchirieriFilme::CosInchirieriFilme() {

}

void CosInchirieriFilme::adaugaCosInchirieri(const Film& film) {
	this->cosFilme.push_back(film);
	notifica();
}

void CosInchirieriFilme::stergeFilmDinCosInchirieri(const string& titluFilm) {
	if (cosFilme.size() == 0)
		throw ExceptieCosFilme("Nu exista filme introduse!\n");

	for (auto it = cosFilme.begin(); it != cosFilme.end(); ++it) {
		if (it->getTitluFilm() == titluFilm) {
			cosFilme.erase(it);
			notifica();
			return;
		}
	}
	throw ExceptieCosFilme("Nu s-a gasit filmul cautat!\n");
}

void CosInchirieriFilme::golesteCosInchirieri() {
	this->cosFilme.clear();
	notifica();
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
	notifica();
}

const vector <Film>& CosInchirieriFilme::getAllCosInchirieri() {
	return this->cosFilme;
	notifica();
}

ostream& operator<<(ostream& out, const ExceptieCosFilme& exceptie) {
	for (const auto& mesaj : exceptie.mesajEroare)
		out << mesaj;
	return out;
}

CosInchirieriFilme::~CosInchirieriFilme() {

}