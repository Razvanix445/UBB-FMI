
#include <exception>

#include "IteratorLP.h"
#include "Lista.h"

#include <iostream>

using namespace std;

Nod::Nod(TElem e, PNod urm, PNod prec) {
	this->e = e;
	this->urm = urm;
	this->prec = prec;
}

TElem Nod::element() {
	return e;
}

PNod Nod::urmator() {
	return urm;
}

PNod Nod::precedent() {
	return prec;
}

// Complexitate: θ(1).
Lista::Lista() {
	this->primNod = nullptr;
	this->ultimNod = nullptr;
}

// Complexitate: θ(1).
IteratorLP Lista::prim() const {
	IteratorLP it = IteratorLP(*this);
	it.curent = primNod;
	return it;
}

// Complexitate: θ(n).
int Lista::dim() const {
	int lungime = 0;
	auto curent = primNod;
	while (curent != nullptr) {
		++lungime;
		curent = curent->urm;
	}
	return lungime;
}

// Complexitate: θ(1).
bool Lista::vida() const {
	return primNod == nullptr;
}

// Complexitate: θ(1).
TElem Lista::element(IteratorLP poz) const {
	if (!poz.valid()) {
		throw std::exception();
	}
	return poz.curent->e;
}

// Complexitate: θ(1).
TElem Lista::sterge(IteratorLP& poz) {
	if (!poz.valid()) {
		throw std::exception();
	}
	auto p = poz.curent;
	auto val = p->e;
	// daca pozitia refera la primul element din lista
	if (p == primNod) {
		primNod = p->urm;
		if (primNod != nullptr) {
			primNod->prec = nullptr;
		}
		else {
			ultimNod = nullptr;
		}
	}
	//daca pozitia refera la ultimul element din lista
	else if (p == ultimNod) {
		ultimNod = p->prec;
		if (ultimNod != nullptr) {
			ultimNod->urm = nullptr;
		}
		else {
			primNod = nullptr;
		}
	}
	//daca pozitia refera la alt element din lista
	else {
		p->prec->urm = p->urm;
		p->urm->prec = p->prec;
	}
	poz.curent = nullptr;
	delete p;
	return val;
}

// Complexitate: θ(n).
IteratorLP Lista::cauta(TElem e) const {
	IteratorLP it = IteratorLP(*this);
	while (it.valid() && it.curent->e != e)
		it.curent = it.curent->urm;
	return it;
}

// Complexitate: θ(1).
TElem Lista::modifica(IteratorLP poz, TElem e) {
	if (!poz.valid()) {
		throw std::exception();
	}
	auto vechi = poz.curent->e;
	poz.curent->e = e;
	return vechi;
}

// Complexitate: θ(1).
void Lista::adaugaInceput(TElem e) {
	//se adauga un nou nod in lista, avand informatia utila e
	PNod nod_nou = new Nod(e, nullptr, nullptr);
	//daca lista e vida
	if (primNod == nullptr) {
		primNod = nod_nou;
		ultimNod = nod_nou;
	}
	else {
		//se adauga inainte de primNod
		primNod->prec = nod_nou;
		nod_nou->urm = primNod;
		primNod = nod_nou;
	}
}

// Complexitate: θ(1).
void Lista::adaugaSfarsit(TElem e) {
	//se adauga un nou nod in lista, avand informatia utila e
	PNod nod_nou = new Nod(e, nullptr, nullptr);
	//daca lista e vida
	if (primNod == nullptr) {
		primNod = nod_nou;
		ultimNod = nod_nou;
	}
	else {
		//se adauga dupa ultimNod
		ultimNod->urm = nod_nou;
		nod_nou->prec = ultimNod;
		ultimNod = nod_nou;
	}
}

// Complexitate: θ(1).
void Lista::adauga(IteratorLP& poz, TElem e) {
	//iteratorul nu e valid
	if (!poz.valid()) {
		throw std::exception();
	}
	else {
		//nodul curent din iterator (e valid)
		PNod nodCurent = poz.curent;
		//se adauga un nou nod in lista, avand informatia utila e
		PNod nou = new Nod(e, nullptr, nullptr);
		//setam iteratorul pe nodul adaugat
		poz.curent = nou;
		//daca se adauga dupa ultim
		if (nodCurent == ultimNod) {
			ultimNod->urm = nou;
			nou->prec = ultimNod;
			ultimNod = nou;
		}
		else {//se adauga intre nodCurent si nodCurent->urm
			nou->urm = nodCurent->urm;
			nodCurent->urm->prec = nou;
			nodCurent->urm = nou;
			nou->prec = nodCurent;
		}
	}
}

// Complexitate: θ(n).
Lista::~Lista() {
	while (primNod != nullptr) {
		PNod p = primNod;
		primNod = primNod->urm;
		delete p;
	}
}
