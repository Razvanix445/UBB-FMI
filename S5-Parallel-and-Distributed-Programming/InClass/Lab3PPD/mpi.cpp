#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>

int main(int argc, char** argv) {
    // main1(argc, argv);
    // main2(argc, argv);
    // main3(argc, argv);
}

int main3(int argc, char** argv) {
    int namelen, myid, numprocs;
    const int n = 100;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);

    int* a = new int[n];
    int* b = new int[n];
    int* c = new int[n];

    int cat = n / numprocs;

    if (myid == 0) {
        for (int i = 0; i < n; i++) {
            a[i] = b[i] = i;
        }
    }

    int* aa = new int[cat];
    int* bb = new int[cat];
    int* cc = new int[cat];

    MPI_Scatter(a, cat, MPI_INT, aa, cat, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(b, cat, MPI_INT, bb, cat, MPI_INT, 0, MPI_COMM_WORLD);

    for (int i = 0; i < cat; i++) {
        cc[i] = aa[i] + bb[i];
    }

    MPI_Gather(cc, cat, MPI_INT, c, cat, MPI_INT, 0, MPI_COMM_WORLD);

    if (myid == 0) {
        for (int i = 0; i < n; i++) {
            if (c[i] != 2 * i) {
                printf("Eroare! Valorile nu sunt corecte!");
                break;
            }
            else {
                printf("%d\n", c[i]);
            }
        }
    }

    MPI_Finalize();
    return 0;
}



int main2(int argc, char** argv) {
    int namelen, myid, numprocs;
    const int n = 100;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);

    int cat = n / numprocs;

    if (myid == 0) {
        int* a = new int[100];
        int* b = new int[100];
        int* c = new int[100];

        for (int i = 0; i < n; i++) {
            a[i] = b[i] = i;
        }

        for (int i = 1; i < numprocs; i++) {
            printf("%d\n", *(a + i * cat));
            MPI_Send(a + i * cat, cat, MPI_INT, i, 111, MPI_COMM_WORLD);
            MPI_Send(b + i * cat, cat, MPI_INT, i, 222, MPI_COMM_WORLD);
        }

        for (int i = 0; i < cat; i++) {
            c[i] = a[i] + b[i];
        }

        for (int i = 1; i < numprocs; i++) {
            MPI_Recv(c + i * cat, cat, MPI_INT, i, 333, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        for (int i = 0; i < n; i++) {
            if (c[i] != 2 * i) {
                printf("Eroare! Valorile nu sunt corecte!");
                break;
            }
        }

    }
    else {
        int* a = new int[cat];
        int* b = new int[cat];
        int* c = new int[cat];

        MPI_Recv(a, cat, MPI_INT, 0, 111, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(b, cat, MPI_INT, 0, 222, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        for (int i = 0; i < cat; i++) {
            c[i] = a[i] + b[i];
        }

        MPI_Send(c, cat, MPI_INT, 0, 333, MPI_COMM_WORLD);
    }



    MPI_Finalize();
    return 0;
}

int main1(int argc, char** argv) {
    int namelen, myid, numprocs;
    char* mymessage = new char[50];
    char* buffer = new char[10];

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);

    strcpy(mymessage, "Hello from ");

    char* sirTotal = new char[50 * numprocs];
    sirTotal[0] = '\0';

    if (myid == 0) {
        strcat(mymessage, "0");
        strcat(sirTotal, mymessage);
        MPI_Status status;
        for (int i = 1; i < numprocs; i++) {
            MPI_Recv(mymessage, 50, MPI_CHAR, i, 111, MPI_COMM_WORLD, &status);
            printf("STATUS SOURCE %d\n", status.MPI_SOURCE);
            strcat(sirTotal, mymessage);
        }
        printf("%s\n", sirTotal);
    }
    else {
        // itoa(myid, buffer, strlen(buffer), 10);
        sprintf(buffer, "%d", myid);
        strcat(mymessage, buffer);
        MPI_Send(mymessage, strlen(mymessage) + 1, MPI_CHAR, 0, 111, MPI_COMM_WORLD);
    }

    MPI_Finalize();
    return 0;
}