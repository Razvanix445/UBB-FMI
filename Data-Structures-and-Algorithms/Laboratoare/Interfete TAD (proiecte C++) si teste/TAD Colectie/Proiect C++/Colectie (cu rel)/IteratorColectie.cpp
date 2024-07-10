#include "IteratorColectie.h"
#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	//seteaza iteratorul pe prima pozitie din vector
	ElemCurent = c.elemente;
	ItCurent = 0;
}

TElem IteratorColectie::element() const{
	//elementul curent
	//if (!valid()) {
		//throw std::runtime_error("Iterator invalid");
	//}
	return *ElemCurent;
}

bool IteratorColectie::valid() const {
	//verifica daca iteratorul indica spre un element al vectorului
	return ItCurent < col.dim();
}

void IteratorColectie::urmator() {
	//trece la elementul urmator
	//if (!valid()) {
		//throw std::runtime_error("Iterator invalid");
	//}
	ItCurent++;
	ElemCurent++;
}

void IteratorColectie::prim() {
	//seteaza iteratorul pe prima pozitie din vector
	ElemCurent = col.elemente;
	ItCurent = 0;
}
