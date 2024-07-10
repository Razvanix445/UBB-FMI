#include "service.h"

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>

/*
	Creeaza un buget al familiei
*/
BugetFamilie creeazaBugetFamilie() {
	BugetFamilie buget;
	buget.Cheltuieli = creeazaListaGoala();
	return buget;
}

/*
	Distruge bugetul familiei
*/
void distrugeBugetFamilie(BugetFamilie* buget) {
	distrugeLista(&buget->Cheltuieli);
}

/*
	Adauga o cheltuiala in bugetul familiei
*/
int adaugaCheltuialaInBuget(BugetFamilie* buget, int id, int zi, float suma, char* tip) {
	Cheltuiala cheltuiala = creeazaCheltuiala(id, zi, suma, tip);
	int erori = valideazaCheltuiala(cheltuiala);
	if (erori != 0) {
		distrugeCheltuiala(&cheltuiala);
		return erori;
	}
	adaugaElementInLista(&buget->Cheltuieli, cheltuiala);
	return 0;
}

/*
	Modifica o cheltuiala existenta
*/
int modificaCheltuialaDinBuget(BugetFamilie* buget, int id, int zi, float suma, char* tip) {
	if (cautaDupaId(&buget->Cheltuieli, id) == 11) {
		return 1;
	}
	Cheltuiala cheltuialaModificata = creeazaCheltuiala(id, zi, suma, tip);
	int erori = valideazaCheltuiala(cheltuialaModificata);
	if (erori != 0) {
		distrugeCheltuiala(&cheltuialaModificata);
		return erori;
	}
	modificaElementDinLista(&buget->Cheltuieli, cheltuialaModificata);
	return 0;
}

/*
	Sterge o cheltuiala din bugetul familiei
	returneaza 0 daca cheltuiala a fost stearsa cu succes sau 1 daca nu s-a gasit
*/
int stergeCheltuialaDinBuget(BugetFamilie* buget, int id) {
	int validare = stergeElementDinLista(&buget->Cheltuieli, id);
	return validare;
}

/*
	Filtreaza cheltuielile dupa zi
*/
Lista filtreazaDupaZi(BugetFamilie* buget, int zi) {
	Lista cheltuieliFiltrate = creeazaListaGoala();
	for (int i = 0; i < buget->Cheltuieli.dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(&buget->Cheltuieli, i);
		if (cheltuiala.zi == zi) {
			adaugaElementInLista(&cheltuieliFiltrate, copiazaCheltuiala(&cheltuiala));
		}
	}
	return cheltuieliFiltrate;
}

Lista filtreazaDupaSuma(BugetFamilie* buget, float suma) {
	Lista cheltuieliFiltrate = creeazaListaGoala();
	for (int i = 0; i < buget->Cheltuieli.dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(&buget->Cheltuieli, i);
		if ((cheltuiala.suma - suma) < 0.00001) {
			adaugaElementInLista(&cheltuieliFiltrate, copiazaCheltuiala(&cheltuiala));
		}
	}
	return cheltuieliFiltrate;
}

Lista filtreazaDupaTip(BugetFamilie* buget, char* tip) {
	if (tip == NULL || strlen(tip) == 0) {
		return copiazaLista(&buget->Cheltuieli);
	}
	Lista cheltuieliFiltrate = creeazaListaGoala();
	for (int i = 0; i < buget->Cheltuieli.dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(&buget->Cheltuieli, i);
		if (strstr(cheltuiala.tip, tip) != NULL) {
			adaugaElementInLista(&cheltuieliFiltrate, copiazaCheltuiala(&cheltuiala));
		}
	}
	return cheltuieliFiltrate;
}

void sorteaza(Lista* listaDeSortat, FunctieComparare functieDeComparare) {
	for (int i = 0; i < size(listaDeSortat); i++) 
		for (int j = i + 1; j < size(listaDeSortat); j++) {
			Cheltuiala cheltuiala1 = getElement(listaDeSortat, i);
			Cheltuiala cheltuiala2 = getElement(listaDeSortat, j);
			if (functieDeComparare(&cheltuiala1, &cheltuiala2) > 0) {
				setElement(listaDeSortat, i, cheltuiala2);
				setElement(listaDeSortat, j, cheltuiala1);
			}
		}
}

int comparareDupaIdCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return (cheltuiala1->id > cheltuiala2->id);
}

int comparareDupaIdDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return (cheltuiala1->id < cheltuiala2->id);
}

Lista sorteazaDupaId(BugetFamilie* buget, int ordineDeComparare) {
	Lista listaDeSortat = copiazaLista(&buget->Cheltuieli);
	if (ordineDeComparare == 0) {
		sorteaza(&listaDeSortat, comparareDupaIdCrescator);
	}
	else {
		sorteaza(&listaDeSortat, comparareDupaIdDescrescator);
	}
	return listaDeSortat;
}

int comparareDupaZiCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return (cheltuiala1->zi > cheltuiala2->zi);
}

int comparareDupaZiDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return (cheltuiala1->zi < cheltuiala2->zi);
}

Lista sorteazaDupaZi(BugetFamilie* buget, int ordineDeComparare) {
	Lista listaDeSortat = copiazaLista(&buget->Cheltuieli);
	if (ordineDeComparare == 0) {
		sorteaza(&listaDeSortat, comparareDupaZiCrescator);
	}
	else {
		sorteaza(&listaDeSortat, comparareDupaZiDescrescator);
	}
	return listaDeSortat;
}

int comparareDupaSumaCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return (cheltuiala1->suma > cheltuiala2->suma);
}

int comparareDupaSumaDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return (cheltuiala1->suma < cheltuiala2->suma);
}

Lista sorteazaDupaSuma(BugetFamilie* buget, int ordineDeComparare) {
	Lista listaDeSortat = copiazaLista(&buget->Cheltuieli);
	if (ordineDeComparare == 0) {
		sorteaza(&listaDeSortat, comparareDupaSumaCrescator);
	}
	else {
		sorteaza(&listaDeSortat, comparareDupaSumaDescrescator);
	}
	return listaDeSortat;
}

int comparareDupaTipCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return strcmp(cheltuiala1->tip, cheltuiala2->tip);
}

int comparareDupaTipDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2) {
	return strcmp(cheltuiala2->tip, cheltuiala1->tip);
}

Lista sorteazaDupaTip(BugetFamilie* buget, int ordineDeComparare) {
	Lista listaDeSortat = copiazaLista(&buget->Cheltuieli);
	if (ordineDeComparare == 0) {
		sorteaza(&listaDeSortat, comparareDupaTipCrescator);
	}
	else {
		sorteaza(&listaDeSortat, comparareDupaTipDescrescator);
	}
	return listaDeSortat;
}