#include <fstream>
#include <iostream>
#include <queue>

using namespace std;

ifstream fin("input.txt");
ofstream fout("output.txt");

int n, nod, numarAparitii[100000], nodDisponibil, parinti[100000];
queue<int> codPrufer;

void main() {

    fin >> n;
    for (int i = 0; i < n; i++) {
        fin >> nod;
        codPrufer.push(nod);
        numarAparitii[nod]++;
        parinti[i] = -1;
    }
    parinti[n] = -1;

    for (int i = 1; i <= n; i++) {
        nod = codPrufer.front();
        nodDisponibil = 0;
        while (numarAparitii[nodDisponibil] != 0)
            nodDisponibil++;
        parinti[nodDisponibil] = nod;
        codPrufer.pop();
        codPrufer.push(nodDisponibil);
        numarAparitii[nodDisponibil]++;
        numarAparitii[nod]--;
    }

    fout << n + 1 << '\n';
    for (int i = 0; i <= n; i++) {
        fout << parinti[i] << " ";
    }
}