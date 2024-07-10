% Combinari de K elemente cu numere de la 1 la N, avand diferenta intre doua numere consecutive din combinare numar par.

exista([E|_], E).
exista([_|T], E) :- exista(T, E).

candidat(N, N).
candidat(N, E) :- N > 1,
                  N1 is N - 1,
                  candidat(N1, E).

creareLista(A, B, []) :- A > B, !.
creareLista(A, B, [A|O]) :- A1 is A + 1,
                            creareLista(A1, B, O).

combinari(K, N, Rez) :- candidat(N, E),
                        combinari_aux([E], K, 1, N, Rez).

combinari_aux(C, K, K, _, C) :- !.
combinari_aux([H|C], K, Lg, N, Rez) :- candidat(N, E),
                                       not(exista(C, E)),
                                       abs(H - E) mod 2 =:= 0,
                                       E < H,
                                       Lg1 is Lg + 1,
                                       combinari_aux([E|[H|C]], K, Lg1, N, Rez).