#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

int arc, numarNoduri, apartineCompConexa[5005], numarMuchii, total;

struct muchie {
    int nodSursa, nodDestinatie, cost;
} muchii[50000], solutie[5000];

bool sorteazaDupaCost(muchie muchie1, muchie muchie2) {
    return muchie1.cost < muchie2.cost;
}

bool sorteazaDupaSursaDestinatie(muchie muchie1, muchie muchie2) {
    return muchie1.nodSursa < muchie2.nodSursa || muchie1.nodSursa == muchie2.nodSursa && muchie1.nodDestinatie < muchie2.nodDestinatie;
}

void unificare(int nod1, int nod2) {
    int val1 = apartineCompConexa[nod1], val2 = apartineCompConexa[nod2];
    for (int i = 0; i < numarNoduri; i++) {
        if (apartineCompConexa[i] == val2)
            apartineCompConexa[i] = val1;
    }
}

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    fin >> numarNoduri >> arc;
    for (int i = 1; i <= arc; i++)
        fin >> muchii[i].nodSursa >> muchii[i].nodDestinatie >> muchii[i].cost;

    //Kruskal
    sort(muchii + 1, muchii + arc + 1, sorteazaDupaCost);
    int contor = 1;
    for (int i = 0; i < numarNoduri; i++)
        apartineCompConexa[i] = i;
    while (numarMuchii < numarNoduri - 1) {
        int sursa, destinatie;
        sursa = muchii[contor].nodSursa;
        destinatie = muchii[contor].nodDestinatie;
        if (apartineCompConexa[sursa] != apartineCompConexa[destinatie]) {
            numarMuchii++;
            solutie[numarMuchii].nodSursa = sursa;
            solutie[numarMuchii].nodDestinatie = destinatie;
            total += muchii[contor].cost;
            unificare(sursa, destinatie);
        }
        contor++;
    }

    fout << total << '\n' << numarNoduri - 1 << '\n';
    sort(solutie + 1, solutie + numarNoduri, sorteazaDupaSursaDestinatie);
    for (int i = 1; i < numarNoduri; i++)
        fout << solutie[i].nodSursa << " " << solutie[i].nodDestinatie << '\n';

    return 0;
}