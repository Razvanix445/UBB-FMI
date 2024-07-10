% Submultimi cu suma impara si numar par de elemente pare.
/*
submultime(_, NrPare, S, []) :- NrPare mod 2 =:= 0,
                                S mod 2 =:= 1.
submultime([H|T], NrPare, S, [H|Rez]) :- H mod 2 =:= 0,
                                         NrPare1 is NrPare + 1,
                                         S1 is S + H,
                                         submultime(T, NrPare1, S1, Rez).
submultime([H|T], NrPare, S, [H|Rez]) :- H mod 2 =:= 1,
                                         S1 is S + H,
                                         submultime(T, NrPare, S1, Rez).
submultime([_|T], NrPare, S, Rez) :- submultime(T, NrPare, S, Rez).

main(L, Rez) :- findall(Sub, submultime(L, 0, 0, Sub), Rez).
*/
% This solution generates duplicates!!!



candidat([E|_], E).
candidat([_|T], E) :- candidat(T, E).

submultimi(L, N, Rez) :- candidat(L, E),
                         submultimi_aux(L, [E], N, 1, 0, E, Rez).

submultimi_aux(_, C, N, K, NrPare, S, C) :- N >= K,
                                            NrPare mod 2 =:= 0,
                                            S mod 2 =:= 1.
submultimi_aux(L, [H|C], N, K, NrPare, S, Rez) :- candidat(L, E),
                                              E mod 2 =:= 0,
                                              not(candidat(C, E)),
                                              E < H,
                                              NrPare1 is NrPare + 1,
                                              K1 is K + 1,
                                              K =< N,
                                              S1 is S + E,
                                              submultimi_aux(L, [E,H|C], N, K1, NrPare1, S1, Rez).
submultimi_aux(L, [H|C], N, K, NrPare, S, Rez) :- candidat(L, E),
                                              E mod 2 =:= 1,
                                              not(candidat(C, E)),
                                              E < H,
                                              K1 is K + 1,
                                              K =< N,
                                              S1 is S + E,
                                              submultimi_aux(L, [E,H|C], N, K1, NrPare, S1, Rez).

main(L, Rez) :- lungimeLista(L, N), findall(S, submultimi(L, N, S), Rez).

lungimeLista([], 0) :- !.
lungimeLista([H|T], N) :- lungimeLista(T, N1),
                          N is N1 + 1.

% This is a correct solution, I guess .^_^.