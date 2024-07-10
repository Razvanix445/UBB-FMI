bits 32

global start        

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

; 7. Se dau doua numere naturale a si b (a: dword, b: word, definite in segmentul de date).
; Sa se calculeze a/b si sa se afiseze restul impartirii in urmatorul format: "<a> mod <b> = <rest>"
; Exemplu: pentru a = 23 si b = 5 se va afisa: "23 mod 5 = 3"
; Valorile vor fi afisate in format decimal (baza 10) cu semn.
segment data use32 class=data
    a dd 0      ; in a, se stocheaza prima valoare citita de la tastatura
    b dd 0      ; in b, se stocheaza a doua valoare citita de la tastatura
    mesaj1 db "Introduceti deimpartitul: ", 0
    mesaj2 db "Introduceti impartitorul: ", 0
    format1 db "%d", 0
    format db "%d mod %d = %d", 0
    
segment code use32 class=code
    start:
        ; printf [mesaj1]
        push dword mesaj1    ; se pune pe stiva adresa mesajului
        call [printf]        ; apelam functia printf pentru afisare
        add esp, 4 * 1       ; eliberam parametrii de pe stiva
        
        ; scanf [%d, &a]
        push dword a
        push dword format1
        call [scanf]
        add esp, 4 * 2
        
        ; printf [mesaj2]
        push dword mesaj2    ; se pune pe stiva adresa mesajului
        call [printf]        ; apelam functia printf pentru afisare
        add esp, 4 * 1       ; eliberam parametrii de pe stiva
        
        ; scanf [%d, &b]
        push dword b
        push dword format1
        call [scanf]
        add esp, 4 * 2
        
        ; dx:ax = a / b
        mov eax, [a]
        cdq
        mov ebx, dword[b]
        idiv ebx
        
        ; printf [format, <a> mod <b> = <rest>]
        push dword edx
        push dword [b]
        push dword [a]
        push dword format
        call [printf]        ; apelam functia printf pentru afisare
        add esp, 4 * 4       ; eliberam parametrii de pe stiva
        
        push dword 0
        call [exit]