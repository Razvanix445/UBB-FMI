;; Definiti o functie care inverseaza o lista impreuna cu toate sublistele sale de pe orice nivel.

(DEFUN inversare (l)
    (COND
        ((null l) nil)
        ((listp (CAR l)) (APPEND (inversare (CDR l)) (list (inversare (CAR l)))))
        (T (APPEND (inversare (CDR l)) (list (CAR l))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb9/Pb9b.lisp")
;; (inversare '(1 ((4 3) 7) 4 (2 3) 9))