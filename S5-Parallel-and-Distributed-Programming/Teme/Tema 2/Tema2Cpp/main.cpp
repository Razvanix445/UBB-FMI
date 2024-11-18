#include <iostream>
#include <fstream>
#include <functional>
#include <chrono>
#include <thread>
#include <cmath>

#include "DynamicMatrices.h"

using namespace std;

int main() {
    std::cout << "Program has started." << std::endl;

    DynamicMatrices* dynamicMatrices = new DynamicMatrices(8);
    dynamicMatrices->run();
    delete dynamicMatrices;

    return 0;
}