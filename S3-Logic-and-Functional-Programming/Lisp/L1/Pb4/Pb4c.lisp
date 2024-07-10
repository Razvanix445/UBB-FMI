;; Sa se scrie o functie care plecand de la o lista data ca argument, inverseaza numai secventele 
;; continue de atomi. Exemplu: (a b c (d (e f) g h i)) => (c b a (d (f e) i h g))

(DEFUN inverseaza (l lRez)
    (COND
        ((null l) lRez)
        ((listp (CAR l)) (APPEND lRez (APPEND (list (inverseaza (CAR l) nil)) (inverseaza (CDR l) nil))))
        (T (inverseaza (CDR l) (APPEND (list (CAR l)) lRez)))
    )
)

(DEFUN main (l)
    (inverseaza l nil)
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb4/Pb4c.lisp")
;; (main '(a b c (d (e f) g h i)))