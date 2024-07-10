#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

struct Edge {
    int u, v, w;
    bool operator<(const Edge& e) const {
        return w < e.w;
    }
};

const int N = 100005;
int parent[N], rang[N];

int find(int u) {
    if (parent[u] != u) {
        parent[u] = find(parent[u]);
    }
    return parent[u];
}

void merge(int u, int v) {
    u = find(u);
    v = find(v);
    if (rang[u] < rang[v]) {
        parent[u] = v;
    }
    else if (rang[u] > rang[v]) {
        parent[v] = u;
    }
    else {
        parent[u] = v;
        rang[v]++;
    }
}

int main() {
    int n, m;
    cin >> n >> m;
    vector<Edge> edges(m);
    for (int i = 0; i < m; i++) {
        cin >> edges[i].u >> edges[i].v >> edges[i].w;
    }
    sort(edges.begin(), edges.end());
    for (int i = 0; i < n; i++) {
        parent[i] = i;
        rang[i] = 0;
    }
    vector<Edge> tree;
    int cost = 0;
    for (int i = 0; i < m; i++) {
        int u = edges[i].u, v = edges[i].v, w = edges[i].w;
        if (find(u) != find(v)) {
            merge(u, v);
            tree.push_back(edges[i]);
            cost += w;
        }
    }
    cout << cost << endl;
    cout << tree.size() << endl;
    for (int i = 0; i < tree.size(); i++) {
        cout << tree[i].u << " " << tree[i].v << endl;
    }
    return 0;
}
