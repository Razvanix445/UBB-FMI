% Sa se scrie un predicat care adauga intr-o lista dupa fiecare element par valoarea 1.

/*
adauga(l1 ... ln) = ∅                           , n = 0
                    l1 ⊕ adauga(l2 ... ln)     , l1 - impar
                    l1 ⊕ 1 ⊕ adauga(l2 ... ln), l1 - par
*/
%adauga(i, i) / adauga(i, o)
adauga([], []).
adauga([H|T], [H,1|Rez]) :- H mod 2 =:= 0, adauga(T, Rez), !.
adauga([H|T], [H|Rez]) :- adauga(T, Rez).