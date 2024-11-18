//
// Created by razva on 11/10/2024.
//

#include "AutomatFinitIdentificatori.h"

AutomatFinitIdentificator::AutomatFinitIdentificator() {
    for (char letter = 'a'; letter <= 'z'; ++letter) {
        transitions[{"q0", letter}] = "q1";
        transitions[{"q1", letter}] = "q1";
    }
    for (char letter = 'A'; letter <= 'Z'; ++letter) {
        transitions[{"q0", letter}] = "q1";
        transitions[{"q1", letter}] = "q1";
    }
    transitions[{"q0", '_'}] = "q1";
    transitions[{"q1", '_'}] = "q1";

    // Tranziții pentru cifre (doar din starea `q1`)
    for (char digit = '0'; digit <= '9'; ++digit) {
        transitions[{"q1", digit}] = "q1";
    }
}

bool AutomatFinitIdentificator::verificaSecventa(const string& secventa) {
    string currentState = startState;

    for (const auto& symbol : secventa) {
        if (transitions.find({currentState, symbol}) == transitions.end()) {
            return false;
        }
        currentState = transitions[{currentState, symbol}];
    }

    return finalStates.find(currentState) != finalStates.end();
}