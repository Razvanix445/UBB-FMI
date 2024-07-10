#pragma once
#include "repo.h"
#include <vector>
#include <map>
#include <string>
#include <ostream>
#include <functional>
#include <fstream>
#include <iostream>

using namespace std;
using std::vector;
using std::map;
using std::string;
using std::ostream;

class RepositoryFake : public Repo {
private:
	int caz;
public:
	RepositoryFake(int caz) : Repo(), caz{ caz } {}
	const vector<Film>& getAll() const noexcept override;
};