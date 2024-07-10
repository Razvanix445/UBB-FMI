% Aranjamente cu numar par de elemente si suma numar impar.

candidat([E|_], E).
candidat([_|T], E) :- candidat(T, E).

aranjamente(L, Rez) :- candidat(L, E),
                             aranjamente_aux(L, [E], 0, 1, Rez).

aranjamente_aux(_, Rez, S, K, Rez) :- S mod 2 =:= 1,
                                      K mod 2 =:= 0.
aranjamente_aux(L, C, S, K, Rez) :- candidat(L, E),
                                    not(candidat(C, E)),
                                    S1 is S + E,
                                    K1 is K + 1,
                                    aranjamente_aux(L, [E|C], S1, K1, Rez).

main(L, Rez) :- findall(A, aranjamente(L, A), Rez).