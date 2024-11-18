//
// Created by razva on 11/11/2024.
//

#include "AutomatFinitBinaries.h"

AutomatFinitBinaries::AutomatFinitBinaries() {
    transitions[{"q0", '0'}] = "q1";

    transitions[{"q1", 'b'}] = "q8";
    transitions[{"q1", 'B'}] = "q8";

    transitions[{"q8", '0'}] = "q2";
    transitions[{"q8", '1'}] = "q2";

    transitions[{"q2", '\''}] = "q3";
    transitions[{"q3", '0'}] = "q2";
    transitions[{"q3", '1'}] = "q2";

    transitions[{"q2", '0'}] = "q2";
    transitions[{"q2", '1'}] = "q2";

    transitions[{"q2", 'u'}] = "q4";
    transitions[{"q2", 'U'}] = "q4";
    transitions[{"q2", 'z'}] = "q5";
    transitions[{"q2", 'Z'}] = "q5";
    transitions[{"q2", 'l'}] = "q5";
    transitions[{"q2", 'L'}] = "q5";

    transitions[{"q4", 'l'}] = "q6";
    transitions[{"q4", 'L'}] = "q6";
    transitions[{"q4", 'z'}] = "q6";
    transitions[{"q4", 'Z'}] = "q6";

    transitions[{"q5", 'u'}] = "q6";
    transitions[{"q5", 'U'}] = "q6";

//    // For + and -
//    transitions[{"q0", '-'}] = "q7";
//    transitions[{"q0", '+'}] = "q7";
//    transitions[{"q7", '0'}] = "q1";
}

bool AutomatFinitBinaries::verificaSecventa(const string &secventa) {
    string currentState = startState;

    for (const auto& symbol : secventa) {
        if (transitions.find({currentState, symbol}) == transitions.end()) {
            return false;
        }
        currentState = transitions[{currentState, symbol}];
    }

    return finalStates.find(currentState) != finalStates.end();
}