% Aranjamente de k elemente.

candidat([E|_], E).
candidat([_|T], E) :- candidat(T, E).

aranjamente(L, S, K, Rez) :- candidat(L, E),
                             aranjamente_aux(L, [E], E, S, 1, K, Rez).

aranjamente_aux(_, Rez, S, S, K, K, Rez) :- !.
aranjamente_aux(L, C, S1, S, K1, K, Rez) :- candidat(L, E),
                                            not(candidat(C, E)),
                                            S2 is S1 + E,
                                            S2 =< S,
                                            K2 is K1 + 1,
                                            K2 =< K,
                                            aranjamente_aux(L, [E|C], S2, S, K2, K, Rez).
