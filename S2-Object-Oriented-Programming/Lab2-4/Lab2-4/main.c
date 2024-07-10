/*
3. Buget de familie

Creati o aplicatie care permite gestiunea bugetului pentru o familie. Aplicatia trebuie sa
stocheze cheltuielile pe o luna. Fiecare cheltuiala are: zi (ziua in care s-a efectuat), suma,
tip (mancare, transport, telefon & internet, imbracaminte, altele)
Aplicatia permite:
a) Adaugarea de cheltuieli noi (cand familia cumpara ceva sau plateste o factura)
b) Modificarea unei cheltuieli existente (ziua, tipul, suma)
c) Stergere cheltuiala
d) Vizualizare lista de cheltuieli filtrat dupa o proprietate (suma, ziua, tipul)
e) Vizualizare lista de cheltuieli ordonat dupa suma sau tip (crescator/descrescator)
*/

#include <stdio.h>
#include <stdlib.h>
#include <crtdbg.h>
#include "consola.h"
#include "teste.h"

int main()
{
    run();
    ruleazaToateTestele();
    _CrtDumpMemoryLeaks();

    return 0;
}