#include "consola.h"
#include "Film.h"
#include <iostream>
#include <string>

using std::cin;
using std::cout;

constexpr int Optiune_Adaugare_Film = 1;
constexpr int Optiune_Stergere_Film = 2;
constexpr int Optiune_Modificare_Film = 3;
constexpr int Optiune_Afisare_Filme = 4;
constexpr int Optiune_Cautare_Film = 5;
constexpr int Optiune_Filtrare_Film = 6;
constexpr int Optiune_Sortare_Film = 7;
constexpr int Optiune_Adaugare_In_Cos_Inchirieri = 8;
constexpr int Optiune_Stergere_Din_Cos_Inchirieri = 9;
constexpr int Optiune_Golire_Cos_Inchirieri = 10;
constexpr int Optiune_Umple_Random_Cos_Inchirieri = 11;
constexpr int Optiune_Afisare_Cos_Inchirieri = 12;
constexpr int Optiune_Exportare_In_Fisier_CSV = 13;
constexpr int Optiune_Undo = 14;
constexpr int Optiune_Iesire = 0;

void UI::ui_adaugaFilm() {
	int anAparitie;
	string titluFilm, genFilm, actorPrincipal;
	cin.ignore();
	cout << "Introduceti titlul filmului: ";
	getline(cin, titluFilm);
	cout << "Introduceti genul filmului: ";
	getline(cin, genFilm);
	cout << "Introduceti anul aparitiei filmului: ";
	cin >> anAparitie;
	cin.ignore();
	cout << "Introduceti numele actorului care joaca in rolul principal: ";
	getline(cin, actorPrincipal);
	service.adaugaFilm(titluFilm, genFilm, anAparitie, actorPrincipal);
	cout << "Filmul " << titluFilm << " a fost adaugat cu succes!\n";
}

void UI::ui_stergeFilm() {
	string titluFilm;
	cin.ignore();
	cout << "Introduceti titlul filmului pentru stergere: ";
	getline(cin, titluFilm);
	service.stergeFilm(titluFilm);
	cout << "Filmul " << titluFilm << " a fost sters cu succes!\n";
}

void UI::ui_modificaFilm() {
	int anAparitie;
	string titluFilm, genFilm, actorPrincipal;
	cin.ignore();
	cout << "Introduceti titlul filmului pentru modificat: ";
	getline(cin, titluFilm);
	cout << "Introduceti noul gen al filmului: ";
	getline(cin, genFilm);
	cout << "Introduceti noul an al aparitiei filmului: ";
	cin >> anAparitie;
	cin.ignore();
	cout << "Introduceti numele actorului care joaca in rolul principal: ";
	getline(cin, actorPrincipal);
	service.modificaFilm(titluFilm, genFilm, anAparitie, actorPrincipal);
	cout << "Filmul " << titluFilm << " a fost modificat cu succes!\n";
}

void UI::ui_afiseazaFilme(const vector<Film>& filme) {
	if (filme.size() == 0) {
		cout << "\nNu exista filme in lista!\n";
		return;
	}
	cout << "Filme: \n";
	cout << "=================================\n";
	int i = 0;
	for (const auto& film : filme) {
		i++;
		cout << i << ". " << film.getTitluFilm() << "; gen: " << film.getGenFilm() << "; aparut in anul: " << film.getAnAparitie() << "; Actor in rolul principal: " << film.getActorPrincipal() << "\n";
	}
	cout << "=================================\n";
}

void UI::ui_cautaFilm() {
	string titluFilm;
	cin.ignore();
	cout << "Introduceti titlul filmului cautat: ";
	getline(cin, titluFilm);
	const Film& film = service.cautaFilm(titluFilm);
	cout << film.getTitluFilm() << "; gen: " << film.getGenFilm() << "; aparut in anul: " << film.getAnAparitie() << "; Actor in rolul principal: " << film.getActorPrincipal() << "\n";
}

void UI::ui_filtrare() {
	// 1 -> gen / 2 -> anul aparitiei / 0 -> iesire (sunt optiunile utilizatorului)
	int reperFiltrare;
	cin.ignore();
	cout << "Alegeti reperul de filtrare (1 -> gen / 2 -> anul aparitiei / 0 -> iesire): ";
	cin >> reperFiltrare;
	if (reperFiltrare == 1) {
		string genFilm;
		cout << "Introduceti genul dupa care sa se filtreze: ";
		cin.ignore();
		getline(cin, genFilm);
		ui_afiseazaFilme(service.filtreazaDupaGen(genFilm));
	}
	else if (reperFiltrare == 2) {
		int anAparitie;
		cout << "Introduceti anul dupa care sa se filtreze: ";
		cin >> anAparitie;
		ui_afiseazaFilme(service.filtreazaDupaAnAparitie(anAparitie));
	}
	else if (reperFiltrare == 0) return;
	else {
		cout << "Reper de filtrare necunoscut!\n";
		ui_filtrare();
	}
}

void UI::ui_sortare() {
	// 1 -> titlu / 2 -> actor principal / 3 -> anul aparitiei + gen / 0 -> iesire (pentru reperul de sortare) -> prima optiune a utilizatorului
	// 1 -> crescator / 2 -> descrescator (pentru ordinea de sortare) -> a doua optiune a utilizatorului
	int reperSortare, ordineSortare;
	cin.ignore();
	cout << "Alegeti reperul dupa care sa se sorteze (1 -> titlu / 2 -> actor principal / 3 -> anul aparitiei + gen / 0 -> iesire): ";
	cin >> reperSortare;
	if (reperSortare == 0) return;
	if (reperSortare != 1 && reperSortare != 2 && reperSortare != 3) {
		cout << "Reper invalid! Va rugam sa introduceti 1, 2 sau 3!\n";
		ui_sortare();
	}
	cout << "Alegeti ordinea de sortare (1 -> crescator / 2 -> descrescator): ";
	cin >> ordineSortare;
	if (ordineSortare != 1 && ordineSortare != 2) {
		cout << "Ordine de sortare invalida! Va rugam sa introduceti 1 sau 2!\n";
		ui_sortare();
	}
	const vector<Film>& filme = service.sorteaza(reperSortare, ordineSortare);
	ui_afiseazaFilme(filme);
}

