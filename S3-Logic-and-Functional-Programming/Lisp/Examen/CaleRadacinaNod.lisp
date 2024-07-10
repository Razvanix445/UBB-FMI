(DEFUN existaNodInLista(l e)
    (COND
        ((null l) nil)
        ((AND (ATOM (CAR l)) (equal (CAR l) e)) T)
        ((listp (CAR l)) (OR (existaNodInLista (CAR l) e) (existaNodInLista (CDR l) e)))
        (T (existaNodInLista (CDR l) e))
    )
)

(DEFUN cale(l e)
    (COND
        ((AND (ATOM l) (equal l e)) l)
        ((AND (ATOM l) (not (equal l e))) nil)
        ((AND (listp l) (existaNodInLista l e)) (append (list (CAR l)) (mapcan #'(lambda (x) (cale x e)) l)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/Examen/CaleRadacinaNod.lisp")
;; (cale '(1 (5 (4 3)) (2 3) 8) 5)

(DEFUN existaNodInLista2(l e)
    (COND
        ((null l) nil)
        ((AND (ATOM (CAR l)) (equal (CAR l) e)) T)
        ((listp (CAR l)) (OR (existaNodInLista2 (CAR l) e) (existaNodInLista2 (CDR l) e)))
        (T (existaNodInLista2 (CDR l) e))
    )
)

(DEFUN cale2(l e nivel)
    (COND
        ((AND (ATOM l) (equal l e)) nivel)
        ((AND (ATOM l) (not (equal l e))) 0)
        ((AND (listp l) (existaNodInLista2 l e)) (apply #'max (mapcar #'(lambda (x) (cale2 x e (+ 1 nivel))) l)))
    )
)
;; ???