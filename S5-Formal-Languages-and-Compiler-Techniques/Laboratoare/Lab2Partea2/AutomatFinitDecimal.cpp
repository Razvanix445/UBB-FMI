//
// Created by razva on 11/11/2024.
//

#include "AutomatFinitDecimal.h"

AutomatFinitDecimal::AutomatFinitDecimal() {
    for (char digit = '1'; digit <= '9'; ++digit)
        transitions[{"q0", digit}] = "q1";

    transitions[{"q1", 'l'}] = "q3";
    transitions[{"q1", 'L'}] = "q3";
    transitions[{"q1", 'z'}] = "q3";
    transitions[{"q1", 'Z'}] = "q3";

    transitions[{"q1", 'u'}] = "q4";
    transitions[{"q1", 'U'}] = "q4";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q1", digit}] = "q1";

    transitions[{"q1", '\''}] = "q2";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q2", digit}] = "q1";

    transitions[{"q3", 'u'}] = "q5";
    transitions[{"q3", 'U'}] = "q5";

    transitions[{"q4", 'l'}] = "q5";
    transitions[{"q4", 'L'}] = "q5";
    transitions[{"q4", 'z'}] = "q5";
    transitions[{"q4", 'Z'}] = "q5";

//    // For + and -
//    transitions[{"q0", '+'}] = "q7";
//    transitions[{"q0", '-'}] = "q7";
//
//    transitions[{"q7", '0'}] = "q1";
//    for (char digit = '1'; digit <= '9'; ++digit)
//        transitions[{"q7", '-'}] = "q2";
}

bool AutomatFinitDecimal::verificaSecventa(const string &secventa) {
    string currentState = startState;

    for (const auto& symbol : secventa) {
        if (transitions.find({currentState, symbol}) == transitions.end()) {
            return false;
        }
        currentState = transitions[{currentState, symbol}];
    }

    return finalStates.find(currentState) != finalStates.end();
}