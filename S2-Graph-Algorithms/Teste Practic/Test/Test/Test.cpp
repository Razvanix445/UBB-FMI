#include <iostream>
#include <fstream>
#include <set>
#include <vector>

using namespace std;

ifstream fin("input.txt");
ofstream fout("output.txt");

int n;
vector<int> parinte, numarSuccesori, codPrufer;
set<int> frunze;

int main() {
	fin >> n;
	parinte.resize(n + 1);
	numarSuccesori.resize(n + 1);
	for (int i = 0; i < n; i++) {
		fin >> parinte[i];
		if (parinte[i] != -1)
			numarSuccesori[parinte[i]]++;
	}

	for (int i = 0; i < n; i++)
		if (numarSuccesori[i] == 0)
			frunze.insert(i);

	while (frunze.size() > 0) {
		int f = *frunze.begin();
		frunze.erase(frunze.begin());
		if (parinte[f] != -1) {
			int pr = parinte[f];
			numarSuccesori[pr]--;
			codPrufer.push_back(pr);
			if (numarSuccesori[pr] == 0) {
				frunze.insert(parinte[f]);
			}
		}
	}

	fout << codPrufer.size() << endl;
	for (int i = 0; i < codPrufer.size(); i++) {
		fout << codPrufer[i] << " ";
	}

	return 0;
}

/*
Am folosit trei vectori, parinte pentru valorile de intrare, numarSuccesori pentru numarul de succesori si codPrufer pentru rezultatul final.
In vectorul ordonat, frunze, am memorat frunzele arborelui. In prim pas, am citit valorile de intrare si le-am memorat in parinti, apoi am inserat in "frunze"
frunzele din arbore. Pana cand mai exista frunze, am introdus in vectorul codPrufer parintii si apoi am eliminat frunzele. La final, am afisat in fisier codul Prufer rezultat.
*/

/*
5
1 -1 4 2 1

7
-1 0 1 1 2 0 5
*/