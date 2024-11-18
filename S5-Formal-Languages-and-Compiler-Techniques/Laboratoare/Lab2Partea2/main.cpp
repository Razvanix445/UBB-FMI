#include <iostream>
#include <fstream>
#include <algorithm>
#include <utility>
#include <string>
#include <cctype>
#include <vector>
#include <memory>
#include <map>
#include <sstream>
#include <set>
#include <iomanip>

#include "TS.h"
#include "FIP.h"
#include "AutomatFinitIdentificatori.h"
#include "AutomatFinitConstanteReale.h"
#include "AutomatFinitDecimal.h"
#include "AutomatFinitOctal.h"
#include "AutomatFinitHexa.h"
#include "AutomatFinitBinaries.h"

using namespace std;

string test1;
string test2;
string test3;
string test4;
string test5;

int atomNo;

struct Atom {
    std::string atomLexical;
    int codAtom;
};


bool isKeyword(const string& word) {
    const string keywords[] = {"int", "double", "void", "struct", "if", "else", "while", "cin", "cout", "return"};
    for (const string& keyword : keywords) {
        if (word == keyword) return true;
    }
    return false;
}

int getCodAtom(const string& atom, vector<Atom>& atomiLexicali) {
    for (int i = 0; i < atomiLexicali.size(); ++i) {
        if (atomiLexicali[i].atomLexical == atom) {
            return atomiLexicali[i].codAtom;
        }
    }
    int newCode = atomiLexicali.size();
    atomiLexicali.push_back({atom, newCode});

    return newCode;
}

vector<Atom> pushUnique(vector<Atom>& atomiLexicali, const string& atom) {
    for (const auto& entry : atomiLexicali) {
        if (entry.atomLexical == atom) {
            return atomiLexicali;
        }
    }
    atomiLexicali.push_back({atom, atomNo ++});
    return atomiLexicali;
}

