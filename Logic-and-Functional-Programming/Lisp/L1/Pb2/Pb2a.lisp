;; Definiti o functie care selecteaza al n-lea element al unei liste, sau NIL, daca nu exista.

(DEFUN selecteaza (l n pos)
    (COND
        ((null l) nil)
        ((= n pos) (CAR l))
        (T (selecteaza (CDR l) n (+ pos 1)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb2/Pb2a.lisp")
;; (main '(1 4 3 2 3 1) 5 1)