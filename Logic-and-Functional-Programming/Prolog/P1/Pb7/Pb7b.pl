% Sa se scrie un predicat care, primind o lista, intoarce multimea tuturor perechilor din lista.
% De exemplu: [a, b, c, d] => [[a, b], [a, c], [a, d], [b, c], [b, d], [c, d]].

/*
main(l1 ... ln, Rez) = (l2 ... ln, perechi(l1, l2 ... ln))
*/
main([], []).
%main([H|T], Rez) :- perechi(H, T, Perechi), main(T, RestRez), append(Perechi, RestRez, Rez).
main([H|T], Rez) :- findall([_, _], perechi(H, T, Perechi), Rez).

/*
perechi(l1 ... ln, E, Rez) = perechi(l2 ... ln, E, [E, l1] | Rez)
*/
perechi([], _, []).
perechi([H|T], E, [[E, H]|Rez]) :- perechi(T, E, Rez).

myAppend([], Y, Y).
myAppend([H|L1], L2, [H|L3]) :- myAppend(L1, L2, L3).