#include "IteratorColectie.h"
#include "Colectie.h"

// Complexitate: BC = WC = AC = θ(1)
IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	//seteaza iteratorul pe prima pozitie din vector
	ElemCurent = c.elemente;
	ItCurent = 0;
}

// Complexitate: BC = WC = AC = θ(1)
TElem IteratorColectie::element() const {
	//elementul curent
	return col.elemente[ItCurent];
}

// Complexitate: BC = WC = AC = θ(1)
bool IteratorColectie::valid() const {
	//verifica daca iteratorul indica spre un element al vectorului
	return ItCurent < col.dim();
}

// Complexitate: BC = WC = AC = θ(1)
void IteratorColectie::urmator() {
	//trece la elementul urmator
	ItCurent++;
	ElemCurent++;
}

// Complexitate: BC = WC = AC = θ(1)
void IteratorColectie::prim() {
	//seteaza iteratorul pe prima pozitie din vector
	ElemCurent = col.elemente;
	ItCurent = 0;
}
