#include "Film.h"

int Film::getAnAparitie() const {
	return anAparitie;
}

string Film::getTitluFilm() const {
	return titluFilm;
}

string Film::getGenFilm() const {
	return genFilm;
}

string Film::getActorPrincipal() const {
	return actorPrincipal;
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
