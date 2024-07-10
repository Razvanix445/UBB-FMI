#pragma once
#include "Film.h"
#include <vector>
#include <string>

using std::vector;
using std::string;
using std::ostream;

class ExceptieValidare {
	vector<string> mesajEroare;
public:
	ExceptieValidare(vector<string> erori) :mesajEroare{ erori } {}
	friend ostream& operator<<(ostream& out, const ExceptieValidare& exceptie);

	vector<string> getMesaj() { return mesajEroare; }
};

ostream& operator<<(ostream& out, const ExceptieValidare& exceptie);

class Validator {
public:
	void valideaza(const Film& film);
};
