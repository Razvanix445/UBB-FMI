% Insertion Sort

insertionSort(Lista, Sortat) :- sorteazaInsert(Lista, [], Sortat).

sorteazaInsert([], Col, Col).
sorteazaInsert([H|T], Col, Sortat) :- insert(H, Col, NCol), sorteazaInsert(T, NCol, Sortat).

insert(X, [Y|T], [Y|NT]) :- X > Y, insert(X, T, NT).
insert(X, [Y|T], [X, Y|T]) :- X =< Y.
insert(X, [], [X]).


% Bubble Sort

bubbleSort(Lista, Sortat) :- sorteazaBubble(Lista, [], Sortat).

sorteazaBubble([], Col, Col).
sorteazaBubble([H|T], Col, Sortat) :- bubble(H, T, NT, Max), sorteazaBubble(NT, [Max|Col], Sortat).

bubble(X, [], [], X).
bubble(X, [Y|T], [Y|NT], Max) :- X > Y, bubble(X, T, NT, Max).
bubble(X, [Y|T], [X|NT], Max) :- X =< Y, bubble(Y, T, NT, Max).