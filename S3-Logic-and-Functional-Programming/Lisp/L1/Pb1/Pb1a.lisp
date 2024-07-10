;; Sa se insereze intr-o lista liniara un atom a dat dupa al 2-lea, al 4-lea, al 6-lea ... element

;; insereaza(l1 ... ln, e, pos) = nil, daca l - vida
;;                              = e U insereaza(l2 ... ln), daca pos % 2 == 0
;;                              = insereaza(l2 ... ln), daca pos % 2 != 0
(DEFUN insereaza (l e pos)
    (COND
        ((null l) nil)
        ((equal (mod pos 2) 0) (CONS (CAR l) (CONS e (insereaza (CDR l) e (+ pos 1)))))
        (T (CONS (CAR l) (insereaza (CDR l) e (+ pos 1))))
    )
)

(DEFUN main (l e)
    (insereaza l e 1)
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb1/Pb1a.lisp")
;; (main '(5 14 3 23 1 6) 0)