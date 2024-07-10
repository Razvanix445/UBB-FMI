;; Sa se scrie o functie care determina numarul de aparitii ale unui atom dat intr-o lista neliniara.

(DEFUN nrAparitii (l e nr)
    (COND
        ((null l) nr)
        ((listp (CAR l)) (+ (nrAparitii (CAR l) e nr) (nrAparitii (CDR l) e 0)))
        ((AND (atom (CAR l)) (equal (CAR l) e)) (nrAparitii (CDR l) e (+ nr 1)))
        (T (nrAparitii (CDR l) e nr))
    )
)

(DEFUN main (l e)
    (nrAparitii l e 0)
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb1/Pb1d.lisp")
;; (main '(1 4 A (3 A) (A (3 1 A)) A) 'A)