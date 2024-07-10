#include <iostream>
#include <vector>

using namespace std;

template <typename T>
class Geanta {

private:

    vector<T> obiecte;

public:

    Geanta() {}

    Geanta(const T& obiect) {
        obiecte.push_back(obiect);
    }

    Geanta<T>& operator+(const T& obiect) {
        obiecte.push_back(obiect);
        return *this;
    }

    typename vector<T>::iterator begin() {
        return obiecte.begin();
    }

    typename vector<T>::iterator end() {
        return obiecte.end();
    }

};

void calatorie() {
    Geanta<std::string> geanta{ "Ion" }; // creaza geanta pentru Ion
    geanta = geanta + std::string{ "haine" }; // adauga obiect in geanta
    geanta + std::string{ "pahar" };
    for (auto obiect : geanta) { // itereaza obiectele din geanta
        std::cout << obiect << "\n";
    }
}

int main() {
    calatorie();
    return 0;
}
