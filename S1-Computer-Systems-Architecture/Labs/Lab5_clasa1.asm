bits 32

global start        

extern exit
import exit msvcrt.dll

; a,b,c-byte; e-doubleword; x-qword
segment data use32 class=data

    a db 10
    b db 5
    c db 2
    e dd 10
    x dq 50

; a/2 + b*b - a*b*c + e + x
segment code use32 class=code
    start:
        ; a/2
        mov al, byte[a] ;al = a
        mov ah, 0       ;ax = a
        mov bl, 2       ;bl = 2
        div bl          ;al = ax / bl
        mov bx, ax      ;bx = ax
        
        ; b*b
        mov al, byte[b] ;al = b
        mov ah, 0       ;ax = b
        mul byte[b]     ;ax = al * b
        
        ; a/2 + b*b
        add bx, ax      ;bx = ax
        mov cx, 0       ;cx:bx = bx
        push cx
        push bx
        pop ebx         ;ebx = bx
        
        ; a*b*c
        mov al, byte[a] ;al = a
        mul byte[b]     ;ax = a*b
        mov cl, byte[c] ;cl = c
        mov ch, 0       ;cx = c
        mul cx          ;dx:ax = ax * cx
        push dx
        push ax
        pop eax         ;eax = ax * cx
        
        ; a/2 + b*b - a*b*c
        sub ebx, eax
        
        push dword 0
        call [exit]
