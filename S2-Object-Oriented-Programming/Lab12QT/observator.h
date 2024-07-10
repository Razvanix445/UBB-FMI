#pragma once
#include <vector>
#include <algorithm>

class Observator {
public:
	virtual void updateaza() = 0;
};

using std::vector;

class Observabil {
private:

	vector<Observator*> observatori;

protected:

	void notifica() {
		for (auto observator : observatori) {
			if (observator)
				observator->updateaza();
		}
	}

public:

	void adaugaObservator(Observator* observator) {
		observatori.push_back(observator);
	}

	void stergeObservator(Observator* observator) {
		observatori.erase(remove(observatori.begin(), observatori.end(), observator), observatori.end());
	}
};