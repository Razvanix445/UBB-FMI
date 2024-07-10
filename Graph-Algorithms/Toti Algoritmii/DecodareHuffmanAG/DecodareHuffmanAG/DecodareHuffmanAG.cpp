#include <iostream>
#include <fstream>
#include <map>

using namespace std;

struct Node {
    char character;
    Node* left;
    Node* right;

    Node(char ch) {
        character = ch;
        left = nullptr;
        right = nullptr;
    }
};

void buildHuffmanTree(const map<char, string>& codes, Node*& root) {
    for (const auto& pair : codes) {
        const string& code = pair.second;
        Node* currentNode = root;

        for (char bit : code) {
            if (bit == '0') {
                if (currentNode->left == nullptr) {
                    currentNode->left = new Node('\0');
                }
                currentNode = currentNode->left;
            }
            else if (bit == '1') {
                if (currentNode->right == nullptr) {
                    currentNode->right = new Node('\0');
                }
                currentNode = currentNode->right;
            }
        }

        currentNode->character = pair.first;
    }
}

void decodeHuffman(const string& encodedText, const Node* root, string& decodedText) {
    const Node* currentNode = root;

    for (char bit : encodedText) {
        if (bit == '0') {
            currentNode = currentNode->left;
        }
        else if (bit == '1') {
            currentNode = currentNode->right;
        }

        if (currentNode == nullptr) {
            // Nodul curent este NULL, deci nu am obținut încă un caracter decodat complet
            continue;
        }

        if (currentNode->character != '\0') {
            decodedText += currentNode->character;
            currentNode = root;
        }
    }
}

void deleteHuffmanTree(Node* root) {
    if (root == nullptr)
        return;

    deleteHuffmanTree(root->left);
    deleteHuffmanTree(root->right);

    delete root;
}

int main() {
    ifstream inputFile("input.txt");
    ofstream outputFile("output.txt");

    int N;
    inputFile >> N;

    map<char, int> frequencies;
    for (int i = 0; i < N; i++) {
        char ch;
        int freq;
        inputFile >> ch >> freq;
        frequencies[ch] = freq;
    }

    string encodedText;
    inputFile >> encodedText;

    map<char, string> codes;
    for (const auto& pair : frequencies) {
        char ch = pair.first;
        codes[ch] = "";
    }

    Node* root = new Node('\0');
    buildHuffmanTree(codes, root);

    string code;
    inputFile >> code;

    for (const auto& pair : frequencies) {
        char ch = pair.first;
        codes[ch] = code.substr(0, pair.second);
        code = code.substr(pair.second);
    }

    string decodedText;
    decodeHuffman(encodedText, root, decodedText);

    outputFile << decodedText;

    deleteHuffmanTree(root);

    inputFile.close();
    outputFile.close();

    return 0;
}
