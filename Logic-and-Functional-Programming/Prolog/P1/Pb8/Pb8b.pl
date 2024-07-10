% Sa se scrie un predicat care elimina primele 3 aparitii ale unui element intr-o lista. 
% Daca elementul apare mai putin de 3 ori, se va elimina de cate ori apare.

/*
elimina(l1 ... ln) = apare(l1 ... ln, l1, Frecventa), sterge(l1 ... ln, l1, Frecventa)
*/
elimina([], [], []).
elimina([H|T], Rez, [H|ElementeSterse]) :- not(elementulExista(ElementeSterse, H)), !, 
                                           sterge([H|T], H, 0, NewRez),  
                                           elimina(NewRez, Rez, ElementeSterse).
elimina([H|T], Rez, ElementeSterse) :- elementulExista(ElementeSterse, H), !, 
                                       elimina(T, Rez, ElementeSterse).

main([], [], []).
main(Lista, Rez, ElementeSterse) :- elimina(Lista, Rez, ElementeSterse).

/*
sterge(l1 ... ln, E, NrStergeri) = []                                     , daca n = 0
                                   sterge(l2 ... ln, E, NrStergeri + 1)   , daca l1 == E si NrStergeri <= 3
                                   l1 (+) sterge(l2 ... ln, E, NrStergeri), daca l1 != E
*/
sterge([], _, _, []) :- !.
sterge([H|T], E, NrStergeri, Rez) :- H == E, NrStergeri < 3, !, 
                                     NewNrStergeri is NrStergeri + 1,
                                     sterge(T, E, NewNrStergeri, Rez).
sterge([H|T], E, NrStergeri, [H|Rez]) :- sterge(T, E, NrStergeri, Rez).

/*
apare(l1 ... ln, E, Frecventa) = []                                , daca n = 0
                                 apare(l2 ... ln, E, Frecventa + 1), daca l1 == E
                                 apare(l2 ... ln, E, Frecventa)    , daca l1 != E
*/
apare([], _, 0) :- !.
apare([H|T], E, NewFrecventa) :- E == H, !, apare(T, E, Frecventa), NewFrecventa is Frecventa + 1.
apare([H|T], E, Frecventa) :- E \= H, !, apare(T, E, Frecventa).

elementulExista([E|_], E) :- !.
elementulExista([_|T], E) :- elementulExista(T, E).

myAppend([], Y, Y) :- !.
myAppend([X|L1], L2, [X|L3]) :- myAppend(L1, L2, L3).