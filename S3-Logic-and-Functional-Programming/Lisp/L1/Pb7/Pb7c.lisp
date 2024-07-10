; Sa se inlocuiasca fiecare sublista a unei liste cu ultimul ei element.
; Prin sublista se intelege element de pe primul nivel, care este lista.
; Exemplu: (a (b c) (d (e (f)))) => (a c (e (f))) => (a c (f)) => (a c f)
;          (a (d (b c)) (d ((e) f))) => (a c ((e) f)) => (a c f)

;; inverseaza(l1 ... ln) = nil                           , daca n = 0
;;                         inverseaza(l2 ... ln) (+) (l1), altfel
(DEFUN inverseaza(l)
  (COND
    ((null l) nil)
    (T        (APPEND (inverseaza (CDR l)) (list (CAR l))))
  )
)

;; lastElement(l1 ... ln) = lastElement(p1), daca l - lista, (unde p1 ... pn = inverseaza(l))
;;                          l              , altfel
(DEFUN lastElement (l)
	(if (listp l) 
        (lastElement (CAR (inverseaza l)))
        l
    )
)

;; inlocuiesteSubliste(l1 ... ln) = nil                                               , daca n = 0
;;                                  lastElement(l1) (+) inlocuiesteSubliste(l2 ... ln), daca l1 - lista
;;                                  {l1} (+) inlocuiesteSubliste(l2 ... ln)           , altfel
(DEFUN inlocuiesteSubliste (l)
  (COND
    ((null l)   nil)
    ((listp l)  (CONS (lastElement (CAR l)) (inlocuiesteSubliste (CDR l))))
    (T          (CONS (CAR l) (inlocuiesteSubliste (CDR l))))
  )
)

;; (load "C:/Users/razva/Documents/Lisp/L1/Pb7/Pb7c.lisp")
;; (inlocuiesteSubliste '(a (b c) (d (e (f)))))