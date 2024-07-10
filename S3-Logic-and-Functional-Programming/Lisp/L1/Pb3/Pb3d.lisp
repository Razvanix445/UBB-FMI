;; Sa se scrie o functie care intoarce intersectia a doua multimi.

(DEFUN intersectie (l p)
    (COND
        ((null l) p)
        ((equal (existaInP p (CAR l)) nil) (intersectie (CDR l) (CONS (CAR l) p)))
        (T (intersectie (CDR l) p))
    )
)

(DEFUN existaInP (p e)
    (COND
    ((null p) nil)
    ((equal (CAR p) e) T)
    (T (existaInP (CDR p) e))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb3/Pb3d.lisp")
;; (existaInP '(1 4 3 2 3 1) 5)
;; (intersectie '(1 4 3) '(5 3 1 2))