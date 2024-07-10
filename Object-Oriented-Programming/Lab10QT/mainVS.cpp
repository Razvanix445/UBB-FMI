/*
2. Inchiriere filme
Creati o aplicatie care permite:
· gestiunea unei liste de filme. Film: titlu, gen, anul aparitiei, actor principal
· adaugare, stergere, modificare si afisare filme
· cautare film
· filtrare filme dupa: titlu, anul aparitiei
· sortare filme dupa titlu, actor principal, anul aparitiei + gen
*/
#include <iostream>
#include "Film.h"
#include "repo.h"
#include "teste.h"
#include "consola.h"
#include "CosFilme.h"

void rulareMain() {
    Repo repository;
    RepoFile repositoryFile{ "filme.txt" };
    Validator validator;
    CosInchirieriFilme cosInchirieri;
    Service service{ repository, validator, cosInchirieri };
    UI ui{ service };
    ui.ruleaza();
}

int mainVS()
{
    ruleazaToateTestele();
    rulareMain();

    _CrtDumpMemoryLeaks();

    return 0;
}