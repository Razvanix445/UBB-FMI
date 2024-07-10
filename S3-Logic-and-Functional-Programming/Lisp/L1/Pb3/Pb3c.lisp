;; Definiti o functie care sorteaza fara pastrarea dublurilor o lista liniara.

(DEFUN sortare (l)
    (COND
        ((null l) nil)
        (T (insert (sortare (CDR l)) (CAR l)))
    )
)

(DEFUN insert (l e)
    (COND
        ((null l) (list e))
        ((equal e (CAR l)) l) ;; Pentru nepastrarea dublurilor, fara aceasta linie, se pastreaza dublurile
        ((< e (CAR l)) (CONS e l))
        (T (CONS (CAR l) (insert (CDR l) e)))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb3/Pb3c.lisp")
;; (sortare '(3 2 7 5 1 4)) => (1 2 3 4 5 7)

;; Explanation Step By Step:
;; insert (sortare (2 7 5 1 4) 3) =>                               => insert ((1 2 4 5 7) 3) => ... => 1 U 2 U insert ((4 5 7) 3) => (1 2 3 4 5 7) ^_^
;;            | (1 2 4 5 7)
;;           insert (sortare (7 5 1 4) 2) =>                         => insert ((1 4 5 7) 2) => 1 U insert ((4 5 7) 2) => 1 U (2 4 5 7) = (1 2 4 5 7)
;;                      | (1 4 5 7)
;;                     insert (sortare (5 1 4) 7) =>                   => insert ((1 4 5) 7) => 1 U insert ((4 5) 7) => ... => 1 U 4 U 5 U (7) => (1 4 5 7)
;;                                | (1 4 5)
;;                               insert (sortare (1 4) 5) =>             => insert ((1 4) 5) => 1 U insert ((4) 5) => 1 U 4 U insert (() 5) => (1 4 5)
;;                                          | (1 4)
;;                                         insert (sortare (4) 1) =>       => insert ((4) 1) => (1 4)
;;                                                    | (4)
;;                                                   insert (sortare () 4) => insert (nil 4) => (4)


;; (3 2 7 5 1 4) (4)
;; (3 2 7 5 1 4) (1 4)
;; (3 2 7 5 1 4) (1 4 5)
;; (3 2 7 5 1 4) (1 4 5 7)
;; (3 2 7 5 1 4) (1 2 4 5 7)
;; (3 2 7 5 1 4) (1 2 3 4 5 7)