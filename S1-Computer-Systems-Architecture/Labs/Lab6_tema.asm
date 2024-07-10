bits 32

global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
    
    s db 1, 3, -2, -5, 3, -8, 5, 0
    l equ $-s
    d1 times l db 0
    d2 times l db 0
    
; 14. Se da un sir de octeti S. Sa se construiasca un sir D1 care sa contina toate numerele pozitive si un sir D2 care sa contina toate numerele negative din S.
segment code use32 class=code
    start:
        
        mov ecx, l ; punem lungimea in ECX pentru a putea realiza bucla loop de ECX ori
        jecxz sfarsit
        mov esi, 0
        mov edi, 0
        mov edx, 0
    repeta:
        ; cmp byte[s+esi], 10000000b ; realizeaza o scadere fictiva pentru a afla daca elementul respectiv din s este pozitiv sau negativ; 10000000b = -128d
        mov al, [s+esi]
        
        ; numarul este pozitiv sau negativ?
        cmp al, 0
        jl negativ
        
        ; se executa cazul in care numarul este pozitiv sau 0
        mov [d1+edi], al ; mutam elementul pozitiv in sirul d1
        inc edi
        jmp dupa
        
    negativ:
        mov [d2+edx], al ; mutam elementul negativ in sirul d2
        inc edx
    
    dupa:
        inc esi
        loop repeta ; se reiau instructiunile pana ECX devine 0
    sfarsit:
        
        push dword 0
        call [exit]