(defun eliminare(l e)
    (cond
        ((and (atom l) (equal l e)) nil)
        ((atom l) (list l))
        (T (list (mapcan #'(lambda (x) (eliminare x e)) l)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/Examen/EliminareCuMapcan.lisp")
;; (eliminare '(1 5 4 (2 3) 7 2 9) 2)