#include "Lista.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

/*
	Creeaza o lista goala
*/
Lista creeazaListaGoala() {
	Lista lista;
	lista.dimensiune = 0;
	lista.capacitate = 2;
	lista.elemente = malloc(sizeof(TipElement) * lista.capacitate);
	return lista;
}

/*
	Distruge lista
*/
void distrugeLista(Lista* lista) {
	for (int i = 0; i < lista->dimensiune; i++) {
		distrugeCheltuiala(&lista->elemente[i]);
	}
	free(lista->elemente);
	lista->elemente = NULL;
	lista->dimensiune = 0;
}

/*
	Returneaza un element din lista de pe o anumita pozitie
*/
TipElement getElement(Lista* lista, int pozitieElementInLista) {
	return lista->elemente[pozitieElementInLista];
}

/*
	Modifica elementul din lista de pe o anumita pozitie
	returneaza elementul inainte sa fie modificat
*/
TipElement setElement(Lista* lista, int pozitieElementInLista, Cheltuiala cheltuiala) {
	TipElement cheltuialaVeche = lista->elemente[pozitieElementInLista];
	lista->elemente[pozitieElementInLista] = cheltuiala;
	return cheltuialaVeche;
}

/*
	Returneaza numarul de elemente din lista
*/
int size(Lista* lista) {
	return lista->dimensiune;
}

/*
	Adauga un element in lista
*/
void adaugaElementInLista(Lista* lista, TipElement element) {
	int esteOK = asiguraCapacitate(lista);
	if (esteOK == 0) {
		lista->elemente[lista->dimensiune] = element;
		lista->dimensiune++;
	}
}

/*
	Aloca mai multa memorie daca este necesar
*/
int asiguraCapacitate(Lista* lista) {
	if (lista->dimensiune < lista->capacitate) {
		return 0;
	}
	int nouaCapacitate = lista->capacitate * 2;
	TipElement* elemente = malloc(sizeof(TipElement) * nouaCapacitate);
	if (elemente == NULL) {
		return 1;
	}
	for (int i = 0; i < nouaCapacitate; i++) {
		if (i < lista->dimensiune)
			elemente[i] = lista->elemente[i];
	}
	free(lista->elemente);
	lista->elemente = elemente;
	lista->capacitate = nouaCapacitate;
	return 0;
}

/*
	Modifica o cheltuiala din lista
*/
int modificaElementDinLista(Lista* lista, Cheltuiala cheltuialaModificata) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Cheltuiala cheltuialaCurenta = getElement(lista, i);
		if (cheltuialaCurenta.id == cheltuialaModificata.id) {
			lista->elemente[i] = cheltuialaModificata;
			distrugeCheltuiala(&cheltuialaCurenta);
			return 0;
		}
	}
	return 1;
}

/*
	Sterge un element din lista
	returneaza 0 daca cheltuiala a fost stearsa cu succes sau 1 daca nu s-a gasit
*/
int stergeElementDinLista(Lista* lista, int id) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(lista, i);
		if (cheltuiala.id == id) {
			distrugeCheltuiala(&cheltuiala);
			for (int j = i; j <= lista->dimensiune; j++) {
				lista->elemente[j] = lista->elemente[j + 1];
			}
			lista->dimensiune--;
			return 0;
		}
	}
	return 1;
}

/*
	Verifica daca exista o cheltuiala cu un anumit id dat ca parametru
*/
int cautaDupaId(Lista* lista, int id) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(lista, i);
		if (cheltuiala.id == id)
			return 10;
	}
	return 11;
}

/*
	Copiaza lista data ca parametru
	returneaza o lista care are aceleasi componente ca cea initiala
*/
Lista copiazaLista(Lista* lista) {
	Lista listaCopiata = creeazaListaGoala();
	for (int i = 0; i < size(lista); i++) {
		TipElement cheltuiala = getElement(lista, i);
		adaugaElementInLista(&listaCopiata, copiazaCheltuiala(&cheltuiala));
	}
	return listaCopiata;
}