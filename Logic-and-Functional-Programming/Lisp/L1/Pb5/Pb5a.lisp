;; Definiti o functie care interclaseaza cu pastrarea dublurilor doua liste liniare sortate.

(DEFUN interclasare (l p lp)
    (COND
        ((null l) p)
        ((null p) l)
        ((<= (CAR l) (CAR p)) (CONS (CAR l) (interclasare (CDR l) p (CONS (CAR l) lp))))
        (T (CONS (CAR p) (interclasare l (CDR p) (CONS (CAR p) lp))))
    )
)

(DEFUN main (l p)
    (interclasare l p nil)
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb5/Pb5a.lisp")
;; (main '(1 3 4 5 8) '(1 2 5 6 9))