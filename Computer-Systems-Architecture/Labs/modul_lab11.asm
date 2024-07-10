bits 32

global conversie       

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
extern string, rezultat

segment data use32 class=data public

    format_print db "Introduceti un numar in baza 2: ", 0
    format_citire_sir db "%s", 0
    
segment code use32 class=code public
    conversie:
        mov dword[rezultat], 0
        ;printf(format_print)
        push dword format_print
        call [printf]
        add esp, 4*1
        
        ;scanf(format_citire_sir, &numar)
        push dword string
        push dword format_citire_sir
        call [scanf]
        add esp, 4*2

        mov ecx, 0
        cld
        mov esi, string
        mov edi, rezultat
        mov dl, 0
        
    repeta:
        lodsb
        cmp al, 0
        je next
        inc ecx
        jmp repeta
        
    next:
        std
        lodsb
        lodsb
        
    reverse:
        add [rezultat], al
        cmp ecx, 0
        je final
        dec ecx
        inc dl
        mov dh, 0
        lodsb        ; al = lowest significant bit
        sub al, '0'
        
    convert:
        inc dh
        cmp dl, dh
        je reverse
        imul ax, 2
        jmp convert
        
    final:
        ret
        
        push dword 0
        call [exit]