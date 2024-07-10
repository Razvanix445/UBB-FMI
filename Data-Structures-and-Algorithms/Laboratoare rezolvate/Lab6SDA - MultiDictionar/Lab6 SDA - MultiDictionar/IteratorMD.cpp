#include "IteratorMD.h"
#include "MD.h"

// Complexitate: Θ(n)
IteratorMD::IteratorMD(const MD& _md) : md{ _md }
{
	for (int i = 0; i < this->md.capacitate; i++) {
		if (this->md.tabelaDispersie[i].first != -1000000) {
			this->elemente.push_back(this->md.tabelaDispersie[i]);
		}
	}
	this->curent = 0;
}

// Complexitate: Θ(1)
void IteratorMD::prim()
{
	this->curent = 0;
}

// Complexitate: Θ(1)
void IteratorMD::urmator()
{
	if (this->valid()) {
		this->curent += 1;
	}
	else {
		throw std::exception();
	}
}

// Complexitate: Θ(1)
bool IteratorMD::valid() const
{
	if (this->curent < this->elemente.size()) {
		return true;
	}
	else {
		return false;
	}
}

// Complexitate: Θ(1)
TElem IteratorMD::element() const
{
	if (this->valid()) {
		return this->elemente[this->curent];
	}
	else {
		throw std::exception();
	}
}
