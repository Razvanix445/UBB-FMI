;; Definiti o functie care obtine dintr-o lista data lista tuturor atomilor care apar, 
;; pe orice nivel, dar in ordine inversa. De exemplu: (((A B) C) (D E)) --> (E D C B A)

(DEFUN inversare (l)
    (COND
        ((null l) nil)
        ((listp (CAR l)) (APPEND (inversare(CDR l)) (inversare (CAR l))))
        (T (APPEND (inversare (CDR l)) (list (CAR l))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb1/Pb1b.lisp")
;; (inversare '(2 (7 (9 3)) 0 2 (2 3)))