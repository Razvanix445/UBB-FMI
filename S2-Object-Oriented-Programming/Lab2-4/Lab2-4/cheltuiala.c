#include "cheltuiala.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

/*
	Creeaza o noua cheltuiala
*/
Cheltuiala creeazaCheltuiala(int id, int zi, float suma, char* tip) {
	Cheltuiala cheltuiala;
	size_t numarDeCaractere = 0;
	if (tip != NULL)
		numarDeCaractere = strlen(tip) + 1;
	cheltuiala.tip = malloc(sizeof(char) * numarDeCaractere);
	if (cheltuiala.tip != NULL)
		if (tip != NULL)
			strcpy_s(cheltuiala.tip, numarDeCaractere, tip);
	cheltuiala.id = id;
	cheltuiala.zi = zi;
	cheltuiala.suma = suma;
	return cheltuiala;
}

/*
	Dealoca memoria ocupata de cheltuiala
*/
void distrugeCheltuiala(Cheltuiala* cheltuiala) {
	free(cheltuiala->tip);
	cheltuiala->tip = NULL;
	cheltuiala->id = -1;
	cheltuiala->zi = -1;
	cheltuiala->suma = -1;
}

/*
	Verifica daca datele introduse de utilizator sunt corecte
*/
int valideazaCheltuiala(Cheltuiala cheltuiala) {
	if (cheltuiala.id < 0) {
		return 1;
	}
	if (cheltuiala.zi < 0 || cheltuiala.zi > 31) {
		return 2;
	}
	if (cheltuiala.suma < 0) {
		return 3;
	}
	if ((strcmp(cheltuiala.tip, "mancare") != 0) && (strcmp(cheltuiala.tip, "transport") != 0) && (strcmp(cheltuiala.tip, "telefon&internet") != 0) && (strcmp(cheltuiala.tip, "imbracaminte") != 0) && (strcmp(cheltuiala.tip, "altele") != 0)) {
		return 4;
	}
	return 0;
}

/*
	Copiaza o cheltuiala
*/
Cheltuiala copiazaCheltuiala(Cheltuiala* cheltuiala) {
	return creeazaCheltuiala(cheltuiala->id, cheltuiala->zi, cheltuiala->suma, cheltuiala->tip);
}