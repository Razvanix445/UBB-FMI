% Se da o lista de numere intregi. Se cere sa se adauge in lista dupa primul element, al 3-lea element, al 7-lea 
% element, al 15-lea element... o valoare data e.
% Exemplu: adaugaDupa(20, [1, 2, 3, 4, 5, 6, 7, 8, 9], 1, 1, []) => [1, 20, 2, 3, 20, 4, 5, 6, 7, 20, 8, 9].

/*
    adaugaDupa(i, i, i, i, o) (model de flux)
    adaugaDupa(E :Element, L :Lista, PozCurenta :Intreg, PozAdd :Intreg, Rez :Lista)

    l1 l2 ... ln   - lista
    E              - elementul de adaugat
    PozCurenta     - pozitia pe care ne aflam la un moment dat
    PozAdd         - pozitia dupa care trebuie sa adaugam elementul

    adaugaDupa(l1 | l2 ... ln, E, PozCurenta, PozAdd) = 
        ∅                                                                   , daca n = 0 si PozAdd = 1
        l1 ∪ adaugaDupa(l2 ... ln, E, PozCurenta + 1, PozAdd)               , daca PozCurenta != PozAdd
        E ∪ adaugaDupa(l1 l2 ... ln, E, PozCurenta + 1, PozAdd + PozAdd + 1), daca PozCurenta == PozAdd
*/

adaugaDupa([], _, _, _, []).
adaugaDupa([H | T], E, PozCurenta, PozAdd, [H | Rez]) :- PozCurenta \= PozAdd, !,
                                                         NextPozCurenta is PozCurenta + 1,
                                                         adaugaDupa(T, E, NextPozCurenta, PozAdd, Rez).
adaugaDupa([H | T], E, PozCurenta, PozAdd, [H, E | Rez]) :- PozCurenta == PozAdd, !,
                                                            NextPozCurenta is PozCurenta + 1,
                                                            NextPozAdd is (PozAdd + PozAdd) + 1,
                                                            adaugaDupa(T, E, NextPozCurenta, NextPozAdd, Rez).

% Predicatul care apeleaza adaugaDupa() pentru a initializa PozCurenta si PozAdd
% adaugaMain(i, i, o) (model de flux)
% adaugaMain(L :Lista, E :Element, Rez :Lista)
adaugaMain([], _, []).
adaugaMain(L, E, Rez) :- adaugaDupa(L, E, 1, 1, Rez).