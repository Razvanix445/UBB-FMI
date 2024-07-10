bits 32

global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
    
    mod_acces db 'r', 0
    nume_fisier db "ana.txt", 0
    descriptor_fisier dd, 0
    
    mesaj_eroare db "Fisierul nu a putut fi deschis!", 0
    
segment code use32 class=code
    start:
        ; deschidere fisier
        ; Eax = fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 2*4
        
        ; verificam daca fisierul a fost deschis cu succes
        cmp eax, 0
        je final
        
        mov [descriptor_fisier], eax
        
        ;prelucram datele din fisier
        
        ;
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
