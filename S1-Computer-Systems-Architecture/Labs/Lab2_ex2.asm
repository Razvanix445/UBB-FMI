bits 32 ;asamblare si compilare pentru arhitectura de 32 biti
;definim punctul de intrare in programul principal
global start        

extern exit ;indicam asamblorului ca exit exista, chiar daca noi nu o vom defini
import exit msvcrt.dll ;exit este o functie care incheie procesul, este definita in msvcrt.dll
;msvcrt.dll contine exit, printf si toate celelalte functii C-runtime importante

segment data use32 class=data ;segmentul de date in care se vor defini variabilele

        a db 4
        b db 2
        c db 20
        d db 5
        
segment code use32 class=code ;segmentul de cod
;c-(d+d+d)+(a-b)
    start:
    
        mov bl, [d] ;bl = d
        add bl, [d] ;bl = d + d
        add bl, [d] ;bl = d + d + d
        mov al, [c] ;ax = c
        sub al, bl  ;ax = ax - bx = c - (d + d + d)
        mov bl, [a] ;bl = a
        sub bl, [b] ;bl = bl - b = a - b
        add al, bl  ;ax = ax + bx = c - (d + d + d) + (a - b)
        
        push    dword 0
        call    [exit]