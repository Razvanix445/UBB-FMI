//
// Created by razva on 10/24/2024.
//

#ifndef LAB1_ANALIZORLEXICAL_TS_H
#define LAB1_ANALIZORLEXICAL_TS_H

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

using namespace std;

struct AtomEntry {
    int pozitie;
    std::string atomLexical;
    int leftLink = -1;
    int rightLink = -1;
};

class TS {
public:
    TS();

    int search(const std::string& atom);
    void insert(const std::string& atom);
    void saveTS(std::ofstream& outFile);

private:
    vector<AtomEntry> arbore;
    int nextPozitie = 0;
};

void insert(const std::string& atom);

int search(const std::string& atom);

void saveTS(std::ofstream& outFile);

#endif //LAB1_ANALIZORLEXICAL_TS_H



//// Tabela de simboluri (bazată pe arbore binar)
//struct Node {
//    int pozitie;
//    std::string atomLexical;
//    Node* left;
//    Node* right;
//
//    Node(int poz, const std::string& lex) : pozitie(poz), atomLexical(lex), left(nullptr), right(nullptr) {}
//};
//
//// Clasa pentru tabela de simboluri (arbore binar)
//class TS {
//public:
//    TS() : root(nullptr), pozitieCurenta(0) {}
//
//    void insert(const std::string& atom);
//
//    int search(const std::string& atom);
//
//    void saveTS(std::ofstream& outFile);
//
//private:
//    Node* root;
//    int pozitieCurenta;
//
//    Node* insertRec(Node* node, const std::string& atom);
//
//    Node* searchRec(Node* node, const std::string& atom);
//
//    void saveInOrder(std::ofstream& outFile, Node* node);
//};
//
//
//#endif //LAB1_ANALIZORLEXICAL_TS_H
