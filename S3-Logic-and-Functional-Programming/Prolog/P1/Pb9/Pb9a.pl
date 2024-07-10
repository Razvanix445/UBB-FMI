main([], 0, []).
main([H|T], PozitieCurenta, [NewE|Rez]) :- NewPozitieCurenta is PozitieCurenta + 1, 
                                           ridicareLaPutere(H, NewPozitieCurenta, NewE), 
                                           main(T, NewPozitieCurenta, Rez).

ridicareLaPutere(_, 0, _).
ridicareLaPutere(E, NumarRidicari, Rez2) :- NumarRidicari \= 0,
                                           NewNumarRidicari is NumarRidicari - 1,
                                           ridicareLaPutere(E, NewNumarRidicari, Rez),
                                           Rez2 =:= Rez * E.
