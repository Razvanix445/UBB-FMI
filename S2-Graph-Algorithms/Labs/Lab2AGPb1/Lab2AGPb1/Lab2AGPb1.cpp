/*
1. Implementați algoritmul lui Moore pentru un graf orientat neponderat (algoritm bazat pe Breath-first search, 
vezi cursul 2). Datele sunt citite din fisierul graf.txt. Primul rând din graf.txt conține numărul vârfurilor, 
iar următoarele rânduri conțin muchiile grafului. Programul trebuie să afiseze lanțul cel mai scurt dintr-un 
vârf (vârful sursa poate fi citit de la tastatura).
*/

#include <fstream>
#include <iostream>
#include <vector>
#include <climits>
#include <queue>
using namespace std;
ifstream f("graf.txt");
vector <int> listaVecini[105];
queue <int> Coada;
int lungimeLant[105], parinte[105], rezultat[105];
int main()
{
	int n, x, y, p, v;
	f >> n;
	while (f >> x >> y)
		listaVecini[x].push_back(y);
	cout << "Introduceti nodul initial: ";
	cin >> p;
	cout << "Introduceti nodul final: ";
	cin >> v;

	for (int i = 1; i <= n; i++)
		if (i != p) lungimeLant[i] = LONG_MAX;

	Coada.push(p);
	while (!Coada.empty())
	{
		x = Coada.front();
		Coada.pop();
		for (unsigned int i = 0; i < listaVecini[x].size(); i++)
			if (lungimeLant[listaVecini[x][i]] == LONG_MAX)
			{
				parinte[listaVecini[x][i]] = x;
				lungimeLant[listaVecini[x][i]] = lungimeLant[x] + 1;
				Coada.push(listaVecini[x][i]);
			}
	}
	int k = lungimeLant[v], k2 = lungimeLant[v];
	rezultat[k] = v;
	while (k)
	{
		rezultat[k - 1] = parinte[rezultat[k]];
		k--;
	}

	cout << '\n' << "Drumul minim: ";
	for (int i = 1; i <= k2; i++)
		cout << rezultat[i] << " ";
	return 0;
}