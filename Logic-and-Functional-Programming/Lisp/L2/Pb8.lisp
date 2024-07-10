;; Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in inordine.

(DEFUN inordine (l)
    (COND
        ((null l) nil)
        (T (APPEND (inordine (CADR l)) (APPEND (list (CAR l)) (inordine (CADDR l)))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb8.lisp")
;; (inordine '(A (B (F (I) (J))) (C (D (G) (H)) (E))))

;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )