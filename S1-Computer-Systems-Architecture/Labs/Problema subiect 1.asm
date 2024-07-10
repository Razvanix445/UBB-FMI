bits 32

global start        

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir dd 1234A678h, 12785634h, 1A4D3C2Bh
    len equ ($-sir)/4
    string times len dw 0
    nr_biti_1 dd 0
    format_afisare db "%d", 0
    
segment code use32 class=code
    start:
        cld
        mov esi, sir
        mov edi, string
        mov ecx, len
    repeta:
        lodsb
        lodsb
        stosb
        lodsb
        lodsb
        stosb
        loop repeta

        mov ecx, len
        mov esi, 0
        mov eax, 0
        mov ax, [string]
    numarare:
        cmp ax, 0
        je next
        shl ax, 1
        adc dword[nr_biti_1], 0
        jmp numarare
        
    next:
        mov eax, 0
        add esi, 2
        mov ax, [string+esi]
        loop numarare
        
    afisare:
        ;printf(format_afisare, nr_biti_1)
        push dword[nr_biti_1]
        push dword format_afisare
        call [printf]
        
        push dword 0
        call [exit]