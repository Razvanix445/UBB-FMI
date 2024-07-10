bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    numar dd 0
    rezultat dd 0
    doi equ 2
    limita equ 32
    format_print db "Introduceti un numar in baza 2: ", 0
    format_citire db "%d", 0
    mesaj_afisare db "Numarul in baza 8 este: %d", 13, 10, 0

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
        
        ; mov dword [rezultat], 0
        mov eax, [numar]
        mov ebx, 0                  ; ebx va retine va retine rezultatul
        mov ecx, 0                  ; ecx va fi contorul
        mov edx, 0                  ; edx va reprezenta pozitia unui anumit bit
        shr eax, 1                  ; shifteaza eax cu un bit spre dreapta
        mov [numar], eax            ; mutam inapoi numarul shiftat
        mov eax, 0                  ; zerorizam eax pentru a avea un registru de lucru
        adc eax, eax                ; atribuim bitul shiftat la eax

    convert_loop:
        add [rezultat], eax
        inc ecx
        inc dl
        mov eax, [numar]            ; mutam inapoi numarul citit in eax
        shr eax, 1                  ; shifteaza eax cu un bit spre dreapta
        mov [numar], eax            ; mutam inapoi numarul shiftat
        mov eax, 0                  ; zerorizam eax pentru a avea un registru de lucru
        adc eax, eax                ; atribuim bitul shiftat la eax
        mov dh, 0
        cmp ecx, limita
        jae next
        
    bit_baza_10:
        imul eax, doi
        inc dh
        cmp dl, dh
        je convert_loop
        jmp bit_baza_10
        
        mov eax, [rezultat]
        
    next:
        ;printf(mesaj_afisare, rezultat)
        push eax
        push dword mesaj_afisare
        call [printf]
        add esp, 4*2
        
        push dword 0
        call [exit]
        ;mov dword[rezultat], 0