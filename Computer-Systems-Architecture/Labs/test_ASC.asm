bits 32

global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
    ;a db '123', '4', '56'
    ;b resd 1
    ;c dw 4-1, 3
    ;len equ ($-a)
    a dw 0ABCDh, 0fedcH
segment code use32 class=code
    start:
        mov esi, a
        lodsw
        lodsb
        
        ;mov ax, 65535
        ;mov bl, 10
        ;idiv bl
        ;mov eax, len
    
        ;mov al, 10000000b
        ;mov bl, 0
        ;sub bl, al
        ;mov eax, -3
        ;mov ebx, -4
    
        ;mov ax, 0000010101001010b
        ;sbb ax, 1111111111111110b
    
    
        ;mov al, -2
        ;cbw
        ;mov ch, 3
        ;imul ch
    
    ; daca OF = 1 si SF = 1, atunci CF = 0 (la add)
    
    ; INTEGER OVERFLOW!
        ;mov ax, 65535
        ;mov bl, 10
        ;div bl
    
        ;mov ecx, -1 << 12  ; ECX = FFFFF000h
    
        ;cmp 0, 0     ;SYNTAX ERROR!
    
        ;mov al, -2
        ;mov bl, -128
        ;mul bl
    
        ;mov al, -1
        ;neg al
    
    ; AX = DCABh
        ;mov esi, a
        ;lodsb
        ;lodsw
    
        ;mov al, -2
        ;mov bl, -128
        ;imul bl        ; bl = -2 * -128 = 256 = 100h
    
    ;EX. CF = 1; OF = 1
        ;mov dh, 62h
        ;mov ch, 200
        ;sub dh, ch
    
    ;EX. AX = 100b
        ;mov al, -2
        ;mov bl, -128
        ;imul al
    
    ;EX. AH = 00h; AL = FFh
        ;mov ax, -1
        ;mov bh, 1
        ;idiv bh
    
    ;EX. SYNTAX ERROR!!! (operation size not specified!)
    ;a resw 1
    ;b db 3ch, 4dh
        ;mov [a], -1
    
    ;EX. SYNTAX ERROR!!! (add WORD[x], 2)!
        ;mov ax, 054ah
        ;add [x], 2
        ;jz a2
        ;a2:
    
    ;EX. Care este efectul secventei?
        ;mov eax, -3&-4
        ;xor al, al
        ;cbw
        ;cwd  ;ATENTIE!!! BAGA IN DX:AX, NU IN EAX!!!
    
    ;EX. EXECUTION ERROR!
        ;mov ax, -1
        ;mov bh, 1
        ;div bh
    
    ;EX. offset 6 = ? (00h)
    ;a times 2 dd 1234h
    ;b db 16h
    
    ;EX. NU functioneaza! INTEGER OVERFLOW!
        ;mov ax, 600
        ;cwde
        ;mov cx, -2
        ;idiv cx
    
    ;EX. Rezultatul este:
        ;mov ax, 65535
        ;mov bl, 10
        ;idiv bl
    
    ;AL = 0DCh
    ;a dw 0ABCDh, 0FEDCh
        ;mov esi, a
        ;lodsw
        ;stosb
    
    ;EX. Care este valoarea instructiunii (2&7)^(23^(~31)) = ?
    ; ~ = NOT; & = AND; ^ = OR
    ;mov ah, (2&7)^(23^(~31)) ; 0F5h
    
    ;ex_9 (ce octeti se genereaza in memorie?)
    ;a times 3 dw 10
    ;b dd 0xAB
        
    ;ex_8 (rezultatul este = ? FC04h)
        ; mov al, -2
        ; mov bl, -128
        ; mul al
    
    ; ex_7 (loop de 16 ori ca val din AX sa fie in BX)
    ; mov ecx, 0
    ; mov ax, 10101010b
    ; mov bx, 11001100b
    ; repeta:
        ; cmp ecx, 16
        ; ja final
        ; shl ax, 1
        ; rcl bx, 1
        ; inc ecx
    ; loop repeta
    ; final:
    
    ; ex_3 (Ce face programul?)
    ;x dw 0FFFDh
        ; mov ax, 054Ah
        ; add byte[x], 2
        ; jz a2
        
        ; a2:
    ;Answer: nu va executa un salt la adresa determinata de a2
    
    ; ex_1 (face corect a - b?)
    ; a dd 10000h
    ; b dw 2
        ; mov ax, [a]    ;ax = 0000h
        ; mov dx, [a+2]  ;dx = 0001h
        ; sub ax, [b]    ;ax = 0001h - 0002h = FFFEh
        ; sbb dx, 0      ;dx = 0000h
        
        push dword 0
        call [exit]