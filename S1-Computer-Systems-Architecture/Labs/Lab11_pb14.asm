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
    format_cate_numere db "Cate numere vor fi citite de la tastatura? ", 0
    format_print db "Introduceti un numar in baza 2: ", 0
    format_citire_sir db "%s", 0
    format_citire db "%d", 0
    mesaj_afisare db "Numarul in baza 8 este: %o", 13, 10, 0

segment code use32 class=code
    start:
        ;printf(format_print)
        push dword format_cate_numere
        call [printf]
        add esp, 4*1
        
        ;scanf("%d", &cate_numere)
        push dword cate_numere
        push dword format_citire

        call [scanf]
        add esp, 4*2
    
    for_loop:
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
        je next2
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
        
    next2:
        ;printf(mesaj_afisare, rezultat)
        push dword [rezultat]
        push dword mesaj_afisare
        call [printf]
        add esp, 4*2
        
        sub dword[cate_numere], 1
        cmp dword[cate_numere], 0
        jne for_loop
    
        push dword 0
        call [exit]