#pragma once

class Observator {
public:
	virtual void updateaza() = 0;
};

#include <vector>
#include <algorithm>

using std::vector;

class Observabil {
private:

	vector<Observator*> observatoare;

public:

	void adaugaObservator(Observator* observator) {
		observatoare.push_back(observator);
	}

	void stergeObservator(Observator* observator) {
		observatoare.erase(remove(observatoare.begin(), observatoare.end(), observator), observatoare.end());
	}

	void notifica() {
		for (auto observator : observatoare) {
			observator->updateaza();
		}
	}
};