vector<Atom> lexicalAnalyzer(const string& code, vector<string>& errors, TS& ts, FIP& fip, vector<Atom> atomiLexicali) {
    AutomatFinitIdentificator afi;
    AutomatFinitDecimal afd;
    AutomatFinitOctal afo;
    AutomatFinitHexa afh;
    AutomatFinitBinaries afb;
    AutomatFinitConstanteReale afcr;

    set<string> fipEntries;
    int i = 0;
    int line = 1;
    int charPosition = 1;
    string token = "";

    while (i < code.length()) {
        char currentChar = code[i];

        // => Verificam daca linia incepe cu "#include"
        if (code.substr(i, 8) == "#include") {
            string directive = "#include";
            i += 8;
            charPosition += 8;

            while (isspace(code[i]) && code[i] != '\n') {
                charPosition++;
                i++;
            }

            string header = "";
            while (i < code.length() && code[i] != '\n') {
                header += code[i];
                i++;
                charPosition++;
            }

            directive += " " + header;

            if (fipEntries.find(directive) == fipEntries.end()) {
                pushUnique(atomiLexicali, directive);
                fip.addEntry(getCodAtom(directive, atomiLexicali), -1);
                fipEntries.insert(directive);
            }
            cout << "Atom: " << directive << ", Type: Include Directive, Line: " << line << ", Char: " << charPosition << endl;

            line++;
            charPosition = 1;
            i++;
            continue;
        }

        // => Verificam daca exista "using namespace std;", daca da, luam tot pana la separatorul ";"
        if (code.substr(i, 16) == "using namespace ") {
            string directive = "using namespace ";
            i += 16;
            charPosition += 16;

            while (i < code.length() && code[i] != ';' && !isspace(code[i])) {
                directive += code[i];
                i++;
                charPosition++;
            }

            if (code[i] == ';') {
                directive += ";";
                i++;
                charPosition++;
            }

            if (fipEntries.find(directive) == fipEntries.end()) {
                pushUnique(atomiLexicali, directive);
                fip.addEntry(getCodAtom(directive, atomiLexicali), -1);
                fipEntries.insert(directive);
            }
            cout << "Atom: " << directive << ", Type: Namespace Directive, Line: " << line << ", Char: " << charPosition << endl;

            while (i < code.length() && isspace(code[i]) && code[i] != '\n') {
                i++;
                charPosition++;
            }
            continue;
        }

        // => Verificam daca este separator, operator sau spatiu gol
        if (isspace(currentChar) || (ispunct(currentChar) && currentChar != '.')) {
            if (!token.empty()) {
                bool isValid = false;
                string atomType = "";

                if (afi.verificaSecventa(token)) {
                    isValid = true;
                    if (isKeyword(token)) {
                        atomType = "Keyword";
                    } else {
                        atomType = "Identifier";
                    }
                } else if (afd.verificaSecventa(token)) {
                    isValid = true;
                    atomType = "Decimal Integer";
                } else if (afo.verificaSecventa(token)) {
                    isValid = true;
                    atomType = "Octal Literal";
                } else if (afh.verificaSecventa(token)) {
                    isValid = true;
                    atomType = "Hexadecimal Literal";
                } else if (afb.verificaSecventa(token)) {
                    isValid = true;
                    atomType = "Binary Literal";
                } else if (afcr.verificaSecventa(token)) {
                    isValid = true;
                    atomType = "Real Literal";
                }

                if (isValid) {
                    if (atomType == "Keyword") {
                        if (fipEntries.find(token) == fipEntries.end()) {
                            pushUnique(atomiLexicali, token);
                            fip.addEntry(getCodAtom(token, atomiLexicali), -1);
                            fipEntries.insert(token);
                        }
                    } else {
                        int posInTS = ts.search(token);
                        if (posInTS == -1) {
                            ts.insert(token);
                            posInTS = ts.search(token);
                        }
                        if (fipEntries.find(token) == fipEntries.end()) {
                            fip.addEntry(0, posInTS);
                            fipEntries.insert(token);
                        }
                    }
                    cout << "Atom: " << token << ", Type: " << atomType << ", Line: " << line << ", Char: " << charPosition << endl;
                } else {
                    errors.push_back("Error: Invalid token '" + token + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition));
                }
                token.clear();
            }

            if (ispunct(currentChar) || currentChar != '.') {
                string op(1, currentChar);

                if ((op == "+" && code[i + 1] == '+') || (op == "-" && code[i + 1] == '-') ||
                    (op == "=" && code[i + 1] == '=') || (op == "<" && code[i + 1] == '<') ||
                    (op == ">" && code[i + 1] == '>') || (op == "<" && code[i + 1] == '=') ||
                    (op == ">" && code[i + 1] == '=')) {
                    op += code[++i];
                    charPosition++;
                }

                if (fipEntries.find(op) == fipEntries.end()) {
                    pushUnique(atomiLexicali, op);
                    fip.addEntry(getCodAtom(op, atomiLexicali), -1);
                    fipEntries.insert(op);
                }
                if (currentChar != ' ')
                    cout << "Atom: " << op << ", Type: Operator, Line: " << line << ", Char: " << charPosition << endl;
            }

            if (currentChar == '\n') {
                line++;
                charPosition = 1;
            } else {
                charPosition++;
            }

            i++;
            continue;
        }

        token += currentChar;
        i++;
        charPosition++;
    }

    if (!token.empty()) {
        bool isValid = false;
        string atomType = "";

        if (afi.verificaSecventa(token)) {
            isValid = true;
            if (isKeyword(token)) {
                atomType = "Keyword";
            } else {
                atomType = "Identifier";
            }
        } else if (afd.verificaSecventa(token)) {
            isValid = true;
            atomType = "Decimal Integer";
        } else if (afo.verificaSecventa(token)) {
            isValid = true;
            atomType = "Octal Literal";
        } else if (afh.verificaSecventa(token)) {
            isValid = true;
            atomType = "Hexadecimal Literal";
        } else if (afb.verificaSecventa(token)) {
            isValid = true;
            atomType = "Binary Literal";
        } else if (afcr.verificaSecventa(token)) {
            isValid = true;
            atomType = "Real Literal";
        }

        if (isValid) {
            if (atomType == "Keyword") {
                if (fipEntries.find(token) == fipEntries.end()) {
                    pushUnique(atomiLexicali, token);
                    fip.addEntry(getCodAtom(token, atomiLexicali), -1);
                    fipEntries.insert(token);
                }
            } else {
                int posInTS = ts.search(token);
                if (posInTS == -1) {
                    ts.insert(token);
                    posInTS = ts.search(token);
                }
                if (fipEntries.find(token) == fipEntries.end()) {
                    fip.addEntry(0, posInTS);
                    fipEntries.insert(token);
                }
            }
            cout << "Atom: " << token << ", Type: " << atomType << ", Line: " << line << ", Char: " << charPosition << endl;
        } else {
            errors.push_back("Error: Invalid token '" + token + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition));
        }
    }

    return atomiLexicali;
}




