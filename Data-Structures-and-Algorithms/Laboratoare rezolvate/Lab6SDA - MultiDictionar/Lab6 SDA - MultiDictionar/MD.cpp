#pragma once
#include "MD.h"
#include "IteratorMD.h"

// Complexitate: Θ(1)
int MD::TFunctieDispersie(TCheie k) {
	int pozitieTabela = k % this->capacitate;
	if (pozitieTabela > 0) {
		return pozitieTabela;
	}
	else {
		return -pozitieTabela;
	}
}

// Complexitate: Θ(n), n - capacitate
MD::MD() {
	this->capacitate = 13;
	this->primLiber = 0;
	this->dimensiune = 0;
	this->tabelaDispersie = new TElem[this->capacitate];
	this->urmator = new int[this->capacitate];
	for (int i = 0; i < this->capacitate; i++) {
		this->urmator[i] = -1;
		this->tabelaDispersie[i].first = -1000000;
		this->tabelaDispersie[i].second = -1000000;
	}
}

// Complexitate: O(n*n), n - capacitate; (redimensionare() * rehashing())
void MD::adauga(TCheie c, TValoare v) {
	int pos = this->TFunctieDispersie(c);
	if (this->dimensiune == this->capacitate) {
		this->redimensionare();
		this->rehashing();
	}
	if (this->tabelaDispersie[pos].first == -1000000) {
		this->tabelaDispersie[pos].first = c;
		this->tabelaDispersie[pos].second = v;
		this->urmator[pos] = -1;
		this->dimensiune += 1;
	}
	else {
		this->tabelaDispersie[this->primLiber].first = c;
		this->tabelaDispersie[this->primLiber].second = v;
		this->urmator[this->primLiber] = -1;
		while (this->urmator[pos] != -1) {
			pos = this->urmator[pos];
		}
		this->urmator[pos] = this->primLiber;
		this->dimensiune += 1;
	}
	if (this->tabelaDispersie[primLiber].first != -1000000) {
		this->primLiber = this->actualizarePrimLiber();
	}
}

// Complexitate: O(n), n - capacitate
bool MD::sterge(TCheie c, TValoare v) {
	int pos = this->TFunctieDispersie(c);
	int posAnterior = -1;

	for(int i = 0; i < this->capacitate && posAnterior == -1; i++)
		if (this->urmator[i] == pos)
			posAnterior = i;

	while (pos != -1 && !(this->tabelaDispersie[pos].first == c && this->tabelaDispersie[pos].second == v)) {
		posAnterior = pos;
		pos = this->urmator[pos];
	}

	if (pos == -1) {
		return false;
	}
	else {
		bool gata = false;
		int posUrmatoare;
		int posCurenta;
		do {
			posUrmatoare = this->urmator[pos];
			posCurenta = pos;
			while (posUrmatoare != -1 && this->TFunctieDispersie(this->tabelaDispersie[posUrmatoare].first) != pos) {
				posCurenta = posUrmatoare;
				posUrmatoare = this->urmator[posUrmatoare];
			}
			if (posUrmatoare == -1) {
				gata = true;
			}
			else {
				this->tabelaDispersie[pos] = this->tabelaDispersie[posUrmatoare];
				posAnterior = posCurenta;
				pos = posUrmatoare;
			}
		} while (!gata);
		if (posAnterior != -1) {
			this->urmator[posAnterior] = this->urmator[pos];
		}
		this->tabelaDispersie[pos].first = -1000000;
		this->tabelaDispersie[pos].second = -1000000;
		this->urmator[pos] = -1;
		if (this->primLiber > pos) {
			this->primLiber = pos;
		}
		this->dimensiune = this->dimensiune - 1;
		return true;
	}
}

// Complexitate: O(n), n - capacitate
std::vector<TValoare> MD::cauta(TCheie c) {
	std::vector<TValoare> valori;
	int pos = this->TFunctieDispersie(c);
	while (pos != -1) {
		if (this->tabelaDispersie[pos].first == c) {
			valori.push_back(this->tabelaDispersie[pos].second);
		}
		pos = this->urmator[pos];
	}
	return valori;
}

