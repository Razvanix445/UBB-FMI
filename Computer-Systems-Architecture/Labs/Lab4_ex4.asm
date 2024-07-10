bits 32

global start        

extern exit
import exit msvcrt.dll

;a,b-byte; c-word; e-doubleword; x-qword - Interpretare cu semn
segment data use32 class=data
    
    a db 45
    b db 12
    c dw 2
    e dd 5
    x dq -17
    aux resd 1
    
; 9.(a-b+c*128)/(a+b)+e-x
segment code use32 class=code
    start:
        ;    EDX                                 EAX
        ;x = xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        ;e =                                     xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        ;c =                                                       xxxxxxxx xxxxxxxx
        ;a =                                                                xxxxxxxx
        ;b =                                                                xxxxxxxx
        
        ;a-b
        mov al, byte[a] ;al = a
        sub al, byte[b] ;al = a - b
        cbw
        mov bx, ax      ;bx = ax = a - b
        
        ;c*128
        mov ax, 128     ;ax = 128
        imul word[c]     ;ax = ax * c = 128 * c
        
        ;(a-b+c*128)
        add ax, bx      ;ax = ax + bx = a - b + c * 128
        cwd
        
        ;(a+b)
        mov bl, byte[a]     ;bl = a
        add bl, byte[b]     ;bl = a + b
        mov dword[aux], eax ;aux = eax (doubleword = doubleword)
        mov al, bl          ;al = bl
        cbw
        mov bx, ax          ;bx = ax
        cwd
        mov eax, dword[aux] ;eax = aux (doubleword = doubleword)
        
        ;(a-b+c*128)/(a+b)
        idiv bx          ;eax = eax / bx
        
        ;(a-b+c*128)/(a+b)+e
        add eax, dword[e] ;eax = eax + e = (a-b+c*128)/(a+b)+e
        
        ;(a-b+c*128)/(a+b)+e-x
        mov edx, 0          ;edx = 0
        sub eax, dword[x+0]
        sbb edx, dword[x+4] ;edx:eax = edx:eax - x - CF

        push dword 0
        call [exit]