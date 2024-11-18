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

using namespace std;

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
    set<string> fipEntries;
    int i = 0;
    int line = 1;
    int charPosition = 1;

    while (i < code.length()) {
        if (isspace(code[i])) {
            if (code[i] == '\n') {
                line++;
                charPosition = 1;
            } else {
                charPosition++;
            }
            i++;
        } else if (code[i] == '#') {
            // => Partea in care includem declararea librariilor ca antete
            string directive = "#";
            i++; charPosition++;

            while (isalpha(code[i])) {
                directive += code[i];
                i++; charPosition++;
            }

            if (fipEntries.find(directive) == fipEntries.end()) {
                pushUnique(atomiLexicali, directive);
                // fip.addEntry(directive, "antet", line, charPosition);
                fip.addEntry(getCodAtom(directive, atomiLexicali), -1);
                fipEntries.insert(directive);
            }
            cout << "Atom: " << directive << ", Tip: antet, Line: " << line << ", Char: " << charPosition << endl;

            while (isspace(code[i])) {
                if (code[i] == '\n') { line++; charPosition = 1; }
                else charPosition++;
                i++;
            }

            if (code[i] == '<') {
                string header = "";
                header += code[i];
                i++; charPosition++;

                while (code[i] != '>') {
                    header += code[i];
                    i++; charPosition++;
                }
                header += '>';
                i++; charPosition++;

                if (fipEntries.find(header) == fipEntries.end()) {
                    pushUnique(atomiLexicali, header);
                    // fip.addEntry(header, "antet", line, charPosition);
                    fip.addEntry(getCodAtom(header, atomiLexicali), -1);
                    fipEntries.insert(header);
                }
                cout << "Atom: " << header << ", Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
            }
        } else if (code.substr(i, 5) == "using") {
            if (fipEntries.find("using") == fipEntries.end()) {
                pushUnique(atomiLexicali, "using");
                // fip.addEntry("using", "antet", line, charPosition);
                fip.addEntry(getCodAtom("using", atomiLexicali), -1);
                fipEntries.insert("using");
            }
            cout << "Atom: using, Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
            i += 5; charPosition += 5;
            while (isspace(code[i])) {
                if (code[i] == '\n') { line++; charPosition = 1; }
                else charPosition++;
                i++;
            }

            if (code.substr(i, 9) == "namespace") {
                if (fipEntries.find("namespace") == fipEntries.end()) {
                    pushUnique(atomiLexicali, "namespace");
                    // fip.addEntry("namespace", "antet", line, charPosition);
                    fip.addEntry(getCodAtom("namespace", atomiLexicali), -1);
                    fipEntries.insert("namespace");
                }
                cout << "Atom: namespace, Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
                i += 9; charPosition += 9;
                while (isspace(code[i])) {
                    if (code[i] == '\n') { line++; charPosition = 1; }
                    else charPosition++;
                    i++;
                }

                string namespaceName = "";
                while (isalnum(code[i]) || code[i] == '_') {
                    namespaceName += code[i];
                    i++; charPosition++;
                }

                pushUnique(atomiLexicali, namespaceName);
                // fip.addEntry(namespaceName, "antet", line, charPosition);
                if (fipEntries.find(namespaceName) == fipEntries.end()) {
                    fip.addEntry(getCodAtom(namespaceName, atomiLexicali), -1);
                    fipEntries.insert(namespaceName);
                }
                cout << "Atom: " << namespaceName << ", Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
            }
        } else if (isalpha(code[i]) || code[i] == '_') {
            string word = "";
            while (isalnum(code[i]) || code[i] == '_') {
                word += code[i];
                i++; charPosition++;
            }

            if (isKeyword(word)) {
                if (fipEntries.find(word) == fipEntries.end()) {
                    pushUnique(atomiLexicali, word);
                    // fip.addEntry(word, "cuvant cheie", line, charPosition);
                    fip.addEntry(getCodAtom(word, atomiLexicali), -1);
                    fipEntries.insert(word);
                }
                cout << "Atom: " << word << ", Tip: cuvant cheie, Line: " << line << ", Char: " << charPosition << endl;
            } else {
                // ts.insert(word);
                int pozitieInTS = ts.search(word);
                // fip.addEntry(word, "identificator", line, charPosition);
                if (pozitieInTS == -1) {
                    ts.insert(word);
                    pozitieInTS = ts.search(word);
                }
                if (fipEntries.find(word) == fipEntries.end()) {
                    fip.addEntry(0, pozitieInTS);
                    fipEntries.insert(word);
                }
                cout << "Atom: " << word << ", Tip: identificator, Line: " << line << ", Char: " << charPosition << endl;
            }
        } else if (isdigit(code[i])) {
            // => Constante
            string number = "";

            while (isdigit(code[i])) {
                number += code[i];
                i++;
                charPosition++;
            }

            if (code[i] == '.') {
                number += code[i];
                i++;
                charPosition++;

                if (isdigit(code[i])) {
                    while (isdigit(code[i])) {
                        number += code[i];
                        i++;
                        charPosition++;
                    }
                } else {
                    string error = "Error: Invalid token '" + number + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition);
                    errors.push_back(error);
                    number.clear();
                }
            }

            string invalidToken = number;
            bool isInvalid = false;

            while (isalpha(code[i])) {
                invalidToken += code[i];
                i++;
                charPosition++;
                isInvalid = true;
            }

            if (isInvalid) {
                string error = "Error: Invalid token '" + invalidToken + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition - 1);
                errors.push_back(error);
            } else if (!number.empty()) {
                // ts.insert(number);
                int pozitieInTS = ts.search(number);
                // fip.addEntry(number, "constanta", line, charPosition);
                if (pozitieInTS == -1) {
                    ts.insert(number);
                    pozitieInTS = ts.search(number);
                }
                if (fipEntries.find(number) == fipEntries.end()) {
                    fip.addEntry(1, pozitieInTS);
                    fipEntries.insert(number);
                }
                cout << "Atom: " << number << ", Tip: constanta, Line: " << line << ", Char: " << charPosition << endl;
            }

            // => Operatori
        } else if (code[i] == '+' || code[i] == '-' || code[i] == '*' || code[i] == '=' || (code[i] == '<' && code[i + 1] != '<') || (code[i] == '>' && code[i + 1] != '>') || code[i] == '.') {
            string op = string(1, code[i]);
            if (fipEntries.find(op) == fipEntries.end()) {
                pushUnique(atomiLexicali, op);
                // fip.addEntry(string(1, code[i]), "operator", line, charPosition);
                fip.addEntry(getCodAtom(op, atomiLexicali), -1);
                fipEntries.insert(op);
            }
            cout << "Atom: " << code[i] << ", Tip: operator, Line: " << line << ", Char: " << charPosition << endl;
            i++; charPosition++;

            // => Caz special in care avem '<<' sau '>>'
        } else if ((code[i] == '<' && code[i + 1] == '<') || (code[i] == '>' && code[i + 1] == '>')) {
            string sirVid = "";
            sirVid += code[i];
            if (fipEntries.find(sirVid) == fipEntries.end()) {
                pushUnique(atomiLexicali, sirVid);
                // fip.addEntry(code.substr(i, 2), "operator", line, charPosition);
                fip.addEntry(getCodAtom(sirVid, atomiLexicali), -1);
                fipEntries.insert(sirVid);
            }
            cout << "Atom: " << code[i] << code[i + 1] << ", Tip: operator, Line: " << line << ", Char: " << charPosition << endl;
            i += 2; charPosition += 2;

            // => Separatori
        } else if (code[i] == '(' || code[i] == ')' || code[i] == '{' || code[i] == '}' || code[i] == ';' || code[i] == ',') {
            string separator = string(1, code[i]);
            if (fipEntries.find(separator) == fipEntries.end()) {
                pushUnique(atomiLexicali, separator);
                // fip.addEntry(string(1, code[i]), "separator", line, charPosition);
                fip.addEntry(getCodAtom(separator, atomiLexicali), -1);
                fipEntries.insert(separator);
            }
            cout << "Atom: " << code[i] << ", Tip: separator, Line: " << line << ", Char: " << charPosition << endl;
            i++; charPosition++;

            // => Atom necunoscut => eroare
        } else {
            string error = "Error: Unknown atom '" + string(1, code[i]) + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition);
            errors.push_back(error);
            i++; charPosition++;
        }
    }
    return atomiLexicali;
}


