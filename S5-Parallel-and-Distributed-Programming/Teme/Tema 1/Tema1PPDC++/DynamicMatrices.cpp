//
// Created by razva on 10/19/2024.
//

#include "DynamicMatrices.h"

void DynamicMatrices::allocateMatrices() {
    matrix = new int*[N];
    for (int i = 0; i < N; i++)
        matrix[i] = new int[M];

    convolutionMatrix = new int*[n];
    for (int i = 0; i < n; i++)
        convolutionMatrix[i] = new int[n];

    borderedMatrix = new int*[N + n - 1];
    for (int i = 0; i < N + n - 1; i++)
        borderedMatrix[i] = new int[M + n - 1];

    resultMatrix = new int*[N];
    for (int i = 0; i < N; i++)
        resultMatrix[i] = new int[M];
}

void DynamicMatrices::deallocateMatrices() {
    for (int i = 0; i < N; i++)
        delete[] matrix[i];
    delete[] matrix;

    for (int i = 0; i < n; i++)
        delete[] convolutionMatrix[i];
    delete[] convolutionMatrix;

    for (int i = 0; i < N + n - 1; i++)
        delete[] borderedMatrix[i];
    delete[] borderedMatrix;

    for (int i = 0; i < N; i++)
        delete[] resultMatrix[i];
    delete[] resultMatrix;
}

void DynamicMatrices::readOriginalMatrix(ifstream &data) {
    for (int row = 0; row < N; row ++)
        for (int col = 0; col < M; col ++)
            data >> matrix[row][col];
}

void DynamicMatrices::readConvolutionMatrix(ifstream &data) {
    for (int row = 0; row < n; row ++)
        for (int col = 0; col < n; col ++)
            data >> convolutionMatrix[row][col];
}

void DynamicMatrices::borderMatrix() {
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

void DynamicMatrices::computeSequential() {
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

void DynamicMatrices::computeForRange(int startRow, int endRow, int startCol, int endCol) {
    for (int row = startRow; row < endRow; row++) {
        for (int col = startCol; col < endCol; col++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sum += borderedMatrix[row - (n / 2) + i][col - (n / 2) + j] * convolutionMatrix[i][j];
                }
            }
            resultMatrix[row - (n / 2)][col - (n / 2)] = sum;
        }
    }
}

void DynamicMatrices::computeParallelVertical(int noOfThreads) {
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

        threads.emplace_back(&DynamicMatrices::computeForRange, this, startRow, endRow, n / 2, M + n / 2);
        startRow = endRow;
    }

    for (auto &thread : threads)
        thread.join();

    auto outTime = chrono::steady_clock::now();
    auto executionTime = chrono::duration_cast<chrono::nanoseconds>(outTime - inTime).count();
    std::cout << "Execution Time: " << executionTime << " ns" << std::endl;

    /*for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << resultMatrix[i][j] << " ";
        }
        cout << endl;
    }*/
}

void DynamicMatrices::computeParallelHorizontal(int noOfThreads) {
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

        threads.emplace_back(&DynamicMatrices::computeForRange, this, n / 2, N + n / 2, startCol, endCol);
        startCol = endCol;
    }

    for (auto &thread : threads)
        thread.join();

    auto outTime = chrono::steady_clock::now();
    auto executionTime = chrono::duration_cast<chrono::nanoseconds>(outTime - inTime).count();
    std::cout << "Execution Time: " << executionTime << " ns" << std::endl;

    /*for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << resultMatrix[i][j] << " ";
        }
        cout << endl;
    }*/
}

void DynamicMatrices::run() {
    allocateMatrices();

    // ==================================
    ifstream data("../N10000M10000n5.txt");

    if (!data.is_open()) {
        cerr << "Could not open the file!" << endl;
        return;
    }

    readOriginalMatrix(data);
    readConvolutionMatrix(data);

    data.close();

    borderMatrix();

    // => EXECUTION
    int noOfThreads = 16;
    // computeSequential();
    // computeParallelVertical(noOfThreads);
    // computeParallelHorizontal(noOfThreads);
    // => END
    // ==================================

    /*cout << "Original Matrix: " << endl;
    for (int row = 0; row < N; row ++) {
        for (int col = 0; col < M; col ++)
            cout << matrix[row][col] << " ";
        cout << endl;
    }

    cout << "Bordered Matrix:" << endl;
    for (int i = 0; i < N + n - 1; i++) {
        for (int j = 0; j < M + n - 1; j++)
            cout << borderedMatrix[i][j] << " ";
        cout << endl;
    }

    cout << "Convolution Matrix: " << endl;
    for (int row = 0; row < n; row ++) {
        for (int col = 0; col < n; col ++)
            cout << convolutionMatrix[row][col] << " ";
        cout << endl;
    }*/

    deallocateMatrices();
}