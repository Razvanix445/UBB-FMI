% Definiti un predicat care selecteaza al n-lea element al unei liste
/*
    selecteaza_N(l1 l2 ... ln :Lista, N :Intreg, Element :Element).
    selecteaza_N(i, i, o) (model de flux)

    l1 l2 ... lm - lista de elemente
    N            - pozitia elementului cautat
    Element      - elementul cautat
    selecteaza_N(l1 | l2 ... lm, N) = ∅                                , daca m = 0 (lista este vida)
                                      l1                               , daca N = 1 si m >= 1
                                      selecteaza_N(l2 l3 ... lm, N - 1), altfel
*/

% selecteaza_N/3 - Predicat care selecteaza al n-lea element al unei liste

% Elementul este primul din lista (N = 1)
selecteaza_N([Element | _], 1, Element) :- !.
% Partea recursiva: Elementul este oricare din lista (N > 1)
selecteaza_N([_ | RestulListei], N, Element) :- N > 1, N1 is N - 1, selecteaza_N(RestulListei, N1, Element).