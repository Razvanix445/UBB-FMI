//
// Created by razva on 10/24/2024.
//

#ifndef LAB1_ANALIZORLEXICAL_FIP_H
#define LAB1_ANALIZORLEXICAL_FIP_H

#include <iostream>
#include <fstream>
#include <algorithm>
#include <utility>
#include <string>
#include <cctype>
#include <vector>
#include <memory>
#include <map>
#include <sstream>
#include <set>
#include <iomanip>

using namespace std;

struct FIPEntry {
    int codAtom;
    int pozitieTS;
};

class FIP {
public:
    void addEntry(int codAtom, int pozitieTS);

    void saveFIP(std::ofstream& outFile);

    void printFIP() {
        for (const auto& entry : fipEntries) {
            cout << std::setw(9) << entry.codAtom << " | " << std::setw(13) << (entry.pozitieTS != -1 ? std::to_string(entry.pozitieTS) : "-") << '\n';
        }
    }

private:
    std::vector<FIPEntry> fipEntries;
};


#endif //LAB1_ANALIZORLEXICAL_FIP_H
