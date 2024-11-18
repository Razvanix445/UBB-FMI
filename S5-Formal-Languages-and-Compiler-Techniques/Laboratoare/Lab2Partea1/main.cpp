#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <map>
#include <set>

using namespace std;

class AutomatFinit {
public:
    set<string> states;
    set<char> alphabet;
    map<pair<string, char>, string> transitions;
    string startState;
    set<string> finalStates;

    // Citire automat de la tastatura
    void citireTastatura() {
        int nrStates, nrFinalStates, nrTransitions;
        string state;

        // Citirea starilor
        cout << "Numar de stari: ";
        cin >> nrStates;
        cout << "Introduceti starile: ";
        for (int i = 0; i < nrStates; ++i) {
            cin >> state;
            states.insert(state);
        }

        // Citirea alfabetului
        int alphabetSize;
        cout << "Dimensiunea alfabetului: ";
        cin >> alphabetSize;
        cout << "Introduceti simbolurile alfabetului: ";
        for (int i = 0; i < alphabetSize; ++i) {
            char symbol;
            cin >> symbol;
            alphabet.insert(symbol);
        }

        // Citirea tranzitiilor
        cout << "Numar de tranzitii: ";
        cin >> nrTransitions;
        cout << "Introduceti tranzitiile (stare curenta, simbol, stare urmatoare): " << endl;
        for (int i = 0; i < nrTransitions; ++i) {
            string currentState, nextState;
            char symbol;
            cin >> currentState >> symbol >> nextState;

            if (transitions.find( {currentState, symbol} ) != transitions.end()) {
                cerr << "Error: Automatul nu este determinist!\n" << "Tranzitie nedeterminista detectata la starea " << currentState << " cu simbolul " << symbol << "." << endl;
                return;
            }

            transitions[{currentState, symbol}] = nextState;
        }

        // Citirea starii de start
        cout << "Starea initiala: ";
        cin >> startState;

        // Citirea starilor finale
        cout << "Numar de stari finale: ";
        cin >> nrFinalStates;
        cout << "Introduceti starile finale: ";
        for (int i = 0; i < nrFinalStates; ++i) {
            cin >> state;
            finalStates.insert(state);
        }
    }

    // Citire automat din fisier
    void citireFisier(const string& filename) {
        ifstream file(filename);

        if (!file.is_open()) {
            cerr << "Eroare la deschiderea fisierului." << endl;
            return;
        }

        int nrStates, nrFinalStates, nrTransitions;
        string state;

        // Citirea starilor
        file >> nrStates;
        for (int i = 0; i < nrStates; ++i) {
            file >> state;
            states.insert(state);
        }

        // Citirea alfabetului
        int alphabetSize;
        file >> alphabetSize;
        for (int i = 0; i < alphabetSize; ++i) {
            char symbol;
            file >> symbol;
            alphabet.insert(symbol);
        }

        // Citirea tranzitiilor
        file >> nrTransitions;
        for (int i = 0; i < nrTransitions; ++i) {
            string currentState, nextState;
            char symbol;
            file >> currentState >> symbol >> nextState;

            if (transitions.find( {currentState, symbol} ) != transitions.end()) {
                cerr << "Error: Automatul nu este determinist!\n" << "Tranzitie nedeterminista detectata la starea " << currentState << " cu simbolul " << symbol << "." << endl;
                file.close();
                return;
            }
            transitions[{currentState, symbol}] = nextState;
        }

        // Citirea starii de start
        file >> startState;

        // Citirea starilor finale
        file >> nrFinalStates;
        for (int i = 0; i < nrFinalStates; ++i) {
            file >> state;
            finalStates.insert(state);
        }

        file.close();
    }

    // Afisarea multimii starilor
    void afisareStari() {
        cout << "Starile sunt: ";
        for (const auto& state : states) {
            cout << state << " ";
        }
        cout << endl;
    }

