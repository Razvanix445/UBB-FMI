;; Definiti o functie care obtine dintr-o lista data lista tuturor atomilor care apar, 
;; pe orice nivel, dar in aceeasi ordine. Exemplu: (((A B) C) (D E)) => (A B C D E)

(DEFUN listaAtomi (l)
    (COND
        ((null l) nil)
        ((listp (CAR l)) (APPEND (listaAtomi (CAR l)) (listaAtomi (CDR l))))
        (T (APPEND (list (CAR l)) (listaAtomi (CDR l))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb4/Pb4b.lisp")
;; (listaAtomi '(((A B) C) (D E)))