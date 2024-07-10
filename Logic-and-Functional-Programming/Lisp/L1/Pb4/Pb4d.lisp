;; Sa se construiasca o functie care intoarce maximul atomilor numerici dintr-o lista, de la nivelul superficial.

(DEFUN maxim (l maxAtom)
    (COND
        ((null l) maxAtom)
        ((AND (numberp (CAR l)) (< maxAtom (CAR l)) (maxim (CDR l) (CAR l))))
        (T (maxim (CDR l) maxAtom))
    )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb4/Pb4d.lisp")
;; (maxim '(a 2 c (5 (4 f) g) 3 i) 0)