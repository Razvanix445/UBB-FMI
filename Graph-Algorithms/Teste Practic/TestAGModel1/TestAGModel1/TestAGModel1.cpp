/*
Problema nota 10 – timp de lucru 40 de minute
In fata blocului se joaca „n” copii, intre care exista relatii de prietenie, de forma „x y”, 
mai exact copilul identificat prin numarul de ordine „x” este prieten cu cel identificat prin 
numarul de ordine „y”. Relatia de prietenie este simetrica, adica daca „x” este prieten cu „y” si „y” 
este prieten cu „x”. Mai mult, relatia de prietenie este si tranzitiva, concret, daca „x” este prieten 
cu „y” si „y” cu „z”, atunci si „x” va fi prieten cu „z”. Sa se determine asa numitele „grupuri de 
prieteni” care se formeaza intre copii. Afisati aceasta solutie. Formatul fisierului de intrare este:
n m #prima linie contine numarul de copii si numarul de relatii intre acestia
x1 y1
x2 y2
...
xn yn # exista o relatie de prietenie intre „x” si „y”
Exemplu „input.txt”:
10 7
1 2
2 3
3 4
4 5
6 10
7 8
7
*/

#include <iostream>
#include <vector>
using namespace std;

const int MAXN = 100005;
vector<int> adj[MAXN];
bool visited[MAXN];

void dfs(int v, vector<int>& friends) {
    visited[v] = true;
    friends.push_back(v);
    for (int i = 0; i < adj[v].size(); i++) {
        int u = adj[v][i];
        if (!visited[u]) {
            dfs(u, friends);
        }
    }
}

int main() {
    int n, m;
    cin >> n >> m;
    for (int i = 0; i < m; i++) {
        int x, y;
        cin >> x >> y;
        adj[x].push_back(y);
        adj[y].push_back(x);
    }

    vector<vector<int>> groups;
    for (int i = 1; i <= n; i++) {
        if (!visited[i]) {
            vector<int> friends;
            dfs(i, friends);
            groups.push_back(friends);
        }
    }

    for (int i = 0; i < groups.size(); i++) {
        cout << "Group " << i + 1 << ": ";
        for (int j = 0; j < groups[i].size(); j++) {
            cout << groups[i][j] << " ";
        }
        cout << endl;
    }

    return 0;
}

/*
Group 1: 1 2 3 4 5
Group 2: 6 10
Group 3: 7 8 9
*/