;; Sa se scrie o functie care transforma o lista liniara intr-o multime.

(DEFUN transformare (l)
    (COND
        ((null l) nil)
        ((< (dublura l (CAR l) 0) 2) (CONS (CAR l) (transformare (CDR l))))
        (T (transformare (CDR l)))
    )
)

(DEFUN dublura (l e contor)
    (COND
        ((null l) contor)
        ((equal (CAR l) e) (dublura (CDR l) e (+ contor 1)))
        (T (dublura (CDR l) e contor))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb2/Pb2d.lisp")
;; (dublura '(2 5 4 2 3 1 2) 2 0)
;; (transformare '(2 5 4 2 3 4 2))