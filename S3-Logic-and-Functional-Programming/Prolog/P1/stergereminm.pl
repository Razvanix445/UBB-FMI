% Stergere din m in m

/*
sterge(l1 ... ln, MCurent, M, Rez) = [], daca n = 0
                                     sterge(l2 ... ln, M, M, Rez), daca MCurent = 0
                                     l1 (+) sterge(l2 ... ln, MCurent - 1, M, Rez), daca MCurent != 0
*/

sterge([], _, _, []) :- !.
sterge([H|T], MCurent, M, [H|Rez]) :- MCurent \= 0, !, 
                                      MCurent2 is MCurent - 1,
                                      sterge(T, MCurent2, M, Rez).
sterge([H|T], MCurent, M, Rez) :- MCurent == 0, !, 
                                  sterge(T, M, M, Rez).


main(Lista, M, Rez) :- sterge(Lista, M, M, Rez).