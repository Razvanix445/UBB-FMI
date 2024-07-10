; Definiti o functie care interclaseaza fara pastrarea dublurilor doua liste liniare sortate.

;; interclasare(l1 ... ln, p1 ... pm) = p1 ... pm, daca n = 0
;;                                      l1 ... ln, daca m = 0
;;                                      {l1} U interclasare(l2 ... ln, p1 ... pm), daca l1 < p1
;;                                      {p1} U interclasare(l1 ... ln, p2 ... pm), daca l1 > p1
;;                                      {l1} U interclasare(l2 ... ln, p2 ... pm), daca l1 = p1

(DEFUN interclasare (l p)
    (COND
        ((null l) p)
        ((null p) l)
        ((< (CAR l) (CAR p)) (CONS (CAR l) (interclasare (CDR l) p)))
        ((> (CAR l) (CAR p)) (CONS (CAR p) (interclasare l (CDR p))))
        ((= (CAR l) (CAR p)) (CONS (CAR l) (interclasare (CDR l) (CDR p))))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb7/Pb7d.lisp")
;; (interclasare '(1 3 4 5 9) '(2 3 5 8 9 10))