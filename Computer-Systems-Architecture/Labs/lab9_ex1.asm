bits 32
global start

extern exit, gets, printf               ;gets = get string
import exit msvcrt.dll
import gets msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

    s times 100 db 0
    format db "%s", 0
    mesaj db "Dati o propozitie", 13, 10, 0
    
; Cititi de la tastatura si afisati o propozitie
; (mai multe siruri de caractere separate prin SPATII si care se termina cu caracterul '.')
segment code use32 class=code
    start:
        ; printf(mesaj)
        push dword mesaj
        call [printf]
        add esp, 1*4
        
        ; gets(s)
        push dword s
        call [gets]
        add esp, 1*4
        
        ; printf(%s, s)
        push dword s
        push dword format
        call [printf]
        add esp, 2*4
        
        
        ; exit(0)
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
