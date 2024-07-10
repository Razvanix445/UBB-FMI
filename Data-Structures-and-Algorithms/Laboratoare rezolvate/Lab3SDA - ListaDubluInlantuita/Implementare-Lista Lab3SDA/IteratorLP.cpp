#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

IteratorLP::IteratorLP(const Lista& l) :lista(l) {
	this->curent = l.primNod;
}

void IteratorLP::prim() {
	this->curent = lista.primNod;
}

void IteratorLP::urmator() {
	if (curent == nullptr)
		throw std::exception();
	this->curent = this->curent->urmator();
}

bool IteratorLP::valid() const {
	return (curent != nullptr);
}

TElem IteratorLP::element() const {
	if (curent == nullptr)
		throw std::exception();
	return curent->element();
}
