;; Definiti o functie care intoarce cel mai mare divizor comun al numerelor dintr-o lista neliniara.

(DEFUN cmmdcList (l)
    (COND
        ((null l) nil)
        ((numberp (CADR l)) (cmmdc (CAR l) (CADR l)))
        (T (CONS (cmmdc (CAR l) (CADR l)) (cmmdcList (CDR l))))
    )
)

(DEFUN cmmdc (a b)
    (COND
        ((equal b 0) a)
        (T (cmmdc b (mod a b)))
    )
)

(DEFUN flattenList (l)
    (COND
        ((null l) nil)
        ((listp (CAR l)) (APPEND (flattenList (CAR l)) (flattenList (CDR l))))
        (T (CONS (CAR l) (flattenList (CDR l))))
    )
)

(DEFUN main (l)
    (cmmdcList (flattenList l))
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb1/Pb1c.lisp")
;; (cmmdc 12 16)
;; (cmmdcList '(12 36 ((24 16) 72)))
;; (flattenList '(1 (3 5) (3 (4 2))))