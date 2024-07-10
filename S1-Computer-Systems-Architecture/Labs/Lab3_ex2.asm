bits 32 ;asamblare si compilare pentru arhitectura de 32 biti
;definim punctul de intrare in programul principal
global start        

extern exit ;indicam asamblorului ca exit exista, chiar daca noi nu o vom defini
import exit msvcrt.dll ;exit este o functie care incheie procesul, este definita in msvcrt.dll
;msvcrt.dll contine exit, printf si toate celelalte functii C-runtime importante

segment data use32 class=data ;segmentul de date in care se vor defini variabilele

        a dw 10
        b db 4
        c db 2
        
segment code use32 class=code ;segmentul de cod
;a+b/c
    start:
    
        mov EAX, [b] ;EAX = b
        mov EBX, [c] ;EBX = c
        div EBX ;EAX = EAX / EBX = b / c
        mov EBX, [a] ;EBX = a
        add EAX, EBX ;EAX = EAX - EBX

        push    dword 0
        call    [exit]