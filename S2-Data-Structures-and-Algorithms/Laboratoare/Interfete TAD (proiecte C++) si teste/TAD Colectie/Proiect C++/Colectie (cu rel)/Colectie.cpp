#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;

bool rel(TElem e1, TElem e2) {
	/* de adaugat */
	return false;
}

void Colectie::redimensionare() {
	//alocam un spatiu de capacitate dubla
	TElem* elementNou = new TElem[capacitate * 2];

	//copiem vechile valori in noua zona
	for (int i = 0; i < dimensiune; i++)
		elementNou[i] = elemente[i];

	//dublam capacitatea
	capacitate = capacitate * 2;
	
	//eliberam vechea zona
	delete[] elemente;

	//vectorul indica spre noua zona
	elemente = elementNou;
}

Colectie::Colectie(Relatie r) {
	//setam capacitatea
	capacitate = 20;

	//setam dimensiunea
	dimensiune = 0;

	//alocam spatiu de memorare pentru vector
	elemente = new TElem[capacitate];

	//setam relatia
	rel = r;
}

void Colectie::adauga(TElem element) {
	//daca s-a atins capacitatea maxima, redimensionam
	if (dimensiune == capacitate)
		redimensionare();

	//variabila i va stoca pozitia unde urmatorul element va trebui inserat
	int i = 0;
	while (i < dimensiune && rel(elemente[i], element))
		i++;

	//mutam toate elementele de pe pozitia dimensiune la i + 1 o pozitie catre dreapta
	for (int j = dimensiune; j > i; j--)
		elemente[j] = elemente[j - 1];

	//inseram noul element in pozitia corecta
	elemente[i] = element;

	//incrementam dimensiunea colectiei
	dimensiune++;
}

bool Colectie::sterge(TElem e) {
	int i = 0;
	while (i < dimensiune && rel(elemente[i], e)) {
		if (elemente[i] == e) {
			dimensiune--;
			for (int j = i; j < dimensiune; j++)
				elemente[j] = elemente[j + 1];
			return true;
		}
		i++;
	}
	return false;
}

bool Colectie::cauta(TElem elem) const {
	int i = 0;
	while (i < dimensiune && rel(elemente[i], elem)) {
		if (elemente[i] == elem)
			return true;
		i++;
	}
	return false;
}

int Colectie::nrAparitii(TElem elem) const {
	int contor = 0, i = 0;
	while (i < dimensiune && rel(elemente[i], elem)) {
		if (elemente[i] == elem)
			contor++;
		i++;
	}
	return contor;
}

int Colectie::dim() const {
	return dimensiune;
}

bool Colectie::vida() const {
	return dimensiune == 0;
}

IteratorColectie Colectie::iterator() const {
	//returnam un iterator pe vector
	return IteratorColectie(*this);
}

Colectie::~Colectie() {
	// eliberam zona de memorare alocata vectorului
	delete[] elemente;
}
