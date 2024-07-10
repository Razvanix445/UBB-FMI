bits 32 ;asamblare si compilare pentru arhitectura de 32 biti
;definim punctul de intrare in programul principal
global start        

extern exit ;indicam asamblorului ca exit exista, chiar daca noi nu o vom defini
import exit msvcrt.dll ;exit este o functie care incheie procesul, este definita in msvcrt.dll
;msvcrt.dll contine exit, printf si toate celelalte functii C-runtime importante

segment data use32 class=data ;segmentul de date in care se vor defini variabilele

        a dw 5
        b dw 2
        c dw 40
        d dw 25
        
segment code use32 class=code ;segmentul de cod
;(c+c+c)-b+(d-a)
    start:
    
        mov ax, [c] ;ax = c
        add ax, [c] ;ax = c + c
        add ax, [c] ;ax = c + c + c
        sub ax, [b] ;ax = c + c + c - b
        mov bx, [d] ;bx = d
        sub bx, [a] ;bx = d - a
        add ax, bx  ;ax = ax + bx
        
        push    dword 0
        call    [exit]