bits 32

; procedura definita in limbajul C
global _return_rezultat

extern _printf

; Se citesc mai multe numere de la tastatura, in baza 2. Sa se afiseze aceste numere in baza 8.
segment data use32 class=data public
    lungime_rezultat dd 0
    adresa_string dd 0
    adresa_rezultat dd 0
    adresa_sir_citit dd 0

segment code use32 class=code public

    ; definire procedura
    _return_rezultat:
    
        ; Codul de intrare
        ; -------------------------------
        ; - crearea unui cadru de stiva (stack frame)
        push ebp
        mov ebp, esp
        
        ; -rezervarea pe stiva a spatiului necesar stocarii variabilelor locale
        sub esp, 4*3
        
        ; argumentele transmise pe stiva functiei return_rezultat
        mov eax, [ebp + 8]                ; primul parametru se afla la EBP + 8
        mov [adresa_string], eax
        
        mov eax, [ebp + 12]               ; al doilea parametru se afla la EBP + 12
        mov [adresa_rezultat], eax
        
        ; salvam adresa sirului care va fi citit
        mov [adresa_sir_citit], ebp
        sub dword[asresa_sir_citit], 4*3
        
        ; Codul de apel
        ; -------------------------------
        
        mov ecx, 0
        cld
        mov esi, [adresa_string]
        mov edi, [adresa_rezultat]
        mov dl, 0
        
    repeta:
        lodsb
        cmp al, 0
        je next
        inc ecx
        jmp repeta
        
    next:
        std
        lodsb
        lodsb
        
    reverse:
        mov [adresa_rezultat + esi], al
        cmp ecx, 0
        je next2
        dec ecx
        inc dl
        mov dh, 0
        lodsb
        sub al, '0'
        
    convert:
        inc dh
        cmp dl, dh
        je reverse
        imul ax, 2
        jmp convert
        
        ; Codul de iesire
        ; -------------------------------
        ; - restaurarea resurselor nevolatile care au fost alterate
        
        ; stergem spatiul rezervat pe stiva
        add esp, 4*3
        
        ; - refacem cadrul de stiva al programului apelant
        mov esp, ebp
        pop ebp
        
        ; - revenirea din procedura si eliberarea parametrilor transmisi procedurii
        mov eax, [lungime_rezultat]
        
        ret