% Sa se scrie un predicat care sterge toate aparitiile unui anumit atom dintr-o lista.

/*
sterge(l1 ... ln, E) = ∅                       , daca n = 0
                       sterge(l2 ... ln)       , daca l1 != E
                       l1 (+) sterge(l2 ... ln), daca l1 = E
*/
sterge([], _, []) :- !.
sterge([H|T], E, [H|Rez]) :- H \= E, !, sterge(T, E, Rez).
sterge([H|T], E, Rez) :- sterge(T, E, Rez).