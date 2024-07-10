/*
Să se implementeze în C++ un anumit container de date (TAD) folosind o anumită reprezentare (indicată) și o tabelă de dispersie (TD)
ca structură de date, cu o anumită metodă (indicată) pentru rezolvarea coliziunilor:
−Liste independente
−Liste întrepătrunse
−Adresare deschisă

19. TAD MultiDicționar – memorarea tuturor perechilor de forma (cheie, valoare), reprezentare sub forma unei TD cu rezolvare
coliziuni prin liste întrepătrunse.
*/

#include <iostream>
#include "TestExtins.h"
#include "TestScurt.h"

using namespace std;

int main() {

	testAll();
	testAllExtins();

	cout << "End";

}
