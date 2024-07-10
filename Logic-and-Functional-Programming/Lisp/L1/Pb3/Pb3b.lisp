;; Sa se construiasca o functie care intoarce adancimea unei liste.

(DEFUN adancime (l maxAdancime)
    (COND
        ((null l) maxAdancime)
        ((listp (CAR l)) (MAX (adancime (CAR l) (+ maxAdancime 1)) (adancime (CDR l) maxAdancime)))
        (T (adancime (CDR l) maxAdancime))
    )
)

;; Ar fi mai corecta adaugarea unei simple functii main care initializeaza maxAdancime cu 1.

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb3/Pb3b.lisp")
;; (adancime '(2 1 (2 (3 4) ((5 6) 7)) (7 8) 9) 1)