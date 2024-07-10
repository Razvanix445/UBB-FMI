//1
/*
using namespace std;
#include <vector>
#include <algorithm>
#include <stack>
#include <iostream>
vector<string> g(vector<string> l) {
	if (l.size() == 0)
		throw exception("Illegal argument");
	std::stack<string> st;
	for (auto& s : l)
		st.push(s);
	vector<string> r;
	while (!st.empty()) {
		r.push_back(st.top());
		st.pop();
	}
	return r;
}

int main() {
	vector<string> l;
	l.push_back("Ionica");
	l.push_back("123");
	vector<string> r = g(l);
	for (auto o : r) {
		cout << o << endl;
	}
	return 0;
}
*/

//2a - varianta corectata - afisare: printBprintC
/*
#include <vector>
#include <iostream>
class A {
public:
	virtual void print() = 0;
};
class B : public A {
public:
	virtual void print() {
		std::cout << "printB";
	}
};
class C : public B {
public:
	virtual void print() {
		std::cout << "printC";
	}
};
int main() {
	std::vector<A*> v;
	B b;
	C c;
	v.push_back(&b);
	v.push_back(&c);
	for (auto e : v) {
		e->print();
	}
	return 0;
}
*/

//2b - afisare: 1314
/*
#include <iostream>
void f(bool b) {
	std::cout << "1";
	if (b) {
		throw std::exception("Error");
	}
	std::cout << "3";
}
int main() {
	try {
		f(false);
		f(true);
		f(false);
	}
	catch (std::exception& ex) {
		std::cout << "4";
	}
	return 0;
}
*/

//3
/*
#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Mancare {

private:

	int pret;

public:

	Mancare(int pret) : pret{ pret } {}

	virtual string descriere() = 0;

	virtual int getPret() {
		return pret;
	}

	virtual ~Mancare() {

	}
};

class CuCartof: public Mancare {

private:

	Mancare* mancare;

public:

	CuCartof(Mancare* mancare) : mancare{mancare}, Mancare { mancare->getPret() } {}

	string descriere() override {
		return mancare->descriere() + " " + "cu cartof";
	}

	int getPret() override {
		return mancare->getPret() + 3;
	}

	~CuCartof() override {
		delete mancare;
	}
};

class CuSos : public Mancare {

private:

	Mancare* mancare;

public:

	CuSos(Mancare* mancare) : mancare{ mancare }, Mancare{ mancare->getPret() } {}

	string descriere() override {
		return mancare->descriere() + " " + "cu sos";
	}

	int getPret() override {
		return mancare->getPret() + 2;
	}

	~CuSos() override {
		delete mancare;
	}
};

class Burger: public Mancare {

private:

	string nume;

public:

	Burger(string nume, int pret) : nume{ nume }, Mancare{ pret } {}

	int getPret() override {
		return Mancare::getPret();
	}

	string descriere() override {
		return nume;
	}

	~Burger() override {

	}
};

vector<Mancare*> getMancare() {
	vector<Mancare*> mancare;
	Mancare* b = new Burger{ "BigMac", 10 };
	Mancare* bcs = new CuSos{ new CuCartof{new Burger{ "BigMac", 10 }} };
	Mancare* bc = new CuCartof{ new Burger{ "Zinger", 10 } };
	Mancare* bs = new CuSos{ new Burger{ "Zinger", 10 } };
	mancare.push_back(b);
	mancare.push_back(bcs);
	mancare.push_back(bc);
	mancare.push_back(bs);
	return mancare;
}

int main() {
	vector<Mancare*> mancare = getMancare();
	sort(mancare.begin(), mancare.end(), [](const auto& mancare1, const auto& mancare2) {
		return mancare1->getPret() > mancare2->getPret();
		});

	for (auto o : mancare) {
		cout << o->descriere() << endl;
		cout << o->getPret() << endl;
		delete o;
	}
	return 0;
}
*/

//4
#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>

using namespace std;

template<typename T>
class Carnet {

private:

	unordered_map<string, T> materii;

public:

	void add(const string& materie, const T& nota) {
		materii[materie] = nota;
	}

	void removeLast() {
		if (!materii.empty()) {
			auto last = std::prev(materii.end());
			materii.erase(last);
		}
	}

	T operator[](const string& materie) {
		if (materii.find(materie) != materii.end())
			return materii[materie];
		throw exception();
	}
};

void anscolar() {
	Carnet<int> cat;
	cat.add("SDA", 9);//adauga nota pentru o materie
	cat.add("OOP", 7), cat.add("FP", 10);
	cout << cat["OOP"];//tipareste nota de la materia data (7 la OOP)
	cat.removeLast(), cat.removeLast();//sterge nuota de la FP si OOP
	try {
		//se arunca exceptie daca nu exista nota pentru materia ceruta
		cout << cat["OOP"];
	}
	catch (std::exception& ex) {
		cout << "Nu exista nota pentru OOP";
	}
}

int main() {
	anscolar();
	return 0;
}