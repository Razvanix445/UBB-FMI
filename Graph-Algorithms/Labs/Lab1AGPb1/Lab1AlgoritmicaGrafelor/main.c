/*
1. Fie un fisier ce contine un graf neorientat reprezentat sub forma: prima linie contine numarul nodurilor iar urmatoarele randuri 
muchiile grafului. Sa se scrie un program in C/C++ care sa citeasca fisierul si sa reprezinte/stocheze un graf folosind matricea de 
adiacenta, lista de adiacenta si matricea de incidenta. Sa se converteasca un graf dintr-o forma de reprezentare in alta.
*/

#include<stdio.h>

int a[101][101], n, fisier, nod1, nod2;
int main() {
	FILE* fisier;
	fisier = fopen("graf.txt", "r");
	fscanf(fisier, "%d", &n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < 2; j++) {
			fscanf(fisier, "%d", &nod1);
			fscanf(fisier, "%d", &nod2);
			a[nod1][nod2] = 1;
			a[nod2][nod1] = 1;
		}
	}

	printf("Matricea de adiacenta este: \n");
	for (int i = 1; i < n; i++) {
		for (int j = 1; j < n; j++) {
			printf("%d ", a[i][j]);
		}
		printf("\n");
	}
	printf("\n");
	fisier = fopen("graf.txt", "r");

	printf("Lista de muchii este: \n");
	fscanf(fisier, "%d", &n);
	for (int i = 0; i < n; i++) {
		fscanf(fisier, "%d", &nod1);
		fscanf(fisier, "%d", &nod2);
		printf("%d ", nod1);
		printf("%d ", nod2);
		printf("\n");
	}

	return 0;
}