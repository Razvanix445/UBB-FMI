#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "teste.h"

void testCreeazaCheltuiala() {
	Cheltuiala cheltuiala = creeazaCheltuiala(1, 10, 100.5, "mancare");
	assert(cheltuiala.id == 1);
	assert(cheltuiala.zi == 10);
	assert(cheltuiala.suma == 100.5);
	assert(strcmp(cheltuiala.tip, "mancare") == 0);
	distrugeCheltuiala(&cheltuiala);
	Cheltuiala cheltuialaTipInvalid = creeazaCheltuiala(1, 10, 100.5, "");
	assert(cheltuialaTipInvalid.id == 1);
	assert(cheltuialaTipInvalid.zi == 10);
	assert(cheltuialaTipInvalid.suma == 100.5);
	assert(cheltuialaTipInvalid.tip[0] == '\0');
	distrugeCheltuiala(&cheltuialaTipInvalid);
}

void testCheltuialaInvalida() {
	Cheltuiala cheltuiala_invalida = creeazaCheltuiala(1, 10, 100.5, "");
	assert(cheltuiala_invalida.id == 1);
	assert(cheltuiala_invalida.zi == 10);
	assert(cheltuiala_invalida.suma == 100.5);
	assert(strcmp(cheltuiala_invalida.tip, "") == 0);
	distrugeCheltuiala(&cheltuiala_invalida);
}

void testCopiazaCheltuiala() {
	Cheltuiala cheltuiala_valida = creeazaCheltuiala(1, 10, 100.5, "mancare");
	Cheltuiala cheltuialaCopiata = copiazaCheltuiala(&cheltuiala_valida);
	assert(valideazaCheltuiala(cheltuiala_valida) == 0);
	distrugeCheltuiala(&cheltuiala_valida);
	distrugeCheltuiala(&cheltuialaCopiata);
}

void testValideazaCheltuiala() {
	Cheltuiala cheltuialaValida = creeazaCheltuiala(-1, 10, 100.5, "mancare");
	assert(valideazaCheltuiala(cheltuialaValida) == 1);
	distrugeCheltuiala(&cheltuialaValida);

	Cheltuiala cheltuialaIdInvalid = creeazaCheltuiala(1, -10, 100.5, "mancare");
	assert(valideazaCheltuiala(cheltuialaIdInvalid) == 2);
	distrugeCheltuiala(&cheltuialaIdInvalid);

	Cheltuiala cheltuialaZiInvalida = creeazaCheltuiala(1, 32, 100.5, "mancare");
	assert(valideazaCheltuiala(cheltuialaZiInvalida) == 2);
	distrugeCheltuiala(&cheltuialaZiInvalida);

	Cheltuiala cheltuialaSumaInvalida = creeazaCheltuiala(1, 30, -1, "mancare");
	assert(valideazaCheltuiala(cheltuialaSumaInvalida) == 3);
	distrugeCheltuiala(&cheltuialaSumaInvalida);

	Cheltuiala cheltuialaTipInvalid = creeazaCheltuiala(1, 10, 100, "mnscare");
	assert(valideazaCheltuiala(cheltuialaTipInvalid) == 4);
	distrugeCheltuiala(&cheltuialaTipInvalid);
}


void testCreeazaListaGoala() {
	Lista lista = creeazaListaGoala();
	assert(lista.dimensiune == 0);
	assert(lista.capacitate == 2);
	assert(lista.elemente != NULL);
	distrugeLista(&lista);
}

void testDistrugeLista() {
	Lista listaDistruge = creeazaListaGoala();
	adaugaElementInLista(&listaDistruge, creeazaCheltuiala(1, 13, 15.5, "mancare"));
	adaugaElementInLista(&listaDistruge, creeazaCheltuiala(2, 15, 15, "transport"));
	distrugeLista(&listaDistruge);
	assert(listaDistruge.dimensiune == 0);
	assert(listaDistruge.elemente == NULL);
}

void testGetElement() {
	Lista listaGetElement = creeazaListaGoala();
	adaugaElementInLista(&listaGetElement, creeazaCheltuiala(1, 13, 50, "mancare"));
	adaugaElementInLista(&listaGetElement, creeazaCheltuiala(2, 15, 30, "transport"));
	Cheltuiala cheltuialaGetElement = getElement(&listaGetElement, 0);
	assert(cheltuialaGetElement.id == 1);
	assert(strcmp(cheltuialaGetElement.tip, "mancare") == 0);
	assert(cheltuialaGetElement.suma == 50);
	distrugeLista(&listaGetElement);
}

