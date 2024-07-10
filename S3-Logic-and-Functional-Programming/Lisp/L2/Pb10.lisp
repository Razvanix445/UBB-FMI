;; Se da un arbore de tipul (2). Sa se precizeze nivelul pe care apare un nod x in arbore. 
;; Nivelul radacinii se considera a fi 0.

(DEFUN apare (l x)
    (COND
        ((null l) nil)
        ((equal (CAR l) x) T)
        (T (OR (apare (CADR l) x) (apare (CADDR l) x)))
    )
)

(DEFUN nivelAparitie (l x nivelCurent)
    (COND
        ((null l) nil)
        ((equal (CAR l) x) nivelCurent)
        ((equal (apare (CADR l) x) T) (nivelAparitie (CADR l) x (+ nivelCurent 1)))
        (T (nivelAparitie (CADDR l) x (+ nivelCurent 1)))
    )
)

(DEFUN main (l x)
    (nivelAparitie l x 0)
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb10.lisp")
;; (main '(A (B (F (I) (J))) (C (D (G) (H)) (E))) 'G)

;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )