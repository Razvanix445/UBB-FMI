#include "Film.h"

int Film::getAnAparitie() const {
	return this->anAparitie;
}

string Film::getTitluFilm() const {
	return this->titluFilm;
}

string Film::getGenFilm() const {
	return this->genFilm;
}

string Film::getActorPrincipal() const {
	return this->actorPrincipal;
}

void Film::setGenFilm(const string genFilmNou) {
	this->genFilm = genFilmNou;
}

void Film::setAnAparitie(const int anAparitieNou) {
	this->anAparitie = anAparitieNou;
}

void Film::setActorPrincipal(const string actorPrincipalNou) {
	this->actorPrincipal = actorPrincipalNou;
}
