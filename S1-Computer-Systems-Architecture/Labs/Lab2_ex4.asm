bits 32 ;asamblare si compilare pentru arhitectura de 32 biti
;definim punctul de intrare in programul principal
global start        

extern exit ;indicam asamblorului ca exit exista, chiar daca noi nu o vom defini
import exit msvcrt.dll ;exit este o functie care incheie procesul, este definita in msvcrt.dll
;msvcrt.dll contine exit, printf si toate celelalte functii C-runtime importante

segment data use32 class=data ;segmentul de date in care se vor defini variabilele

        d dw 2
        
segment code use32 class=code ;segmentul de cod
;[100*(d+3)-10]/d
    start:
    
        mov ax, [d] ;ax = d
        add ax, 3   ;ax = ax + 3 = d + 3
        mov bx, 100 ;bx = 100
        mul bx      ;ax = ax * bx = (d + 3) * 100
        sub ax, 10  ;ax = ax - 10 = (d + 3) * 100 - 10
        div word[d] ;ax = ax / d = ((d + 3) * 100 - 10) / d
        
        push    dword 0
        call    [exit]