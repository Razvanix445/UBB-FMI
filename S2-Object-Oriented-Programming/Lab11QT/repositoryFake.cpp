#include "repositoryFake.h"

const vector<Film>& RepositoryFake::getAll() const noexcept {
	static vector<Film> filme;
	filme.clear();
	if (caz == 1) {
		filme.push_back(Film("Cars", "Family", 2000, "McQuine"));
		return filme;
	}
	else if (caz == 2) {
		filme.push_back(Film("Titanic", "Drama", 2020, "Leonardo"));
		filme.push_back(Film("Titanicum", "Romance", 2019, "Leonardo"));
		filme.push_back(Film("Titanice", "Drama", 2020, "Leonardo"));
		return filme;
	}
}