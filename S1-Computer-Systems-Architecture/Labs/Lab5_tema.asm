bits 32

global start        

extern exit
import exit msvcrt.dll

; R, T, Q - doubleword
segment data use32 class=data

    ;R dd 01010010101010100101010101001010b
    ;T dd 10100100101010010111010101101001b
    R dd 12348a9fh
    T dd 5678af9ah
    Q dd 0
;a84c252b
;
;
; 26.
; Se dau 2 dublucuvinte R si T. Sa se obtina dublucuvantul Q astfel:
; bitii 0-6 din Q coincid cu bitii 10-16 a lui T
; bitii 7-24 din Q coincid cu bitii obtinuti 7-24 in urma aplicarii R XOR T
; bitii 25-31 din Q coincid cu bitii 5-11 a lui R
segment code use32 class=code
    start:
        
        ; bitii 0-6 din Q coincid cu bitii 10-16 a lui T
        mov eax, [T]                               ; mutam numarul de la care dorim sa extragem
        and eax, 00000000000000011111110000000000b ; formam masca de biti
        ror eax, 10                                ; rotim spre dreapta cu 10 biti pentru a pune pe bitii 0-6 din Q ceea ce se cere
        mov ebx, 0                  
        or ebx, eax                                ; concatenam
        
        ; bitii 25-31 din Q coincid cu bitii 5-11 a lui R
        mov eax, [R]                               ; mutam numarul de la care dorim sa extragem
        and eax, 00000000000000000000111111100000b ; formam masca de biti
        rol eax, 20                                ; rotim spre stanga cu 20 de biti pentru a pune pe bitii 25-31 din Q ceea ce se cere
        or ebx, eax                                ; concatenam
        
        ; bitii 7-24 din Q coincid cu bitii obtinuti 7-24 in urma aplicarii R XOR T
        mov eax, [R]                               ; mutam numarul R in EAX
        xor eax, [T]                               ; aplicam R XOR T
        and eax, 00000001111111111111111110000000b ; formam masca de biti
                                                   ; nu mai este nevoie sa rotim deoarece bitii sunt la locul potrivit
        or ebx, eax                                ; concatenam
        mov eax, ebx                               ; retinem rezultatul in EAX
        mov dword[Q], eax
        
        push dword 0
        call [exit]