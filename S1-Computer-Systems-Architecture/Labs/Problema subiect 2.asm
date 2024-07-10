bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    N dd 0
    dublucuvant dd 0
    sir times 10 dd 0
    suma_cifre_pare times 10 db 0
    format_citire db '%d', 0
    contor dd 0
    copie dd 0
    copie_cifra db 0
    
segment code use32=code
    start:
        ;scanf(format_citire, N)
        push dword N
        push dword format_citire
        call [scanf]
        add esp, 4*2
        
        mov ecx, [N]
        mov esi, sir
        mov edi, suma_cifre_pare
        jecxz final
    repeta:
        mov [contor], ecx
        ;scanf(format_citire, dublucuvant)
        push dword dublucuvant
        push dword format_citire
        call [scanf]
        add esp, 4*2
        mov ecx, [contor]
        
        mov eax, [dublucuvant]
        mov [esi], eax
        
    calculare_suma:
        mov edx, 0
        cmp eax, 0
        je next
        
        mov bx, 10
        div bx             ;impartim numarul la 10 si vom avea in dx ultima cifra
        mov [copie], eax
        
        mov eax, edx
        mov [copie_cifra], edx
        mov edx, 0
        mov bx, 2
        div bx             ;impartim ultima cifra la 2 ca sa verificam daca e para
        mov eax, [copie]
        
        cmp dx, 0
        je este_par
        jmp calculare_suma
        
    este_par:
        mov dx, [copie_cifra]
        add [edi], dx
        jmp calculare_suma
        
    next:
        add esi, 4
        add edi, 1
        loop repeta
        
    final:
        push dword 0
        call [exit]