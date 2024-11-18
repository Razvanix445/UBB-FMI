//
// Created by razva on 11/11/2024.
//

#include "AutomatFinitHexa.h"

AutomatFinitHexa::AutomatFinitHexa() {
    transitions[{"q0", '0'}] = "q1";

    transitions[{"q1", 'x'}] = "q8";
    transitions[{"q1", 'X'}] = "q8";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q8", digit}] = "q2";
    for (char digit = 'a'; digit <= 'f'; ++digit)
        transitions[{"q8", digit}] = "q2";
    for (char digit = 'A'; digit <= 'F'; ++digit)
        transitions[{"q8", digit}] = "q2";

    transitions[{"q2", '\''}] = "q6";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q6", digit}] = "q2";
    for (char digit = 'a'; digit <= 'f'; ++digit)
        transitions[{"q6", digit}] = "q2";
    for (char digit = 'A'; digit <= 'F'; ++digit)
        transitions[{"q6", digit}] = "q2";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q2", digit}] = "q2";
    for (char digit = 'a'; digit <= 'f'; ++digit)
        transitions[{"q2", digit}] = "q2";
    for (char digit = 'A'; digit <= 'F'; ++digit)
        transitions[{"q2", digit}] = "q2";
    transitions[{"q2", 'u'}] = "q3";
    transitions[{"q2", 'U'}] = "q3";
    transitions[{"q2", 'z'}] = "q4";
    transitions[{"q2", 'Z'}] = "q4";
    transitions[{"q2", 'l'}] = "q4";
    transitions[{"q2", 'L'}] = "q4";

    transitions[{"q3", 'l'}] = "q5";
    transitions[{"q3", 'L'}] = "q5";
    transitions[{"q3", 'z'}] = "q5";
    transitions[{"q3", 'Z'}] = "q5";

    transitions[{"q4", 'u'}] = "q5";
    transitions[{"q4", 'U'}] = "q5";

//    // For + and -
//    transitions[{"q0", '-'}] = "q7";
//    transitions[{"q0", '+'}] = "q7";
//    transitions[{"q7", '0'}] = "q1";
}

bool AutomatFinitHexa::verificaSecventa(const string &secventa) {
    string currentState = startState;

    for (const auto& symbol : secventa) {
        if (transitions.find({currentState, symbol}) == transitions.end()) {
            return false;
        }
        currentState = transitions[{currentState, symbol}];
    }

    return finalStates.find(currentState) != finalStates.end();
}