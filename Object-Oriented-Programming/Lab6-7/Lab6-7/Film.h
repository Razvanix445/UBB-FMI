#pragma once
#include <iostream>
#include <string>
using std::string;
using std::cout;

class Film {

private:

	string titluFilm;
	string genFilm;
	int anAparitie;
	string actorPrincipal;

public:

	int getAnAparitie() const;

	string getTitluFilm() const;

	string getGenFilm() const;

	string getActorPrincipal() const;
	
	void setGenFilm(const string genFilmNou);

	void setAnAparitie(const int anAparitieNou);

	void setActorPrincipal(const string actorPrincipalNou);

	Film(const string titluFilm, const string genFilm, const int anAparitie, const string actorPrincipal) : titluFilm{ titluFilm }, genFilm{ genFilm }, anAparitie{ anAparitie }, actorPrincipal{ actorPrincipal } {}

	Film(const Film& other) :titluFilm{ other.titluFilm }, genFilm{ other.genFilm }, anAparitie{ other.anAparitie }, actorPrincipal{ other.actorPrincipal } {
		cout << "!!!!!!!!!!!!!!!!!!!!\n";
	}

};