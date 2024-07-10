#include <iostream>
#include <vector>
#include <algorithm>
#include <fstream>

using namespace std;

int main() {
    int n, parinti[100000] = { 0 };
    ifstream fin("input.txt");
    ofstream fout("output.txt");
    fin >> n;
    for (int i = 0; i < n; i++)
        fin >> parinti[i];

    vector<int> rezultat;

    int count = n - 1;
    while (count--) {
        int i, w;
        for (i = 0; i < n; i++)
        {
            w = 0;
            for (int j = 0; j < n; j++)
                if (parinti[j] == i)
                    w = 1;
            if (w == 0)
                break;
        }
        rezultat.push_back(parinti[i]);
        parinti[i] = i;
    }
    fout << rezultat.size() << "\n";
    for (int i: rezultat) {
        fout << i << " ";
    }
    return 0;
}
