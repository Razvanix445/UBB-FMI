bits 32

; procedura definita in limbajul C
global _return_rezultat

segment code use32 class=code public

    ; definire procedura
    _return_rezultat:
    
        ; Codul de intrare
        ; -------------------------------
        ; - crearea unui cadru de stiva (stack frame)
        push ebp
        mov ebp, esp
                
        ; argumentele transmise pe stiva functiei return_rezultat
        mov esi, [ebp + 8]      ; sirul de biti
        mov ecx, [ebp + 12]     ; al doilea parametru se afla la EBP + 12
        cld
        
        mov ebx, 0
        mov eax, 0
        mov dh, 2
        dec esi

    repeta:
        mov dl, cl
        dec dl
        inc esi
        cmp byte[esi], 0
        je final
        
        mov al, byte[esi]
        sub al, '0'
        
    inmultire:
        cmp dl, 0
        je next
        imul dh
        dec dl
        jmp inmultire
        
    next:
        add bx, ax
        loop repeta
        
    final:
        ; Codul de iesire
        ; -------------------------------
        ; - refacem cadrul de stiva al programului apelant
        mov esp, ebp
        pop ebp
        
        ; - revenirea din procedura si eliberarea parametrilor transmisi procedurii
        mov eax, ebx      ; in EAX trebuie sa returnez valoarea lui n in baza 10
        
        ret
