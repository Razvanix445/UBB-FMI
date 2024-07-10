;; Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in preordine.

(DEFUN preordine (l)
    (COND
        ((null l) nil)
        (T (APPEND (list (CAR l)) (APPEND (preordine (CADR l)) (preordine (CADDR l)))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb12.lisp")
;; (preordine '(A (B (F (I) (J))) (C (D (G) (H)) (E))))

;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )