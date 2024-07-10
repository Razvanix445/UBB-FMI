% Sa se scrie un predicat care intoarce diferenta a doua multimi.

/*
diferentaMultimi(l1 ... ln, p1 ... pm) = ∅                                              , n = 0
                                         diferentaMultimi(l2 ... ln, p1 p2 ... pm)      , daca l1 ∈ p1 ... pm
                                         l1 ⊕ diferentaMultimi(l2 ... ln, p1 p2 ... pm), daca l1 ∉ p1 ... pm
*/
%diferentaMultimi(i, i, i) / diferentaMultimi(i, i, o)
diferentaMultimi([], _, []).
diferentaMultimi([H|T], Multime, Rez) :- seAflaInMultime(Multime, H), diferentaMultimi(T, Multime, Rez), !.
diferentaMultimi([H|T], Multime, [H|Rez]) :- diferentaMultimi(T, Multime, Rez).

/*
seAflaInMultime(l1 ... ln, E) = true                         , daca l1 = E
                                false                        , daca n = 0
                                seAflaInMultime(l2 ... ln, E), l1 != E
*/

seAflaInMultime([E|_], E) :- !.
seAflaInMultime([_|T], E) :- seAflaInMultime(T, E).