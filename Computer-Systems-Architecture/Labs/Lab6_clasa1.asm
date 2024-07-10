bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s1 dw 1234h, 67abh, 89cdh
    len equ ($-s1)/2
    s2 dw 2345h, 5678h, 4567h
    d times len dw 0
    
; Se dau doua siruri de cuvinte s1 si s2.
; Se cere sirul de cuvinte d obtinut in interpretarea cu semn, astfel:
; - d[i] = s1[i], daca s1[i] > s2[i]
; - d[i] = s2[i], altfel.
segment code use32 class=code
    start:
        mov ecx, len
        jecxz sfarsit
        cld
        mov esi, s1
        mov edi, s2
    repeta:
        cmpsw
        jg edi_devine_esi
        jmp next
    edi_devine_esi:
        lodsw
        stosw
    next:
        loop repeta
        
        mov ecx, len
        jecxz sfarsit
        cld
        mov esi, s2
        mov edi, d
        rep movsw
    sfarsit:
        
        push dword 0
        call [exit]