void testSetElement() {
	Lista listaSetElement = creeazaListaGoala();
	adaugaElementInLista(&listaSetElement, creeazaCheltuiala(1, 13, 50, "mancare"));
	adaugaElementInLista(&listaSetElement, creeazaCheltuiala(2, 15, 15, "transport"));
	Cheltuiala cheltuialaNouaSetElement = creeazaCheltuiala(3, 30, 20, "telefon&internet");
	Cheltuiala cheltuialaVecheSetElement = setElement(&listaSetElement, 0, cheltuialaNouaSetElement);
	assert(cheltuialaVecheSetElement.id == 1);
	assert(strcmp(cheltuialaVecheSetElement.tip, "mancare") == 0);
	assert(cheltuialaVecheSetElement.suma == 50);
	Cheltuiala cheltuialaSetElement = getElement(&listaSetElement, 0);
	assert(cheltuialaSetElement.id == 3);
	assert(strcmp(cheltuialaSetElement.tip, "telefon&internet") == 0);
	assert(cheltuialaSetElement.suma == 20);
	assert(size(&listaSetElement) == 2);
	distrugeCheltuiala(&cheltuialaVecheSetElement);
	distrugeLista(&listaSetElement);
}

void testAdaugaModificaStergeLista() {
	Lista listaAdaugare = creeazaListaGoala();
	adaugaElementInLista(&listaAdaugare, creeazaCheltuiala(1, 13, 15.5, "mancare"));
	adaugaElementInLista(&listaAdaugare, creeazaCheltuiala(2, 15, 15, "transport"));
	adaugaElementInLista(&listaAdaugare, creeazaCheltuiala(3, 13, 15.5, "mancare"));
	adaugaElementInLista(&listaAdaugare, creeazaCheltuiala(4, 15, 15, "transport"));
	adaugaElementInLista(&listaAdaugare, creeazaCheltuiala(5, 13, 15.5, "mancare"));
	adaugaElementInLista(&listaAdaugare, creeazaCheltuiala(6, 15, 15, "transport"));
	Cheltuiala cheltuialaAdaugare = creeazaCheltuiala(6, 10, 100.5, "mancare");
	int existaModifica = modificaElementDinLista(&listaAdaugare, cheltuialaAdaugare);
	assert(existaModifica == 0);
	Cheltuiala cheltuialaAdaugare2 = creeazaCheltuiala(7, 10, 100.5, "mancare");
	int nuExistaModifica = modificaElementDinLista(&listaAdaugare, cheltuialaAdaugare2);
	assert(nuExistaModifica == 1);
	int existaCauta = cautaDupaId(&listaAdaugare, 6);
	assert(existaCauta == 10);
	int nuExistaCauta = cautaDupaId(&listaAdaugare, 7);
	assert(nuExistaCauta == 11);
	int existaStergere = stergeElementDinLista(&listaAdaugare, 6);
	assert(existaStergere == 0);
	int nuExistaStergere = stergeElementDinLista(&listaAdaugare, 7);
	assert(nuExistaStergere == 1);
	distrugeCheltuiala(&cheltuialaAdaugare2);
	distrugeLista(&listaAdaugare);
}

void testCopiazaLista() {
	Lista lista1 = creeazaListaGoala();
	Lista listaCopiata1 = copiazaLista(&lista1);
	assert(size(&lista1) == size(&listaCopiata1));

	Cheltuiala cheltuiala1 = creeazaCheltuiala(1, 10, 125.5, "mancare");
	Cheltuiala cheltuiala2 = creeazaCheltuiala(2, 13, 50, "transport");
	Lista lista2 = creeazaListaGoala();
	adaugaElementInLista(&lista2, cheltuiala1);
	adaugaElementInLista(&lista2, cheltuiala2);
	Lista listaCopiata2 = copiazaLista(&lista2);
	assert(size(&lista2) == size(&listaCopiata2));
	assert(getElement(&lista2, 0).zi == getElement(&listaCopiata2, 0).zi);
	assert(strcmp(getElement(&lista2, 1).tip, getElement(&listaCopiata2, 1).tip) == 0);
	distrugeLista(&listaCopiata2);
	distrugeLista(&lista2);
	distrugeLista(&listaCopiata1);
	distrugeLista(&lista1);
}


