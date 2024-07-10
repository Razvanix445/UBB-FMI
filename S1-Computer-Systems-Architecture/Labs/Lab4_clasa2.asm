bits 32

global start        

extern exit
import exit msvcrt.dll

;a,b,c - byte, d - word
segment data use32 class=data
    
    a db 6
    b db 4
    c db 6
    d dw 10
    aux resb 1
    
segment code use32 class=code
; 19. [(a-b)*3+c*2]-d
    start:
        
        ;(a-b)*3
        mov al, byte[a]
        sub al, byte[b]
        mov bl, 3
        mul bl
        mov [aux], al
        
        ;c*2
        mov al, 2
        mul byte[c]
        
        ;(a-b)*3+c*2
        add al, [aux]
        mov ah, 0
        
        ;[(a-b)*3+c*2]-d
        sub ax, word[d]
        
        push    dword 0
        call    [exit]