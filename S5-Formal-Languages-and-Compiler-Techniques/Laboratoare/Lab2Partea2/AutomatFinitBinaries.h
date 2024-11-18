//
// Created by razva on 11/11/2024.
//

#ifndef LAB2PARTEA2_AUTOMATFINITBINARIES_H
#define LAB2PARTEA2_AUTOMATFINITBINARIES_H


#include <iostream>
#include <string>
#include <set>
#include <map>
#include <cctype>

using namespace std;

class AutomatFinitBinaries {
public:
    set<string> states = {"q0", "q1", "q2", "q3", "q4", "q5", "q6", "q8"};
    set<char> alphabet = {'0', '1', 'b', 'B', 'u', 'U', 'l', 'L', 'z', 'Z'};
    map<pair<string, char>, string> transitions;
    string startState = "q0";
    set<string> finalStates = {"q2", "q4", "q5", "q6"};

    AutomatFinitBinaries();

    bool verificaSecventa(const string& secventa);
};


#endif //LAB2PARTEA2_AUTOMATFINITBINARIES_H