void testCreeazaBugetFamilie() {
	BugetFamilie buget = creeazaBugetFamilie();
	assert(buget.Cheltuieli.dimensiune == 0);
	assert(buget.Cheltuieli.capacitate == 2);
	assert(buget.Cheltuieli.elemente != NULL);
	distrugeBugetFamilie(&buget);
}

void testAdaugaCheltuialaInBuget() {
	BugetFamilie bugetAdaugare = creeazaBugetFamilie();
	assert(adaugaCheltuialaInBuget(&bugetAdaugare, 1, 1, 100.0, "mancare") == 0);
	assert(bugetAdaugare.Cheltuieli.dimensiune == 1);
	assert(bugetAdaugare.Cheltuieli.elemente[0].id == 1);
	assert(bugetAdaugare.Cheltuieli.elemente[0].zi == 1);
	assert(bugetAdaugare.Cheltuieli.elemente[0].suma == 100.0);
	assert(strcmp(bugetAdaugare.Cheltuieli.elemente[0].tip, "mancare") == 0);
	distrugeBugetFamilie(&bugetAdaugare);
}

void testModificaCheltuialaDinBuget() {
	BugetFamilie bugetModificare = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetModificare, 1, 1, 100.0, "mancare");
	assert(modificaCheltuialaDinBuget(&bugetModificare, 1, 2, 200.0, "transport") == 0);
	assert(bugetModificare.Cheltuieli.dimensiune == 1);
	assert(bugetModificare.Cheltuieli.elemente[0].id == 1);
	assert(bugetModificare.Cheltuieli.elemente[0].zi == 2);
	assert(bugetModificare.Cheltuieli.elemente[0].suma == 200.0);
	assert(strcmp(bugetModificare.Cheltuieli.elemente[0].tip, "transport") == 0);
	assert(modificaCheltuialaDinBuget(&bugetModificare, 2, 3, 300.0, "chirii") == 1);
	assert(modificaCheltuialaDinBuget(&bugetModificare, 1, 2, 200.0, "transports") == 4);
	distrugeBugetFamilie(&bugetModificare);
}

void testStergeCheltuialaDinBuget() {
	BugetFamilie bugetStergere = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetStergere, 1, 10, 100.0, "alimente");
	adaugaCheltuialaInBuget(&bugetStergere, 2, 20, 200.0, "transport");
	assert(stergeCheltuialaDinBuget(&bugetStergere, 1) == 1);
	assert(bugetStergere.Cheltuieli.dimensiune == 1);
	assert(stergeCheltuialaDinBuget(&bugetStergere, 3) == 1);
	distrugeBugetFamilie(&bugetStergere);
}

void testFiltreazaDupaZi() {
	BugetFamilie bugetZi = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetZi, 1, 10, 50, "mancare");
	adaugaCheltuialaInBuget(&bugetZi, 2, 20, 100, "imbracaminte");
	adaugaCheltuialaInBuget(&bugetZi, 3, 10, 200, "transport");
	Lista listaFiltrataZi = filtreazaDupaZi(&bugetZi, 10);
	assert(listaFiltrataZi.dimensiune == 2);
	assert(getElement(&listaFiltrataZi, 0).zi == 10);
	assert(getElement(&listaFiltrataZi, 0).suma == 50);
	assert(getElement(&listaFiltrataZi, 1).zi == 10);
	assert(getElement(&listaFiltrataZi, 1).suma == 200);
	distrugeLista(&listaFiltrataZi);
	distrugeBugetFamilie(&bugetZi);
}

void testFiltreazaDupaSuma() {
	BugetFamilie bugetSuma = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetSuma, 1, 10, 50, "mancare");
	adaugaCheltuialaInBuget(&bugetSuma, 2, 20, 100, "imbracaminte");
	adaugaCheltuialaInBuget(&bugetSuma, 3, 10, 50, "transport");
	Lista listaFiltrataSuma = filtreazaDupaSuma(&bugetSuma, 50);
	assert(listaFiltrataSuma.dimensiune == 2);
	assert(getElement(&listaFiltrataSuma, 0).suma == 50);
	assert(getElement(&listaFiltrataSuma, 1).suma == 50);
	distrugeLista(&listaFiltrataSuma);
	distrugeBugetFamilie(&bugetSuma);
}

