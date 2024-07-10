#include <iostream>
#include <vector>
#include <climits>

using namespace std;

const int INF = INT_MAX;
const int MAXN = 100005;

vector<pair<int, int>> graph[MAXN];
int dist[MAXN];
bool visited[MAXN];

int minDistance(int n) {
    int minDist = INF, minNode = -1;
    for (int i = 1; i <= n; i++) {
        if (!visited[i] && dist[i] < minDist) {
            minDist = dist[i];
            minNode = i;
        }
    }
    return minNode;
}

void dijkstra(int start, int n) {
    for (int i = 1; i <= n; i++) {
        dist[i] = INF;
        visited[i] = false;
    }
    dist[start] = 0;

    for (int i = 1; i <= n; i++) {
        int u = minDistance(n);
        visited[u] = true;

        for (auto neighbor : graph[u]) {
            int v = neighbor.first;
            int w = neighbor.second;
            if (!visited[v] && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
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
    cout << "Introduceti numarul de noduri si numarul de muchii: ";
    cin >> n >> m;

    for (int i = 1; i <= m; i++) {
        int u, v, w;
        cout << "Introduceti capetele si ponderea muchiei " << i << ": ";
        cin >> u >> v >> w;
        graph[u].push_back({ v, w });
        graph[v].push_back({ u, w }); // Daca graful este neorientat, adaugam muchiile in ambele directii
    }

    int start;
    cout << "Introduceti nodul de start: ";
    cin >> start;

    dijkstra(start, n);

    return 0;
}
/*
9 14
0 1 4
1 2 8
2 3 7
3 4 9
4 5 10
5 3 14
5 2 4
2 8 2
8 6 6
6 5 2
6 7 1
0 7 8
1 7 11
7 8 7

*/