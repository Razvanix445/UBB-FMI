#include "repo.h"

//Repo::Repo() 
//{
//}

void Repo::adaugaFilm(const Film& film) {
	if (exista(film)) {
		throw ExceptieRepo("Filmul cu titlul " + film.getTitluFilm() + " este deja adaugat!\n");
	}
	filme.push_back(film);
}

void Repo::stergeFilm(int pozitie) {
	if (pozitie != filme.size() - 1) {
		for (pozitie; pozitie < filme.size() - 1; pozitie++) {
			filme[pozitie] = filme[pozitie + 1];
		}
		filme.pop_back();
	}
	else {
		filme.pop_back();
	}
}

void Repo::modificaFilm(const Film filmModificat, int pozitie) {
	if (!exista(filmModificat)) {
		throw ExceptieRepo("Titlul " + filmModificat.getTitluFilm() + " nu a fost gasit!\n");
	}
	filme[pozitie].setGenFilm(filmModificat.getGenFilm());
	filme[pozitie].setAnAparitie(filmModificat.getAnAparitie());
	filme[pozitie].setActorPrincipal(filmModificat.getActorPrincipal());
}

bool Repo::exista(const Film& film) {
	try {
		cautaFilm(film.getTitluFilm());
		return true;
	}
	catch (ExceptieRepo&) {
		return false;}
}

const Film& Repo::cautaFilm(string titluFilm) {
	for (const auto& film : filme) {
		if (film.getTitluFilm() == titluFilm) {
			return film;
		}
	}
	throw ExceptieRepo("Nu s-a gasit filmul cu titlul " + titluFilm + "!\n");
}

int Repo::cautaPozitieDupaTitlu(const string titluFilm) const {
	int pozitie = -1, i = -1;
	for (const auto& film : filme) {
		i++;
		if (film.getTitluFilm() == titluFilm) {
			pozitie = i;
		}
	}
	if (pozitie == -1) {
		throw ExceptieRepo("Nu exista film cu titlul " + titluFilm);
	}
	return pozitie;
}

const vector<Film>& Repo::getAll() const noexcept {
	return this->filme;
}

//Repo::~Repo() 
//{
//}

ostream& operator<<(ostream& out, const ExceptieRepo& exceptie) {
	for (const auto& mesaj: exceptie.mesajEroare)
		out << mesaj;
	return out;
}
