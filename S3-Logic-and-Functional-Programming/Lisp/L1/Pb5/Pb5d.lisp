;; Definiti o functie care intoarce cel mai mare divizor comun al numerelor dintr-o lista liniara.

(DEFUN cmmdcList (l dv)
    (COND
        ((null l) dv)
        (T (cmmdcList (CDR l) (cmmdc (CAR l) dv)))
    )
)

(DEFUN cmmdc (a b)
    (COND
        ((equal b 0) a)
        ((> a b) (cmmdc (- a b) b))
        (T (cmmdc b (mod a b)))
    )
)

(DEFUN main (l)
    (cmmdcList l (CAR l))
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb5/Pb5d.lisp")
;; (main '(12 36 24 16 72))