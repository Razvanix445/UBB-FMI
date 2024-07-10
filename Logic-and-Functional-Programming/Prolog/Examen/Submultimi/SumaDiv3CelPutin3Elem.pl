candidat([E|_], E).
candidat([_|T], E) :- candidat(T, E).

submultime(L, N, Rez) :- candidat(L, E),
                         submultime_aux(L, [E], N, 1, E, Rez).

submultime_aux(_, C, N, PozC, S, C) :- S mod 3 =:= 0,
                                       PozC >= N.
submultime_aux(L, [H|C], N, PozC, S, Rez) :- candidat(L, E),
                                             E < H,
                                             PozC1 is PozC + 1,
                                             S1 is S + E,
                                             submultime_aux(L, [E,H|C], N, PozC1, S1, Rez).

main(L, N, Rez) :- findall(Subm, submultime(L, N, Subm), Rez).