% Sa se scrie un predicat care testeaza egalitatea a doua multimi, fara sa se faca apel la diferenta a doua multimi.
/*
    egalitateMultimi(l1 l2 ... ln :Multime, p1 p2 ... pm :Multime).
    egalitateMultimi(i, i) - determinist (model de flux)
    egalitateMultimi(l1 l2 ... ln, p1 p2 ... pm) = true , daca multimile sunt egale
                                                   false, altfel


    esteInMultime(l1 l2 ... ln :Multime, p1 p2 ... pm :Multime).
    esteInMultime(i, i) - determinist  (model de flux)
    esteInMultime(l1 | l2 ... ln, p1 p2 ... pm) =   true                                      , daca n = 0;
                                                    false                                     , daca l1 nu apartine multimii [p1 p2 ... pm]
                                                    esteInMultime(l2 l3 ... ln, p1 p2 ... pm) , altfel
                                                    
                                                    
    elementulExista(Element :element, l1 l2 ... ln :Multime).
    elementulExista(i, i) - determinist  (model de flux)
    elementulExista(Element, l1 | l2 ... ln) = false                                 , daca n = 0;
                                               true                                  , daca l1 = Element;
                                               elementulExista(Element, l2 l3 ... ln), altfel.
*/

% egalitateMultimi/2 - Predicat care verifica daca doua multimi sunt egale
egalitateMultimi(Multime1, Multime2) :- esteInMultime(Multime1, Multime2), esteInMultime(Multime2, Multime1).

% elementulExista/2 - Predicat care verifica daca un element este inclus intr-o multime
elementulExista(E, [E|_]) :- !.
elementulExista(E, [_|RestulMultimii]) :- elementulExista(E, RestulMultimii).

% esteInMultime/2 - Predicat care verifica incluziunea tuturor elementelor din prima multime in a doua multime
esteInMultime([], _).
esteInMultime([X|RestulMultimii], Multime) :- elementulExista(X, Multime), esteInMultime(RestulMultimii, Multime).