    // Afisarea alfabetului
    void afisareAlfabet() {
        cout << "Alfabetul este: ";
        for (const auto& symbol : alphabet) {
            cout << symbol << " ";
        }
        cout << endl;
    }

    // Afisarea tranzitiilor
    void afisareTranzitii() {
        cout << "Tranzitiile sunt: " << endl;
        for (const auto& transition : transitions) {
            cout << "(" << transition.first.first << ", " << transition.first.second << ") -> " << transition.second << endl;
        }
    }

    // Afisarea starilor finale
    void afisareStariFinale() {
        cout << "Starile finale sunt: ";
        for (const auto& state : finalStates) {
            cout << state << " ";
        }
        cout << endl;
    }

    // Verifica daca o secventa este acceptata de automat
    bool verificaSecventa(const string& secventa) {
        string currentState = startState;

        for (const auto& symbol : secventa) {
            if (transitions.find({currentState, symbol}) == transitions.end()) {
                return false;
            }
            currentState = transitions[{currentState, symbol}];
        }

        return finalStates.find(currentState) != finalStates.end();
    }

    // Determina cel mai lung prefix acceptat dintr-o secventa
    string celMaiLungPrefixAcceptat(const string& secventa) {
        string currentState = startState;
        string longestPrefix = "";
        string currentPrefix = "";

        for (const auto& symbol : secventa) {
            if (transitions.find({currentState, symbol}) == transitions.end()) {
                break;
            }
            currentState = transitions[{currentState, symbol}];
            currentPrefix += symbol;
            if (finalStates.find(currentState) != finalStates.end()) {
                longestPrefix = currentPrefix;
            }
        }

        return longestPrefix;
    }

    bool esteDeterminist() const {
        set<pair<string, char>> tranzitiiGasite;

        for (const auto& tranzitie: transitions) {
            string stare = tranzitie.first.first;
            char simbol = tranzitie.first.second;

            if (tranzitiiGasite.find( {stare, simbol} ) != tranzitiiGasite.end())
                return false;

            tranzitiiGasite.insert( {stare, simbol} );
        }
        return true;
    }
};

// Afisare meniu
void afisareMeniu() {
    cout << "1. Afisare multimea starilor" << endl;
    cout << "2. Afisare alfabetul" << endl;
    cout << "3. Afisare tranzitiile" << endl;
    cout << "4. Afisare multimea starilor finale" << endl;
    cout << "5. Verifica secventa" << endl;
    cout << "6. Cel mai lung prefix acceptat" << endl;
    cout << "0. Iesire" << endl;
}

// Functie principala
int main() {
    AutomatFinit af;
    int optiune;

    cout << "Citire automat finit din fisier (1) sau tastatura (2): ";
    cin >> optiune;

    if (optiune == 1) {
        string filename;
        // cout << "Introduceti numele fisierului: ";
        // cin >> filename;
        af.citireFisier("C:/Users/razva/CLionProjects/LFTC/Lab2Partea1/automatClasa.txt");
    } else {
        af.citireTastatura();
    }

    do {
        afisareMeniu();
        cout << "Selectati o optiune: ";
        cin >> optiune;

        switch (optiune) {
            case 1:
                af.afisareStari();
                break;
            case 2:
                af.afisareAlfabet();
                break;
            case 3:
                af.afisareTranzitii();
                break;
            case 4:
                af.afisareStariFinale();
                break;
            case 5: {
                string secventa;
                cout << "Introduceti secventa de verificat: ";
                cin >> secventa;
                if (af.verificaSecventa(secventa)) {
                    cout << "Secventa este acceptata." << endl;
                } else {
                    cout << "Secventa nu este acceptata." << endl;
                }
                break;
            }
            case 6: {
                string secventa;
                cout << "Introduceti secventa: ";
                cin >> secventa;
                string prefix = af.celMaiLungPrefixAcceptat(secventa);
                cout << "Cel mai lung prefix acceptat: " << prefix << endl;
                break;
            }
        }
    } while (optiune != 0);

    return 0;
}
