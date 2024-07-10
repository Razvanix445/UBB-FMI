
// 14. Se citesc mai multe numere de la tastatura, in baza 2. Sa se afiseze aceste numere in baza 8.

#include <stdio.h>
#include <string.h>

// declararea procedurii, definita in limbajul de asamblare
int return_rezultat(int, char sir[]);

// main entry point
int main()
{
    int nr;
    printf("Cate numere vor fi citite de la tastatura?\n");
    scanf("%d", &nr);

    for (int i = 0; i < nr; i ++)
    {
        char sir_biti[31] = "";
        printf("Introduceti un numar in baza 2: ");
        scanf("%s", sir_biti);
        int len = strlen(sir_biti);                     // determin cati biti sunt in numarul binar
        int rez = return_rezultat(len, sir_biti);
        printf("Numarul in baza 8 este: %o\n", rez);
    }
    
    return 0;
}
