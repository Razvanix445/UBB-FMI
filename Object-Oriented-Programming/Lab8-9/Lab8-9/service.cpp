#include "service.h"
#include <functional>
#include <algorithm>
#include <assert.h>

/*
	Functia creeaza, valideaza si adauga un nou film in repository
	arunca exceptie daca parametrii filmului nu sunt valizi sau daca exista deja un film cu acelasi titlu
*/
void Service::adaugaFilm(const string& titluFilm, const string& genFilm, const int anAparitie, const string& actorPrincipal) {
	const Film film{ titluFilm, genFilm, anAparitie, actorPrincipal };
	validator.valideaza(film);
	repository.adaugaFilm(film);
	ActiuniUndo.push_back(std::make_unique<UndoAdauga>(film, this->repository));
}

void Service::stergeFilm(const string& titluFilm) {
	const int pozitie = repository.cautaPozitieDupaTitlu(titluFilm);
	const Film film = repository.cautaFilm(titluFilm);
	repository.stergeFilm(pozitie);
	ActiuniUndo.push_back(std::make_unique<UndoSterge>(film, this->repository));
}

void Service::modificaFilm(const string& titluFilm, const string& genFilm, int anAparitie, const string& actorPrincipal) {
	const Film filmModificat{ titluFilm, genFilm, anAparitie, actorPrincipal };
	validator.valideaza(filmModificat);
	const Film filmAnterior = repository.cautaFilm(titluFilm);
	repository.modificaFilm(filmModificat);
	ActiuniUndo.push_back(std::make_unique<UndoModifica>(filmAnterior, this->repository));
}

const vector<Film>& Service::getAll() const {
	return repository.getAll();
}

const Film& Service::cautaFilm(const string& titluFilm) const {
	return repository.cautaFilm(titluFilm);
}

vector<Film> Service::filtreazaDupaGen(const string& genFilm) {
	const vector<Film>& filme = repository.getAll();
	vector<Film> filmeFiltrate(filme.size());
	auto iterator = copy_if(filme.begin(), filme.end(), filmeFiltrate.begin(), [genFilm](const Film& film) {
		return film.getGenFilm() == genFilm;
		});
	filmeFiltrate.resize(distance(filmeFiltrate.begin(), iterator));
	return filmeFiltrate;
}

vector<Film> Service::filtreazaDupaAnAparitie(int anAparitie) {
	const vector<Film>& filme = repository.getAll();
	vector<Film> filmeFiltrate(filme.size());
	auto iterator = copy_if(filme.begin(), filme.end(), filmeFiltrate.begin(), [anAparitie](const Film& film) {
		return film.getAnAparitie() == anAparitie;
		});
	filmeFiltrate.resize(distance(filmeFiltrate.begin(), iterator));
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

vector<Film> Service::sorteaza(int reperSortare, int ordineSortare) {

	vector<Film> filme = repository.getAll();

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
	else if (reperSortare == 3) {
		if (ordineSortare == 1)
			functieDeComparare = comparareDupaGenSiAnCrescator;
		else
			functieDeComparare = comparareDupaGenSiAnDescrescator;
	}

	sort(filme.begin(), filme.end(), functieDeComparare);
	return filme;
}

void Service::undo() {
	if (ActiuniUndo.empty()) { throw ExceptieRepo{ "Nu mai este undo de facut!\n" }; }
	ActiuniUndo.back()->doUndo();
	ActiuniUndo.pop_back();
}


const vector<Film>& Service::adaugaFilmInCosInchirieri(const string& titluFilm) {
	int filmDejaAdaugat = 0;
	const vector<Film>& filme = getAllCosInchirieri();
	const Film& filmCautat = this->repository.cautaFilm(titluFilm);
	for (const auto& film : filme)
		if (film.getTitluFilm() == titluFilm) {
			filmDejaAdaugat = 1;
			break;
		}
	if (!filmDejaAdaugat)
		this->cosInchirieri.adaugaCosInchirieri(filmCautat);
	return this->cosInchirieri.getAllCosInchirieri();
}

const vector<Film>& Service::golesteCosInchirieri() {
	this->cosInchirieri.golesteCosInchirieri();
	return this->cosInchirieri.getAllCosInchirieri();
}

const vector<Film>& Service::umpleRandomCosInchirieri(int dimensiune) {
	this->cosInchirieri.umpleRandomCosInchirieri(dimensiune, this->repository.getAll());
	return this->cosInchirieri.getAllCosInchirieri();
}

const vector<Film>& Service::getAllCosInchirieri() {
	return this->cosInchirieri.getAllCosInchirieri();
}

void Service::exportaInCSV(const string& numeFisier, const vector<Film>& filme) {
	std::ofstream fout(numeFisier, std::ios::trunc);
	if (!fout.is_open()) { throw ExceptieRepo("Fisierul cu numele " + numeFisier + " nu a putut fi deschis!\n"); }
	for (const auto& film : filme) {
		fout << film.getTitluFilm() << "," << film.getGenFilm() << "," << film.getAnAparitie() << "," << film.getActorPrincipal() << "\n";
	}
	fout.close();
}

void Service::exportaInHTML(const string& numeFisier, const vector<Film>& filme) {
	std::ofstream fout(numeFisier);
	if (!fout.is_open()) { throw ExceptieRepo("Fisierul cu numele " + numeFisier + " nu a putut fi deschis!\n"); }
	fout << "<html><body>\n";
	fout << "<table border=\"1\" style=\"width:100 % \">\n";
	for (const auto& film : filme) {
		fout << "<tr>\n";
		fout << "<td>" << film.getTitluFilm() << "</td>\n";
		fout << "<td>" << film.getGenFilm() << "</td>\n";
		fout << "<td>" << film.getAnAparitie() << "</td>\n";
		fout << "<td>" << film.getActorPrincipal() << "</td>\n";
		fout << "</tr>\n";
	}
	fout << "</table>\n";
	fout << "</body></html>\n";
	fout.close();
}


Service::~Service()
{
}