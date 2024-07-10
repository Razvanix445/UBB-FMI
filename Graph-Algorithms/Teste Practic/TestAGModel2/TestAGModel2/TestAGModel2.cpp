/*
Problema nota 10 – timp de lucru 40 de minute
Se da un graf neorientat neponderat. Sa se determine daca acest graf 
este aciclic. Hint: determinati daca, dupa o parcurgere in adancime raman 
muchii din graf prin care nu ati trecut. Raspunsul va fi de forma „DA” 
(graful este aciclic), respectiv „NU” (graful are cel putin un ciclu). 
Date de intrare:
n m # prima linie contine numarul de noduri si numarul de muchii
x1 y1
x2 y2
...
xn yn # exista o muchie intre „x” si „y”
Exemplu „input.txt”:
7 7
1 2
2 3
3 4
4 1
1 5
5 6
5 7
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAXN = 100005;

vector<int> graph[MAXN];
bool visited[MAXN];

bool hasCycle(int node, int parent) {
    visited[node] = true;
    for (int neighbor : graph[node]) {
        if (!visited[neighbor]) {
            if (hasCycle(neighbor, node)) {
                return true;
            }
        }
        else if (neighbor != parent) {
            // Daca vecinul este vizitat si nu este parintele nodului curent,
            // atunci exista o muchie care formeaza un ciclu in graf.
            return true;
        }
    }
    return false;
}

int main() {
    int n, m;
    cin >> n >> m;

    // Citim muchiile si construim graful.
    for (int i = 1; i <= m; i++) {
        int x, y;
        cin >> x >> y;
        graph[x].push_back(y);
        graph[y].push_back(x);
    }

    // Initializam vectorul de vizitare cu false.
    for (int i = 1; i <= n; i++) {
        visited[i] = false;
    }

    // Parcurgem graful in adancime si verificam daca exista cicluri.
    bool has_cycle = false;
    for (int i = 1; i <= n; i++) {
        if (!visited[i]) {
            if (hasCycle(i, -1)) {
                has_cycle = true;
                break;
            }
        }
    }

    // Afisam rezultatul.
    if (has_cycle) {
        cout << "NU\n";
    }
    else {
        cout << "DA\n";
    }

    return 0;
}
