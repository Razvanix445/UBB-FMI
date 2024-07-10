#pragma once

class IteratorLP;

typedef int TElem;

//referire a clasei Nod

class Nod;

//se defineste tipul PNod ca fiind adresa unui Nod

typedef Nod* PNod;

class Nod {
public:

	friend class Lista;

	//constructor
	Nod(TElem e, PNod urm, PNod prec);
	TElem element();
	PNod urmator();
	PNod precedent();

private:

	TElem e;
	//cele doua legaturi - lista dublu inlantuita
	PNod urm, prec;
};

class Lista {
private:

	friend class IteratorLP;
	/* aici e reprezentarea */

	//primNod / ultimNod refera primul / ultimul nod din LDI
	PNod primNod;
	PNod ultimNod;

public:

	// constructor
	Lista();

	// prima pozitie din lista
	IteratorLP prim() const;

	// returnare dimensiune
	int dim() const;

	// verifica daca lista e vida
	bool vida() const;

	// returnare element de pe pozitia curenta
	//arunca exceptie daca poz nu e valid
	TElem element(IteratorLP poz) const;

	// modifica element de pe pozitia poz si returneaza vechea valoare
	//arunca exceptie daca poz nu e valid
	TElem modifica(IteratorLP poz, TElem e);

	// adaugare element la inceput
	void adaugaInceput(TElem e);

	// adaugare element la sfarsit
	void adaugaSfarsit(TElem e);

	// adaugare element dupa o pozitie poz - data de iterator
	//dupa adaugare, poz este pozitionat pe elementul adaugat
	//arunca exceptie daca poz nu e valid
	void adauga(IteratorLP& poz, TElem e);

	// sterge element de pe o pozitie poz si returneaza elementul sters
	//dupa stergere poz este pozitionat pe elementul de dupa cel sters
	//arunca exceptia daca poz nu e valid
	TElem sterge(IteratorLP& poz);

	// cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
	IteratorLP cauta(TElem e) const;

	//destructor
	~Lista();
};
