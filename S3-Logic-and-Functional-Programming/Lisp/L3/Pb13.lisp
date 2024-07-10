;; Definiti o functie care substituie un element prin altul la toate nivelurile unei liste date.

;; substituie(e, eV, eN) = 
;;       = eN , daca e - numar si daca e = eV
;;       = e , daca e - numar si e != eV
;;       = U substituie(li), i = (1, n), altfel
(DEFUN substituie (L eV eN)
    (COND 
        ((AND (NUMBERP L) (equal L eV)) eN)
        ((AND (NUMBERP L) (not (equal L eV))) L)
        (T (MAPCAR #'(lambda (x) (substituie x eV eN)) L))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L3/Pb13.lisp")
;; (substituie '(1 3 (2 5) (2 (2 4) ((2 3) 1)) (2 3) 3) 2 0)