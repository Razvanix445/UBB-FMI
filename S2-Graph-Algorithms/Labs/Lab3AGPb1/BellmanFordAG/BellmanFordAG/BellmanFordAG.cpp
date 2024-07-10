#include <iostream>
#include <vector>
#include <climits>

using namespace std;

const int INF = INT_MAX;
const int MAXN = 100005;

vector<pair<int, int>> graph[MAXN];
int dist[MAXN];

void bellmanFord(int start, int n) {
    for (int i = 1; i <= n; i++) {
        dist[i] = INF;
    }
    dist[start] = 0;

    for (int i = 1; i < n; i++) {
        for (int j = 1; j <= n; j++) {
            for (auto neighbor : graph[j]) {
                int v = neighbor.first;
                int w = neighbor.second;
                if (dist[j] != INF && dist[j] + w < dist[v]) {
                    dist[v] = dist[j] + w;
                }
            }
        }
    }

    // Verificam daca exista cicluri negative
    for (int j = 1; j <= n; j++) {
        for (auto neighbor : graph[j]) {
            int v = neighbor.first;
            int w = neighbor.second;
            if (dist[j] != INF && dist[j] + w < dist[v]) {
                cout << "Graful contine cicluri negative!" << endl;
                return;
            }
        }
    }

    // Afisam distantele minime
    for (int i = 1; i <= n; i++) {
        cout << "Distanta minima de la nodul " << start << " la nodul " << i << " este: ";
        if (dist[i] == INF) {
            cout << "infinit" << endl;
        }
        else {
            cout << dist[i] << endl;
        }
    }
}

int main() {
    int n, m;
    cin >> n >> m;

    for (int i = 1; i <= m; i++) {
        int u, v, w;
        cin >> u >> v >> w;
        graph[u].push_back({ v, w });
    }

    bellmanFord(1, n);

    return 0;
}

/*
Input:
5 8
1 2 -1
1 3 4
2 3 3
4 3 5
4 2 1
2 4 2
2 5 2
5 4 -3

Output:
Distanta minima de la nodul 1 la nodul 1 este: 0
Distanta minima de la nodul 1 la nodul 2 este: -1
Distanta minima de la nodul 1 la nodul 3 este: 2
Distanta minima de la nodul 1 la nodul 4 este: -2
Distanta minima de la nodul 1 la nodul 5 este: 1
*/