% Sa se scrie un predicat care adauga dupa 1-ul, al 2-lea, al 4-lea, al 8-lea 
% element al unei liste o valoare v data.

/*
adauga(l1 ... ln, E, PozCurenta, PozAdd) = 
            ∅                                                              , daca n = 0
            l1 ⊕ E ⊕ adauga(l2 ... ln, E, PozCurenta + 1, PozAdd * PozAdd), daca PozCurenta = PozAdd
            l1 ⊕ adauga(l2 ... ln, E, PozCurenta + 1, PozAdd )             , daca PozCurenta != PozAdd
*/
adauga([], _, _, _, []).
adauga([H|T], E, PozCurenta, PozAdd, [H|Rez]) :- PozCurenta \= PozAdd, !, PozCurenta1 is PozCurenta + 1, 
                                            adauga(T, E, PozCurenta1, PozAdd, Rez).
adauga([H|T], E, PozCurenta, PozAdd, [H,E|Rez]) :- PozCurenta == PozAdd, !, PozCurenta1 is PozCurenta + 1, 
                                            PozAdd1 is PozAdd * 2, adauga(T, E, PozCurenta1, PozAdd1, Rez).


/*
main(l1 ... ln) = adauga(l1 ... ln, E, 1, 1)
*/
main([], _, []).
main(Lista, E, Rez) :- adauga(Lista, E, 1, 1, Rez).