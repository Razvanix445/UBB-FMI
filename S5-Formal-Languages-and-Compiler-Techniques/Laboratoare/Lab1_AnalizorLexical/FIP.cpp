//
// Created by razva on 10/24/2024.
//

#include "FIP.h"

void FIP::addEntry(int codAtom, int pozitieTS) {
    fipEntries.push_back({codAtom, pozitieTS});
}

void FIP::saveFIP(std::ofstream& outFile) {
    outFile << "Cod Atom  | Pozitie in TS\n";
    outFile << "--------------------------\n";

    for (const auto& entry : fipEntries) {
        outFile << std::setw(9) << entry.codAtom << " | " << std::setw(13) << (entry.pozitieTS != -1 ? std::to_string(entry.pozitieTS) : "-") << '\n';
    }
}