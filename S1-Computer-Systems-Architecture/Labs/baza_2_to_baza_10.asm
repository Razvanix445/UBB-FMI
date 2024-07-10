bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

;Se citesc mai multe numere de la tastatura, in baza 2. Sa se afiseze aceste numere in baza 8.
segment data use32 class=data
    string dd 0
    rezultat dd 0
    cate_numere dd 0
    lungime equ 4
    format_cate_numere db "Cate numere vor fi citite de la tastatura? ", 0
    format_print db "Introduceti un numar in baza 2: ", 0
    format_citire_sir db "%s", 0
    format_citire db "%d", 0
    mesaj_afisare db "Numarul in baza 8 este: %d", 13, 10, 0

segment code use32 class=code
    start:

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

        mov ebx, 0
        mov eax, 0
        mov dh, 2
        mov ecx, lungime
        cld
        mov esi, string
        dec esi
        
    repeta:
        mov dl, cl
        dec dl
        inc esi
        cmp byte[esi], 0
        je inainte_de_final
        
        mov al, byte[esi]
        sub al, '0'
        
    inmultire:
        cmp dl, 0
        je next
        imul dh
        dec dl
        jmp inmultire
        
    next:
        add bx, ax
        loop repeta
        
    inainte_de_final:
        mov eax, ebx
        
    final:
        ;printf(mesaj_afisare, rezultat)
        push eax
        push dword mesaj_afisare
        call [printf]
        add esp, 4*2
        
        push dword 0
        call [exit]