#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <functional>

using namespace std;

class Smoothy {

private:

	int pret;

public:

	Smoothy(int pret) : pret{ pret } {}

	virtual int getPret() {
		return pret;
	}

	virtual string descriere() = 0;

	virtual ~Smoothy() {

	}
};

class DecoratorSmoothy : public Smoothy {

private:

	Smoothy* smoothy;

public:

	DecoratorSmoothy(Smoothy* smoothy) : smoothy{ smoothy }, Smoothy(smoothy->getPret()) {}

	int getPret() override {
		return smoothy->getPret();
	}

	string descriere() override {
		return smoothy->descriere();
	}

	~DecoratorSmoothy() override {
		delete smoothy;
	}
};

class BasicSmoothy: public Smoothy {

private:

	string nume;

public:

	BasicSmoothy(string nume, int pret) : nume{ nume }, Smoothy( pret ) {}

	int getPret() override {
		return Smoothy::getPret();
	}

	string descriere() override {
		return nume;
	}

	~BasicSmoothy() override {

	}
};

class SmoothyCuFrisca : public DecoratorSmoothy {

public:

	SmoothyCuFrisca(Smoothy* smoothy) : DecoratorSmoothy{ smoothy } {}

	string descriere() override {
		return DecoratorSmoothy::descriere() + " " + "cu frisca";
	}

	int getPret() override {
		return DecoratorSmoothy::getPret() + 2;
	}

	~SmoothyCuFrisca() override {

	}
};

class SmoothyCuUmbreluta : public DecoratorSmoothy {

public:

	SmoothyCuUmbreluta(Smoothy* smoothy) : DecoratorSmoothy{ smoothy } {}

	string descriere() override {
		return DecoratorSmoothy::descriere() + " " + "cu umbreluta";
	}

	int getPret() override {
		return DecoratorSmoothy::getPret() + 3;
	}

	~SmoothyCuUmbreluta() override {

	}
};

vector<Smoothy*> getSmoothies() {
	vector<Smoothy*> smoothies;
	Smoothy* skfu = new SmoothyCuUmbreluta{ new SmoothyCuFrisca{new BasicSmoothy{"kivi", 10}} };
	Smoothy* scf = new SmoothyCuFrisca{ new BasicSmoothy{"capsune", 10} };
	Smoothy* sk = new BasicSmoothy{"kivi", 10};
	smoothies.push_back(skfu);
	smoothies.push_back(scf);
	smoothies.push_back(sk);
	return smoothies;
}

int main() {
	vector<Smoothy*> smoothies = getSmoothies();
	sort(smoothies.begin(), smoothies.end(), [](const auto& smoothie1, const auto& smoothie2) {
		return smoothie1->descriere() < smoothie2->descriere();
		});

	for (auto smoothie : smoothies) {
		cout << smoothie->getPret() << endl;
		cout << smoothie->descriere() << endl;
		delete smoothie;
	}

	_CrtDumpMemoryLeaks();
}