#include <iostream>
#include <vector>
#include <queue>
#include <limits>

using namespace std;

// Structura pentru reprezentarea arcului
struct Edge {
    int source;
    int destination;
    int capacity;
    int flow;
};

// Funcția pentru determinarea fluxului maxim folosind algoritmul Edmonds-Karp
int maxFlow(vector<vector<int>>& graph, vector<Edge>& edges, int source, int destination) {
    int numVertices = graph.size();
    vector<int> parent(numVertices);
    int maxFlow = 0;

    while (true) {
        // Inițializare
        fill(parent.begin(), parent.end(), -1);
        parent[source] = -2;
        vector<int> minCapacity(numVertices, numeric_limits<int>::max());
        minCapacity[source] = 0;

        // BFS pentru găsirea drumului de creștere
        queue<int> q;
        q.push(source);

        while (!q.empty()) {
            int u = q.front();
            q.pop();

            for (int v : graph[u]) {
                Edge& edge = edges[v];
                if (parent[edge.destination] == -1 && edge.flow < edge.capacity) {
                    parent[edge.destination] = v;
                    minCapacity[edge.destination] = min(minCapacity[edge.source], edge.capacity - edge.flow);
                    if (edge.destination != destination)
                        q.push(edge.destination);
                    else {
                        // S-a găsit un drum de creștere până la destinație
                        maxFlow += minCapacity[destination];

                        // Actualizarea capacităților și fluxurilor arcelor pe drumul de creștere
                        int curr = destination;
                        while (curr != source) {
                            int prev = parent[curr];
                            edges[prev].flow += minCapacity[destination];
                            edges[prev ^ 1].flow -= minCapacity[destination];
                            curr = edges[prev].source;
                        }
                        break;
                    }
                }
            }
        }

        if (parent[destination] == -1)
            break;
    }

    return maxFlow;
}

int main() {
    int numVertices, numEdges;
    cin >> numVertices >> numEdges;

    // Inițializarea grafului și listei de arce
    vector<vector<int>> graph(numVertices);
    vector<Edge> edges;

    for (int i = 0; i < numEdges; ++i) {
        int source, destination, capacity;
        cin >> source >> destination >> capacity;

        // Adăugarea arcului în graf și lista de arce
        graph[source].push_back(edges.size());
        edges.push_back({ source, destination, capacity, 0 });

        // Adăugarea și arcul invers (cu flux 0)
        graph[destination].push_back(edges.size());
        edges.push_back({ destination, source, 0, 0 });
    }

    int source = 0;
    int destination = numVertices - 1;

    // Calcularea fluxului maxim folosind algoritmul Edmonds-Karp
    int maxFlowValue = maxFlow(graph, edges, source, destination);

    cout << maxFlowValue << endl;

    return 0;
}
