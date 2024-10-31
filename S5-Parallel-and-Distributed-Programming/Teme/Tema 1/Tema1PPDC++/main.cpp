#include <iostream>
#include <fstream>
#include <functional>
#include <chrono>
#include <thread>
#include <cmath>

#include "DynamicMatrices.h"
#include "StaticMatrices.h"

using namespace std;

void runStatic() {
    StaticMatrices* staticMatrices = new StaticMatrices();
    staticMatrices->run();
    delete staticMatrices;
}

void runDynamic() {
    DynamicMatrices* dynamicMatrices = new DynamicMatrices();
    dynamicMatrices->run();
    delete dynamicMatrices;
}

int main() {
    std::cout << "Program has started." << std::endl;

    // runStatic();
    // runDynamic();

    return 0;
}