% Submultimi cu suma para.

submultime(_, 0, S, []) :- S mod 2 =:= 0.
submultime([H|T], K, S, [H|Rez]) :- K > 0,
                                    S1 is S + H,
                                    K1 is K - 1,
                                    submultime(T, K1, S1, Rez).
submultime([_|T], K, S, Rez) :- K > 0,
                                submultime(T, K, S, Rez).

main(L, K, Rez) :- findall(Subs, submultime(L, K, 0, Subs), Rez).