void saveToFile(vector<Atom> atomiLexicali) {
    ofstream file("../atomiLexicali.txt");
    if (file.is_open()) {
        file << "Atom Lexical\tCod Atom\n";
        file << "--------------------------\n";

        for (const auto& atom : atomiLexicali) {
            file << atom.atomLexical << "\t\t" << atom.codAtom << endl;
        }

        file.close();
    } else {
        cerr << "Error: Could not open file for saving Atom Lexical!" << endl;
    }
}


int main() {
//    AutomatFinitIdentificator afi;
//    test1 = "a123";
//    test2 = "abc";
//    test3 = "_a1b2c3";
//    test4 = "a____b";
//    test5 = "___";
//
//    cout << "\nAutomat Finit Itentifier:\n";
//    cout << test1 << " is " << (afi.verificaSecventa(test1) ? "valid" : "invalid") << endl;
//    cout << test2 << " is " << (afi.verificaSecventa(test2) ? "valid" : "invalid") << endl;
//    cout << test3 << " is " << (afi.verificaSecventa(test3) ? "valid" : "invalid") << endl;
//    cout << test4 << " is " << (afi.verificaSecventa(test4) ? "valid" : "invalid") << endl;
//    cout << test5 << " is " << (afi.verificaSecventa(test5) ? "valid" : "invalid") << endl;
//
//
//
//    AutomatFinitDecimal afd;
//    test1 = "1234";
//    test2 = "0";
//    test3 = "12'34l";
//    test4 = "1234u";
//    test5 = "12'34'56'78z";
//
//    cout << "\nAutomat Finit Decimal:\n";
//    cout << test1 << " is " << (afd.verificaSecventa(test1) ? "valid" : "invalid") << endl;
//    cout << test2 << " is " << (afd.verificaSecventa(test2) ? "valid" : "invalid") << endl;
//    cout << test3 << " is " << (afd.verificaSecventa(test3) ? "valid" : "invalid") << endl;
//    cout << test4 << " is " << (afd.verificaSecventa(test4) ? "valid" : "invalid") << endl;
//    cout << test5 << " is " << (afd.verificaSecventa(test5) ? "valid" : "invalid") << endl;
//
//
//
//    AutomatFinitOctal afo;
//
//    string test1 = "0123";
//    string test2 = "0'0";
//    string test3 = "0'123L";
//    string test4 = "0123u";
//    string test5 = "0'12'34'56z";
//
//    cout << "\nAutomat Finit Octal:\n";
//    cout << test1 << " is " << (afo.verificaSecventa(test1) ? "valid" : "invalid") << endl;
//    cout << test2 << " is " << (afo.verificaSecventa(test2) ? "valid" : "invalid") << endl;
//    cout << test3 << " is " << (afo.verificaSecventa(test3) ? "valid" : "invalid") << endl;
//    cout << test4 << " is " << (afo.verificaSecventa(test4) ? "valid" : "invalid") << endl;
//    cout << test5 << " is " << (afo.verificaSecventa(test5) ? "valid" : "invalid") << endl;
//
//
//
//    AutomatFinitHexa afh;
//
//    test1 = "0x123";
//    test2 = "0X0";
//    test3 = "0x123L";
//    test4 = "0xABCDEFu";
//    test5 = "0X12'34'56zu";
//
//    cout << "\nAutomat Finit Hexadecimal:\n";
//    cout << test1 << " is " << (afh.verificaSecventa(test1) ? "valid" : "invalid") << endl;
//    cout << test2 << " is " << (afh.verificaSecventa(test2) ? "valid" : "invalid") << endl;
//    cout << test3 << " is " << (afh.verificaSecventa(test3) ? "valid" : "invalid") << endl;
//    cout << test4 << " is " << (afh.verificaSecventa(test4) ? "valid" : "invalid") << endl;
//    cout << test5 << " is " << (afh.verificaSecventa(test5) ? "valid" : "invalid") << endl;
//
//
//
//    AutomatFinitBinaries afb;
//
//    test1 = "0b101";
//    test2 = "0B0";
//    test3 = "0b101L";
//    test4 = "0B1101u";
//    test5 = "0b1'01'01zu";
//
//    cout << "\nAutomat Finit Binary:\n";
//    cout << test1 << " is " << (afb.verificaSecventa(test1) ? "valid" : "invalid") << endl;
//    cout << test2 << " is " << (afb.verificaSecventa(test2) ? "valid" : "invalid") << endl;
//    cout << test3 << " is " << (afb.verificaSecventa(test3) ? "valid" : "invalid") << endl;
//    cout << test4 << " is " << (afb.verificaSecventa(test4) ? "valid" : "invalid") << endl;
//    cout << test5 << " is " << (afb.verificaSecventa(test5) ? "valid" : "invalid") << endl;
//
//
//
//    AutomatFinitConstanteReale afcr;
//    test1 = "010.e+1f";
//    test2 = "123E+5L";
//    test3 = "-00103e4f";
//    test4 = ".456E-5";
//    test5 = "1.23e+4L";
//
//    cout << "\nAutomat Finit Constante Reale:\n";
//    cout << test1 << " is " << (afcr.verificaSecventa(test1) ? "valid" : "invalid") << endl;
//    cout << test2 << " is " << (afcr.verificaSecventa(test2) ? "valid" : "invalid") << endl;
//    cout << test3 << " is " << (afcr.verificaSecventa(test3) ? "valid" : "invalid") << endl;
//    cout << test4 << " is " << (afcr.verificaSecventa(test4) ? "valid" : "invalid") << endl;
//    cout << test5 << " is " << (afcr.verificaSecventa(test5) ? "valid" : "invalid") << endl;


    ifstream file("../data_fara_spatii.txt");

    if (!file.is_open()) {
        cerr << "Error: Could not open the file!" << endl;
        return 1;
    }

    string code((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
    if (code.empty()) {
        cerr << "Error: The file is empty!" << endl;
        return 1;
    }

    atomNo = 2;
    vector<string> errors;
    vector<Atom> atomiLexicali;
    atomiLexicali.push_back({"ID", 0});
    atomiLexicali.push_back({"CONST", 1});

    FIP fip;
    TS ts;

    atomiLexicali = lexicalAnalyzer(code, errors, ts, fip, atomiLexicali);

    saveToFile(atomiLexicali);

    std::ofstream tsFile("../ts.txt");
    std::ofstream fipFile("../fip.txt");

    ts.saveTS(tsFile);
    fip.saveFIP(fipFile);

    if (!errors.empty()) {
        cout << "\nErrors found during lexical analysis:\n";
        for (const string& error : errors) {
            cerr << error << endl;
        }
    } else {
        cout << "\nNo errors found during lexical analysis." << endl;
    }

    return 0;
}