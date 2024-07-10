% Sa se scrie un predicat care transforma o lista intr-o multime, in ordinea primei aparitii.
% Exemplu: [1, 2, 3, 1, 2] e transformat in [1, 2, 3].

/*
transform(l1 ... ln) = ∅, daca n = 0
                       verificaDuplicat(l1, )
*/
transform([], Col, Col).
transform([H|T], Col, Rez) :- not(elementulExista(H, Col)), 
                                  myAppend(Col, [H], Col1),
                                  transform(T, Col1, Rez).
transform([H|T], Col, Rez) :- elementulExista(H, Col), transform(T, Col, Rez).


% main
main(Lista, Rez) :- transform(Lista, [], Rez).

% elementulExista/2 - Predicat care verifica daca un element este inclus intr-o multime
elementulExista(E, [E|_]) :- !.
elementulExista(E, [_|T]) :- elementulExista(E, T).

% append
myAppend([], Y, Y).
myAppend([X|L1], L2, [X|L3]) :- myAppend(L1, L2, L3).