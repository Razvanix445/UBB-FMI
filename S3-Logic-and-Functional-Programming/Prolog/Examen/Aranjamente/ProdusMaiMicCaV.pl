% Aranjamente pentru care produsul elementelor este mai mic decat o valoare V data.

candidat(E, [E|_]).
candidat(E, [_|T]) :- candidat(E, T).

aranjamente(L, V, K, Rez) :- candidat(E, L),
                             aranjamente_aux(L, [E], V, K, 1, E, Rez).

aranjamente_aux(_, C, V, K, K, Produs, C) :- Produs < V, !.
aranjamente_aux(L, C, V, K, Lg, Produs, Rez) :- candidat(E, L),
                                                not(candidat(E, C)),
                                                Produs1 is Produs * E,
                                                Produs1 < V,
                                                Lg1 is Lg + 1,
                                                Lg1 =< K,
                                                aranjamente_aux(L, [E|C], V, K, Lg1, Produs1, Rez).