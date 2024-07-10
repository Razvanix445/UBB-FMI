;; Sa se scrie o functie care testeaza daca o lista este liniara

;; esteListaLiniara(l1 ... ln) = true                       , daca n = 0
;;                               esteListaLiniara(l2 ... ln), daca l1 - atom
;;                               false                      , altfel (daca l1 nu este atom)

(DEFUN esteListaLiniara (l)
    (COND
        ((null l)        T)
        ((atom (CAR l))  (esteListaLiniara (CDR l)))
        (T               nil)
    )
)

(DEFUN listaNoduriPerNivel (l nivel nivelCurent)
    (COND
        ((null l) nil)
        ((<= nivel nivelCurent) (APPEND (listaNoduriPerNivel (CADR l) nivel (+ 1 nivelCurent)) (CONS (CAR l) (listaNoduriPerNivel (CADDR l) nivel (+ 1 nivelCurent)))))
        (T (APPEND (listaNoduriPerNivel (CADR l) nivel (+ 1 nivelCurent)) (listaNoduriPerNivel (CADDR l) nivel (+ 1 nivelCurent))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb11.lisp")
;; (setq arbore '(A (B) (C (D) (E))))
;; (listaNoduriPerNivel arbore 1 0)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb7/Pb7a.lisp")
;; (esteListaLiniara '(1 2 3 4)) => T
;; (esteListaLiniara '(1 (2 3) 4)) => NIL