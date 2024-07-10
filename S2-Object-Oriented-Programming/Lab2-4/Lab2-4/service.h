#pragma once

#include "Lista.h"

typedef int(*FunctieComparare)(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);
typedef struct {
	Lista Cheltuieli;
} BugetFamilie;

/*
	Creeaza un buget al familiei
*/
BugetFamilie creeazaBugetFamilie();

/*
	Distruge bugetul familiei
*/
void distrugeBugetFamilie(BugetFamilie* buget);

/*
	Adauga o cheltuiala in bugetul familiei
*/
int adaugaCheltuialaInBuget(BugetFamilie* buget, int id, int zi, float suma, char* tip);

/*
	Modifica o cheltuiala existenta
*/
int modificaCheltuialaDinBuget(BugetFamilie* buget, int id, int zi, float suma, char* tip);

/*
	Sterge o cheltuiala din buget
	returneaza 0 daca cheltuiala a fost stearsa cu succes sau 1 daca nu s-a gasit
*/
int stergeCheltuialaDinBuget(BugetFamilie* buget, int id);

/*
	Filtreaza cheltuielile dupa zi
*/
Lista filtreazaDupaZi(BugetFamilie* buget, int zi);

/*
	Filtreaza cheltuielile dupa suma
*/
Lista filtreazaDupaSuma(BugetFamilie* buget, float suma);

/*
	Filtreaza cheltuielile dupa tip
*/
Lista filtreazaDupaTip(BugetFamilie* buget, char* tip);

/*
	Sorteaza cheltuielile dupa o anumita relatie
*/
void sorteaza(Lista* listaDeSortat, FunctieComparare functieDeComparare);

int comparareDupaIdCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaIdDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaId(BugetFamilie* buget, int ordineDeComparare);

int comparareDupaZiCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaZiDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaZi(BugetFamilie* buget, int ordineDeComparare);

int comparareDupaSumaCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaSumaDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaSuma(BugetFamilie* buget, int ordineDeComparare);

int comparareDupaTipCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaTipDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaTip(BugetFamilie* buget, int ordineDeComparare);