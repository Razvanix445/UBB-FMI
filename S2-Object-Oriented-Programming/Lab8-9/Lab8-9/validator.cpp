#include "validator.h"
#include <assert.h>
#include <sstream>

void Validator::valideaza(const Film& film) {
	vector<string> mesajEroare;
	if (film.getTitluFilm().size() == 0) mesajEroare.push_back("Titlu invalid!\n");
	if (film.getGenFilm().size() == 0) mesajEroare.push_back("Gen invalid!\n");
	if (film.getAnAparitie() < 1500) mesajEroare.push_back("An invalid!\n");
	if (film.getActorPrincipal().size() == 0) mesajEroare.push_back("Actor principal invalid!\n");
	if (mesajEroare.size() > 0) {
		throw ExceptieValidare(mesajEroare);
	}
}

ostream& operator<<(ostream& out, const ExceptieValidare& exceptie) {
	for (const auto& mesaj : exceptie.mesajEroare) {
		out << mesaj << " ";
	}
	return out;
}
