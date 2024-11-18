#include <iostream>
#include <fstream>
#include <string>

bool areFilesIdentical(const std::string& file1, const std::string& file2) {
    std::ifstream f1(file1, std::ios::binary);
    std::ifstream f2(file2, std::ios::binary);

    // Check if both files could be opened
    if (!f1.is_open() || !f2.is_open()) {
        std::cerr << "Error opening one of the files." << std::endl;
        return false;
    }

    // Compare byte by byte
    char ch1, ch2;
    while (f1.get(ch1) && f2.get(ch2)) {
        if (ch1 != ch2) {
            return false;  // Files differ
        }
    }

    // Check if both files reached the end
    return f1.eof() && f2.eof();
}

int main() {
    std::string file1 = "test1/Numar3Secv.txt", file2 = "test1/Numar3V2.txt";

    if (areFilesIdentical(file1, file2)) {
        std::cout << "The files are identical." << std::endl;
    } else {
        std::cout << "The files are different." << std::endl;
    }

    return 0;
}
