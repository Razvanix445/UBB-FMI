#pragma once
#include "Lista.h"

class Lista;

class IteratorLP {
	friend class Lista;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container

	IteratorLP(const Lista& l);

	//contine o referinta catre containerul pe care il itereaza
	const Lista& lista;

	/* aici e reprezentarea  specifica a iteratorului */
	PNod curent;

public:

	//reseteaza pozitia iteratorului la inceputul containerului
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	void urmator();

	//verifica daca iteratorul e valid (indica un element al containerului)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	TElem element() const;

};
