#include "repo.h"

void Repo::adaugaFilm(const Film& film) {
	if (exista(film)) {
		throw ExceptieRepo("Filmul cu titlul " + film.getTitluFilm() + " este deja adaugat!\n");
	}
	filme.push_back(film);
}

void Repo::stergeFilm(int pozitie) {
	if (pozitie != filme.size() - 1) {
		std::copy(filme.begin() + pozitie + 1, filme.end(), filme.begin() + pozitie);
	}
	filme.pop_back();
}

void Repo::modificaFilm(const Film filmModificat) {
	auto iterator = std::find_if(filme.begin(), filme.end(), [&](const Film& f) {
		return f.getTitluFilm() == filmModificat.getTitluFilm();
		});
	if (iterator == filme.end()) {
		throw ExceptieRepo("Titlul " + filmModificat.getTitluFilm() + " nu a fost gasit!\n");
	}
	*iterator = filmModificat;
}

bool Repo::exista(const Film& film) {
	return std::find_if(filme.begin(), filme.end(), [&](const Film& f) { return f.getTitluFilm() == film.getTitluFilm(); }) != filme.end();
}

const Film& Repo::cautaFilm(string titluFilm) {
	auto iterator = std::find_if(filme.begin(), filme.end(), [&](const Film& f) { return f.getTitluFilm() == titluFilm; });
	if (iterator == filme.end()) {
		throw ExceptieRepo("Nu s-a gasit filmul cu titlul " + titluFilm + "!\n");
	}
	return *iterator;
}

int Repo::cautaPozitieDupaTitlu(const string titluFilm) const {
	auto iterator = std::find_if(filme.begin(), filme.end(), [&](const Film& f) { return f.getTitluFilm() == titluFilm; });
	if (iterator == filme.end()) {
		throw ExceptieRepo("Nu exista film cu titlul " + titluFilm);
	}
	return static_cast<int>(std::distance(filme.begin(), iterator));
}

const vector<Film>& Repo::getAll() const noexcept {
	return this->filme;
}

ostream& operator<<(ostream& out, const ExceptieRepo& exceptie) {
	for (const auto& mesaj : exceptie.mesajEroare)
		out << mesaj;
	return out;
}

Repo::~Repo()
{
}


void RepoFile::readAllFromFile() {
	std::ifstream fin(numeFisier);
	if (!fin.is_open()) {
		throw ExceptieRepo("Fisierul nu a putut fi deschis: " + numeFisier);
	}
	while (true) {
		string titluFilm;
		std::getline(fin >> std::ws, titluFilm);
		if (fin.fail()) {
			break;
		}
		string genFilm;
		std::getline(fin >> std::ws, genFilm);
		int anAparitie;
		fin >> anAparitie;
		string actorPrincipal;
		std::getline(fin >> std::ws, actorPrincipal);

		Film film{ titluFilm, genFilm, anAparitie, actorPrincipal };
		Repo::adaugaFilm(film);
	}
	fin.close();
}

void RepoFile::writeAllToFile() {
	std::ofstream fout(numeFisier);
	if (!fout.is_open()) {std::string mesaj("Fisierul nu a putut fi deschis: ");throw ExceptieRepo(mesaj);}
	for (auto& film : Repo::getAll()) {
		fout << film.getTitluFilm() << std::endl;
		fout << film.getGenFilm() << std::endl;
		fout << film.getAnAparitie() << std::endl;
		fout << film.getActorPrincipal() << std::endl;
	}
	fout.close();
}