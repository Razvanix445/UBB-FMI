//
// Created by razva on 10/19/2024.
//

#ifndef TEMA1PPD_STATICMATRICES_H
#define TEMA1PPD_STATICMATRICES_H

#include <iostream>
#include <fstream>
#include <functional>
#include <chrono>
#include <thread>
#include <cmath>
#include <vector>

using namespace std;

class StaticMatrices {

private:
// Constants
    static const int N = 10000;
    static const int M = 10000;
    static const int n = 5;
    static const int p = 4;

// Static Matrices
    static int matrix[N][M];
    static int convolutionMatrix[n][n];
    static int borderedMatrix[N + n - 1][M + n - 1];
    static int resultMatrix[N][M];

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


#endif //TEMA1PPD_STATICMATRICES_H
