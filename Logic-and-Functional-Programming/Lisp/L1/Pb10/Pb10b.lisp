;; Sa se scrie o functie care, primind o lista, intoarce multimea tuturor perechilor din lista.
;; De exemplu: (a b c d) => ((a b) (a c) (a d) (b c) (b d) (c d))

(DEFUN perechi (l currentE)
    (COND
        ((null l) nil)
        ((not (null (CADR l))) (CONS (list currentE (CADR l)) (APPEND (perechi (CDR l) currentE) (perechi (CDR l) (CADR l)))))
        (T nil)
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb10/Pb10b.lisp")
;; (perechi '(a b c d) 'a)

;; Kind of works ^_^