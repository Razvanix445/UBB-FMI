//
// Created by razva on 10/19/2024.
//

#ifndef TEMA1PPD_DYNAMICMATRICES_H
#define TEMA1PPD_DYNAMICMATRICES_H

#include <iostream>
#include <fstream>
#include <functional>
#include <chrono>
#include <thread>
#include <cmath>

using namespace std;

class DynamicMatrices {

private:
// Constants
    static const int N = 10000;
    static const int M = 10000;
    static const int n = 5;

// Dynamic Matrices
    int** matrix;
    int** convolutionMatrix;
    int** borderedMatrix;
    int** resultMatrix;

    void allocateMatrices();
    void deallocateMatrices();
    void readOriginalMatrix(ifstream &data);
    void readConvolutionMatrix(ifstream &data);
    void borderMatrix();
    void computeSequential();
    void computeForRange(int startRow, int endRow, int startCol, int endCol);
    void computeParallelVertical(int noOfThreads);
    void computeParallelHorizontal(int noOfThreads);

public:
    void run();
};


#endif //TEMA1PPD_DYNAMICMATRICES_H
