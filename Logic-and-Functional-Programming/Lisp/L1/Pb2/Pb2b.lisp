;; Sa se construiasca o functie care verifica daca un atom este membru al unei liste neliniare.

(DEFUN apartine (l e)
    (COND
        ((null l) nil)
        ((AND (atom (CAR l)) (equal (CAR l) e)) T)
        ((listp (CAR l)) (OR (apartine (CAR l) e) (apartine (CDR l) e)))
        (T (apartine (CDR l) e))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb2/Pb2b.lisp")
;; (apartine '(1 2 (3 (2 5)) 3 1) 5)