#include <iostream>
#include <fstream>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

void generateRandomNumber(int n, const string& filename) {
    ofstream file(filename);
    if (!file.is_open()) {
        cerr << "Eroare la deschiderea fișierului " << filename << endl;
        return;
    }

    vector<int> number(n);
    srand(time(0));

    number[0] = rand() % 9 + 1;

    for (int i = 1; i < n; ++i) {
        number[i] = rand() % 10;
    }

    file << n << "\n";
    for (int digit : number) {
        file << digit << " ";
    }
    file << endl;

    file.close();
    cout << "Numărul aleatoriu de " << n << " cifre a fost salvat în " << filename << endl;
}

int main() {
    int n;
    cout << "Introduceți numărul de cifre pentru numărul aleatoriu: ";
    cin >> n;

    generateRandomNumber(n, "test2/Numar2.txt");

    return 0;
}
