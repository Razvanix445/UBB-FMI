% Stergere secvente de numere descrescatoare

/*
sterge(l1 ... ln, Rez) = [], daca n = 0
                         l1 (+) sterge(l2 ... ln, Rez), daca !(l1 <= l2 <= l3)
                         sterge(l2 ... ln, Rez), daca l1 <= l2 <= l3                    
*/
sterge([], []) :- !.
sterge([H1], [H1]) :- !.

sterge([H1, H2], []) :- H1 =< H2, !.
sterge([H1, H2], [H1, H2]) :- H1 > H2, !.

sterge([H1, H2, H3], []) :- H1 =< H2, H2 =< H3, !.
sterge([H1, H2, H3], [H3|Rez]) :- H1 =< H2, H2 > H3, !.
sterge([H1, H2, H3], [H1, H2, H3|Rez]) :- H1 > H2,  H2 > H3, !.
sterge([H1, H2, H3], [H1, H2, H3|Rez]) :- H1 > H2,  H2 =< H3, !.

sterge([H1, H2, H3|T], Rez) :- H1 =< H2, H2 =< H3, !,
                               sterge([H2, H3|T], Rez).
sterge([H1, H2, H3|T], Rez) :- H1 =< H2, H2 > H3, !,
                               sterge([H2, H3|T], Rez).
sterge([H1, H2, H3|T], [H1|Rez]) :- H1 > H2, H2 > H3, !, 
                                    sterge([H2, H3|T], Rez).
sterge([H1, H2, H3|T], [H1|Rez]) :- H1 > H2, H2 =< H3, !, 
                                    sterge([H2, H3|T], Rez).