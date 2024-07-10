bits 32 ;asamblare si compilare pentru arhitectura de 32 biti
;definim punctul de intrare in programul principal
global start        

extern exit ;indicam asamblorului ca exit exista, chiar daca noi nu o vom defini
import exit msvcrt.dll ;exit este o functie care incheie procesul, este definita in msvcrt.dll
;msvcrt.dll contine exit, printf si toate celelalte functii C-runtime importante

segment data use32 class=data ;segmentul de date in care se vor defini variabilele

        a db 5
        b db 2
        c db 4
        d db 6
        
segment code use32 class=code ;segmentul de cod
;(a+b)*(c+d)
    start:
    
        mov al, [a] ;al = a
        add al, [b] ;al = al + b = a + b
        mov bl, [c] ;bl = c
        add bl, [d] ;bl = bl + d = c + d
        mul bl      ;al = al * bl = (a + b) * (c + d)

        push    dword 0
        call    [exit]