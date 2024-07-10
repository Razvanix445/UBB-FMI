% Sa se elimine toate aparitiile elementului maxim dintr-o lista de numere intregi.

/*
maxim(l1 ... ln, Maxim) = ∅                      , daca n = 0
                          maxim(l2 ... ln, Maxim), daca l1 < Maxim
                          maxim(l2 ... ln, l1)   , daca l1 > Maxim
*/
maxim([], Maxim, Maxim).
maxim([H|T], Maxim, Rez) :- H > Maxim, maxim(T, H, Rez).
maxim([H|T], Maxim, Rez) :- H =< Maxim, maxim(T, Maxim, Rez).

/*
sterge(l1 ... ln, E) = ∅                       , daca n = 0
                       sterge(l2 ... ln, E)    , daca l1 == E
                       l1 (+) sterge(l2 ... ln), daca l1 != E
*/
sterge([], _, []).
sterge([H|T], E, Rez) :- H == E, sterge(T, E, Rez).
sterge([H|T], E, [H|Rez]) :- H \= E, sterge(T, E, Rez).

main(Lista, Rez) :- maxim(Lista, 0, Maxim), sterge(Lista, Maxim, Rez).