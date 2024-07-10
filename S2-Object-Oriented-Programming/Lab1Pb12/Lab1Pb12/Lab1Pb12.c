//Determina valoarea x^n (x este un numar real dat, n este un numar natural dat),
//utilizand operatii de inmultire si de ridicare la patrat.
#include <stdio.h>

int putere;
float numarDeInmultit, numarRezultat = 1;

void ridicareLaPutereNumarReal(float numarDeInmultit, int putere, float* numarRezultat) {
    while (putere > 0) {
        *numarRezultat *= numarDeInmultit;
        putere--;
    }
}

int main()
{
    printf("Introduceti un numar real de ridicat la putere: ");
    scanf("%f", &numarDeInmultit);

    printf("Introduceti puterea la care sa se ridice numarul anterior: ");
    scanf("%d", &putere);

    ridicareLaPutereNumarReal(numarDeInmultit, putere, &numarRezultat);
    
    printf("Valoarea numarului real %.2f ridicat la puterea %d este: %.2f", numarDeInmultit, putere, numarRezultat);
}