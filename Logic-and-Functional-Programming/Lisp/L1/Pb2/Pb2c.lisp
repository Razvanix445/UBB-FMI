;; Sa se construiasca lista tuturor sublistelor unei liste. Prin sublista se intelege fie lista insasi, 
;; fie un element de pe orice nivel, care este lista.
;; Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10)) => ( (1 2 (3 (4 5) (6 7)) 8 (9 10)) (3 (4 5) (6 7)) (4 5) (6 7) (9 10) ).

(DEFUN subliste (l)
    (COND
        ((null l) nil)
        ((AND (listp (CAR l)) (null (CDR l))) (CONS (CAR l) (subliste (CAR l))))
        ((listp (CAR l)) (CONS (CAR l) (CONS (subliste (CAR l)) (subliste (CDR l)))))
        (T (subliste (CDR l)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb2/Pb2c.lisp")
;; (subliste '(1 (2 (3 4)) (5 6)))

;; (1 (2 (3 4)) (5 6)) => (2 (3 4))