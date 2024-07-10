;; Sa se decida daca un arbore de tipul (2) este echilibrat (diferenta dintre adancimile celor 
;; 2 subarbori nu este mai mare decat 1).

(DEFUN echilibrat (l)
    (COND
        ((null l) T)
        ((> (diferenta (adancime (CADR l)) (adancime (CADDR l))) 1) nil)
        (T (AND (echilibrat (CADR l)) (echilibrat (CADDR l))))
    )
)

(DEFUN adancime (l)
    (COND
        ((null l) 0)
        (T (+ 1 (MAX (adancime (CADR l)) (adancime (CADDR l)))))
    )
)

(DEFUN diferenta (a b)
    (COND
        ((> a b) (- a b))
        (T (- b a))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb16.lisp")
;; (echilibrat '(A (B (F (I) (J))) (C (D (G) (H)) (E))))
;; (adancime '(A (B (F (I) (J))) (C (D (G) (H)) (E))))

;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )