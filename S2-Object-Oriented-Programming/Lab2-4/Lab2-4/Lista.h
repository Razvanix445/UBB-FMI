#pragma once
#include "cheltuiala.h"
typedef Cheltuiala TipElement;
typedef struct {
	TipElement* elemente;
	int dimensiune;
	int capacitate;
} Lista;

/*
	Creeaza o lista goala
*/
Lista creeazaListaGoala();

/*
	Dealoca lista 
*/
void distrugeLista(Lista* lista);

/*
	Returneaza un element din lista de pe o anumita pozitie
*/
TipElement getElement(Lista* lista, int pozitieElementInLista);

/*
	Modifica elementul din lista de pe o anumita pozitie
*/
TipElement setElement(Lista* lista, int pozitieElementInLista, Cheltuiala cheltuiala);

/*
	Returneaza numarul de elemente din lista
*/
int size(Lista* lista);

/*
	Adauga un element in lista
*/
void adaugaElementInLista(Lista* lista, TipElement element);

/*
	Aloca mai multa memorie daca este necesar
*/
int asiguraCapacitate(Lista* lista);

/*
	Modifica o cheltuiala din lista
*/
int modificaElementDinLista(Lista* lista, Cheltuiala cheltuialaModificata);

/*
	Sterge un element din lista
	returneaza 0 daca cheltuiala a fost stearsa cu succes sau 1 daca nu s-a gasit
*/
int stergeElementDinLista(Lista* lista, int id);

/*
	Verifica daca exista o cheltuiala cu un anumit id dat ca parametru
*/
int cautaDupaId(Lista* lista, int id);

/*
	Copiaza lista data ca parametru
	returneaza o lista care are aceleasi componente ca cea initiala
*/
Lista copiazaLista(Lista* lista);