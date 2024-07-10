#include<stdio.h>

int a[101][101], n, fisier, nod1, nod2;
int main1() {
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



	return 0;
}