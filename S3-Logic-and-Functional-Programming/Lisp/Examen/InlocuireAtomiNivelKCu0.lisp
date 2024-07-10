;; inlocuire a atomilor de pe nivelul k cu 0
(DEFUN inlocuire(l k nivel)
    (COND
        ((AND (ATOM l) (equal nivel k)) (list 0))
        ((ATOM l) (list l))
        (T (list (mapcan #'(lambda (x) (inlocuire x k (+ nivel 1))) l)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/Examen/InlocuireAtomiNivelKCu0.lisp")
;; (inlocuire '(1 (5 (4 3)) (2 3) 8) 2 0)

(DEFUN inlocuire2(l k nivel)
    (COND
        ((AND (ATOM l) (equal nivel k)) 0)
        ((ATOM l) l)
        (T (mapcar #'(lambda (x) (inlocuire2 x k (+ 1 nivel))) l))
    )
)