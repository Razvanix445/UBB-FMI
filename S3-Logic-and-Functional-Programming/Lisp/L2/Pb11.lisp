;; Se da un arbore de tipul (2). Sa se afiseze nivelul (si lista corespunzatoare a nodurilor) avand numar
;; maxim de noduri. Nivelul radacinii se considera 0.

;; functie care returneaza lista de noduri de la un anumit nivel
;; listaNoduriPerNivel(l1 l2 l3, nivel, nivelCurent) = 
;;     nil                                                                                                 , daca l - vida
;;     l1                                                                                                  , daca nivel = nivelCurent si l nu e vida
;;     listaNoduriPerNivel(l2, nivel, nivelCurent + 1) (+) listaNoduriPerNivel(l3, nivel, nivelCurent + 1) , altfel
(DEFUN listaNoduriPerNivel (l nivel nivelCurent)
    (COND
        ((null l) nil)
        ((= nivel nivelCurent) (list (CAR l)))
        (T (APPEND (listaNoduriPerNivel (CADR l) nivel (+ 1 nivelCurent)) (listaNoduriPerNivel (CADDR l) nivel (+ 1 nivelCurent))))
    )
)

(DEFUN mainListaNoduriPerNivel (l nivel)
    (listaNoduriPerNivel l nivel 0)
)



;; functie care returneaza numarul de elemente din lista
;; nrElementeLista(l1 ... ln, nrElem) = 0, daca l - vida
;;                                      1 (+) nrElementeLista(l2 ... ln, nrElem), altfel
(DEFUN nrElementeLista (l nrElem)
    (COND
        ((null l) nrElem)
        (T        (nrElementeLista (CDR l) (+ 1 nrElem)))
    )
)

(DEFUN mainNrElementeLista (l)
    (nrElementeLista l 0)
)



;; functie care afiseaza nivelul care are numar maxim de noduri, respectiv lista corespunzatoare a nodurilor
;; nivelMaximNoduri(l1 l2 l3, nivelCurent, valMax, listaNoduri) = 
;;       valMax si listaNoduri                                    , daca mainNrElementeLista(mainListaNoduriPerNivel, l, nivelCurent) = 0
;;       nivelMaximNoduri(l1 l2 l3, nivelCurent + 1, mainNrElementeLista(mainListaNoduriPerNivel(l, nivelCurent)), (mainListaNoduriPerNivel(l, nivelCurent))), daca mainNrElementeLista(mainListaNoduriPerNivel(l, nivelCurent)) > valMax
;;       nivelMaximNoduri(l, nivelCurent + 1, valMax, listaNoduri), altfel
(DEFUN nivelMaximNoduri (l nivelCurent valMax listaNoduri)
    (COND
        ((= (mainNrElementeLista (mainListaNoduriPerNivel l nivelCurent)) 0)      (values valMax listaNoduri))
        ((> (mainNrElementeLista (mainListaNoduriPerNivel l nivelCurent)) valMax) (nivelMaximNoduri l (+ 1 nivelCurent) (mainNrElementeLista (mainListaNoduriPerNivel l nivelCurent)) (mainListaNoduriPerNivel l nivelCurent)))
        (T                                                                        (nivelMaximNoduri l (+ 1 nivelCurent) valMax listaNoduri))
    )
)

(DEFUN mainNivelMaximNoduri (l)
    (nivelMaximNoduri l 0 0 '())
)


;; (load "C:/Users/razva/Documents/Lisp/L2/Pb11.lisp")

;; (listaNoduriPerNivel '(A (B) (C (D) (E))) 1 0)
;; (mainListaNoduriPerNivel '(A (B) (C (D) (E))) 1)

;; (nrElementeLista '(A (B) (C (D) (E))) 0)
;; (mainNrElementeLista '(A (B) (C (D) (E))))

;; (nivelMaximNoduri '(A (B) (C (D) (E))) 0 0 '())
;; (nivelMaximNoduri '(A (B (F (I) (J))) (C (D (G) (H)) (E))) 0 0 '())
;; (mainNivelMaximNoduri '(A (B (F (I) (J))) (C (D (G) (H)) (E))))
;; (mainNivelMaximNoduri '(A (B (D (E (F (G) (H (J) (K)))))) (C (L (N) (Q (P) (R))) (M))))

;; (mainListaNoduriPerNivel '(A (B (D (E (F (G) (H (J) (K)))))) (C (L (N) (Q (P) (R))) (M))) 2)

;; nivel 2: B C
;; nivel 3: D L M
;; nivel 4: E N Q
;; nivel 5: F P R
;; nivel 6: G H
;; nivel 7: J K
;; nivel 8: NIL

(DEFUN main ()
    (MULTIPLE-VALUE-BIND (valMax-result listaNoduri-result) (mainNivelMaximNoduri '(A (B (F (I) (J))) (C (D (G) (H)) (E))))
        (FORMAT T "Nivelul cu cele mai multe noduri este: ~a~%" valMax-result)
        (FORMAT T "Lista de noduri de pe nivelul respectiv este: ~a~%" listaNoduri-result)
    )
)



;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )