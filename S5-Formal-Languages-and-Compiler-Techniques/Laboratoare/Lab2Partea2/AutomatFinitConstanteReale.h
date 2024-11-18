//
// Created by razva on 11/10/2024.
//

#ifndef LAB2PARTEA2_AUTOMATFINITCONSTANTEREALE_H
#define LAB2PARTEA2_AUTOMATFINITCONSTANTEREALE_H


#include <iostream>
#include <string>
#include <set>
#include <map>
#include <cctype>

using namespace std;

class AutomatFinitConstanteReale {
public:
    set<string> states = {"q0", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9"};
    set<char> alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', 'e', 'E', '+', '-', 'f', 'F', 'l', 'L'};
    map<pair<string, char>, string> transitions;
    string startState = "q0";
    set<string> finalStates = {"q2", "q3", "q4", "q6", "q9"};

    AutomatFinitConstanteReale();

    bool verificaSecventa(const string& secventa);
};


#endif //LAB2PARTEA2_AUTOMATFINITCONSTANTEREALE_H
