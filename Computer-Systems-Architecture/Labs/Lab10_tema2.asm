bits 32

global start        

extern exit, fopen, fprintf, fclose, perror
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
import perror msvcrt.dll

; 24. Se dau un nume de fisier si un text (definite in segmentul de date).
; Textul contine litere mici, litere mari, cifre si caractere speciale. 
; Sa se inlocuiasca toate CIFRELE din textul dat cu caracterul 'C'.
; Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut prin inlocuire in fisier.
segment data use32 class=data
    mod_acces db "w", 0
    nume_fisier db "lab10_text.txt", 0
    text db "oibO2'Y4Bi12on90.[;p]e", 10, 13, 0
    l equ $-text
    descriptor_fisier dd 0
    
segment code use32 class=code
    start:
        mov ecx, l
        mov esi, 0
        jecxz final
        
    repeta:
        ; verificam daca codul ascii al caracterului este mai mare decat 48 (codul ascii al caracterului 1, primul caracter care ne intereseaza pentru schimbare in caracterul 'C')
        ; daca este adevarat, se sare la loop-ul "mai_mare:"
        cmp byte[text+esi], 48
        jge mai_mare
        
        ; daca s-a parcurs tot textul, se trece la scrierea acestuia in fisier
        cmp ecx, 0
        je sari_la_fisier
        jmp next                   ; reluam loop-ul repeta
        
    mai_mare:
        ; verificam daca codul ascii al caracterului este mai mic sau egal decat 57 (codul ascii al caracterului 9, ultimul caracter care ne intereseaza pentru schimbare in caracterul 'C')
        ; daca este adevarat, se sare la loop-ul "este_cifra:"
        cmp byte[text+esi], 57
        jle este_cifra
        jmp next                   ; reluam loop-ul repeta
        
    este_cifra:
        ; daca s-a ajuns aici, inseamna ca avem un caracter cifra pe care il vom schimba cu caracterul 'C'
        mov byte[text+esi], 'C'
        jmp next                   ; reluam loop-ul repeta
        
    next:
        inc esi
        loop repeta
    
    sari_la_fisier:
        ; apelam fopen pentru a crea fisierul
        ; functia va returna in EAX descriptorul fisierului sau 0 in caz de eroare
        ; eax = fopen(nume_fisier, mod_acces)
        push dword mod_acces     
        push dword nume_fisier
        call [fopen]
        add esp, 4 * 2                ; eliberam parametrii de pe stiva
        
        mov [descriptor_fisier], eax  ; salvam valoarea returnata de fopen in variabila descriptor_fisier 
        
        ; verificam daca fisierul a fost deschis cu succes
        cmp eax, 0
        je final
        
        ; scriem textul in fisierul deschis
        ; fprintf(descriptor_fisier, text)
        push dword text
        push dword [descriptor_fisier]
        call [fprintf]
        add esp, 4 * 2                ; eliberam parametrii de pe stiva
        
        ; inchidem fisierul
        ; fclose(descriptor_fisier)
        push dword [descriptor_fisier]
        call [fclose]
        add esp, 4 * 1
        
    final:
        
        push dword 0
        call [exit]
