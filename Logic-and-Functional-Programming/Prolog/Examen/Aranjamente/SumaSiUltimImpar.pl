% Aranjamente de N elemente in care suma elementelor este S si aranjamentul se termina cu o valoare impara.

candidat([E|_], E).
candidat([_|T], E) :- candidat(T, E).

aranjamente(L, K, S, Rez) :- candidat(L, E),
                             E mod 2 =:= 1, % conditie ca primul element, adica ultimul din aranjament sa fie impar
                             E =< S,
                             aranjamente_aux(L, [E], E, S, K, 1, Rez).

% aranjamente_aux(lista, colectoare, element(care este suma curenta), sumaFinala, lungimeaMaxima, lungimeaCurenta, Rezultat)
aranjamente_aux(_, Rez, S, S, K, K, Rez) :- !.
aranjamente_aux(L, C, S1, S, K, Lg, Rez) :- candidat(L, E),      % alege un nou candidat din L
                                            not(candidat(C, E)), % verifica ca noul candidat nu a fost introdus deja in C
                                            S2 is S1 + E,
                                            S2 =< S,
                                            Lg1 is Lg + 1,
                                            Lg1 =< K,
                                            aranjamente_aux(L, [E|C], S2, S, K, Lg1, Rez).