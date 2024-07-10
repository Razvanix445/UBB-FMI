% Sa se scrie o functie care descompune o lista de numere intr-o lista de forma 
% [lista-de-numere-pare lista-de-numere-impare] (deci lista cu doua elemente care sunt liste de intregi), 
% si va intoarce si numarul elementelor pare si impare.

/*
main(l1 ... ln, ListaPare, ListaImpare, NumarPare, NumarImpare) =
                    ∅                 , daca n = 0
                    l1 (+) ListaPare  , daca l1 mod 2 = 0
                    l1 (+) ListaImpare, daca l1 mod 2 = 1
*/

creareListe([], [], [], 0, 0).
creareListe([H|T], [H|ListaPare], ListaImpare, NumarPare1, NumarImpare) :- 
                                H mod 2 =:= 0,
                                creareListe(T, ListaPare, ListaImpare, NumarPare, NumarImpare),
                                NumarPare1 is NumarPare + 1.
creareListe([H|T], ListaPare, [H|ListaImpare], NumarPare, NumarImpare1) :- 
                                H mod 2 =:= 1,
                                creareListe(T, ListaPare, ListaImpare, NumarPare, NumarImpare),
                                NumarImpare1 is NumarImpare + 1.

main(Lista, [ListaPare, ListaImpare], NumarPare, NumarImpare) :- creareListe(Lista, ListaPare, ListaImpare, NumarPare, NumarImpare).