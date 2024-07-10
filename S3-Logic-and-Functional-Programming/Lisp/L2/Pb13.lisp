;; Se da un arbore de tipul (2). Sa se afiseze calea de la radacina pana la un nod x dat.

(DEFUN apare (l e)
    (COND
        ((null l) nil)
        ((equal (CAR l) e) T)
        (T (OR (apare (CADR l) e) (apare (CADDR l) e)))
    )
)

(DEFUN path (l xNode)
    (COND
        ((not (apare l xNode)) nil)
        ((equal (CAR l) xNode) (list xNode))
        ((apare (CADR l) xNode) (CONS (CAR l) (path (CADR l) xNode)))
        (T (CONS (CAR l) (path (CADDR l) xNode)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L2/Pb13.lisp")
;; (cale '(A (B (F (I) (J))) (C (D (G) (H)) (E))) 'G)

;; (       A      )
;; (      / \     )
;; (     B   C    )
;; (    /   / \   )
;; (   F   D   E  )
;; (  /\   /\     )
;; ( I  J G  H    )

;; ATENTIE!!! Problema rezolvata cu arbore de tipul (2).