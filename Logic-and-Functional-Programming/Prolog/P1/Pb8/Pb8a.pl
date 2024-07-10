% Sa se scrie un predicat care testeaza daca o lista este multime.

/*
esteMultime(l1 ... ln) = true, daca n = 0
                         esteMultime(l2 ... ln), daca apare(l1 ... ln, l1, Frecventa) = 1
                         false, daca apare(l1 ... ln, l1, Frecventa) = 0
*/
esteMultime([]).
esteMultime([H|T]) :- apare([H|T], H, Frecventa), Frecventa = 1, esteMultime(T).

/*
apare(l1 ... ln, E, Frecventa) = 0, daca n = 0
                                 apare(l2 ... ln, E, Frecventa + 1), l1 == E
                                 apare(l2 ... ln, E, Frecventa), l1 != E
*/
apare([], _, 0).
apare([H|T], E, NewFrecventa) :- H = E, apare(T, E, Frecventa), NewFrecventa is Frecventa + 1.
apare([H|T], E, Frecventa) :- H \= E, apare(T, E, Frecventa).