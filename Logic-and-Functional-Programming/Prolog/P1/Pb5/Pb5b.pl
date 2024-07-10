% Definiti un predicat care, dintr-o lista de atomi, produce o lista de perechi (atom n), unde atom apare
% in lista initiala de n ori.
% De exemplu: numar([1, 2, 1, 2, 1, 3, 1], X) va produce X = [[1, 4], [2, 2], [3, 1]].

/*
perechi(l1 ... ln) = pereche(l1) (+) perechi(l2 ... ln)
*/
perechi([], []).
perechi([H|T], [[H,Frecventa]|Rez]) :- apare(H, [H|T], Frecventa), sterge(T, H, NewRez), perechi(NewRez, Rez).

/*
apare(E, l1 ... ln, Frecventa) = 0                                 , daca n = 0
                                 apare(E, l2 ... ln, Frecventa + 1), daca l1 = E
                                 apare(E, l2 ... ln, Frecventa)    , daca l1 != E
*/
apare(_, [], 0) :- !.
apare(E, [H|T], NewFrecventa) :- H == E, !, apare(E, T, Frecventa), NewFrecventa is Frecventa + 1.
apare(E, [H|T], Frecventa) :- H \= E, apare(E, T, Frecventa).

sterge([], _, []) :- !.
sterge([H|T], E, [H|Rez]) :- H \= E, !, sterge(T, E, Rez).
sterge([H|T], E, Rez) :- sterge(T, E, Rez).