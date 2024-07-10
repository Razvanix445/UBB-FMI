% Lista permutarilor multimii 1..N cu proprietatea ca valoarea absoluta a diferentei intre 2 valori consecutive este >= V.

apare(E, [E|_]) :- !.
apare(E, [_|T]) :- apare(E, T).

candidat(N, N).
candidat(N, E) :- N > 1,
                  N1 is N - 1,
                  candidat(N1, E).

permutari(N, V, Rez) :- candidat(N, E),
                        permutari_aux(N, V, [E], 1, Rez).

permutari_aux(N, _, C, N, C) :- !.
permutari_aux(N, V, [H|T], Lg, Rez) :- candidat(N, E),
                                       abs(H - E) >= V,
                                       not(apare(E, [H|T])),
                                       Lg1 is Lg + 1,
                                       permutari_aux(N, V, [E|[H|T]], Lg1, Rez).

main(N, V, Rez) :- findall(P, permutari(N, V, P), Rez).