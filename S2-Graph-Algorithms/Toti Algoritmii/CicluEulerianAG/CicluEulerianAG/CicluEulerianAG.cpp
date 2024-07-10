#include <iostream>
#include <fstream>
#include <vector>

using namespace std;
using CicluEulerian = vector<int>;
using ListaAdiacenta = vector<vector<int>>;

int numarNoduri;
CicluEulerian cicluEulerian;
ListaAdiacenta listaAdiacenta;

void gasesteCicluEulerian(int nod) {
    while (!listaAdiacenta[nod].empty()) {
        int nodUrmator = listaAdiacenta[nod].back();
        listaAdiacenta[nod].pop_back();
        for (auto it = listaAdiacenta[nodUrmator].begin(); it != listaAdiacenta[nodUrmator].end(); ++it) {
            if (*it == nod) {
                listaAdiacenta[nodUrmator].erase(it);
                break;
            }
        }
        gasesteCicluEulerian(nodUrmator);
    }
    cicluEulerian.emplace_back(nod);
}

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    int nod1, nod2, numMuchii;
    fin >> numarNoduri >> numMuchii;
    listaAdiacenta = ListaAdiacenta(numarNoduri);
    for (int i = 1; i <= numMuchii; i++) {
        fin >> nod1 >> nod2;
        listaAdiacenta[nod1].emplace_back(nod2);
        listaAdiacenta[nod2].emplace_back(nod1);
    }
    gasesteCicluEulerian(0);
    for (auto& nod : cicluEulerian)
        fout << nod << " ";
    return 0;
}