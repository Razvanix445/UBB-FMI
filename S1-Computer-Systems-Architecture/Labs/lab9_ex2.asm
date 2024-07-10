bits 32
global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    max equ 100
    nums times max dd 0
    n dd 0
    mesaj db "Dati un numar: ", 0
    format db "%d", 0
    format2 db "%d ", 0
    
; Cititi de la tastatura si afisati un sir de numere intregi.
; (citirea se termina atunci cand utilizatorul introduce numarul ZERO)
segment code use32 class=code
    start:
        mov ecx, max
        jecxz final
        cld
        mov edi, nums
        mov ebx, 0
        
    citeste:
        push ecx
        
        ; printf [mesaj]
        push dword mesaj
        call [printf]
        add esp, 1*4
        
        ; scanf ("%d", &n)
        push dword n
        push dword format
        call [scanf]
        add esp, 2*4
        
        ; stochez in sir
        mov eax, [n]
        cmp eax, 0
        je afara
        stosd
        inc ebx
        pop ecx

    loop citeste
        
    afara:
        mov ecx, ebx
        cld
        mov esi, nums
        
    afiseaza:
        push ecx
        lodsd
        push eax
        push dword format2
        call [printf]
        add esp, 2*4
        pop ecx

    loop afiseaza

    final:
    
        ; exit(0)
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