// Complexitate: Θ(1)
int MD::diferentaCheieMaxMin() {
	if (vid()) {
		return -1;
	}
	TCheie cheieMinima;
	TCheie cheieMaxima;
	for (int i = 0; i < this->capacitate; i++) {
		if (tabelaDispersie[i].first != -1000000) {
			cheieMinima = tabelaDispersie[i].first;
			cheieMaxima = tabelaDispersie[i].first;
			break;
		}
	}
	for (int i = 0; i < this->capacitate; i++) {
		if (this->tabelaDispersie[i].first < cheieMinima && this->tabelaDispersie[i].first != -1000000)
			cheieMinima = this->tabelaDispersie[i].first;
		else if (this->tabelaDispersie[i].first > cheieMaxima && this->tabelaDispersie[i].first != -1000000) {
			cheieMaxima = this->tabelaDispersie[i].first;
		}
	}
	return cheieMaxima - cheieMinima;
}

/*
// Complexitate: O(n+m), n - capacitate, m - pozitia primei chei in tabela
int diferentaCheieMaxMin(md):
	preconditii: md - MultiDictionar
	postconditii: se returneaza (cheieMaxima - cheieMinima)
	
	Daca vid(md) atunci
		returneaza -1
	Pentru i <- 0, md.capacitate executa
		Daca md.tabelaDispersie[i].first != -1000000 atunci
			cheieMinima <- md.tabelaDispersie[i].first
			cheieMaxima <- md.tabelaDispersie[i].first
			break;
		SfDaca
	SfPentru
	Pentru  i <- 0, md.capacitate executa
		Daca md.tabelaDispersie[i].first < cheieMinima si md.tabelaDispersie[i].first != -1000000 atunci
			cheieMinima <- md.tabelaDispersie[i].first
		altfel
			cheieMaxima <- md.tabelaDispersie[i].first > cheieMinima && md.tabelaDispersie[i].first != -1000000
		SfDaca
	SfPentru
	returneaza cheieMaxima - cheieMinima
*/

// Complexitate: Θ(1)
int MD::dim() const {
	return this->dimensiune;
}

// Complexitate: Θ(1)
bool MD::vid() const {
	return this->dimensiune == 0;
}

void MD::rehashing() {
	TElem* tabelaDispersieVeche = new TElem[this->capacitate];
	for (int i = 0; i < this->capacitate; i++) {
		tabelaDispersieVeche[i] = this->tabelaDispersie[i];
	}
	int dimensiuneVeche = this->dimensiune;
	for (int i = 0; i < this->capacitate; i++) {
		this->tabelaDispersie[i].first = -1000000;
		this->tabelaDispersie[i].second = -1000000;
		this->urmator[i] = -1;
	}
	this->primLiber = this->actualizarePrimLiber();
	this->dimensiune = 0;
	for (int i = 0; i < dimensiuneVeche; i++) {
		this->adauga(tabelaDispersieVeche[i].first, tabelaDispersieVeche[i].second);
	}
}

// Complexitate: Θ(n), n - capacitate
void MD::redimensionare()
{
	TElem* tabelaDispersieNoua = new TElem[this->capacitate * 2];
	int* urmatorNou = new int[this->capacitate * 2];
	for (int i = 0; i < this->capacitate; i++) {
		tabelaDispersieNoua[i] = this->tabelaDispersie[i];
		urmatorNou[i] = this->urmator[i];
	}
	for (int i = this->capacitate; i < this->capacitate * 2; i++) {
		tabelaDispersieNoua[i].first = -1000000;
		tabelaDispersieNoua[i].second = -1000000;
		urmatorNou[i] = -1;
	}
	this->primLiber = this->capacitate;
	this->capacitate *= 2;
	delete[] this->tabelaDispersie;
	delete[] this->urmator;
	this->tabelaDispersie = tabelaDispersieNoua;
	this->urmator = urmatorNou;
}

// Complexitate: O(n), n - capacitate
int MD::actualizarePrimLiber()
{
	for (int i = 0; i < this->capacitate; i++) {
		if (this->tabelaDispersie[i].first == -1000000) {
			return i;
		}
	}
}

// Complexitate: Θ(n), n - capacitate
IteratorMD MD::iterator() const
{
	return IteratorMD(*this);
}

// Complexitate: Θ(1)
MD::~MD()
{
	delete[] this->tabelaDispersie;
	delete[] this->urmator;
}
