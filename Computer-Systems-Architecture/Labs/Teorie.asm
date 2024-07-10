bits 32

global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
    ; x dw -256, 256h
    ; y dw 256 | -256, 256h & 256
    ; z db $-z, y-x
      ; db 'y'-'x', 'y-x'
    ; a db 512>>2, -512<<2
    ; b dw z-a, !(z-a)
    ; c dd ($-b) + (d-$), 0h
    ; d db -128, 128^(~128)
    ; e times 2 resw 6
      ; times 2 dd 1234h, 5678h
    
segment code use32 class=code
    start:
    
    
    ;ex_3 (Directive)
    ; Def! Directivele sunt indicatii date asamblorului.
    
    ;ex_8 (Formula de calcul a offset-ului unui operand se utilizeaza in:)
    ; adresarea directa si indirecta
    
    ; ex_6 (Segmentul de memorie)
    ; Def! O diviziune logica a memoriei unui program caracterizat prin adresa de baza, dimensiune si tip.
    
    ; ex_5 (Directive)
    ; Def! Modul in care sunt generate codul si datele in momentul asamblarii.
    
    ; ex_4 ([ebx*3])
    ; un operand specificat in mod adresare indirecta la memorie, bazat-indexat cu factorul de scala 2