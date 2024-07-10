% Se da o lista eterogena, formata din numere intregi si liste de numere intregi. Lista incepe cu un numar si nu sunt 2 elemente consecutive 
% care sunt liste. Se cere ca in fiecare sublista sa se adauge dupa 1-ul, al 3-lea, al 7-lea... element valoarea care se gaseste inainte de 
% sublista in lista eterogena.
% De exemplu: [1, [2, 3], 7, [4, 1, 4], 3, 6, [7, 5, 1, 3, 9, 8, 2, 7], 5] => 
%                      [1, [2, 1, 3], 7, [4, 7, 1, 4, 7], 3, 6, [7, 6, 5, 1, 6, 3, 9, 8, 2, 6, 7], 5].

/*
    adaugaInLista(i, i, o) (model de flux)
    adaugaInLista(LE :ListaEterogena, E :Element, ListaRez :ListaEterogena)

    adaugaInLista(l1 | l2 ... ln, E) = ∅                                                     , daca n = 0 (lista eterogena este vida)
                                       l1 ∪ adaugaInLista(l2 ... ln, l1)                     , daca n != 0 si l1 este numar
                                       adaugaDupa(E, l1, 1, 1) ∪ adaugaInLista(l2 ... ln, E) , daca n != 0 si l1 este lista
*/



% adaugaInLista(i, i, o) (model de flux)
% adaugaInLista(LE :ListaEterogena, E :Element, ListaRez :ListaEterogena)
adaugaInLista([], _, []) :- !.
adaugaInLista([H | T], _, ListaRez) :- number(H), !,
                                       adaugaInLista(T, H, ListaRez1), 
                                       ListaRez = [H | ListaRez1].
adaugaInLista([H | T], E, ListaRez) :- is_list(H), !,
                                       adaugaMain(H, E, ListaCuElement), 
                                       adaugaInLista(T, E, ListaRezUpdatata), 
                                       ListaRez = [ListaCuElement | ListaRezUpdatata].


adaugaInListaMain([], []).
adaugaInListaMain(L, Rez) :- adaugaInLista(L, E, Rez).


% adaugaDupa(i, i, i, i, o) (model de flux)
% adaugaDupa(E :Element, L :Lista, PozCurenta :Intreg, PozAdd :Intreg, Rez :Lista)
adaugaDupa([], _, _, _, []).
adaugaDupa([H | T], E, PozCurenta, PozAdd, [H | Rez]) :- PozCurenta \= PozAdd, !,
                                                         NextPozCurenta is PozCurenta + 1,
                                                         adaugaDupa(T, E, NextPozCurenta, PozAdd, Rez).
adaugaDupa([H | T], E, PozCurenta, PozAdd, [H, E | Rez]) :- PozCurenta == PozAdd, !,
                                                            NextPozCurenta is PozCurenta + 1,
                                                            NextPozAdd is (PozAdd + PozAdd) + 1,
                                                            adaugaDupa(T, E, PozCurenta, NextPozAdd, Rez).

% Predicatul care apeleaza adaugaDupa() pentru a initializa PozCurenta si PozAdd
% adaugaMain(i, i, o) (model de flux)
% adaugaMain(L :Lista, E :Element, Rez :Lista)
adaugaMain([], _, []).
adaugaMain(L, E, Rez) :- adaugaDupa(L, E, 1, 1, Rez).