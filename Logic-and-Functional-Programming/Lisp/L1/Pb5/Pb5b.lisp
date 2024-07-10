;; Definiti o functie care substituie un element E prin elementele unei liste L1 la toate 
;; nivelurile unei liste date L.

(DEFUN substituie (l lE e)
    (COND
        ((null l) nil)
        ((listp (CAR l)) (CONS (substituie (CAR l) lE e) (substituie (CDR l) lE e)))
        ((equal (CAR l) e) (CONS lE (substituie (CDR l) lE e)))
        (T (CONS (CAR l) (substituie (CDR l) lE e)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb5/Pb5b.lisp")
;; (substituie '(1 3 4 (3 4) 3) '(0 0 0) 3)