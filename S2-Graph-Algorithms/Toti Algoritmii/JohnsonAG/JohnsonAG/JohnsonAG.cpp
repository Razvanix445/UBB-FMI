#include <queue>
#include <cstdio>
#include <algorithm>
#include <vector>
#include <iostream>
#include <climits>
#define NMAX 1001

using namespace std;

vector < pair<int, int> > graf[NMAX];
int parinte[105];

int numar_muchie;
int numar_noduri;


void read()
{
	freopen("graf.in", "r", stdin);
	scanf("%d\n", &numar_muchie);


	int x, y, pondere;

	for (int i = 0; i < numar_muchie; i++)
	{
		scanf("%d %d %d", &x, &y, &pondere);
		graf[x].push_back({ y,pondere });
		if (numar_noduri < x || numar_noduri < y)
			numar_noduri = max(x, y);
	}

	for (int i = 1; i <= numar_noduri; i++)
	{
		graf[0].push_back({ i,0 });
	}


}


void bellmanford(int distante[NMAX], int start)
{
	for (int i = 1; i < numar_noduri; i++)
		distante[i] = INT_MAX;

	queue <int> coada;
	int vizitat[NMAX] = { 0 };
	coada.push(start);

	while (!coada.empty())
	{
		int nod = coada.front();
		coada.pop();
		for (int i = 0; i < graf[nod].size(); i++)
		{

			int vecin = graf[nod][i].first;
			int costvecin = graf[nod][i].second;

			if (distante[vecin] > distante[nod] + costvecin)
			{
				vizitat[vecin]++;
				distante[vecin] = distante[nod] + costvecin;
				coada.push(vecin);
				if (vizitat[vecin] >= numar_noduri)
					return;
			}
		}
	}
}

void dijkstra(int cost[NMAX], int start)
{
	priority_queue <pair<int, int>, vector<pair<int, int> >, greater<pair<int, int> > > coada;
	coada.push({ 0,start });
	bool vizitat[NMAX];
	for (int i = 1; i <= numar_noduri; i++)
		cost[i] = INT_MAX;
	cost[start] = 0;

	while (!coada.empty())
	{
		int nod = coada.top().second;
		int costul = coada.top().first;
		coada.pop();
		if (vizitat[nod] == true)
			continue;
		vizitat[nod] = true;
		int limita = graf[nod].size();
		for (int i = 0; i < limita; i++)
		{
			int vecin = graf[nod][i].first;
			int costmuchie = graf[nod][i].second;
			if (cost[nod] + costmuchie < cost[vecin])
			{
				cost[vecin] = costmuchie + cost[nod];
				parinte[vecin] = nod;
			}
			coada.push({ costmuchie + cost[nod],vecin });
		}
	}
	int p = parinte[numar_noduri];
	cout << numar_noduri << " ";
	while (p != 0)
	{
		cout << p << " ";
		p = parinte[p];
	}
}


void Recalculateweight(int drumuri[NMAX])
{
	for (int i = 1; i <= numar_noduri; i++)
	{
		for (int j = 0; j < graf[i].size(); j++)
		{
			int nod = graf[i][j].first;
			int cost = graf[i][j].second;
			graf[i][j] = { nod,cost + drumuri[i] - drumuri[nod] };
		}
	}
}


int main()
{
	int drumuri[NMAX] = { 0 };
	read();
	bellmanford(drumuri, 0);
	Recalculateweight(drumuri);
	dijkstra(drumuri, 1);
	return 0;
}
