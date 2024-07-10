bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    numar dd 0
    format_print db "Introduceti un numar in baza 2: ", 0
    format_citire db "%s", 0
    mesaj_afisare db "Numarul in baza 8 este: %o", 13, 10, 0

segment code use32 class=code
    start:
        ;printf(format_print)
        push dword format_print
        call [printf]
        add esp, 4*1
        
        ;scanf(format_citire, &numar)
        push dword numar
        push dword format_citire
        call [scanf]
        add esp, 4*2

        ;printf(mesaj_afisare, numar)
        push numar
        push dword mesaj_afisare
        call [printf]
        add esp, 4*2
        
        push dword 0
        call [exit]