void saveToFile(vector<Atom> atomiLexicali) {
    ofstream file("atomiLexicali.txt");
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
    ifstream file("data.txt");

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

    std::ofstream tsFile("ts.txt");
    std::ofstream fipFile("fip.txt");

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



/*
#include <iostream>
#include <fstream>
#include <string>
#include <cctype>
#include <vector>
#include <memory>
#include <map>
#include <sstream>

using namespace std;

// Structura Arbore Binar de Cautare
struct TreeNode {
    string identifier;
    int position;
    unique_ptr<TreeNode> left;
    unique_ptr<TreeNode> right;

    TreeNode(const string& id, int pos) : identifier(id), position(pos), left(nullptr), right(nullptr) {}
};

// Arbore Binar de Cautare pentru tabela de simboluri
class TS {
private:
    unique_ptr<TreeNode> root;
    int currentPosition;

    void insert(unique_ptr<TreeNode>& node, const string& id, int pos) {
        if (!node) {
            node = make_unique<TreeNode>(id, pos);
            return;
        }
        if (id < node->identifier) {
            insert(node->left, id, pos);
        } else if (id > node->identifier) {
            insert(node->right, id, pos);
        }
    }

public:
    TS() : root(nullptr), currentPosition(0) {}

    void insert(const string& id) {
        insert(root, id, currentPosition++);
    }

    void saveToFile(const string& filename) {
        ofstream file(filename);
        if (file.is_open()) {
            file << "Identifier\tPosition\n";
            file << "--------------------------\n";
            saveHelper(file, root);
            file.close();
        } else {
            cerr << "Error: Could not open file for saving TS!" << endl;
        }
    }

    void saveHelper(ofstream& file, unique_ptr<TreeNode>& node) {
        if (node) {
            saveHelper(file, node->left);
            file << node->identifier << "\t" << node->position << endl;
            saveHelper(file, node->right);
        }
    }
};

// Structura FIP
struct FIPEntry {
    string token;
    string type;
    int line;
    int charPosition;
};

class FIP {
private:
    vector<FIPEntry> entries;

public:
    void addEntry(const string& token, const string& type, int line, int charPosition) {
        entries.push_back({token, type, line, charPosition});
    }

    void saveToFile(const string& filename) {
        ofstream file(filename);
        if (file.is_open()) {
            file << "Token\tType\tLine\tChar Position\n";
            file << "-------------------------------------------\n";
            for (const auto& entry : entries) {
                file << entry.token << "\t"
                     << entry.type << "\t"
                     << entry.line << "\t"
                     << entry.charPosition << endl;
            }
            file.close();
        } else {
            cerr << "Error: Could not open file for saving FIP!" << endl;
        }
    }
};

bool isKeyword(const string& word) {
    const string keywords[] = {"int", "double", "void", "struct", "if", "else", "while", "cin", "cout", "return"};
    for (const string& keyword : keywords) {
        if (word == keyword) return true;
    }
    return false;
}

void lexicalAnalyzer(const string& code, vector<string>& errors, TS& ts, FIP& fip) {
    int i = 0;
    int line = 1;
    int charPosition = 1;

    while (i < code.length()) {
        if (isspace(code[i])) {
            if (code[i] == '\n') {
                line++;
                charPosition = 1;
            } else {
                charPosition++;
            }
            i++;
        } else if (code[i] == '#') {
            // => Partea in care includem declararea librariilor ca antete
            string directive = "#";
            i++; charPosition++;

            while (isalpha(code[i])) {
                directive += code[i];
                i++; charPosition++;
            }

            fip.addEntry(directive, "antet", line, charPosition);
            cout << "Atom: " << directive << ", Tip: antet, Line: " << line << ", Char: " << charPosition << endl;

            while (isspace(code[i])) {
                if (code[i] == '\n') { line++; charPosition = 1; }
                else charPosition++;
                i++;
            }

            if (code[i] == '<') {
                string header = "";
                header += code[i];
                i++; charPosition++;

                while (code[i] != '>') {
                    header += code[i];
                    i++; charPosition++;
                }
                header += '>';
                i++; charPosition++;
                fip.addEntry(header, "antet", line, charPosition);
                cout << "Atom: " << header << ", Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
            }
        } else if (code.substr(i, 5) == "using") {
            fip.addEntry("using", "antet", line, charPosition);
            cout << "Atom: using, Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
            i += 5; charPosition += 5;
            while (isspace(code[i])) {
                if (code[i] == '\n') { line++; charPosition = 1; }
                else charPosition++;
                i++;
            }

            if (code.substr(i, 9) == "namespace") {
                fip.addEntry("namespace", "antet", line, charPosition);
                cout << "Atom: namespace, Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
                i += 9; charPosition += 9;
                while (isspace(code[i])) {
                    if (code[i] == '\n') { line++; charPosition = 1; }
                    else charPosition++;
                    i++;
                }

                string namespaceName = "";
                while (isalnum(code[i]) || code[i] == '_') {
                    namespaceName += code[i];
                    i++; charPosition++;
                }

                fip.addEntry(namespaceName, "antet", line, charPosition);
                cout << "Atom: " << namespaceName << ", Tip: antet, Line: " << line << ", Char: " << charPosition << endl;
            }
        } else if (isalpha(code[i]) || code[i] == '_') {
            string word = "";
            while (isalnum(code[i]) || code[i] == '_') {
                word += code[i];
                i++; charPosition++;
            }

            if (isKeyword(word)) {
                fip.addEntry(word, "cuvant cheie", line, charPosition);
                cout << "Atom: " << word << ", Tip: cuvant cheie, Line: " << line << ", Char: " << charPosition << endl;
            } else {
                ts.insert(word);
                fip.addEntry(word, "identificator", line, charPosition);
                cout << "Atom: " << word << ", Tip: identificator, Line: " << line << ", Char: " << charPosition << endl;
            }
        } else if (isdigit(code[i])) {
            // => Constante
            string number = "";

            while (isdigit(code[i])) {
                number += code[i];
                i++;
                charPosition++;
            }

            if (code[i] == '.') {
                number += code[i];
                i++;
                charPosition++;

                if (isdigit(code[i])) {
                    while (isdigit(code[i])) {
                        number += code[i];
                        i++;
                        charPosition++;
                    }
                } else {
                    string error = "Error: Invalid token '" + number + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition);
                    errors.push_back(error);
                    number.clear();
                }
            }

            string invalidToken = number;
            bool isInvalid = false;

            while (isalpha(code[i])) {
                invalidToken += code[i];
                i++;
                charPosition++;
                isInvalid = true;
            }

            if (isInvalid) {
                string error = "Error: Invalid token '" + invalidToken + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition - 1);
                errors.push_back(error);
            } else if (!number.empty()) {
                ts.insert(number);
                fip.addEntry(number, "constanta", line, charPosition);
                cout << "Atom: " << number << ", Tip: constanta, Line: " << line << ", Char: " << charPosition << endl;
            }

            // => Operatori
        } else if (code[i] == '+' || code[i] == '-' || code[i] == '*' || code[i] == '=' || (code[i] == '<' && code[i + 1] != '<') || (code[i] == '>' && code[i + 1] != '>') || code[i] == '.') {
            fip.addEntry(string(1, code[i]), "operator", line, charPosition);
            cout << "Atom: " << code[i] << ", Tip: operator, Line: " << line << ", Char: " << charPosition << endl;
            i++; charPosition++;

            // => Caz special in care avem '<<' sau '>>'
        } else if ((code[i] == '<' && code[i + 1] == '<') || (code[i] == '>' && code[i + 1] == '>')) {
            fip.addEntry(code.substr(i, 2), "operator", line, charPosition);
            cout << "Atom: " << code[i] << code[i + 1] << ", Tip: operator, Line: " << line << ", Char: " << charPosition << endl;
            i += 2; charPosition += 2;

            // => Separatori
        } else if (code[i] == '(' || code[i] == ')' || code[i] == '{' || code[i] == '}' || code[i] == ';' || code[i] == ',') {
            fip.addEntry(string(1, code[i]), "separator", line, charPosition);
            cout << "Atom: " << code[i] << ", Tip: separator, Line: " << line << ", Char: " << charPosition << endl;
            i++; charPosition++;

            // => Atom necunoscut => eroare
        } else {
            string error = "Error: Unknown atom '" + string(1, code[i]) + "' at Line: " + to_string(line) + ", Char: " + to_string(charPosition);
            errors.push_back(error);
            i++; charPosition++;
        }
    }
}


int main() {
    ifstream file("data.txt");

    if (!file.is_open()) {
        cerr << "Error: Could not open the file!" << endl;
        return 1;
    }

    string code((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
    if (code.empty()) {
        cerr << "Error: The file is empty!" << endl;
        return 1;
    }

    vector<string> errors;
    TS ts;
    FIP fip;

    lexicalAnalyzer(code, errors, ts, fip);

    ts.saveToFile("ts.txt");
    fip.saveToFile("fip.txt");

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
 */