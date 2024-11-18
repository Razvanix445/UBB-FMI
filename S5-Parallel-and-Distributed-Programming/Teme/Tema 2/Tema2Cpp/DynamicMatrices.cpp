//
// Created by razva on 10/27/2024.
//

#include "DynamicMatrices.h"

void DynamicMatrices::allocateMatrices() {
    // Allocating the bordered matrix with additional rows and columns for convolution borders
    matrix = new int*[N];
    for (int i = 0; i < N; i++)
        matrix[i] = new int[M];

    // Allocating the convolution matrix
    convolutionMatrix = new int*[3];
    for (int i = 0; i < 3; i++)
        convolutionMatrix[i] = new int[3];
}

void DynamicMatrices::deallocateMatrices() {
    // Deallocating bordered matrix
    for (int i = 0; i < N; i++)
        delete[] matrix[i];
    delete[] matrix;

    // Deallocating convolution matrix
    for (int i = 0; i < 3; i++)
        delete[] convolutionMatrix[i];
    delete[] convolutionMatrix;
}

void DynamicMatrices::readOriginalMatrix(ifstream &data) {
    // Read the original matrix and place it into the bordered matrix with appropriate padding
    for (int row = 0; row < N; row++) {
        for (int col = 0; col < M; col++) {
            data >> matrix[row][col];
        }
    }
}

void DynamicMatrices::readConvolutionMatrix(ifstream &data) {
    // Read the convolution matrix
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            data >> convolutionMatrix[row][col];
        }
    }
}

void DynamicMatrices::writeToFile(const string& filename) {
    ofstream outputFile(filename);

    if (!outputFile.is_open()) {
        cerr << "Could not open the file for writing!" << endl;
        return;
    }

    // Write only the center NxM matrix, excluding the borders
    for (int row = 0; row < N; row++) {
        for (int col = 0; col < M; col++) {
            outputFile << matrix[row][col] << " ";
        }
        outputFile << "\n";
    }

    outputFile.close();
    std::cout << "Matrix successfully written to " << filename << std::endl;
}

bool DynamicMatrices::areEqual(const string& file1, const string& file2) {
    ifstream inputFile1(file1);
    ifstream inputFile2(file2);

    if (!inputFile1.is_open() || !inputFile2.is_open()) {
        cerr << "Could not open one or both files for reading!" << endl;
        return false;
    }

    string line1, line2;
    while (getline(inputFile1, line1) && getline(inputFile2, line2)) {
        if (line1 != line2) {
            inputFile1.close();
            inputFile2.close();
            std::cout << "Matrices are not equal." << std::endl;
            return false;
        }
    }

    if (inputFile1.eof() != inputFile2.eof()) {
        inputFile1.close();
        inputFile2.close();
        std::cout << "Matrices are not equal." << std::endl;
        return false;
    }

    inputFile1.close();
    inputFile2.close();
    std::cout << "Matrices are equal." << std::endl;
    return true;
}

void DynamicMatrices::computeSequential() {
    int* prevRow = new int[M];
    int* currentResultRow = new int[M];
    int* nextRow = new int[M];

    // Initialize prevRow with the first row of the matrix
    std::copy(matrix[0], matrix[0] + M, prevRow);

    for (int row = 0; row < N; row++) {
        // Set nextRow to the next row in the matrix, or duplicate the current row if it's the last one
        if (row < N - 1) {
            std::copy(matrix[row + 1], matrix[row + 1] + M, nextRow);
        } else {
            std::copy(matrix[row], matrix[row] + M, nextRow);
        }

        for (int col = 0; col < M; col++) {
            int convolutionSum = 0;

            // Apply the convolution matrix
            for (int convRow = 0; convRow < n; convRow++) {
                for (int convCol = 0; convCol < n; convCol++) {
                    int neighborRow = row - n / 2 + convRow;
                    int neighborCol = col - n / 2 + convCol;

                    // Boundary conditions: Clamp to matrix edges
                    if (neighborRow < 0) neighborRow = 0;
                    if (neighborCol < 0) neighborCol = 0;
                    if (neighborRow >= N) neighborRow = N - 1;
                    if (neighborCol >= M) neighborCol = M - 1;

                    // Use the appropriate row based on the neighboring position
                    if (neighborRow == row - 1) {
                        convolutionSum += prevRow[neighborCol] * convolutionMatrix[convRow][convCol];
                    } else if (neighborRow >= row + 1) {
                        convolutionSum += nextRow[neighborCol] * convolutionMatrix[convRow][convCol];
                    } else {
                        convolutionSum += matrix[neighborRow][neighborCol] * convolutionMatrix[convRow][convCol];
                    }
                }
            }
            currentResultRow[col] = convolutionSum;
        }

        // Update prevRow to be the current row in matrix
        std::copy(matrix[row], matrix[row] + M, prevRow);

        // Move current result to the main matrix
        std::copy(currentResultRow, currentResultRow + M, matrix[row]);
    }

    // Clean up dynamic memory
    delete[] prevRow;
    delete[] currentResultRow;
    delete[] nextRow;
}

