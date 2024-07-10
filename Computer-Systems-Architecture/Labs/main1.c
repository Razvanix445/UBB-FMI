#include<stdio.h>
//Se citesc mai multe numere de la tastatura, in baza 2. Sa se afiseze aceste numere in baza 8.

//declararea procedurii, definita in limbajul de asamblare
int return_rezultat();

//main entry point
int main()
{
    int nr = 0;
    char string[31];
    
    printf("Cate numere vor fi citite de la tastatura?\n");
    scanf("%s", nr);
    scanf("%s", string);
    for (int i = 0; i < nr; i ++)
    {
        printf("Introduceti un numar in baza 2: ")
        int rez = return_rezultat(string);
        printf("Numarul in baza 8 este: %o\n", rez);
        char string[31] = "";
        if (i < nr - 1)
            scanf("%s", string);
    }
    
    return 0;
}