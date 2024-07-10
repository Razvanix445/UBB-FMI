#pragma once
#include "service.h"
#include "Film.h"

class UI {
private:

	Service& service;

	/*
	Citeste datele de la tastatura si adauga film nou in repository
	arunca exceptie daca parametrii nu sunt valizi sau titlul filmului este deja existent
	*/
	void ui_adaugaFilm();

	/*
	Sterge un film din repository
	*/
	void ui_stergeFilm();

	/*
	Modifica un film din repository
	*/
	void ui_modificaFilm();

	/*
	Afiseaza lista de filme din repository
	*/
	void ui_afiseazaFilme(const vector<Film>& filme);

	/*
	Cauta un film dupa titlul acestuia
	*/
	void ui_cautaFilm();

	/*
	Filtreaza filme dupa gen / anul aparitiei
	*/
	void ui_filtrare();

	/*
	Sorteaza filme dupa actorul principal / gen + anul aparitiei
	*/
	void ui_sortare();

	/*
	Adauga un film in cosul de inchirieri daca exista titlul filmului
	*/
	void ui_adaugaFilmInCosInchirieri();

	/*
	Sterge un film din cosul de inchirieri daca exista titlul filmului
	*/
	void ui_stergeFilmDinCosInchirieri();

	/*
	Adauga un numar dat de filme in cosul de inchirieri
	*/
	void ui_umpleRandomCosInchirieri();

	/*
	Exporta filmele existente in cosul de inchirieri intr-un fisier CSV cu numele dat de utilizator
	*/
	void ui_exportaInFisier();
	
	/*
	Face operatia de undo la functionalitatile de adaugare, stergere, modificare
	*/
	void ui_undo();

public:
	UI(Service& service) :service{ service } {}
	//nu permitem copierea obiectelor
	UI(const UI& other) = delete;

	~UI();

	void ruleaza();

};