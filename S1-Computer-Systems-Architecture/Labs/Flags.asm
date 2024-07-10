bits 32

global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
    
segment code use32 class=code
    start:
    
    
    ; ex_3 (CF = 1; OF = 0)
        ;mov al, 255
        ;add al, -1
    
    ; ex_2 (SF=0, CF=1, OF=1, ZF=1)
        ; mov ah, -128
        ; mov bh, 80h
        ; add ah, bh
    