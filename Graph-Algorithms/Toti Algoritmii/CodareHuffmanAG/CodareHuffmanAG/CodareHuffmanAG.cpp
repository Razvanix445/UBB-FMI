#include <iostream>
#include <fstream>
#include <cstring>
#include <string>
#include <queue>
#include <map>

using namespace std;

struct Node {
    char character;
    int frequency;
    Node* left;
    Node* right;

    Node(char ch, int freq) {
        character = ch;
        frequency = freq;
        left = nullptr;
        right = nullptr;
    }

    Node(Node* l, Node* r) {
        character = '\0';
        frequency = l->frequency + r->frequency;
        left = l;
        right = r;
    }
};

struct Compare {
    bool operator()(Node* l, Node* r) {
        if (l->frequency == r->frequency)
            return l->character > r->character;
        return l->frequency > r->frequency;
    }
};

void generateCodes(Node* root, string code, map<char, string>& codes) {
    if (root == nullptr)
        return;

    if (root->character != '\0') {
        codes[root->character] = code;
    }

    generateCodes(root->left, code + "0", codes);
    generateCodes(root->right, code + "1", codes);
}

map<char, int> huffmanEncoding(const string& text, map<char, string>& codes) {
    map<char, int> frequencies;

    for (char ch : text) {
        frequencies[ch]++;
    }

    priority_queue<Node*, vector<Node*>, Compare> pq;

    for (const auto& pair : frequencies) {
        Node* newNode = new Node(pair.first, pair.second);
        pq.push(newNode);
    }

    while (pq.size() > 1) {
        Node* left = pq.top();
        pq.pop();
        Node* right = pq.top();
        pq.pop();

        Node* newNode = new Node(left, right);
        pq.push(newNode);
    }

    if (pq.empty()) {
        return frequencies;
    }

    Node* root = pq.top();

    generateCodes(root, "", codes);

    delete root;

    return frequencies;
}

int main() {
    ifstream inputFile("input.txt");
    ofstream outputFile("output.txt");

    string text;
    getline(inputFile, text);

    map<char, string> codes;
    map<char, int> frequencies = huffmanEncoding(text, codes);

    outputFile << codes.size() << "\n";
    for (const auto& pair : codes) {
        outputFile << pair.first << " " << frequencies[pair.first] << "\n";
    }

    for (char ch : text) {
        outputFile << codes[ch];
    }

    inputFile.close();
    outputFile.close();

    return 0;
}
