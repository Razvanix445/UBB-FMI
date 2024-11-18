//
// Created by razva on 11/10/2024.
//

#include "AutomatFinitConstanteReale.h"

AutomatFinitConstanteReale::AutomatFinitConstanteReale() {
    transitions[{"q0", '.'}] = "q5";
    transitions[{"q0", '0'}] = "q2";
    for (char digit = '1'; digit <= '9'; ++digit)
        transitions[{"q0", digit}] = "q3";

    transitions[{"q2", '.'}] = "q4";
    transitions[{"q2", 'e'}] = "q7";
    transitions[{"q2", 'E'}] = "q7";
    transitions[{"q2", 'l'}] = "q9";
    transitions[{"q2", 'L'}] = "q9";

    transitions[{"q3", '.'}] = "q4";
    transitions[{"q3", 'e'}] = "q7";
    transitions[{"q3", 'E'}] = "q7";
    transitions[{"q3", 'l'}] = "q9";
    transitions[{"q3", 'L'}] = "q9";
    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q3", digit}] = "q3";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q4", digit}] = "q4";
    transitions[{"q4", 'e'}] = "q7";
    transitions[{"q4", 'E'}] = "q7";
    transitions[{"q4", 'l'}] = "q9";
    transitions[{"q4", 'L'}] = "q9";
    transitions[{"q4", 'f'}] = "q9";
    transitions[{"q4", 'F'}] = "q9";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q5", digit}] = "q4";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q6", digit}] = "q6";
    transitions[{"q6", 'l'}] = "q9";
    transitions[{"q6", 'L'}] = "q9";
    transitions[{"q6", 'f'}] = "q9";
    transitions[{"q6", 'F'}] = "q9";

    transitions[{"q7", '+'}] = "q8";
    transitions[{"q7", '-'}] = "q8";
    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q7", digit}] = "q6";

    for (char digit = '0'; digit <= '9'; ++digit)
        transitions[{"q8", digit}] = "q6";
}

bool AutomatFinitConstanteReale::verificaSecventa(const string& secventa) {
    string currentState = startState;

    for (const auto& symbol : secventa) {
        if (transitions.find({currentState, symbol}) == transitions.end()) {
            return false;
        }
        currentState = transitions[{currentState, symbol}];
    }

    return finalStates.find(currentState) != finalStates.end();
}