//
// Created by razva on 11/11/2024.
//

#include "AutomatFinitOctal.h"

AutomatFinitOctal::AutomatFinitOctal() {
    transitions[{"q0", '0'}] = "q1";

    for (char digit = '0'; digit <= '7'; ++digit)
        transitions[{"q1", digit}] = "q1";
    transitions[{"q1", '\''}] = "q2";

    transitions[{"q1", 'u'}] = "q3";
    transitions[{"q1", 'U'}] = "q3";
    transitions[{"q1", 'l'}] = "q4";
    transitions[{"q1", 'L'}] = "q4";
    transitions[{"q1", 'z'}] = "q4";
    transitions[{"q1", 'Z'}] = "q4";

    transitions[{"q3", 'l'}] = "q5";
    transitions[{"q3", 'L'}] = "q5";
    transitions[{"q3", 'z'}] = "q5";
    transitions[{"q3", 'Z'}] = "q5";

    transitions[{"q4", 'u'}] = "q5";
    transitions[{"q4", 'U'}] = "q5";

//    // For + and -
//    transitions[{"q0", '+'}] = "q7";
//    transitions[{"q0", '-'}] = "q7";
//    transitions[{"q7", '0'}] = "q1";
}

bool AutomatFinitOctal::verificaSecventa(const string &secventa) {
    string currentState = startState;

    for (const auto& symbol : secventa) {
        if (transitions.find({currentState, symbol}) == transitions.end()) {
            return false;
        }
        currentState = transitions[{currentState, symbol}];
    }

    return finalStates.find(currentState) != finalStates.end();
}