/*
2. Sa se determine �nchiderea transitiv� a unui graf orientat. (�nchiderea tranzitiv� poate fi reprezentat� ca matricea care 
descrie, pentru fiecare v�rf �n parte, care sunt v�rfurile accesibile din acest v�rf. Matricea inchiderii tranzitive arat� 
unde se poate ajunge din fiecare v�rf.) ex. figura inchidere_tranzitiva.png - pentru graful de sus se construieste matricea 
de jos care arata inchiderea tranzitiva.
*/

#include <iostream>

using namespace std;

int numarulDeNoduri, numarulDeMuchii, matriceTranzitiva[101][101];

int main()
{
    cout << "Introduceti numarul de noduri: ";
    cin >> numarulDeNoduri;
    cout << "Introduceti numarul de muchii: ";
    cin >> numarulDeMuchii;
    cout << "Introduceti muchiile: \n";

    for (int i = 1; i <= numarulDeMuchii; i++) {
        int x, y;
        cin >> x >> y;
        matriceTranzitiva[x][y] = 1;
    }

    for (int k = 1; k <= numarulDeNoduri; k++)
        for (int i = 1; i <= numarulDeNoduri; i++)
            for (int j = 1; j <= numarulDeNoduri; j++)
                if (matriceTranzitiva[i][j] == 0)
                    matriceTranzitiva[i][j] = (matriceTranzitiva[i][k] && matriceTranzitiva[k][j]);

    for (int i = 1; i <= numarulDeNoduri; i++) {
        for (int j = 1; j <= numarulDeNoduri; j++)
            cout << matriceTranzitiva[i][j] << " ";
        cout << endl;
    }
    return 0;
}
