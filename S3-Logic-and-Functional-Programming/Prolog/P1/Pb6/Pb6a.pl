% Sa se scrie un predicat care elimina dintr-o lista toate elementele care se repeta.
% De exemplu: [1, 2, 1, 4, 1, 3 ,4] => [2, 4].

/*
main(l1 ... ln, Rez) = 
*/
main([], []).
main([H|T], Rez) :- apare([H|T], H, Frecventa), Frecventa \= 1, sterge(T, H, NewRez), main(NewRez, Rez).
main([H|T], [H|Rez]) :- apare([H|T], H, Frecventa), Frecventa = 1, main(T, Rez).

/*
apare(l1 ... ln, E, Frecventa) = 0                                 , daca n = 0
                                 apare(l2 ... ln, E, Frecventa + 1), daca l1 = E
                                 apare(l2 ... ln, E, Frecventa)    , daca l1 != E
*/
apare([], _, 0) :- !.
apare([H|T], E, NewFrecventa) :- H == E, !, apare(T, E, Frecventa), NewFrecventa is Frecventa + 1.
apare([H|T], E, Frecventa) :- H \= E, apare(T, E, Frecventa).

/*
sterge(l1 ... ln, E, Rez) = sterge(l2 ... ln, E, Rez), daca l1 = E
                            l1 (+) sterge(l2 ... ln, E, Rez), daca l1 != E
*/
sterge([], _, []) :- !.
sterge([H|T], E, Rez) :- H == E, !, sterge(T, E, Rez).
sterge([H|T], E, [H|Rez]) :- H\=E, !, sterge(T, E, Rez).