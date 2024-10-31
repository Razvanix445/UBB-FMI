//
// Created by razva on 10/19/2024.
//

#include "StaticMatrices.h"

int StaticMatrices::matrix[N][M];
int StaticMatrices::convolutionMatrix[n][n];
int StaticMatrices::borderedMatrix[N + n - 1][M + n - 1];
int StaticMatrices::resultMatrix[N][M];

void StaticMatrices::readOriginalMatrix(ifstream &data) {
    for (int row = 0; row < N; row ++)
        for (int col = 0; col < M; col ++)
            data >> matrix[row][col];
}

void StaticMatrices::readConvolutionMatrix(ifstream &data) {
    for (int row = 0; row < n; row ++)
        for (int col = 0; col < n; col ++)
            data >> convolutionMatrix[row][col];
}

void StaticMatrices::borderMatrix() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            borderedMatrix[i + n / 2][j + n / 2] = matrix[i][j];
        }
    }

    for (int j = 0; j < M; j++) {
        for (int i = 0; i < n / 2; i++) {
            borderedMatrix[i][j + n / 2] = matrix[0][j];
            borderedMatrix[N + i + n / 2][j + n / 2] = matrix[N - 1][j];
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < n / 2; j++) {
            borderedMatrix[i + n / 2][j] = matrix[i][0];
            borderedMatrix[i + n / 2][M + j + n / 2] = matrix[i][M - 1];
        }
    }

    for (int i = 0; i < n / 2; i++) {
        for (int j = 0; j < n / 2; j++) {
            borderedMatrix[i][j] = matrix[0][0];
            borderedMatrix[i][M + j + n / 2] = matrix[0][M - 1];
            borderedMatrix[N + i + n / 2][j] = matrix[N - 1][0];
            borderedMatrix[N + i + n / 2][M + j + n / 2] = matrix[N - 1][M - 1];
        }
    }
}

void StaticMatrices::computeSequential() {
    auto inTime = chrono::steady_clock::now();

    for (int row = (n / 2); row < N + (n / 2); row ++) {
        for (int col = (n / 2); col < M + (n / 2); col ++) {
            int sum = 0;
            for (int i = 0; i < n; i ++) {
                for (int j = 0; j < n; j ++)
                    sum += borderedMatrix[row - (n / 2) + i][col - (n / 2) + j] * convolutionMatrix[i][j];
            }
            resultMatrix[row - (n / 2)][col - (n / 2)] = sum;
        }
    }

    auto outTime = chrono::steady_clock::now();
    auto executionTime = outTime - inTime;
    std::cout << "Execution Time: " << chrono::duration_cast<chrono::nanoseconds>(executionTime).count() << " ns" << std::endl;

    /*for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << resultMatrix[i][j] << " ";
        }
        cout << endl;
    }*/
}

void StaticMatrices::computeForRange(int startRow, int endRow, int startCol, int endCol) {
    for (int row = startRow; row < endRow; row ++) {
        for (int col = startCol; col < endCol; col ++) {
            int sum = 0;
            for (int i = 0; i < n; i ++)
                for (int j = 0; j < n; j ++)
                    sum += borderedMatrix[row - (n / 2) + i][col - (n / 2) + j] * convolutionMatrix[i][j];
            resultMatrix[row - (n / 2)][col - (n / 2)] = sum;
        }
    }
}

void StaticMatrices::computeParallelVertical(int noOfThreads) {
    auto inTime = chrono::steady_clock::now();

    int rowsPerThread = N / noOfThreads;
    int remainingRows = N % noOfThreads;

    vector<thread> threads;
    int startRow = n / 2;

    for (int i = 0; i < noOfThreads; i++) {
        int endRow = startRow + rowsPerThread;
        if (remainingRows > 0) {
            endRow++;
            remainingRows--;
        }

        threads.emplace_back(&StaticMatrices::computeForRange, this, startRow, endRow, n / 2, M + n / 2);
        startRow = endRow;
    }

    for (auto &thread: threads)
        thread.join();

    auto outTime = chrono::steady_clock::now();
    auto executionTime = outTime - inTime;
    std::cout << "Execution Time: " << chrono::duration_cast<chrono::nanoseconds>(executionTime).count() << " ns" << std::endl;

    /*for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << resultMatrix[i][j] << " ";
        }
        cout << endl;
    }*/
}

void StaticMatrices::computeParallelHorizontal(int noOfThreads) {
    auto inTime = chrono::steady_clock::now();

    int colsPerThread = M / noOfThreads;
    int remainingCols = M % noOfThreads;

    vector<thread> threads;
    int startCol = n / 2;

    for (int i = 0; i < noOfThreads; i++) {
        int endCol = startCol + colsPerThread;
        if (remainingCols > 0) {
            endCol++;
            remainingCols--;
        }

        threads.emplace_back(&StaticMatrices::computeForRange, this, n / 2, N + n / 2, startCol, endCol);
        startCol = endCol;
    }

    for (auto &thread: threads)
        thread.join();

    auto outTime = chrono::steady_clock::now();
    auto executionTime = outTime - inTime;
    std::cout << "Execution Time: " << chrono::duration_cast<chrono::nanoseconds>(executionTime).count() << " ns" << std::endl;

    /*for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << resultMatrix[i][j] << " ";
        }
        cout << endl;
    }*/
}

void StaticMatrices::run() {
    ifstream data("../N10000M10000n5.txt");

    if (!data.is_open()) {
        cerr << "Could not open the file!" << endl;
        return;
    }

    readOriginalMatrix(data);
    readConvolutionMatrix(data);

    data.close();

    borderMatrix();

    // EXECUTION =>
    int noOfThreads = 16;
    // computeSequential();
    // computeParallelVertical(noOfThreads);
    // computeParallelHorizontal(noOfThreads);
    // => END
}