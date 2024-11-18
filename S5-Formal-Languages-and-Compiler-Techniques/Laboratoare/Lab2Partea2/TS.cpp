//
// Created by razva on 10/24/2024.
//

#include <string>
#include "TS.h"

TS::TS() {

}

void TS::insert(const std::string& atom) {
    int index = 0;

    if (arbore.empty()) {
        arbore.push_back({nextPozitie++, atom});
        return;
    }

    while (index < arbore.size()) {
        if (arbore[index].atomLexical.empty()) {
            arbore[index] = {nextPozitie++, atom};
            return;
        } else if (arbore[index].atomLexical == atom) {
            return; // Atom already exists
        } else if (atom < arbore[index].atomLexical) {
            int leftChildIndex = 2 * index + 1;
            if (arbore[index].leftLink == -1) {
                arbore[index].leftLink = nextPozitie;
            }
            if (leftChildIndex >= arbore.size()) arbore.resize(leftChildIndex + 1);
            index = leftChildIndex;
        } else {
            int rightChildIndex = 2 * index + 2;
            if (arbore[index].rightLink == -1) {
                arbore[index].rightLink = nextPozitie;
            }
            if (rightChildIndex >= arbore.size()) arbore.resize(rightChildIndex + 1);
            index = rightChildIndex;
        }
    }
}

int TS::search(const std::string& atom) {
    int index = 0;
    while (index < arbore.size() && !arbore[index].atomLexical.empty()) {
        if (arbore[index].atomLexical == atom) {
            return arbore[index].pozitie;
        } else if (atom < arbore[index].atomLexical) {
            index = 2 * index + 1;
        } else {
            index = 2 * index + 2;
        }
    }
    return -1;
}

void TS::saveTS(std::ofstream& outFile) {
    vector<AtomEntry> sortedArbore;
    for (const auto& entry : arbore) {
        if (!entry.atomLexical.empty()) {
            sortedArbore.push_back(entry);
        }
    }

    std::sort(sortedArbore.begin(), sortedArbore.end(), [](const AtomEntry& a, const AtomEntry& b) {
        return a.pozitie < b.pozitie;
    });

    outFile << "| Pozitie | AtomLexical | Left Link | Right Link |\n";
    outFile << "| ---------------------------------------------- |\n";

    for (const auto& entry : sortedArbore) {
        outFile << "| " << std::setw(7) << entry.pozitie << " | "
                << std::setw(11) << entry.atomLexical << " | "
                << std::setw(9) << (entry.leftLink != -1 ? to_string(entry.leftLink) : "-") << " | "
                << std::setw(10) << (entry.rightLink != -1 ? to_string(entry.rightLink) : "-") << " |\n";
    }
}