#include <mpi.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <chrono>

using namespace std;

vector<unsigned char> readNumberFromFile(const string& filename, int& num_digits) {
    ifstream fin(filename);
    fin >> num_digits;
    vector<unsigned char> number(num_digits);
    
    for (int i = num_digits - 1; i >= 0; i--) {
        unsigned char digit;
        fin >> digit;
        number[i] = digit - '0';
    }
    fin.close();
    return number;
}

bool filesAreIdentical(const string& file1, const string& file2) {
    ifstream f1(file1);
    ifstream f2(file2);

    if (!f1.is_open() || !f2.is_open()) {
        cerr << "Error opening one of the files for comparison." << endl;
        return false;
    }

    string line1, line2;
    while (getline(f1, line1) && getline(f2, line2)) {
        line1.erase(find_if(line1.rbegin(), line1.rend(), [](int ch) {
            return !isspace(ch);
        }).base(), line1.end());

        line2.erase(find_if(line2.rbegin(), line2.rend(), [](int ch) {
            return !isspace(ch);
        }).base(), line2.end());

        if (line1 != line2) {
            return false;
        }
    }

    return true;
}

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);
    
    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    auto totalTimeStart = chrono::high_resolution_clock::now();
    
    vector<unsigned char> num1, num2;
    int N1 = 0, N2 = 0, N = 0;
    
    if (rank == 0) {
        num1 = readNumberFromFile("test1/Numar1.txt", N1);
        num2 = readNumberFromFile("test1/Numar2.txt", N2);
        
        N = max(N1, N2);
        if (N % size != 0) {
            N = (N / size + 1) * size;
        }
        
        num1.resize(N, 0);
        num2.resize(N, 0);
    }
    
    MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
    
    int chunk_size = N / size;
    vector<unsigned char> local_num1(chunk_size);
    vector<unsigned char> local_num2(chunk_size);
    
    MPI_Scatter(num1.data(), chunk_size, MPI_UNSIGNED_CHAR,
                local_num1.data(), chunk_size, MPI_UNSIGNED_CHAR,
                0, MPI_COMM_WORLD);
    MPI_Scatter(num2.data(), chunk_size, MPI_UNSIGNED_CHAR,
                local_num2.data(), chunk_size, MPI_UNSIGNED_CHAR,
                0, MPI_COMM_WORLD);
    
    vector<unsigned char> local_sum(chunk_size);
    unsigned char carry = 0;

    for (int i = 0; i < chunk_size; i++) {
        int sum = local_num1[i] + local_num2[i] + carry;
        local_sum[i] = sum % 10;
        carry = sum / 10;
    }

    if (rank < size - 1) {
        MPI_Send(&carry, 1, MPI_UNSIGNED_CHAR, rank + 1, 0, MPI_COMM_WORLD);
    }
    
    if (rank > 0) {
        unsigned char received_carry;
        MPI_Recv(&received_carry, 1, MPI_UNSIGNED_CHAR, rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        int i = 0;
        while (received_carry > 0 && i < chunk_size) {
            int sum = local_sum[i] + received_carry;
            local_sum[i] = sum % 10;
            received_carry = sum / 10;
            i++;
        }
    }
    
    vector<unsigned char> final_result;
    if (rank == 0) {
        final_result.resize(N);
    }
    
    MPI_Gather(local_sum.data(), chunk_size, MPI_UNSIGNED_CHAR,
               final_result.data(), chunk_size, MPI_UNSIGNED_CHAR,
               0, MPI_COMM_WORLD);
    
    if (rank == 0) {
        ofstream fout("test1/Numar3V2.txt");

        auto totalTimeEnd = chrono::high_resolution_clock::now();
        auto totalTime = chrono::duration_cast<chrono::nanoseconds>(totalTimeEnd - totalTimeStart).count();
        cout << "Total execution time: " << totalTime << " ns" << endl;

        int actual_size = N;
        while (actual_size > 0 && final_result[actual_size - 1] == 0) {
            actual_size--;
        }
        if (actual_size == 0) actual_size = 1;

        fout << actual_size << endl;

        for (int i = actual_size - 1; i >= 0; i--) {
            fout << (int)final_result[i] << " ";
        }
        fout.close();

        if (filesAreIdentical("test1/Numar3V2.txt", "test1/RezultatCorect.txt")) {
            cout << "The result matches the correct output." << endl;
        } else {
            cout << "The result does NOT match the correct output." << endl;
        }
    }
    
    MPI_Finalize();

    return 0;
}