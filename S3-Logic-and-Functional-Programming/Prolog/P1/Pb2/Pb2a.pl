% Sa se scrie un predicat care determina cel mai mic multiplu comun 
% al elementelor unei liste formate din numere intregi.

/*
main(l1 ... ln) = 0, daca n = 0
                  l1, daca n = 1
                  cmmmc(l1, main(l2 ... ln)), daca n > 1
*/
main([E], E).
main([H|T], Rez) :- main(T, Mul), cmmmc(H, Mul, Rez).

/*
cmmmc(X, Y) = X * Y / cmmdc(X, Y)
*/
cmmmc(X, Y, Mul) :- cmmdc(X, Y, Div), Mul is X * Y / Div.

/*
cmmdc(X, Y) = X                , daca Y = 0
              Y                , daca X = 0
              X                , daca X = Y
              cmmdc(X - Y, Y), daca X > Y
              cmmdc(X, Y - X), daca X < Y
*/
cmmdc(0, Y, Y) :- !.
cmmdc(X, 0, X) :- !.
cmmdc(X, Y, Div) :- X = Y, !, Div = X.
cmmdc(X, Y, Div) :- X < Y, !, Y1 is Y - X, cmmdc(X, Y1, Div).
cmmdc(X, Y, Div) :- X > Y, X1 is X - Y, cmmdc(X1, Y, Div).