void testFiltreazaDupaTip() {
	BugetFamilie bugetTip = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetTip, 1, 10, 50, "mancare");
	adaugaCheltuialaInBuget(&bugetTip, 2, 20, 100, "mancare");
	adaugaCheltuialaInBuget(&bugetTip, 3, 10, 50, "transport");
	Lista listaFiltrataTip = filtreazaDupaTip(&bugetTip, "mancare");
	assert(listaFiltrataTip.dimensiune == 2);
	assert(strcmp(getElement(&listaFiltrataTip, 0).tip, "mancare") == 0);
	assert(strcmp(getElement(&listaFiltrataTip, 1).tip, "mancare") == 0);
	distrugeLista(&listaFiltrataTip);
	distrugeBugetFamilie(&bugetTip);
	Lista cheltuieliFiltrateDupaTip2 = filtreazaDupaTip(&bugetTip, NULL);
	assert(size(&cheltuieliFiltrateDupaTip2) == size(&bugetTip.Cheltuieli));
	distrugeLista(&cheltuieliFiltrateDupaTip2);
}

void testSorteazaDupaId() {
	BugetFamilie bugetSortare = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetSortare, 3, 1, 100.0, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 4, 10, 15, "transport");
	adaugaCheltuialaInBuget(&bugetSortare, 1, 13, 15.5, "imbracaminte");
	adaugaCheltuialaInBuget(&bugetSortare, 2, 15, 15, "telefon&internet");
	adaugaCheltuialaInBuget(&bugetSortare, 6, 13, 15.5, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 5, 15, 15, "transport");

	Lista cheltuieliDupaId;
	cheltuieliDupaId = sorteazaDupaId(&bugetSortare, 0);
	for (int i = 0; i < size(&cheltuieliDupaId); i++) {
		Cheltuiala cheltuiala = getElement(&cheltuieliDupaId, i);
		assert(cheltuiala.id == i + 1);
	}
	distrugeLista(&cheltuieliDupaId);
	cheltuieliDupaId = sorteazaDupaId(&bugetSortare, 1);
	for (int i = 0; i < size(&cheltuieliDupaId); i++) {
		Cheltuiala cheltuiala = getElement(&cheltuieliDupaId, i);
		assert(cheltuiala.id == size(&cheltuieliDupaId) - i);
	}
	distrugeLista(&cheltuieliDupaId);
	distrugeBugetFamilie(&bugetSortare);
}

void testSorteazaDupaZi() {
	BugetFamilie bugetSortare = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetSortare, 3, 1, 100.0, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 4, 10, 15, "transport");
	adaugaCheltuialaInBuget(&bugetSortare, 1, 13, 15.5, "imbracaminte");
	adaugaCheltuialaInBuget(&bugetSortare, 2, 15, 15, "telefon&internet");
	adaugaCheltuialaInBuget(&bugetSortare, 6, 13, 15.5, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 5, 15, 15, "transport");

	Lista cheltuieliDupaZi;
	cheltuieliDupaZi = sorteazaDupaZi(&bugetSortare, 0);
	assert(cheltuieliDupaZi.elemente[0].zi == 1);
	assert(cheltuieliDupaZi.elemente[1].zi == 10);
	assert(cheltuieliDupaZi.elemente[2].zi == 13);
	assert(cheltuieliDupaZi.elemente[3].zi == 13);
	assert(cheltuieliDupaZi.elemente[4].zi == 15);
	assert(cheltuieliDupaZi.elemente[5].zi == 15);
	distrugeLista(&cheltuieliDupaZi);
	cheltuieliDupaZi = sorteazaDupaZi(&bugetSortare, 1);
	assert(cheltuieliDupaZi.elemente[0].zi == 15);
	assert(cheltuieliDupaZi.elemente[1].zi == 15);
	assert(cheltuieliDupaZi.elemente[2].zi == 13);
	assert(cheltuieliDupaZi.elemente[3].zi == 13);
	assert(cheltuieliDupaZi.elemente[4].zi == 10);
	assert(cheltuieliDupaZi.elemente[5].zi == 1);
	distrugeLista(&cheltuieliDupaZi);
	distrugeBugetFamilie(&bugetSortare);
}

