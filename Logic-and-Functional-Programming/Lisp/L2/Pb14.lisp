;; Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in preordine.

(DEFUN postordine (l)
    (COND
        ((null l) nil)
        (T (APPEND (postordine (CADR l)) (APPEND (postordine (CADDR l)) (list (CAR l)))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb14.lisp")
;; (postordine '(A (B (F (I) (J))) (C (D (G) (H)) (E))))

;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )