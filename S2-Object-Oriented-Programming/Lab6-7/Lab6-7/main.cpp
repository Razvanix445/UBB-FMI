/*
2 Închiriere filme
Creați o aplicație care permite:
· gestiunea unei liste de filme. Film: titlu, gen, anul apariției, actor principal
· adăugare, ștergere, modificare și afișare filme
· căutare film
· filtrare filme după: titlu, anul apariției
· sortare filme după titlu, actor principal, anul apariției + gen
*/
#include <iostream>
#include "Film.h"
#include "repo.h"
#include "teste.h"
#include "consola.h"

void rulareMain() {
    Repo repository;
    Validator validator;
    Service service{ repository, validator };
    UI ui{ service };
    ui.ruleaza();
    
}

int main()
{
    //ruleazaToateTestele();
    rulareMain();

    _CrtDumpMemoryLeaks();
    return 0;
}