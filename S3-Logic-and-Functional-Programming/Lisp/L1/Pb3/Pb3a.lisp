;; Definiti o functie care intoarce produsul a doi vectori.

(DEFUN produs (l p)
    (COND
        ((OR (null l) (null p)) 0)
        (T (+ (* (CAR l) (CAR p)) (produs (CDR l) (CDR p))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb3/Pb3a.lisp")
;; (produs '(2 1 2 3) '(1 3 3 4 6))