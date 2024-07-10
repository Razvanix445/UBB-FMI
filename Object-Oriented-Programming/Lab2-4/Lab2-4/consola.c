#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <stdio.h>

#include "consola.h"

void ui_adaugaCheltuiala(BugetFamilie* buget) {
	int id;
	int zi;
	float suma;
	char tip[30];
	printf("Introduceti id-ul cheltuielii: "); scanf_s("%d", &id);
	printf("Introduceti ziua in care s-a efectuat cheltuiala: "); scanf_s("%d", &zi);
	printf("Introduceti suma cheltuielii: "); scanf_s("%f", &suma);
	printf("Introduceti tipul cheltuielii: "); scanf_s("%s", tip, 30);
	int erori = adaugaCheltuialaInBuget(buget, id, zi, suma, tip);
	if (erori == 0) {
		printf("Cheltuiala cu id-ul %d a fost adaugata cu succes!\n", id);
	}
	else {
		printf("Date introduse gresite!\n");
	}
}

void ui_modificaCheltuiala(BugetFamilie* buget) {
	int id;
	int zi;
	float suma;
	char tip[30];
	printf("Introduceti id-ul cheltuielii: "); scanf_s("%d", &id);
	printf("Introduceti ziua in care s-a efectuat cheltuiala: "); scanf_s("%d", &zi);
	printf("Introduceti suma cheltuielii: "); scanf_s("%f", &suma);
	printf("Introduceti tipul cheltuielii: "); scanf_s("%s", tip, 30);
	int erori = modificaCheltuialaDinBuget(buget, id, zi, suma, tip);
	if (erori == 0) {
		printf("Cheltuiala cu id-ul %d a fost modificata cu succes!\n", id);
	}
	else {
		printf("Date introduse gresite!\n");
	}
}

void ui_stergeCheltuiala(BugetFamilie* buget) {
	int id = -1;
	printf("Introduceti id-ul cheltuielii pentru stergere: ");
	scanf_s("%d", &id);
	int validare = stergeCheltuialaDinBuget(buget, id);
	if (validare == 0) {
		printf("Cheltuiala cu id-ul %d a fost stearsa cu succes!\n", id);
	}
	else {
		printf("Nu exista cheltuiala cu id-ul %d!\n", id);
	}
}

void ui_afiseazaBugetulFamiliei(Lista* lista) {
	int existaCheltuieli = 0;
	for (int i = 0; i < size(lista); i++){
		Cheltuiala cheltuiala = getElement(lista, i);
		existaCheltuieli++;
		if (existaCheltuieli == 1) {
			printf("   Bugetul Familiei:\n");
		}
		printf("%d. Cheltuiala cu id-ul %d din ziua %d cu suma %.2f are tipul %s.\n", existaCheltuieli, cheltuiala.id, cheltuiala.zi, cheltuiala.suma, cheltuiala.tip);
	}
	if (existaCheltuieli == 0) {
		printf("Nu exista cheltuieli adaugate!\n");
	}
}

void ui_filtreaza(BugetFamilie* buget) {
	int reperDeFiltrare = 0;
	printf("Introduceti reperul dupa care se va filtra (zi->1; suma -> 2; tip -> 3): ");
	scanf_s("%d", &reperDeFiltrare);
	if (reperDeFiltrare == 1) {
		printf("Introduceti ziua dupa care sa se filtreze: ");
		int zi;
		scanf_s("%d", &zi);
		Lista listaFiltrataDupaZi = filtreazaDupaZi(buget, zi);
		ui_afiseazaBugetulFamiliei(&listaFiltrataDupaZi);
		distrugeLista(&listaFiltrataDupaZi);
	}
	else if (reperDeFiltrare == 2) {
		printf("Introduceti suma dupa care sa se filtreze: ");
		float suma;
		scanf_s("%f", &suma);
		Lista listaFiltrataDupaSuma = filtreazaDupaSuma(buget, suma);
		ui_afiseazaBugetulFamiliei(&listaFiltrataDupaSuma);
		distrugeLista(&listaFiltrataDupaSuma);
	}
	else if (reperDeFiltrare == 3) {
		printf("Introduceti tipul dupa care sa se filtreze: ");
		char tip[20];
		scanf_s("%s", tip, 20);
		Lista listaFiltrataDupaTip = filtreazaDupaTip(buget, tip);
		ui_afiseazaBugetulFamiliei(&listaFiltrataDupaTip);
		distrugeLista(&listaFiltrataDupaTip);
	}
	else {
		printf("%d nu este una dintre variante!\n", reperDeFiltrare);
	}
}

void ui_sorteaza(BugetFamilie* buget) {
	int reperDeSortare;
	int ordineDeSortare;
	printf("Introduceti reperul dupa care sa se sorteze (1->id, 2->zi, 3->suma, 4->tip): ");
	scanf_s("%d", &reperDeSortare);
	printf("Introduceti ordinea de sortare (0 pentru crescator, 1 pentru descrescator): ");
	scanf_s("%d", &ordineDeSortare);
	Lista cheltuieliSortate;
	switch (reperDeSortare) {
	case 1:
		cheltuieliSortate = sorteazaDupaId(buget, ordineDeSortare);
		break;
	case 2:
		cheltuieliSortate = sorteazaDupaZi(buget, ordineDeSortare);
		break;
	case 3:
		cheltuieliSortate = sorteazaDupaSuma(buget, ordineDeSortare);
		break;
	case 4:
		cheltuieliSortate = sorteazaDupaTip(buget, ordineDeSortare);
		break;
	default:
		printf("Reper invalid!\n");
	}
	ui_afiseazaBugetulFamiliei(&cheltuieliSortate);
	distrugeLista(&cheltuieliSortate);
}

void run() {
	BugetFamilie buget = creeazaBugetFamilie();
	int ruleaza = 1;
	int ok = 0;
	while (ruleaza) {
		int comanda = 0;
		printf("\n   Meniu Buget Familie:\n1. Adauga cheltuiala\n2. Modifica cheltuiala\n3. Sterge cheltuiala\n4. Afiseaza tot bugetul familiei\n5. Filtreaza bugetul familiei\n6. Ordoneaza bugetul familiei\n0. Iesire din aplicatie\n\n");
		scanf_s("%d", &comanda);
		if (ok == 0) {
			adaugaCheltuialaInBuget(&buget, 3, 1, 100.0, "mancare");
			adaugaCheltuialaInBuget(&buget, 4, 10, 15, "transport");
			adaugaCheltuialaInBuget(&buget, 1, 13, 15.5, "imbracaminte");
			adaugaCheltuialaInBuget(&buget, 2, 15, 15, "telefon&internet");
			adaugaCheltuialaInBuget(&buget, 6, 13, 15.5, "mancare");
			adaugaCheltuialaInBuget(&buget, 5, 15, 15, "transport");
			ok = 1;
		}
		switch (comanda) {
		case 1:
			ui_adaugaCheltuiala(&buget);
			break;
		case 2:
			ui_modificaCheltuiala(&buget);
			break;
		case 3:
			ui_stergeCheltuiala(&buget);
			break;
		case 4:
			ui_afiseazaBugetulFamiliei(&buget.Cheltuieli);
			break;
		case 5:
			ui_filtreaza(&buget);
			break;
		case 6:
			ui_sorteaza(&buget);
			break;
		case 0:
			ruleaza = 0;
			break;
		default:
			printf("Comanda necunoscuta!\n");
		}
	}
	distrugeBugetFamilie(&buget);
}