void testSorteazaDupaSuma() {
	BugetFamilie bugetSortare = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetSortare, 3, 1, 100.0, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 4, 10, 15, "transport");
	adaugaCheltuialaInBuget(&bugetSortare, 1, 13, 15.5, "imbracaminte");
	adaugaCheltuialaInBuget(&bugetSortare, 2, 15, 15, "telefon&internet");
	adaugaCheltuialaInBuget(&bugetSortare, 6, 13, 15.5, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 5, 15, 15, "transport");

	double epsilon = 0.0001;
	Lista cheltuieliDupaSuma;
	cheltuieliDupaSuma = sorteazaDupaSuma(&bugetSortare, 0);
	assert((cheltuieliDupaSuma.elemente[0].suma - 15) < epsilon);
	assert((cheltuieliDupaSuma.elemente[1].suma - 15) < epsilon);
	assert((cheltuieliDupaSuma.elemente[2].suma - 15) < epsilon);
	assert((cheltuieliDupaSuma.elemente[3].suma - 15.5) < epsilon);
	assert((cheltuieliDupaSuma.elemente[4].suma - 15.5) < epsilon);
	assert((cheltuieliDupaSuma.elemente[5].suma - 100) < epsilon);
	distrugeLista(&cheltuieliDupaSuma);
	cheltuieliDupaSuma = sorteazaDupaSuma(&bugetSortare, 1);
	assert((cheltuieliDupaSuma.elemente[0].suma - 100) < epsilon);
	assert((cheltuieliDupaSuma.elemente[1].suma - 15.5) < epsilon);
	assert((cheltuieliDupaSuma.elemente[2].suma - 15.5) < epsilon);
	assert((cheltuieliDupaSuma.elemente[3].suma - 15) < epsilon);
	assert((cheltuieliDupaSuma.elemente[4].suma - 15) < epsilon);
	assert((cheltuieliDupaSuma.elemente[5].suma - 15) < epsilon);
	distrugeLista(&cheltuieliDupaSuma);
	distrugeBugetFamilie(&bugetSortare);
}

void testSorteazaDupaTip() {
	BugetFamilie bugetSortare = creeazaBugetFamilie();
	adaugaCheltuialaInBuget(&bugetSortare, 3, 1, 100.0, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 4, 10, 15, "transport");
	adaugaCheltuialaInBuget(&bugetSortare, 1, 13, 15.5, "imbracaminte");
	adaugaCheltuialaInBuget(&bugetSortare, 2, 15, 15, "telefon&internet");
	adaugaCheltuialaInBuget(&bugetSortare, 6, 13, 15.5, "mancare");
	adaugaCheltuialaInBuget(&bugetSortare, 5, 15, 15, "transport");
	
	Lista cheltuieliDupaTip;
	cheltuieliDupaTip = sorteazaDupaTip(&bugetSortare, 0);
	assert(strcmp(cheltuieliDupaTip.elemente[0].tip, "imbracaminte") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[1].tip, "mancare") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[2].tip, "mancare") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[3].tip, "telefon&internet") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[4].tip, "transport") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[5].tip, "transport") == 0);
	distrugeLista(&cheltuieliDupaTip);
	cheltuieliDupaTip = sorteazaDupaTip(&bugetSortare, 1);
	assert(strcmp(cheltuieliDupaTip.elemente[0].tip, "transport") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[1].tip, "transport") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[2].tip, "telefon&internet") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[3].tip, "mancare") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[4].tip, "mancare") == 0);
	assert(strcmp(cheltuieliDupaTip.elemente[5].tip, "imbracaminte") == 0);
	distrugeLista(&cheltuieliDupaTip);
	distrugeLista(&cheltuieliDupaTip);
	distrugeBugetFamilie(&bugetSortare);
}


void ruleazaTesteCheltuiala() {
	testCreeazaCheltuiala();
	testCheltuialaInvalida();
	testCopiazaCheltuiala();
	testValideazaCheltuiala();
}

void ruleazaTesteLista() {
	testCreeazaListaGoala();
	testDistrugeLista();
	testGetElement();
	testSetElement();
	testAdaugaModificaStergeLista();
	testCopiazaLista();
}

void ruleazaTesteService() {
	testCreeazaBugetFamilie();
	testAdaugaCheltuialaInBuget();
	testModificaCheltuialaDinBuget();
	testStergeCheltuialaDinBuget();
	testFiltreazaDupaZi();
	testFiltreazaDupaSuma();
	testFiltreazaDupaTip();
	testSorteazaDupaId();
	testSorteazaDupaZi();
	testSorteazaDupaSuma();
	testSorteazaDupaTip();
}

void ruleazaToateTestele() {
	ruleazaTesteCheltuiala();
	ruleazaTesteLista();
	ruleazaTesteService();
}