#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <unordered_set>
#include <unordered_map>
using namespace std;

struct tranzitie {
    string stare_sursa;
    string intrare;
    string stare_destinatie;
};

class AutomatFinit {
    private:
        unordered_set<string> stari;   // Q
        unordered_set<string> alfabet; // Σ
        vector<tranzitie> tranzitii;   // δ
        string stare_initiala;  // q0
        unordered_set<string> stari_finale; // F
    public:
        AutomatFinit(unordered_set<string> stari,
                unordered_set<string> alfabet,
                vector<tranzitie> tranzitii,
                string stare_initiala,
                unordered_set<string> stari_finale) {
            this->stari = stari;
            this->alfabet = alfabet;
            this->tranzitii = tranzitii;
            this->stare_initiala = stare_initiala;
            this->stari_finale = stari_finale;
        }

        AutomatFinit() {}

        unordered_set<string> getStari() { return this->stari; }
        unordered_set<string> getAlfabet() { return this->alfabet; }
        vector<tranzitie> getTranzitii() { return this->tranzitii; }
        string getStareInitiala() { return this->stare_initiala; }
        unordered_set<string> getStariFinale() { return this->stari_finale; }

        bool esteDeterminist() {
            unordered_map<string, unordered_map<string, unordered_set<string>>> map_tranzitii;
            for (const auto& t : tranzitii) {
                map_tranzitii[t.stare_sursa][t.intrare].insert(t.stare_destinatie);
                if (map_tranzitii[t.stare_sursa][t.intrare].size() > 1) {
                    return false;
                }
            }
            return true;
        }
};

const int CITIRE_CONSOLA = 1;
const int CITIRE_FISIER = 2;
const int AFISARE_MULTIME_STARI = 3;
const int AFISARE_ALFABET = 4;
const int AFISARE_TRANZITII = 5;
const int AFISARE_MULTIME_STARI_FINALE = 6;
const int VERIFICA_SECVENTA_ACCEPTATA = 7;
const int PREFIX_LUNGIME_MAX_ACCEPTAT = 8;
const int IESIRE = 9;

AutomatFinit citire_consola() {
    string stare_initiala;
    unordered_set<string> stari, alfabet, stari_finale;
    vector<tranzitie> tranzitii;
    cin >> stare_initiala;
    int numar_linii;
    cin >> numar_linii;
    string stare_finala;
    for(int i = 0; i < numar_linii; i++) {
        cin >> stare_finala;
        stari_finale.insert(stare_finala);
    }
    cin >> numar_linii;
    string stare_sursa, intrare, stare_destinatie;
    for(int i = 0; i < numar_linii; i++) {
        cin >> stare_sursa >> intrare >> stare_destinatie;
        tranzitie tranzitie = {stare_sursa, intrare, stare_destinatie};
        tranzitii.push_back(tranzitie);
        stari.insert(stare_sursa);
        stari.insert(stare_destinatie);
        alfabet.insert(intrare);
    }
    return AutomatFinit(stari, alfabet, tranzitii, stare_initiala, stari_finale);
}

AutomatFinit citire_fisier() {
    ifstream fin("input.txt");
    string stare_initiala;
    unordered_set<string> stari, alfabet, stari_finale;
    vector<tranzitie> tranzitii;
    fin >> stare_initiala;
    int numar_linii;
    fin >> numar_linii;
    string stare_finala;
    for(int i = 0; i < numar_linii; i++) {
        fin >> stare_finala;
        stari_finale.insert(stare_finala);
    }
    fin >> numar_linii;
    string stare_sursa, intrare, stare_destinatie;
    for(int i = 0; i < numar_linii; i++) {
        fin >> stare_sursa >> intrare >> stare_destinatie;
        tranzitie tranzitie = {stare_sursa, intrare, stare_destinatie};
        tranzitii.push_back(tranzitie);
        stari.insert(stare_sursa);
        stari.insert(stare_destinatie);
        alfabet.insert(intrare);
    }
    fin.close();
    return AutomatFinit(stari, alfabet, tranzitii, stare_initiala, stari_finale);
}

void afisare_multime_stari(AutomatFinit& af) {
    unordered_set<string> stari = af.getStari();
    cout << " ~~ Stari ~~ " << endl;
    for(string stare: stari) {
        cout << stare << endl;
    }
}

void afisare_alfabet(AutomatFinit& af) {
    unordered_set<string> alfabet = af.getAlfabet();
    cout << " ~~ Alfabet ~~ " << endl;
    for(string simbol: alfabet) {
        cout << simbol << endl;
    }
}

