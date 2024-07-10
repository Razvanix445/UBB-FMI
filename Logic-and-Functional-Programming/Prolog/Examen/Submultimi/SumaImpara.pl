% Submultimi cu suma elementelor impara.

candidat(A, B, []) :- A > B, !.
candidat(A, B, [A|T]) :- A1 is A + 1,
                         candidat(A1, B, T).

submultime(_, 0, S, []) :- S mod 2 =:= 1.
submultime([H|T], K, S, [H|Rez]) :- K > 0,
                                    S1 is S + H,
                                    K1 is K - 1,
                                    submultime(T, K1, S1, Rez).
submultime([H|T], K, S, Rez) :- K > 0,
                                submultime(T, K, S, Rez).

main(A, B, Rez) :- candidat(A, B, L), findall(Sub, submultime(L, 3, 0, Sub), Rez).

% Trebuie sa facem un predicat de calcul al lungimii listei rezultate din predicatul candidat si sa parcurgem de lungimea listei
% de ori pentru a lua fiecare submultime de fiecare lungime posibila. Valoarea 3 din findall trebuie inlocuita cu un K de la 1 la lungimea listei.