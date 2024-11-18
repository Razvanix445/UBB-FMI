#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>
#include <chrono>

using namespace std;

vector<unsigned char> readNumber(const string& filename) {
    ifstream file(filename);
    int numDigits;
    file >> numDigits;
    vector<unsigned char> number(numDigits);
    for (int i = 0; i < numDigits; ++i) {
        int digit;
        file >> digit;
        number[numDigits - 1 - i] = digit;
    }
    return number;
}

vector<unsigned char> addLargeNumbers(const vector<unsigned char>& num1, const vector<unsigned char>& num2) {
    int maxSize = max(num1.size(), num2.size());
    vector<unsigned char> result(maxSize + 1, 0);

    unsigned char carry = 0;
    for (int i = 0; i < maxSize; ++i) {
        unsigned char digit1 = (i < num1.size()) ? num1[i] : 0;
        unsigned char digit2 = (i < num2.size()) ? num2[i] : 0;
        
        unsigned char sum = digit1 + digit2 + carry;
        result[i] = sum % 10;
        carry = sum / 10;
    }
    
    if (carry > 0) {
        result[maxSize] = carry;
    }

    while (result.size() > 1 && result.back() == 0) {
        result.pop_back();
    }

    return result;
}

void writeNumberToFile(const vector<unsigned char>& number, const string& filename) {
    ofstream file(filename);
    if (file.is_open()) {
        file << number.size() << "\n";

        for (auto it = number.rbegin(); it != number.rend(); ++it) {
            file << static_cast<int>(*it) << " ";
        }
        file << endl;
        file.close();

        cout << "Rezultatul a fost scris în " << filename << endl;
    } else {
        cerr << "Eroare la deschiderea fișierului " << filename << endl;
    }
}

bool filesAreIdentical(const std::string& file1, const std::string& file2) {
    std::ifstream f1(file1);
    std::ifstream f2(file2);

    if (!f1.is_open() || !f2.is_open()) {
        std::cerr << "Error opening one of the files for comparison." << std::endl;
        return false;
    }

    std::string line1, line2;

    while (std::getline(f1, line1) && std::getline(f2, line2)) {
        line1.erase(std::find_if(line1.rbegin(), line1.rend(), [](int ch) {
            return !std::isspace(ch);
        }).base(), line1.end());

        line2.erase(std::find_if(line2.rbegin(), line2.rend(), [](int ch) {
            return !std::isspace(ch);
        }).base(), line2.end());

        if (line1 != line2) {
            return false;
        }
    }

    return true;
}

int main() {
    auto totalTimeStart = std::chrono::high_resolution_clock::now();

    vector<unsigned char> number1 = readNumber("test1/Numar1.txt");
    vector<unsigned char> number2 = readNumber("test1/Numar2.txt");

    vector<unsigned char> result = addLargeNumbers(number1, number2);

    auto totalTimeEnd = std::chrono::high_resolution_clock::now();
    auto totalTime = std::chrono::duration_cast<std::chrono::nanoseconds>(totalTimeEnd - totalTimeStart).count();
    cout << "Total execution time: " << totalTime << " ns" << endl;

    writeNumberToFile(result, "test1/Numar3Secv.txt");

    if (filesAreIdentical("test1/Numar3Secv.txt", "test1/RezultatCorect.txt")) {
        cout << "The result matches the correct output." << endl;
    } else {
        cout << "The result does NOT match the correct output." << endl;
    }

    return 0;
}
