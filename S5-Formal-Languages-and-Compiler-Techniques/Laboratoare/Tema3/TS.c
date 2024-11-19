#include <stdio.h>
#include <string.h>

#define MAX_ENTRIES 1000
#define MAX_SYMBOLS 1000

typedef struct {
    int position;
    char atom[50];
    int left;
    int right;
} AtomEntry;

typedef struct {
    AtomEntry nodes[MAX_SYMBOLS];
    int size;
    int nextPosition;
} TS;

void initTS(TS* ts) {
    ts->size = 0;
    ts->nextPosition = 0;
    for (int i = 0; i < MAX_SYMBOLS; i++) {
        ts->nodes[i].left = -1;
        ts->nodes[i].right = -1;
    }
}

int findSymbol(TS* ts, const char* symbol) {
    int index = 0;
    while (index != -1) {
        if (strcmp(ts->nodes[index].atom, symbol) == 0) {
            return ts->nodes[index].position;
        } else if (strcmp(symbol, ts->nodes[index].atom) < 0) {
            index = ts->nodes[index].left;
        } else {
            index = ts->nodes[index].right;
        }
    }
    return -1;
}

int addSymbol(TS* ts, const char* symbol) {
    if (ts->size >= MAX_SYMBOLS) {
        printf("Error: TS is full.\n");
        return -1;
    }

    int index = 0;

    if (ts->size == 0) {
        strcpy(ts->nodes[ts->size].atom, symbol);
        ts->nodes[ts->size].position = ts->nextPosition++;
        ts->size++;
        return ts->nodes[0].position;
    }

    while (index != -1) {
        if (strcmp(ts->nodes[index].atom, symbol) == 0) {
            return ts->nodes[index].position;
        } else if (strcmp(symbol, ts->nodes[index].atom) < 0) {
            if (ts->nodes[index].left == -1) {
                ts->nodes[index].left = ts->size;
                break;
            } else {
                index = ts->nodes[index].left;
            }
        } else {
            if (ts->nodes[index].right == -1) {
                ts->nodes[index].right = ts->size;
                break;
            } else {
                index = ts->nodes[index].right;
            }
        }
    }

    strcpy(ts->nodes[ts->size].atom, symbol);
    ts->nodes[ts->size].position = ts->nextPosition++;
    ts->size++;
    return ts->nodes[ts->size - 1].position;
}

void saveTS(TS* ts, FILE* outFile) {
    fprintf(outFile, "| Position | Atom         | Left Link | Right Link |\n");
    fprintf(outFile, "|--------------------------------------------------|\n");
    for (int i = 0; i < ts->size; i++) {
        char leftStr[10];
        char rightStr[10];
        
        sprintf(leftStr, "%s", ts->nodes[i].left == -1 ? "-" : "");
        if (ts->nodes[i].left != -1) sprintf(leftStr, "%d", ts->nodes[i].left);
        sprintf(rightStr, "%s", ts->nodes[i].right == -1 ? "-" : "");
        if (ts->nodes[i].right != -1) sprintf(rightStr, "%d", ts->nodes[i].right);

        fprintf(outFile, "| %8d | %-12s | %9s | %10s |\n",
                ts->nodes[i].position,
                ts->nodes[i].atom,
                leftStr,
                rightStr);
    }
}

void printTS(TS* ts) {
    printf("| Position | Atom         | Left Link | Right Link |\n");
    printf("|--------------------------------------------------|\n");
    for (int i = 0; i < ts->size; i++) {
        printf("| %8d | %-12s | %9d | %10d |\n",
               ts->nodes[i].position,
               ts->nodes[i].atom,
               ts->nodes[i].left,
               ts->nodes[i].right);
    }
}