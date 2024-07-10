candidat([E|_], E).
candidat([_|T], E) :- candidat(T, E).

submultime(L, K, Rez) :- candidat(L, E),
                         E mod 2 =:= 1,
                         submultime_aux(L, [E], -1, K, 1, Rez).

submultime_aux(_, C, _, K, K, C) :- !.
submultime_aux(L, [H|T], -1, K, KC, Rez) :- !,
                                            candidat(L, E),
                                            E mod 2 =:= 1,
                                            E < H,
                                            KC1 is KC + 1,
                                            D1 is H - E,
                                            submultime_aux(L, [E,H|T], D1, K, KC1, Rez).
submultime_aux(L, [H|T], D, K, KC, Rez) :- candidat(L, E),
                                           E mod 2 =:= 1,
                                           H - E =:= D,
                                           KC1 is KC + 1,
                                           submultime_aux(L, [E,H|T], D, K, KC1, Rez).

main(L, K, Rez) :- findall(Subm, submultime(L, K, Subm), Rez).