#include "service.h"
#include <functional>
#include <algorithm>
#include <assert.h>

/*
	Functia creeaza, valideaza si adauga un nou film in repository
	arunca exceptie daca parametrii filmului nu sunt valizi sau daca exista deja un film cu acelasi titlu
*/
void Service::adaugaFilm(const string titluFilm, const string genFilm, int anAparitie, const string actorPrincipal) {
	Film film{titluFilm, genFilm, anAparitie, actorPrincipal};
	validator.valideaza(film);
	repository.adaugaFilm(film);
}

void Service::stergeFilm(const string titluFilm) {
	const int pozitie = repository.cautaPozitieDupaTitlu(titluFilm);
	repository.stergeFilm(pozitie);
}

void Service::modificaFilm(const string titluFilm, const string genFilm, int anAparitie, const string actorPrincipal) {
	Film film{ titluFilm, genFilm, anAparitie, actorPrincipal };
	validator.valideaza(film);
	const int pozitie = repository.cautaPozitieDupaTitlu(titluFilm);
	repository.modificaFilm(film, pozitie);
}

const VectorDinamic<Film>& Service::getAll() const {
	return repository.getAll();
}

const Film& Service::cautaFilm(const string& titluFilm) const {
	return repository.cautaFilm(titluFilm);
}

VectorDinamic<Film> Service::filtreazaDupaGen(const string genFilm) {
	VectorDinamic<Film> filmeFiltrate;
	VectorDinamic<Film> filme = repository.getAll();
	for (const auto& film : filme) {
		if (film.getGenFilm() == genFilm) {
			filmeFiltrate.push_back(film);
		}
	}
	return filmeFiltrate;
}

VectorDinamic<Film> Service::filtreazaDupaAnAparitie(int anAparitie) {
	VectorDinamic<Film> filmeFiltrate;
	VectorDinamic<Film> filme = repository.getAll();
	for (const auto& film : filme) {
		if (film.getAnAparitie() == anAparitie) {
			filmeFiltrate.push_back(film);
		}
	}
	return filmeFiltrate;
}

bool comparareDupaTitluCrescator(const Film& film1, const Film& film2) {
	return film1.getTitluFilm() < film2.getTitluFilm();
}

bool comparareDupaActorPrincipalCrescator(const Film& film1, const Film& film2) {
	return film1.getActorPrincipal() < film2.getActorPrincipal();
}

bool comparareDupaGenSiAnCrescator(const Film& film1, const Film& film2) {
	if (film1.getAnAparitie() == film2.getAnAparitie())
		return film1.getGenFilm() < film2.getGenFilm();
	return film1.getAnAparitie() < film2.getAnAparitie();
}

bool comparareDupaTitluDescrescator(const Film& film1, const Film& film2) {
	return film1.getTitluFilm() > film2.getTitluFilm();
}

bool comparareDupaActorPrincipalDescrescator(const Film& film1, const Film& film2) {
	return film1.getActorPrincipal() > film2.getActorPrincipal();
}

bool comparareDupaGenSiAnDescrescator(const Film& film1, const Film& film2) {
	if (film1.getAnAparitie() == film2.getAnAparitie())
		return film1.getGenFilm() > film2.getGenFilm();
	return film1.getAnAparitie() > film2.getAnAparitie();
}

VectorDinamic<Film> Service::sorteaza(int reperSortare, int ordineSortare) {

	VectorDinamic<Film> filme = repository.getAll();

	bool(*functieDeComparare)(const Film & film1, const Film & film2) { comparareDupaActorPrincipalCrescator };
	
	if (reperSortare == 1) {
		if (ordineSortare == 1)
			functieDeComparare = comparareDupaTitluCrescator;
		else
			functieDeComparare = comparareDupaTitluDescrescator;
	}
	else if (reperSortare == 2) {
		if (ordineSortare == 1)
			functieDeComparare = comparareDupaActorPrincipalCrescator;
		else
			functieDeComparare = comparareDupaActorPrincipalDescrescator;
	}
	else if (reperSortare == 3){
		if (ordineSortare == 1)
			functieDeComparare = comparareDupaGenSiAnCrescator;
		else
			functieDeComparare = comparareDupaGenSiAnDescrescator;
	}
	for (int i = 0; i < (int)filme.size() - 1; i++) {
		for (int j = i + 1; j < (int)filme.size(); j++) {
			if ((functieDeComparare)(filme[i], filme[j]) == false) {
				Film aux = filme[i];
				filme[i] = filme[j];
				filme[j] = aux;
			}
		}
	}
	return filme;
}

Service::~Service() 
{
}