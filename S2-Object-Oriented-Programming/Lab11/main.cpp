/*
2. Inchiriere filme
Creati o aplicatie care permite:
� gestiunea unei liste de filme. Film: titlu, gen, anul aparitiei, actor principal
� adaugare, stergere, modificare si afisare filme
� cautare film
� filtrare filme dupa: titlu, anul aparitiei
� sortare filme dupa titlu, actor principal, anul aparitiei + gen
*/
#include <iostream>
#include "Film.h"
#include "repo.h"
#include "teste.h"
#include "consola.h"
#include "CosFilme.h"

void rulareMain() {
    Repo repository;
    //RepoNou repository{ 50 };
    RepoFile repositoryFile{ "filme.txt" };
    Validator validator;
    CosInchirieriFilme cosInchirieri;
    Service service{ repositoryFile, validator, cosInchirieri };
    UI ui{ service };
    ui.ruleaza();
}

int main()
{
    ruleazaToateTestele();
    rulareMain();

    return 0;
}