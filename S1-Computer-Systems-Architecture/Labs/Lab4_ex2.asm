bits 32

global start        

extern exit
import exit msvcrt.dll

;a - byte, b - word, c - double word, d - qword - Interpretare cu semn
segment data use32 class=data
    
    a db 45
    b dw -12
    c dd 30
    d dq 23
    
; 9.a-d+b+b+c
segment code use32 class=code
    start:
        ;    EDX                                 EAX
        ;d = xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        ;c =                                     xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        ;b =                                                       xxxxxxxx xxxxxxxx
        ;a =                                                                xxxxxxxx
        
        ;a-d
        mov al, byte[a]     ;al = a
        cbw
        cwde
        cdq
        mov ebx, dword[d+0]
        mov ecx, dword[d+2] ;ecx:ebx = d (conversie cu semn)
        sub eax, ebx
        sbb edx, ecx        ;edx:eax = a - d - CF
        mov ebx, eax
        mov ecx, edx        ;edx:eax = ecx:ebx
        
        ;b+b
        mov ax, 2   ;ax = 2
        imul word[b] ;ax = 2 * b = b + b
        cwde
        cdq
        
        ;a-d+b+b
        add ebx, eax ;ebx = ebx + eax
        adc ecx, edx ;ecx = ecx + edx + CF
        
        ;a-d+b+b+c
        mov eax, dword[c] ;eax = eax + c
        cdq
        add eax, ebx      ;eax = eax + ebx
        adc edx, ecx      ;edx = edx + ecx + CF

        push dword 0
        call [exit]