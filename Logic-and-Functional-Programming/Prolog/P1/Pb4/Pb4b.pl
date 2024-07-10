% Sa se elimine elementul de pe pozitia a n-a a unei liste liniare.

/*
eliminare(l1 ... ln, PozitieCurenta, PozitieEliminare) = 
                                ∅, daca n = 0
                                l1 (+) eliminare(l2 ... ln), daca PozitieCurenta != PozitieEliminare
                                eliminare(l2 ... ln), daca PozitieCurenta = PozitieEliminare
*/

eliminare([], _, _, []).
eliminare([H|T], PozitieCurenta, PozitieEliminare, [H|Rez]) :- 
                                    PozitieCurenta \= PozitieEliminare,
                                    PozitieCurenta1 is PozitieCurenta + 1,
                                    eliminare(T, PozitieCurenta1, PozitieEliminare, Rez).
eliminare([H|T], PozitieCurenta, PozitieEliminare, Rez) :- 
                                    PozitieCurenta == PozitieEliminare,
                                    PozitieCurenta1 is PozitieCurenta + 1, 
                                    eliminare(T, PozitieCurenta1, PozitieEliminare, Rez).

main(Lista, PozitieEliminare, Rez) :- eliminare(Lista, 1, PozitieEliminare, Rez).