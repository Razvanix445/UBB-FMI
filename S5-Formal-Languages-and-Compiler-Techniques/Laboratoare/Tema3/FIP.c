#include <stdio.h>
#include <string.h>

#define MAX_ENTRIES 1000
#define MAX_SYMBOLS 1000

typedef struct {
    int codAtom;
    int pozitieTS;
} FIPEntry;

typedef struct {
    FIPEntry entries[MAX_ENTRIES];
    int size;
} FIP;

void initFIP(FIP* fip) {
    fip->size = 0;
}

void addFIPEntry(FIP* fip, int codAtom, int pozitieTS) {
    for (int i = 0; i < fip->size; i++) {
        if (fip->entries[i].codAtom == codAtom && fip->entries[i].pozitieTS == pozitieTS) {
            return;
        }
    }

    if (fip->size < MAX_ENTRIES) {
        fip->entries[fip->size].codAtom = codAtom;
        fip->entries[fip->size].pozitieTS = pozitieTS;
        fip->size++;
    } else {
        printf("Error: FIP is full.\n");
    }
}

void saveFIP(const FIP* fip, FILE* outFile) {
    fprintf(outFile, "Cod Atom  | Pozitie in TS\n");
    fprintf(outFile, "--------------------------\n");
    for (int i = 0; i < fip->size; i++) {
        char pozStr[16];
        if (fip->entries[i].pozitieTS == -1) {
            strcpy(pozStr, "-");
        } else {
            sprintf(pozStr, "%d", fip->entries[i].pozitieTS);
        }
        fprintf(outFile, "%9d | %13s\n", fip->entries[i].codAtom, pozStr);
    }
}