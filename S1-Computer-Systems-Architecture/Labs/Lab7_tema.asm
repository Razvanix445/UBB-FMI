bits 32
global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data

    A dd 11223344h, 12345678h, 44332211h, 87654321h
    len equ ($-A)/4
    B1 times len db 0
    B2 times len db 0

; 12.
; Se da un sir A de dublucuvinte. Construiti doua siruri de octeti  
;  - B1: contine ca elemente partea inferioara a cuvintelor inferioara din A
;  - B2: contine ca elemente partea superioara a cuvintelor superioare din A
segment code use32 class=code
    start:
        mov ecx, len   ;vom parcurge elementele sirului A intr-o bucla loop cu len iteratii
        jecxz final
        cld
        mov esi, A     ;mutam in esi adresa sirului sursa A
        mov edi, B1    ;mutam in edi adresa sirului destinatie B1
        
    repeta_B1:
        movsb          ;in sirul B1, va fi pusa partea inferioara a cuvantului inferior pentru fiecare element din A
        lodsb          ;in al, vom avea octetul high al cuvantului low al elementului la care am ajuns
        lodsw          ;in ax, vom avea cuvantul high al elementului la care am ajuns
        jmp next_B1    ;se sare la instructiunea de loop
        
    next_B1:
        loop repeta_B1 ;daca mai sunt elemente de parcurs, se reia ciclul
        
        mov ecx, len   ;vom parcurge elementele sirului A intr-o bucla loop cu len iteratii
        cld
        mov esi, A     ;mutam in esi adresa sirului sursa A
        mov edi, B2    ;mutam in edi adresa sirului destinatie B2
        
    repeta_B2:
        lodsw          ;in ax, vom avea cuvantul low al elementului la care am ajuns
        lodsb          ;in al, vom avea partea low a cuvantului high al elementului la care am ajuns
        lodsb          ;in al, vom avea partea high a cuvantului high al elementului la care am ajuns
        stosb          ;in sirul B2, va fi pusa partea superioara a cuvantului superior pentru fiecare element din A
        jmp next_B2    ;se sare la instructiunea de loop
    
    next_B2:
        loop repeta_B2 ;daca mai sunt elemente de parcurs, se reia ciclul
    
    final:           ;incheiem programul
    
        push    dword 0
        call    [exit]