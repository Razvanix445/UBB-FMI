//
// Created by razva on 10/27/2024.
//

#ifndef TEMA1PPD_DYNAMICMATRICES_H
#define TEMA1PPD_DYNAMICMATRICES_H

#include <iostream>
#include <fstream>
#include <functional>
#include <algorithm>
#include <chrono>
#include <thread>
#include <atomic>
#include <barrier>
#include <vector>
#include <cmath>
#include <mutex>
#include <condition_variable>
#include <barrier>
#include <cstring>

using namespace std;

class my_barrier {
public:
    explicit my_barrier(int count) : thread_count(count), counter(0), waiting(0) {}

    void wait() {
        std::unique_lock<std::mutex> lock(mutex_);
        int local_counter = counter;
        ++waiting;

        if (waiting == thread_count) {
            // Release all threads and reset the barrier for reuse
            std::cout << "Barrier releasing all threads\n"; // Debug output
            counter++;
            waiting = 0;
            condition_.notify_all();
        } else {
            // Debug output to track waiting threads
            std::cout << "Thread waiting at barrier, waiting count = " << waiting << "\n";
            condition_.wait(lock, [&] { return counter != local_counter; });
            std::cout << "Thread passed the barrier\n"; // Debug output after pass
        }
    }

private:
    std::mutex mutex_;
    std::condition_variable condition_;
    int thread_count;
    int counter;
    int waiting;
};


class DynamicMatrices {

private:
// Constants
    int N;
    int M;
    static const int n = 3;
    int noOfThreads;

// Dynamic Matrices
    int** matrix;
    int** convolutionMatrix;
    my_barrier barrier;

    void allocateMatrices();
    void deallocateMatrices();
    void readOriginalMatrix(ifstream &data);
    void readConvolutionMatrix(ifstream &data);
    void writeToFile(const string& filename);
    bool areEqual(const string& filename1, const string& filename2);
    void computeSequential();
    void computeForRange(int startRow, int endRow);
    void computeParallelHorizontal(int noOfThreads);

public:
    DynamicMatrices(int noOfThreads): barrier(noOfThreads) {
        this->noOfThreads = noOfThreads;
        allocateMatrices();
    }

    ~DynamicMatrices() {
        //deallocateMatrices();
    }

    void run();
};


#endif //TEMA1PPD_DYNAMICMATRICES_H
