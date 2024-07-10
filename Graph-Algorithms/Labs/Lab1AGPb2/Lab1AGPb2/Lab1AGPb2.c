/*
2. Fie un graf reprezentat sub o anumita forma (graful este citit dintr-un fisier). Sa se rezolve:
a. sa se determine nodurile izolate dintr-un graf.
b. sa se determine daca graful este regular.
c. pentru un graf reprezentat cu matricea de adiacenta sa se determine matricea distantelor.
d. pentru un graf reprezentat cu o matrice de adiacenta sa se determine daca este conex.
*/

#include<stdio.h>

#define INF 99999

int a[101][101], n, fisier, grade[101];
int main() {
	//citire matrice de adiacenta + construire vector de grade
	FILE* fisier;
	fisier = fopen("graf.txt", "r");
	fscanf_s(fisier, "%d", &n);
	for (int i = 0; i < n; i++) {
		grade[i] = 0;
		for (int j = 0; j < n; j++) {
			fscanf_s(fisier, "%d", &a[i][j]);
			if (a[i][j] != 0) {
				grade[i]++;
			}
		}
	}
	fclose(fisier);

	// a) gasirea nodurilor izolate
	for (int i = 0; i < n; i++) {
		int nodIzolat = 1;
		for (int j = 0; j < n; j++) {
			if (a[i][j] != 0 || a[j][i] != 0) {
				nodIzolat = 0;
				break;
			}
		}
		if (nodIzolat) {
			printf("Nodul %d este izolat!\n", i + 1);
		}
	}
	printf("\n");

	// b) verificare daca graful este regular
	int grad = grade[0];
	for (int i = 0; i < n; i++) {
		if (grade[i] != grad) {
			grad = -1;
		}
	}
	if (grad == -1) {
		printf("Graful nu este regular!\n");
	}
	else {
		printf("Graful este regular!\n");
	}
	printf("\n");

	// c) determinarea matricei distantelor
	int matriceaDistantelor[101][101];
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			matriceaDistantelor[i][j] = a[i][j];
			if (matriceaDistantelor[i][j] == 0) {
				matriceaDistantelor[i][j] = INF;
			}
			if (i == j) {
				matriceaDistantelor[i][j] = 0;
			}
		}
	}
	//Algoritm Roy-Floyd
	for (int k = 0; k < n; k++) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matriceaDistantelor[i][k] != INF && matriceaDistantelor[k][j] != INF) {
					if (matriceaDistantelor[i][k] + matriceaDistantelor[k][j] < matriceaDistantelor[i][j]) {
						matriceaDistantelor[i][j] = matriceaDistantelor[i][k] + matriceaDistantelor[k][j];
					}
				}
			}
		}
	}
	printf("Matricea distantelor: \n");
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (matriceaDistantelor[i][j] == INF) {
				printf("INF ");
			}
			else {
				printf("%d ", matriceaDistantelor[i][j]);
			}
		}
		printf("\n");
	}
	printf("\n");

	// d) verificare graf conex
	if (Conex()) {
		printf("Graful este conex!\n");
	}
	else {
		printf("Graful nu este conex!\n");
	}

	return 0;
}

int viz[101];
void DFS(int x) {
	int i;
	viz[x] = 1;
	for (i = 1; i <= n; i++) {
		if (a[x][i] == 1 && viz[i] == 0) {
			DFS(i);
		}
	}
}

int Conex() {
	for (int i = 1; i <= n; i++)
		viz[i] = 0;
	DFS(1);
	for (int i = 1; i <= n; i++) {
		if (viz[i] == 0)
			return 0;
	}
	return 1;
}