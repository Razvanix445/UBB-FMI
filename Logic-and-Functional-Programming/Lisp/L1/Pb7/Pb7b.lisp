;; Definiti o functie care substituie prima aparitie a unui element intr-o lista data.

;; substituieElement(l1 ... ln, eV, eN) = 
;;                     nil                                         , daca n = 0
;;                     {eN} U (l2 ... ln)                          , daca eV = l1 si n != 0
;;                     {l1} U substituieElement(l2 ... ln, eV, eN) , altfel (daca eV != l1)

(DEFUN substituieElement (eV eN l)
    (COND
        ((null l)                                           nil)
        ((AND (listp (CAR l)) (existaInLista eV (CAR l)))   (CONS (substituieElement eV eN (CAR l)) (CDR l)))
        ((AND (atom (CAR l)) (equal (CAR l) eV))            (CONS eN (CDR l)))
        (T                                                  (CONS (CAR l) (substituieElement eV eN (CDR l))))
    )
)

;; existaInLista(eV l1 ... ln) = false                       , daca n = 0
;;                               true                        , daca l1 == eV
;;                               existaInLista(eV, l2 ... ln), altfel (daca l1 != eV)
(DEFUN existaInLista (eV l)
    (COND
        ((null l)             nil)
        ((listp (CAR l))      (OR (existaInLista eV (CAR l)) (existaInLista eV (CDR l))))
        ((equal (CAR l) eV)   T)
        (T                    (existaInLista eV (CDR l)))
    )
)
;; (1 (4 (2 6) 1))


;; (DEFUN substituieElement (eV eN l)
;;     (COND
;;         ((null l)        nil)
;;         ((equal (CAR l) eV)  (CONS eN (CDR l)))
;;         (T               (CONS (CAR l) (substituieElement eV eN (CDR l))))
;;     )
;; )

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb7/Pb7b.lisp")
;; (substituieElement 2 10 '(1 (2 3) 2 5))
;; (substituieElement 2 10 '(1 (3 2) 2 5))
;; (substituieElement 2 10 '(1 (4 3) (2 5)))

;; (substituieElement 2 10 '(1 ((2 4) 3) 2 5))