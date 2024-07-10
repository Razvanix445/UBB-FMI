#pragma once
typedef struct {
	int id;
	int zi;
	float suma;
	char* tip;
} Cheltuiala;

/*
	Creeaza o noua cheltuiala
*/
Cheltuiala creeazaCheltuiala(int id, int zi, float suma, char* tip);

/*
	Dealoca memoria ocupata de cheltuiala
*/
void distrugeCheltuiala(Cheltuiala* cheltuiala);

/*
	Verifica daca datele introduse de utilizator sunt corecte
*/
int valideazaCheltuiala(Cheltuiala cheltuiala);

/*
	Copiaza o cheltuiala
*/
Cheltuiala copiazaCheltuiala(Cheltuiala* cheltuiala);