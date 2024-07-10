#include "teste.h"

void testRepoAdauga() {
	Repo repository;
	Film film{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film);
	assert(repository.getAll().size() == 1);

	try {
		repository.adaugaFilm(film);
		assert(false);
	}
	catch (const ExceptieRepo&) {
		assert(true);
	}

	try {
		repository.cautaFilm("La La Land");
		assert(false);
	}
	catch (const ExceptieRepo& eroare) {
		std::stringstream os;
		os << eroare;
		assert(os.str().find("adaugat") >= 0);
	}
}

void testRepoSterge() {
	Repo repository;
	Film film1{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film1);
	Film film2{ "The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch" };
	repository.adaugaFilm(film2);
	Film film3{ "Titanic", "Epic Romance", 1997, "Leonardo DiCaprio" };
	repository.adaugaFilm(film3);
	Film film4{ "Avatar 2", "Science-Fiction", 2022, "Sam Worthington" };
	repository.adaugaFilm(film4);
	Film film5{ "The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio" };
	repository.adaugaFilm(film5);
	assert(repository.getAll().size() == 5);
	repository.stergeFilm(2);
	assert(repository.getAll().size() == 4);
	repository.stergeFilm(3);
}

void testRepoModifica() {
	Repo repository;
	Film film1{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film1);
	Film film2{ "The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch" };
	repository.adaugaFilm(film2);
	Film film3{ "Titanic", "Epic Romance", 1997, "Leonardo DiCaprio" };
	repository.adaugaFilm(film3);
	assert(repository.getAll().size() == 3);
	Film filmModificat{ "Titanic", "Romance", 2010, "Leonardo DiCaprio" };
	repository.modificaFilm(filmModificat, 2);
	assert(repository.getAll().size() == 3);
	assert(filmModificat.getGenFilm() == "Romance");
	Film filmModificat1{ "Titanicum", "Romance", 2010, "Leonardo DiCaprio" };
	try {
		repository.modificaFilm(filmModificat1, 2);
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void testRepoCauta() {
	Repo repository;
	Film film{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film);
	auto filmCautat = repository.cautaFilm("John Wick: Chapter 4");
	assert(filmCautat.getTitluFilm() == film.getTitluFilm());
	assert(filmCautat.getGenFilm() == film.getGenFilm());
	assert(filmCautat.getAnAparitie() == film.getAnAparitie());
	assert(filmCautat.getActorPrincipal() == film.getActorPrincipal());

	try {
		repository.cautaFilm("La La Land");
		assert(false);
	}
	catch (ExceptieRepo&){
		assert(true);
	}
}

void testRepoCautaPozitieDupaTitlu() {
	Repo repository;
	Film film1{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film1);
	Film film2{ "The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch" };
	repository.adaugaFilm(film2);
	Film film3{ "Titanic", "Epic Romance", 1997, "Leonardo DiCaprio" };
	repository.adaugaFilm(film3);
	const int pozitieGasita = repository.cautaPozitieDupaTitlu("The Imitation Game");
	assert(pozitieGasita == 1);
	try {
		repository.cautaPozitieDupaTitlu("La La Land");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void ruleazaTesteValidator() {
	Validator validator;
	Film filmValid{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	validator.valideaza(filmValid);
	Film filmInvalid{"", "", -1, ""};
	try {
		validator.valideaza(filmInvalid);
		assert(false);
	}
	catch (const ExceptieValidare& exceptie) {
		std::stringstream sout;
		sout << exceptie;
		assert(sout.str().find("invalid") >= 0);
	}
}

void testServiceAdauga() {
	Repo repository;
	Validator validator;
	Service service{ repository, validator };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	assert(service.getAll().size() == 1);
	
	try {
		service.adaugaFilm("", "", -1, "");
		assert(false);
	}
	catch (ExceptieValidare&) {
		assert(true);
	}

	try {
		service.adaugaFilm("John Wick: Chapter 4", "Aventura", -1, "Benedict Cumberbatch");
		assert(false);
	}
	catch (ExceptieValidare&) {
		assert(true);
	}
}

void testServiceSterge() {
	Repo repository;
	Validator validator;
	Service service{ repository, validator };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Epic Romance", 1997, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Science-Fiction", 2022, "Sam Worthington");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	assert(service.getAll().size() == 5);
	service.stergeFilm("Titanic");
	assert(service.getAll().size() == 4);

	try {
		service.stergeFilm("La La Land");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void testServiceModifica() {
	Repo repository;
	Validator validator;
	Service service{ repository, validator };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	assert(service.getAll().size() == 3);
	service.modificaFilm("The Imitation Game", "Drama", 2007, "Arnold Scwarzenegger");
	assert(service.getAll().size() == 3);
}

void testServiceCauta() {
	Repo repository;
	Validator validator;
	Service service{ repository, validator };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	assert(service.getAll().size() == 3);
	Film film = service.cautaFilm("The Imitation Game");
	assert(film.getTitluFilm() == "The Imitation Game");
	assert(film.getGenFilm() == "Historic Drama");
	assert(film.getAnAparitie() == 2014);
	assert(film.getActorPrincipal() == "Benedict Cumberbatch");

	try {
		service.cautaFilm("Avatar");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void testServiceFiltreazaDupaGenDupaAnAparitie() {
	Repo repository;
	Validator validator;
	Service service{ repository, validator };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2020, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Drama", 2021, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Romance", 2020, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Actiune", 2020, "Sam Worthington");
	service.adaugaFilm("The Revenant", "Drama", 2022, "Leonardo DiCaprio");
	assert(service.getAll().size() == 5);

	vector<Film> filmeFiltrateDupaGen = service.filtreazaDupaGen("Drama");
	assert(filmeFiltrateDupaGen.size() == 2);
	assert(filmeFiltrateDupaGen[0].getTitluFilm() == "The Imitation Game");
	assert(filmeFiltrateDupaGen[1].getTitluFilm() == "The Revenant");

	vector<Film> filmeFiltrateDupaAnAparitie = service.filtreazaDupaAnAparitie(2020);
	assert(filmeFiltrateDupaAnAparitie.size() == 3);
	assert(filmeFiltrateDupaAnAparitie[0].getTitluFilm() == "John Wick: Chapter 4");
	assert(filmeFiltrateDupaAnAparitie[1].getTitluFilm() == "Titanic");
	assert(filmeFiltrateDupaAnAparitie[2].getTitluFilm() == "Avatar 2");
}

void testServiceSortari() {
	Repo repository;
	Validator validator;
	Service service{ repository, validator };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2020, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Drama", 2021, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Romance", 2020, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Actiune", 2020, "Sam Worthington");
	service.adaugaFilm("The Revenant", "Drama", 2022, "Leonardo DiCaprio");
	assert(service.getAll().size() == 5);

	vector<Film> filmeFiltrateDupaTitluCrescator = service.sorteaza(1, 1);
	assert(filmeFiltrateDupaTitluCrescator[0].getTitluFilm() == "Avatar 2");
	assert(filmeFiltrateDupaTitluCrescator[1].getTitluFilm() == "John Wick: Chapter 4");
	assert(filmeFiltrateDupaTitluCrescator[2].getTitluFilm() == "The Imitation Game");
	assert(filmeFiltrateDupaTitluCrescator[3].getTitluFilm() == "The Revenant");
	assert(filmeFiltrateDupaTitluCrescator[4].getTitluFilm() == "Titanic");

	vector<Film> filmeFiltrateDupaTitluDescrescator = service.sorteaza(1, 2);
	assert(filmeFiltrateDupaTitluDescrescator[4].getTitluFilm() == "Avatar 2");
	assert(filmeFiltrateDupaTitluDescrescator[3].getTitluFilm() == "John Wick: Chapter 4");
	assert(filmeFiltrateDupaTitluDescrescator[2].getTitluFilm() == "The Imitation Game");
	assert(filmeFiltrateDupaTitluDescrescator[1].getTitluFilm() == "The Revenant");
	assert(filmeFiltrateDupaTitluDescrescator[0].getTitluFilm() == "Titanic");

	vector<Film> filmeFiltrateDupaActorCrescator = service.sorteaza(2, 1);
	assert(filmeFiltrateDupaActorCrescator[0].getActorPrincipal() == "Benedict Cumberbatch");
	assert(filmeFiltrateDupaActorCrescator[1].getActorPrincipal() == "Keanu Reeves");
	assert(filmeFiltrateDupaActorCrescator[2].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorCrescator[3].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorCrescator[4].getActorPrincipal() == "Sam Worthington");

	vector<Film> filmeFiltrateDupaActorDescrescator = service.sorteaza(2, 2);
	assert(filmeFiltrateDupaActorDescrescator[4].getActorPrincipal() == "Benedict Cumberbatch");
	assert(filmeFiltrateDupaActorDescrescator[3].getActorPrincipal() == "Keanu Reeves");
	assert(filmeFiltrateDupaActorDescrescator[2].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorDescrescator[1].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorDescrescator[0].getActorPrincipal() == "Sam Worthington");

	vector<Film> filmeFiltrateDupaAnSiGenCrescator = service.sorteaza(3, 1);
	assert(filmeFiltrateDupaAnSiGenCrescator[0].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenCrescator[1].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenCrescator[2].getGenFilm() == "Romance");
	assert(filmeFiltrateDupaAnSiGenCrescator[3].getGenFilm() == "Drama");
	assert(filmeFiltrateDupaAnSiGenCrescator[4].getGenFilm() == "Drama");

	vector<Film> filmeFiltrateDupaAnSiGenDescrescator = service.sorteaza(3, 2);
	assert(filmeFiltrateDupaAnSiGenDescrescator[4].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenDescrescator[3].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenDescrescator[2].getGenFilm() == "Romance");
	assert(filmeFiltrateDupaAnSiGenDescrescator[1].getGenFilm() == "Drama");
	assert(filmeFiltrateDupaAnSiGenDescrescator[0].getGenFilm() == "Drama");

	assert(filmeFiltrateDupaAnSiGenCrescator[0].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenCrescator[1].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenCrescator[2].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenCrescator[3].getAnAparitie() == 2021);
	assert(filmeFiltrateDupaAnSiGenCrescator[4].getAnAparitie() == 2022);

	assert(filmeFiltrateDupaAnSiGenDescrescator[4].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenDescrescator[3].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenDescrescator[2].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenDescrescator[1].getAnAparitie() == 2021);
	assert(filmeFiltrateDupaAnSiGenDescrescator[0].getAnAparitie() == 2022);
}

void ruleazaTesteRepo() {
	testRepoAdauga();
	testRepoSterge();
	testRepoModifica();
	testRepoCauta();
	testRepoCautaPozitieDupaTitlu();
}

void ruleazaTesteService() {
	testServiceAdauga();
	testServiceSterge();
	testServiceModifica();
	testServiceCauta();
	testServiceFiltreazaDupaGenDupaAnAparitie();
	testServiceSortari();
}

void ruleazaToateTestele() {
	ruleazaTesteRepo();
	ruleazaTesteValidator();
	ruleazaTesteService();
}