void afisare_tranzitii(AutomatFinit& af) {
    vector<tranzitie> tranzitii = af.getTranzitii();
    cout << " ~~ Tranzitii ~~ " << endl;
    for(tranzitie tranzitie: tranzitii) {
        cout << tranzitie.stare_sursa << " " << tranzitie.intrare << " " << tranzitie.stare_destinatie << endl;
    }
}

void afisare_multime_stari_finale(AutomatFinit& af) {
    unordered_set<string> stari_finale = af.getStariFinale();
    cout << " ~~ Stari finale ~~ " << endl;
    for(string stare: stari_finale) {
        cout << stare << endl;
    }
}

bool verifica_secventa_acceptata(AutomatFinit& af, string secventa) {
    if(!af.esteDeterminist())
        return false;
    string stare_curenta = af.getStareInitiala();
    for(char caracter: secventa) {
        string simbol = "";
        simbol += caracter;
        bool tranzitie_gasita = false;
        for(tranzitie tranzitie: af.getTranzitii()) {
            if(stare_curenta == tranzitie.stare_sursa && simbol == tranzitie.intrare) {
                stare_curenta = tranzitie.stare_destinatie;
                tranzitie_gasita = true;
                break;
            }
        }
        if(!tranzitie_gasita)
            return false;
    }
    for(string stare_finala: af.getStariFinale()) {
        if(stare_curenta == stare_finala)
            return true;
    }
    return false;
}

string prefix_lungime_max_acceptat(AutomatFinit& af, string secventa) {
    if(!af.esteDeterminist())
        return "";
    string prefix;
    string secventa_prefix = "";
    string stare_curenta = af.getStareInitiala();
    for(char caracter: secventa) {
        string simbol(1, caracter);
        bool tranzitie_gasita = false;
        for(tranzitie tranzitie: af.getTranzitii()) {
            if(stare_curenta == tranzitie.stare_sursa && simbol == tranzitie.intrare) {
                stare_curenta = tranzitie.stare_destinatie;
                secventa_prefix += simbol;
                tranzitie_gasita = true;
                break;
            }
        }
        for(string stare_finala: af.getStariFinale()) {
            if(stare_curenta == stare_finala) {
                prefix = secventa_prefix;
                if(!tranzitie_gasita) {
                    return prefix;
                }
            }
        }
        if(!tranzitie_gasita)
            return "";
    }
    return prefix;
}

int main() {
    AutomatFinit af;
    int exit = 0;
    while(!exit) {
        cout << endl;
        cout << " ~~ Meniu ~~ " << endl;
        cout << "1. Citeste de la consola" << endl;
        cout << "2. Citeste din fisier" << endl;
        cout << "3. Afiseaza multimea starilor" << endl;
        cout << "4. Afiseaza alfabetul" << endl;
        cout << "5. Afiseaza tranzitiile" << endl;
        cout << "6. Afiseaza multimea starilor finale" << endl;
        cout << "7. Verifica secventa acceptata" << endl;
        cout << "8. Determina cel mai lung prefix acceptat dintr-o secventa" << endl;
        cout << "9. Iesire" << endl;
        cout << "Alegeti o optiune: ";
        int optiune;
        cin >> optiune;
        string secventa;
        switch(optiune) {
            case CITIRE_CONSOLA:
                af = citire_consola();
                break;
            case CITIRE_FISIER:
                af = citire_fisier();
                break;
            case AFISARE_MULTIME_STARI:
                afisare_multime_stari(af);
                break;
            case AFISARE_ALFABET:
                afisare_alfabet(af);
                break;
            case AFISARE_TRANZITII:
                afisare_tranzitii(af);
                break;
            case AFISARE_MULTIME_STARI_FINALE:
                afisare_multime_stari_finale(af);
                break;
            case VERIFICA_SECVENTA_ACCEPTATA:
                cout << "Introduceti secventa: ";
                cin >> secventa;
                if(verifica_secventa_acceptata(af, secventa)) {
                    cout << "acceptat" << endl;
                }
                else {
                    cout << "respins" << endl;
                }
                break;
            case PREFIX_LUNGIME_MAX_ACCEPTAT:
                cout << "Introduceti secventa: ";
                cin >> secventa;
                cout << prefix_lungime_max_acceptat(af, secventa);
                break;
            case IESIRE:
                exit = 1;
                break;
            default:
                cout << "Optiune inexistenta!";
        }
    }
    return 0;
}