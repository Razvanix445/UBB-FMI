% Sa se scrie un predicat care substituie intr-o lista un element printr-o alta lista.

/*
substituie(l1 ... ln, E, Lista) = ∅, daca n = 0
                                  Lista (+) substituie(l2 ... ln), daca l1 = E
                                  E (+) substituie(l2 ... ln), daca l1 != E
*/

substituie([], _, _, []).
substituie([H|T], E, Lista, [H|Rez]) :- H \= E, substituie(T, E, Lista, Rez).
substituie([H|T], E, Lista, [Lista|Rez]) :- H == E, substituie(T, E, Lista, Rez).