void UI::ui_adaugaFilmInCosInchirieri() {
	string titluFilm;
	cin.ignore();
	cout << "Introduceti titlul filmului pentru a-l adauga in cosul de inchirieri: ";
	getline(cin, titluFilm);
	ui_afiseazaFilme(service.adaugaFilmInCosInchirieri(titluFilm));
}

void UI::ui_stergeFilmDinCosInchirieri() {
	string titluFilm;
	cin.ignore();
	cout << "Introduceti titlul filmului pentru a-l sterge din cosul de inchirieri: ";
	getline(cin, titluFilm);
	ui_afiseazaFilme(service.stergeFilmDinCosInchirieri(titluFilm));
}

void UI::ui_umpleRandomCosInchirieri() {
	int dimensiune;
	cin.ignore();
	cout << "Introduceti numarul de filme de adaugat in cosul de inchirieri: ";
	cin >> dimensiune;
	ui_afiseazaFilme(service.umpleRandomCosInchirieri(dimensiune));
}

void UI::ui_exportaInFisier() {
	string numeFisier, formatFisier;
	cin.ignore();
	cout << "Introduceti tipul de fisier in care sa se introduca cosul (CSV / HTML): ";
	getline(cin, formatFisier);
	cout << "Introduceti numele unui fisier pentru a salva continutul cosului de inchirieri: ";
	getline(cin, numeFisier);
	const vector<Film>& filme = service.getAllCosInchirieri();
	if (formatFisier == "CSV")
		service.exportaInCSV(numeFisier, filme);
	else if (formatFisier == "HTML")
		service.exportaInHTML(numeFisier, filme);
	else
		cout << "Tip fisier invalid!\n";
}

void UI::ui_undo() {
	service.undo();
	ui_afiseazaFilme(service.getAll());
}

void UI::ruleaza() {
	/*
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Epic Romance", 1997, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Science-Fiction", 2022, "Sam Worthington");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	*/
	while (true) {
		cout << "\n=================================\n";
		cout << "   Meniu inchiriere filme: \n";
		cout << "1. Adauga film.\n";
		cout << "2. Sterge film.\n";
		cout << "3. Modifica film.\n";
		cout << "4. Afiseaza toate filmele disponibile pentru inchiriere.\n";
		cout << "5. Cauta un film dupa titlu.\n";
		cout << "6. Filtreaza filme dupa titlu / anul aparitiei.\n";
		cout << "7. Sorteaza filme dupa titlu / actorul principal / anul aparitiei + gen.\n";
		cout << "8. Adauga film in cosul de inchirieri.\n";
		cout << "9. Sterge film din cosul de inchirieri.\n";
		cout << "10.Goleste cosul de inchirieri.\n";
		cout << "11.Adauga un numar de filme aleatoare in cosul de inchirieri.\n";
		cout << "12.Afiseaza cosul de inchirieri.\n";
		cout << "13.Exporta cosul in fisier CSV / HTML.\n";
		cout << "14.Undo.\n";
		cout << "0. Iesire din aplicatie.\n";
		cout << "=================================\n";
		int comanda;
		cout << ">>>";
		cin >> comanda;
		try {
			switch (comanda) {
			case Optiune_Adaugare_Film:
				ui_adaugaFilm();
				break;
			case Optiune_Stergere_Film:
				ui_stergeFilm();
				break;
			case Optiune_Modificare_Film:
				ui_modificaFilm();
				break;
			case Optiune_Afisare_Filme:
				ui_afiseazaFilme(service.getAll());
				break;
			case Optiune_Cautare_Film:
				ui_cautaFilm();
				break;
			case Optiune_Filtrare_Film:
				ui_filtrare();
				break;
			case Optiune_Sortare_Film:
				ui_sortare();
				break;
			case Optiune_Adaugare_In_Cos_Inchirieri:
				ui_adaugaFilmInCosInchirieri();
				break;
			case Optiune_Stergere_Din_Cos_Inchirieri:
				ui_stergeFilmDinCosInchirieri();
				break;
			case Optiune_Golire_Cos_Inchirieri:
				ui_afiseazaFilme(service.golesteCosInchirieri());
				break;
			case Optiune_Umple_Random_Cos_Inchirieri:
				ui_umpleRandomCosInchirieri();
				break;
			case Optiune_Afisare_Cos_Inchirieri:
				ui_afiseazaFilme(service.getAllCosInchirieri());
				break;
			case Optiune_Exportare_In_Fisier_CSV:
				ui_exportaInFisier();
				break;
			case Optiune_Undo:
				ui_undo();
				break;
			case Optiune_Iesire:
				return;
			default:
				cout << "Comanda necunoscuta!\n";
			}
			cout << '\n';
		}
		catch (const ExceptieRepo& exceptie) {
			cout << exceptie << "\n";
		}
		catch (const ExceptieValidare& exceptie) {
			cout << exceptie << "\n";
		}
	}
}

UI::~UI()
{
}