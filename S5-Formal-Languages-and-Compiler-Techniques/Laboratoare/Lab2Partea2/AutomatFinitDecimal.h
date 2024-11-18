//
// Created by razva on 11/11/2024.
//

#ifndef LAB2PARTEA2_AUTOMATFINITDECIMAL_H
#define LAB2PARTEA2_AUTOMATFINITDECIMAL_H


#include <iostream>
#include <string>
#include <set>
#include <map>
#include <cctype>

using namespace std;

class AutomatFinitDecimal {
public:
    set<string> states = {"q0", "q1", "q2", "q3", "q4", "q5"};
    set<char> alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'u', 'U', 'l', 'L', 'z', 'Z'};
    map<pair<string, char>, string> transitions;
    string startState = "q0";
    set<string> finalStates = {"q1", "q3", "q4", "q5"};

    AutomatFinitDecimal();

    bool verificaSecventa(const string& secventa);
};


#endif //LAB2PARTEA2_AUTOMATFINITDECIMAL_H
