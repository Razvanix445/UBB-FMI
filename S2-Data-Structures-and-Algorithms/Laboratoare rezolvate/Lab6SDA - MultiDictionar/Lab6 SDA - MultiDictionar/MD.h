#pragma once
#include <vector>
#include <iostream>
#include <utility>

typedef int TCheie;
typedef int TValoare;
typedef std::pair<TCheie, TValoare> TElem;

class IteratorMD;

class MD
{
	friend class IteratorMD;

private:
	/* aici e reprezentarea */
	TElem* tabelaDispersie;
	int* urmator;
	int capacitate;
	int primLiber;
	int dimensiune;

	int TFunctieDispersie(TCheie k);

	void rehashing();

	void redimensionare();

	int actualizarePrimLiber();

public:

	//constructorul implicit al MultiDictionarului
	MD();

	//adauga o pereche (cheie, valoare) in MD	
	void adauga(TCheie c, TValoare v);

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	std::vector<TValoare> cauta(TCheie c);

	//returneaza diferenta dintre cheia maxima si cheia minima (presupunem valori intregi)
	int diferentaCheieMaxMin();

	//returneaza numarul de perechi (cheie, valoare) din MD 
	int dim() const;

	//verifica daca MultiDictionarul e vid 
	bool vid() const;

	//se returneaza iterator pe MD
	IteratorMD iterator() const;

	//destructorul MultiDictionarului	
	~MD();
};