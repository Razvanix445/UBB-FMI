#pragma once;

typedef int TElem;

typedef bool(*Relatie)(TElem, TElem);

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

//pentru ca Iteratorul sa poata accesa reprezentarea vectorului
class IteratorColectie;

class Colectie {

	friend class IteratorColectie;

private:
	/* aici e reprezentarea */
	//capacitate
	int capacitate;

	//dimensiune
	int dimensiune;

	//elemente
	TElem *elemente;

	Relatie rel;

public:

	//constructorul implicit
	Colectie(Relatie rel);

	//adauga un element in colectie
	void adauga(TElem element);

	//pentru redimensionare
	void redimensionare();

	//sterge o aparitie a unui element din colectie
	//returneaza adevarat daca s-a putut sterge
	bool sterge(TElem element);

	//verifica daca un element se afla in colectie
	bool cauta(TElem element) const;

	//returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem element) const;

	//intoarce numarul de elemente din colectie;
	int dim() const;

	//verifica daca colectia e vida;
	bool vida() const;

	//returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();

};

