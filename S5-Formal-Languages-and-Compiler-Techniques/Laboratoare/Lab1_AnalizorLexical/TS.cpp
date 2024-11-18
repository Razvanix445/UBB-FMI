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

//
//void TS::insert(const std::string& atom) {
//    root = insertRec(root, atom);
//}
//
//int TS::search(const std::string& atom) {
//    Node* foundNode = searchRec(root, atom);
//    if (foundNode)
//        return foundNode->pozitie;
//    return -1;
//}
//
//void TS::saveTS(std::ofstream& outFile) {
//    saveInOrder(outFile, root);
//}
//
//
//
//Node* TS::insertRec(Node* node, const std::string& atom) {
//    if (node == nullptr)
//        return new Node(++pozitieCurenta, atom);
//
//    if (atom < node->atomLexical)
//        node->left = insertRec(node->left, atom);
//    else if (atom > node->atomLexical)
//        node->right = insertRec(node->right, atom);
//
//    return node;
//}
//
//Node* TS::searchRec(Node* node, const std::string& atom) {
//    if (node == nullptr || node->atomLexical == atom)
//        return node;
//
//    if (atom < node->atomLexical)
//        return searchRec(node->left, atom);
//
//    return searchRec(node->right, atom);
//}
//
//void TS::saveInOrder(std::ofstream& outFile, Node* node) {
//    if (node == nullptr)
//        return;
//
//    saveInOrder(outFile, node->left);
//
//    static bool headersWritten = false;
//    if (!headersWritten) {
//        outFile << "| Pozitie | AtomLexical | Left Link | Right Link |\n";
//        outFile << "| ---------------------------------------------- |\n";
//        headersWritten = true;
//    }
//
//    outFile << "| "
//            << std::setw(7) << node->pozitie << " | "
//            << std::setw(11) << node->atomLexical << " | "
//            << std::setw(9) << ((node->left) ? node->left->pozitie : -1) << " | "
//            << std::setw(10) << ((node->right) ? node->right->pozitie : -1) << " |\n";
//
//    saveInOrder(outFile, node->right);
//}