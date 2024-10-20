#include <iostream>
#include <functional>
#include <chrono>
#include <thread>
#include <cmath>

using namespace std;

void vectorsSum(int length, double vector1[], double vector2[], double resultVector[], function<double(double, double)> f) {
    for (int i = 0; i < length; i++) {
        resultVector[i] = f(vector1[i], vector2[i]);
    }
}

void partialSum(int start, int end, double vector1[], double vector2[], double resultVector[], function<double(double, double)> f) {
    for (int i = start; i < end; i++) {
        resultVector[i] = f(vector1[i], vector2[i]);
    }
}

void sumOfThreads(int noOfThreads, int length, double vector1[], double vector2[], double resultVector[], function<double(double, double)> f) {
    thread** threads = new thread*[noOfThreads];

    int batch = length / noOfThreads;
    int rest = length % noOfThreads;

    int start = 0, end = 0;
    for (int i = 0; i < noOfThreads; i++) {
        int elementPerThread = batch;
        if (rest > 0) {
            elementPerThread += 1;
            rest -= 1;
        }

        end = start + elementPerThread;
        threads[i] = new thread(partialSum, start, end, vector1, vector2, resultVector, f);
        start = end;
    }

    for (int i = 0; i < noOfThreads; i++)
        threads[i]->join();
}


void runCyclicThread(int step, int max, int start, double vector1[], double vector2[], double resultVector[], function<double(double, double)> f) {
    for (int i = start; i < max; i += step) {
        resultVector[i] = f(vector1[i], vector2[i]);
    }
}

void sumOfThreadsCyclic(int noOfThreads, int length, double vector1[], double vector2[], double resultVector[], function<double(double, double)> f) {
    thread **threads = new thread *[noOfThreads];

    for (int i = 0; i < noOfThreads; i++) {
        threads[i] = new thread(runCyclicThread, noOfThreads, length, i, vector1, vector2, resultVector, f);
    }

    for (int i = 0; i < noOfThreads; i++)
        threads[i]->join();
}


void initializeVector(int length, double vector[]) {
    for (int i = 0; i < length; i++)
        vector[i] = i;
}

bool verifyVectorsSimilarity(int length, double vector1[], double vector2[]) {
    for (int i = 0; i < length; i++)
        if (vector1[i] != vector2[i])
            return false;
    return true;
}

void printVector(int length, double vector[]) {
    for (int i = 0; i < length; i++) {
        cout << i << " ";
    }
    cout << endl;
}

const int MAX = 100;
/*double vector1[MAX];
double vector2[MAX];
double vector3[MAX];
double vector4[MAX];*/


int main() {
    std::cout << "Hello, World!" << std::endl;

    int max = MAX;
    int noOfThreads = 10;

    double* vector1 = new double[max];
    double* vector2 = new double[max];
    double* vector3 = new double[max];
    double* vector4 = new double[max];
    double* vector5 = new double[max];

    initializeVector(max, vector1);
    initializeVector(max, vector2);

    auto start = std::chrono::steady_clock::now();

    vectorsSum(max, vector1, vector2, vector3, [=](double a, double b) { return sqrt(pow(a, 3) + pow(b, 3)); });

    auto end = std::chrono::steady_clock::now();

    chrono::duration<double, milli> executionTime = end - start;
    //chrono::duration<double, milli> executionTime;
    //cout << chrono::duration_cast<chrono::nanoseconds>(end - start).count();

    // printVector(max, vector3);

    std::cout << executionTime.count();


    cout << endl;


    start = std::chrono::steady_clock::now();

    sumOfThreads(noOfThreads, max, vector1, vector2, vector4, [=](double a, double b) { return sqrt(pow(a, 3) + pow(b, 3)); });

    end = std::chrono::steady_clock::now();

    executionTime = end - start;

    // printVector(max, vector4);

    std::cout << executionTime.count();


    cout << endl;


    start = std::chrono::steady_clock::now();

    sumOfThreadsCyclic(noOfThreads, max, vector1, vector2, vector5, [=](double a, double b) { return sqrt(pow(a, 3) + pow(b, 3)); });

    end = std::chrono::steady_clock::now();

    executionTime = end - start;

    // printVector(max, vector5);

    std::cout << executionTime.count();




    if (verifyVectorsSimilarity(max, vector3, vector4))
        cout << "\nThe Vectors are Equal!";
    else
        cout << "\nThe Vectors are Not Equal!";

    return 0;
}
