bits 32

global start

extern exit
import exit msvcrt.dll

; segment de date
; a - byte, b - word
; a + b - c + d
segment data use32 class=data
    a db 78h
    b db 56h
    c db 34h
    d db 12h
    x resb 1
    
; segment de cod
segment code use32 class=code
    start:
        ; a + b
        mov al, [a]  ; al = a
        add al, [b]  ; al = a + b
        sub al, [c]  ;al = a + b - c
        add al, [d]  ;al = a + b - c + d
        
        ;stochez in variabila x
        mov [x]
    
        ; exit(0)
        push dword 0
        call [exit]
