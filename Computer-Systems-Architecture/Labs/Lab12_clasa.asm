bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    result db "Numarul in baza 8 este: ", 0
    buffer resb 16
  
 
section .bss
    number resb 16

segment code use32 class=code
    start:
        ; Citeste numarul in baza 2 de la tastatura
        mov eax, 3
        mov ebx, 0
        mov ecx, number
        mov edx, 16
        int 0x80

        ; Converteste numarul din baza 2 in baza 10
        mov ebx, 0 ; ebx va fi rezultatul conversiei
        mov ecx, 0 ; ecx va fi contorul nostru
        mov edx, number ; edx va fi numarul nostru in baza 2

    convert_loop:
        mov eax, [edx + ecx] ; incarca caracterul de pe pozitia ecx din numar
        sub eax, '0' ; scade ASCII value of '0' pentru a obtine valoarea numerica a caracterului
        add ebx, eax ; adauga valoarea numerica la rezultatul nostru
        imul ebx, 2 ; inmulteste rezultatul cu 2 pentru a tine cont de baza 2
        inc ecx ; incrementeaza contorul
        cmp ecx, 16 ; verifica daca am parcurs tot numarul
        jne convert_loop ; daca nu, continuam conversia

        ; Converteste numarul din baza 10 in baza 8
        mov edx, 0 ; edx va fi rezultatul nostru in baza 8
        mov esi, ebx ; esi va fi numarul nostru in baza 10

    convert_loop_2:
        xor edx, edx ; seteaza rezultatul la 0
        mov eax, esi
        mov ecx, 8
        div ecx ; eax = esi / 8, edx = esi % 8
        add dl, '0' ; adauga ASCII value of '0' pentru a obtine caracterul in baza 8
        mov [buffer + ecx], dl ; salveaza caracterul in buffer
        dec ecx
        mov esi, eax ; actualizeaza numarul nostru cu catul impartirii
        cmp esi, 0 ; verifica daca am ajuns la final
        jne convert_loop_2 ; daca nu, continuam conversia

      ; Afiseaza rezultatul
        mov eax, 4
        mov ebx, 1
        mov ecx, result
        mov edx, 23
        int 0x80

        mov eax, 4
        mov ebx, 1
        mov ecx, buffer
        mov edx, 16
        int 0x80

      ; Iesi din program
        mov eax, 1
        xor ebx, ebx
        int 0x80
