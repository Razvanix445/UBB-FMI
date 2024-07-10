bits 32

global start        

extern exit
import exit msvcrt.dll

;a - byte, b - word, c - double word, d - qword - Interpretare fara semn
segment data use32 class=data
    
    a db 9
    b dw 12
    c dd 31
    d dq 240
    aux resd 1

; 9.(d+d-b)+(c-a)+d
segment code use32 class=code
    start:
        ;(d+d-b)
        ;    EDX                                 EAX
        ;d = xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        ;b =                                                       xxxxxxxx xxxxxxxx
        mov eax, dword[d+0]
        mov edx, dword[d+4]
        add eax, dword[d+0]
        adc edx, dword[d+4] ;edx:eax = d + d + CF (conversie fara semn)
        sub eax, [b]        ;edx:eax = d + d - b
        
        ;(c-a)
        ;    BH                BL
        ;c = xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        ;a =                            xxxxxxxx
        mov bl, byte[a]
        mov bh, 0           ;bx = a (conversie fara semn)
        mov cx, 0           ;cx:bx = a (conversie fara semn)
        mov word[aux+0], bx
        mov word[aux+2], cx ;aux = a
        mov ebx, dword[c]   ;ebx = c
        sub ebx, dword[aux] ;ebx = ebx - aux
        
        ;(d+d-b)+(c-a)
        add eax, ebx ;eax = eax + ebx
        
        ;(d+d-b)+(c-a)+d
        add eax, dword[d+0]
        adc edx, dword[d+2] ;edx:eax = edx:eax + d + CF
        
        push dword 0
        call [exit]