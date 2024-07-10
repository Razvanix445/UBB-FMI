% Sa se scrie un predicat care intoarce reuniunea a doua multimi.

/*
reuniune(l1 ... ln, p1 ... pm) = ∅                                    , daca n = 0
                                 l1 (+) reuniune(l2 ... ln, p1 ... pm)
*/
reuniune([], Set2, Set2) :- !.
reuniune([H|T], Set2, Rez) :- elementulExista(Set2, H), !, reuniune(T, Set2, Rez).
reuniune([H|T], Set2, [H|Rez]) :- reuniune(T, Set2, Rez).


elementulExista([E|_], E) :- !.
elementulExista([_|T], E) :- elementulExista(T, E).


myAppend([], Y, Y) :- !.
myAppend([H|L1], L2, [H|L3]) :- myAppend(L1, L2, L3).