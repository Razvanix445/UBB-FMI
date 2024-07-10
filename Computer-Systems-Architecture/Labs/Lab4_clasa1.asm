bits 32

global start        

extern exit
import exit msvcrt.dll

; a, b, c - byte; d - word
segment data use32 class=data
    
    a db 3
    b db 5
    c db 2
    d dw 12
    
segment code use32 class=code
; 21. d-[3*(a+b+2)-5*(c+2)]
    start:
        ;3*(a+b+2)
        mov al, 2
        add al, byte[a]
        add al, byte[b]
        mov bl, 3
        mul bl
        mov cl, al
        
        ;5*(c+2)
        mov al, 2
        add al, byte[c]
        mov bl, 5
        mul bl
        sub cl, al
        
        ;d-[3*(a+b+2)-5*(c+2)]
        mov al, cl
        mov ah, 0
        mov bx, word[d]
        sub bx, ax
        xchg bx, ax
        
        push    dword 0
        call    [exit]
