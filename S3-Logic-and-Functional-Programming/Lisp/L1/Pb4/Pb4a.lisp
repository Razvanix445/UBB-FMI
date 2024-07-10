;; Definiti o functie care intoarce suma a doi vectori.

(DEFUN suma (l p)
    (COND
        ((null l) nil)
        (T (CONS (+ (CAR l) (CAR p)) (suma (CDR l) (CDR p))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb4/Pb4a.lisp")
;; (suma '(1 3 4 5 9) '(2 3 5 8 9))