bits 32 ;asamblare si compilare pentru arhitectura de 32 biti
;definim punctul de intrare in programul principal
global start        

extern exit ;indicam asamblorului ca exit exista, chiar daca noi nu o vom defini
import exit msvcrt.dll ;exit este o functie care incheie procesul, este definita in msvcrt.dll
;msvcrt.dll contine exit, printf si toate celelalte functii C-runtime importante

segment data use32 class=data ;segmentul de date in care se vor defini variabilele
        
        a dw 10
        b db 2
        c db 4
        
segment code use32 class=code ;segmentul de cod
;a-b*c
    start:
    
        mov al, [b]  ;al = b
        mov bl, [c]  ;bl = c
        mul bl       ;al = al * bl = b * c
        mov bl, al   ;bl = al
        mov ax, [a]  ;ax = a
        sub ax, bx   ;ax = ax - bx

        push    dword 0
        call    [exit]