void DynamicMatrices::computeForRange(int startRow, int endRow) {
    int* prevRow = new int[M];
    int* currentResultRow = new int[M];
    int* nextRow = new int[M];

    if (startRow > 0) {
        std::copy(matrix[startRow - 1], matrix[startRow - 1] + M, prevRow);
    } else {
        std::copy(matrix[startRow], matrix[startRow] + M, prevRow);
    }

    if (endRow < N) {
        std::copy(matrix[endRow], matrix[endRow] + M, nextRow);
    }

    std::cout << "Thread processing rows " << startRow << " to " << endRow << " starting computation\n";
    barrier.wait();

    for (int row = startRow; row < endRow; row++) {
        for (int col = 0; col < M; col++) {
            int convolutionSum = 0;

            for (int convRow = 0; convRow < n; convRow++) {
                for (int convCol = 0; convCol < n; convCol++) {
                    int neighborRow = row - n / 2 + convRow;
                    int neighborCol = col - n / 2 + convCol;

                    if (neighborRow < 0) neighborRow = 0;
                    if (neighborCol < 0) neighborCol = 0;
                    if (neighborRow >= N) neighborRow = N - 1;
                    if (neighborCol >= M) neighborCol = M - 1;

                    if (neighborRow == row - 1) {
                        convolutionSum += prevRow[neighborCol] * convolutionMatrix[convRow][convCol];
                    } else if (neighborRow >= endRow) {
                        convolutionSum += nextRow[neighborCol] * convolutionMatrix[convRow][convCol];
                    } else {
                        convolutionSum += matrix[neighborRow][neighborCol] * convolutionMatrix[convRow][convCol];
                    }
                }
            }
            currentResultRow[col] = convolutionSum;
        }
        if (row < N - 1) {
            std::copy(matrix[row], matrix[row] + M, prevRow);
        }
        std::copy(currentResultRow, currentResultRow + M, matrix[row]);
    }

    delete[] prevRow;
    delete[] currentResultRow;
    delete[] nextRow;

    std::cout << "Thread processing rows " << startRow << " to " << endRow << " completed\n";
}

void DynamicMatrices::computeParallelHorizontal(int noOfThreads) {
    std::vector<std::thread> threads(noOfThreads);

    int rowsPerThread = N / noOfThreads;
    int remainingRows = N % noOfThreads;
    int currentStartRow = 0;

    for (int threadNo = 0; threadNo < noOfThreads; threadNo++) {
        int currentEndRow = currentStartRow + rowsPerThread + (remainingRows > 0 ? 1 : 0);
        remainingRows--;

        threads[threadNo] = std::thread(&DynamicMatrices::computeForRange, this, currentStartRow, currentEndRow);
        currentStartRow = currentEndRow;
    }

    for (auto& thread : threads) {
        if (thread.joinable()) {
            thread.join();
        }
    }
}

void DynamicMatrices::run() {
    allocateMatrices();

    // ==================================
    ifstream data("../N10M10n3.txt");

    if (!data.is_open()) {
        cerr << "Could not open the file!" << endl;
        return;
    }

    readOriginalMatrix(data);
    readConvolutionMatrix(data);

    data.close();

    N = 10;
    M = 10;

    // => EXECUTION
    // computeSequential();
    computeParallelHorizontal(noOfThreads);
    // => END
    // ==================================

    writeToFile("../outputTestHorizontal.txt");

    /*if (areEqual("outputTestSequential.txt", "outputTestHorizontal.txt"))
        cout << "Matricele sunt egale!";
    else
        cout << "Matricele NU sunt egale!";*/

    deallocateMatrices();
}