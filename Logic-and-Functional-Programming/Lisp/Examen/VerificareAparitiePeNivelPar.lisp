;; verificare aparitie a elementului e pe nivel par
(DEFUN sau(l)
    (COND
        ((null l) nil)
        (T (OR (CAR l) (sau (CDR l))))
    )
)

(DEFUN verificareAparitie(l e nivel)
    (COND
        ((AND (ATOM l) (equal l e) (equal 0 (mod nivel 2))) T)
        ((AND (ATOM l) (equal l e) (equal 1 (mod nivel 2))) nil)
        ((AND (ATOM l) (not (equal l e))) nil)
        (T (FUNCALL #'sau (mapcar #'(lambda (x) (verificareAparitie x e (+ 1 nivel))) l)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/Examen/verificareAparitiePeNivelPar.lisp")
;; (verificareAparitie '(1 (5 (4 3)) (2 3) 8) 5 0)