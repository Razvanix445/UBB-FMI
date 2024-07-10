#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;

bool rel(TElem e1, TElem e2) {
	return e1 <= e2;
}

// Complexitate: BC = WC = AC = θ(n)
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

// Complexitate: BC = WC = AC = θ(1)
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

// Complexitate: BC = θ(1), WC = θ(n), AC = θ(n)
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

// Complexitate: BC = WC = AC = θ(n)
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

// Complexitate: BC = θ(1), WC = θ(n), AC = θ(n)
bool Colectie::cauta(TElem elem) const {
	int i = 0;
	while (i < dimensiune && rel(elemente[i], elem)) {
		if (elemente[i] == elem)
			return true;
		i++;
	}
	return false;
}

// Complexitate: BC = θ(1), WC = θ(n), AC = θ(n)
int Colectie::nrAparitii(TElem elem) const {
	int contor = 0, i = 0;
	while (i < dimensiune && rel(elemente[i], elem)) {
		if (elemente[i] == elem)
			contor++;
		i++;
	}
	return contor;
}

// Complexitate: BC = WC = AC = θ(n)
int Colectie::eliminaAparitii(int nr, TElem elem) {
	if (nr < 0) {
		throw std::invalid_argument("Numar negativ!");
	}
	int contor = 0, i = 0;
	while (i < dimensiune && rel(elemente[i], elem)) {
		if (elemente[i] == elem && contor <= nr) {
			dimensiune--;
			for (int j = i; j < dimensiune; j++)
				elemente[j] = elemente[j + 1];
			contor++;
		}
		i++;
	}
	return contor;
}

/*
Subalgoritm eliminaAparitii(C, nr, elem)
	pre: nr - intreg, elem ∈ TElem, C - colectie
	post: C' = C - elem (elem va fi eliminat de nr ori din C)
	Daca nr < 0 atunci
		@arunca "Numar negativ!"
	SfDaca
	contor <- 0
	i <- 0
	CatTimp i < c.dimensiune executa
		Daca c.elemente[i] = elem si contor <= nr atunci
			c.dimensiune <- c.dimensiune - 1
			Pentru j = i, dimensiune executa
				c.elemente[j] <- c.elemente[j + 1]
			contor <- contor + 1
		SfDaca
		i <- i + 1
	SfCatTimp
	returneaza contor
*/

// Complexitate: BC = WC = AC = θ(1)
int Colectie::dim() const {
	return dimensiune;
}

// Complexitate: BC = WC = AC = θ(1)
bool Colectie::vida() const {
	return dimensiune == 0;
}

// Complexitate: BC = WC = AC = θ(1)
IteratorColectie Colectie::iterator() const {
	//returnam un iterator pe vector
	return IteratorColectie(*this);
}

// Complexitate: BC = WC = AC = θ(1)
Colectie::~Colectie() {
	// eliberam zona de memorare alocata vectorului
	delete[] elemente;
}
