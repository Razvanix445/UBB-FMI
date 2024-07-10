#pragma once
#define CAPACITATE_INITIALA 5
template <typename Element>
class IteratorVector;

template <typename Element>
class VectorDinamic {
private:

	int dimensiune;
	int capacitate;
	Element* elemente;

	void asiguraCapacitate();

public:

	/*
	Constructor default
	Alocam spatiu pentru 5 elemente
	*/
	VectorDinamic();

	/*
	Constructor de copiere
	rule 1 of 3
	*/
	VectorDinamic(const VectorDinamic& other);

	/*
	Eliberam memoria
	rule 2 of 3
	*/
	~VectorDinamic();

	/*
	Operator de atribuire (elibereaza continutul obiectului curent, aloca spatiu pentru elemente, copiaza elementele din other in this)
	rule 3 of 3
	*/
	VectorDinamic& operator=(const VectorDinamic& other);

	/*
	Constructor de mutare
	*/
	VectorDinamic(VectorDinamic&& other);

	/*
	Atribuire prin mutare (folosit la atribuire)
	*/
	VectorDinamic& operator=(VectorDinamic&& other);


	void adauga(const Element& element);

	Element& get(int pozitieElement) const;

	void set(int pozitieElement, const Element& element);

	int size() const noexcept;

	void push_back(const Element& element);

	void pop_back();

	Element& operator[](int index);

	friend class IteratorVector<Element>;

	//functii care creeaza iteratori
	IteratorVector<Element> begin() const;
	IteratorVector<Element> end() const;

};

/*
	Constructor default
	Alocam spatiu pentru 5 elemente
*/
template<typename Element>
VectorDinamic<Element>::VectorDinamic() :elemente{ new Element[CAPACITATE_INITIALA] }, capacitate{ CAPACITATE_INITIALA }, dimensiune{ 0 } {}

/*
	Constructor de copiere
*/
template<typename Element>
VectorDinamic<Element>::VectorDinamic(const VectorDinamic<Element>& other) {
	elemente = new Element[other.capacitate];
	//copiere elemente
	for (int i = 0; i < other.dimensiune; i++) {
		elemente[i] = other.elemente[i];
	}
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;
}

/*
	Eliberam memoria
*/
template<typename Element>
VectorDinamic<Element>::~VectorDinamic() {
	delete[] elemente;
}

/*
	Operator de atribuire (elibereaza continutul obiectului curent, aloca spatiu pentru elemente, copiaza elementele din other in this)
	rule 3 of 3
*/
template<typename Element>
VectorDinamic<Element>& VectorDinamic<Element>::operator=(const VectorDinamic<Element>& other) {
	if (this == &other) {
		return *this;
	}
	delete[] elemente;
	elemente = new Element[other.capacitate];
	//copiere elemente
	for (int i = 0; i < other.dimensiune; i++) {
		elemente[i] = other.elemente[i];
	}
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;
	return *this;
}

/*
	Constructor de mutare
*/
template<typename Element>
VectorDinamic<Element>::VectorDinamic(VectorDinamic&& other) {
	elemente = other.elemente;
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;

	other.elemente = nullptr;
	other.dimensiune = 0;
	other.capacitate = 0;
}

/*
	Atribuire prin mutare (folosit la atribuire)
*/
template<typename Element>
VectorDinamic<Element>& VectorDinamic<Element>::operator=(VectorDinamic<Element>&& other) {
	if (this == &other) {
		return *this;
	}
	delete[] elemente;

	elemente = other.elemente;
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;

	other.elemente = nullptr;
	other.dimensiune = 0;
	other.capacitate = 0;
	return *this;
}

template<typename Element>
void VectorDinamic<Element>::adauga(const Element& element) {
	asiguraCapacitate();
	elemente[dimensiune++] = element;
}

template<typename Element>
Element& VectorDinamic<Element>::get(int pozitieElement) const {
	return elemente[pozitieElement];
}

template<typename Element>
void VectorDinamic<Element>::set(int pozitieElement, const Element& element) {
	elemente[pozitieElement] = element;
}

template<typename Element>
int VectorDinamic<Element>::size() const noexcept {
	return dimensiune;
}

template<typename Element>
void VectorDinamic<Element>::asiguraCapacitate() {
	if (dimensiune < capacitate) {
		return;
	}
	capacitate *= 2;
	Element* elementeAuxiliar = new Element[capacitate];
	for (int i = 0; i < dimensiune; i++) {
		elementeAuxiliar[i] = elemente[i];
	}
	delete[] elemente;
	elemente = elementeAuxiliar;
}

template<typename Element>
void VectorDinamic<Element>::push_back(const Element& element) {
	if (dimensiune == capacitate) {
		asiguraCapacitate();
	}
	elemente[dimensiune] = element;
	dimensiune++;
}

template<typename Element>
void VectorDinamic<Element>::pop_back() {
	dimensiune--;
}

template<typename Element>
Element& VectorDinamic<Element>::operator[](int index) {
	return elemente[index];
}

template<typename Element>
IteratorVector<Element> VectorDinamic<Element>::begin() const {
	return IteratorVector<Element>(*this);
}

template<typename Element>
IteratorVector<Element> VectorDinamic<Element>::end() const {
	return IteratorVector<Element>(*this, dimensiune);
}

template<typename Element>
class IteratorVector {
private:

	const VectorDinamic<Element>& vector;
	int pozitieElement = 0;

public:

	IteratorVector(const VectorDinamic<Element>& vector) noexcept;
	IteratorVector(const VectorDinamic<Element>& vector, int pozitieElement) noexcept;
	bool valid() const noexcept;
	Element& element() const noexcept;
	void next() noexcept;
	Element& operator*();
	IteratorVector& operator++();
	bool operator==(const IteratorVector& other) noexcept;
	bool operator!=(const IteratorVector& other) noexcept;
};

template<typename Element>
IteratorVector<Element>::IteratorVector(const VectorDinamic<Element>& vector) noexcept :vector{ vector } {}

template<typename Element>
IteratorVector<Element>::IteratorVector(const VectorDinamic<Element>& vector, int pozitieElement) noexcept :vector{ vector }, pozitieElement{ pozitieElement } {}

template<typename Element>
bool IteratorVector<Element>::valid() const noexcept {
	return pozitieElement < vector.dimensiune;
}

template<typename Element>
Element& IteratorVector<Element>::element() const noexcept {
	return vector.elemente[pozitieElement];
}

template<typename Element>
void IteratorVector<Element>::next() noexcept {
	pozitieElement++;
}

template<typename Element>
Element& IteratorVector<Element>::operator*() {
	return element();
}

template<typename Element>
IteratorVector<Element>& IteratorVector<Element>::operator++() {
	next();
	return *this;
}

template<typename Element>
bool IteratorVector<Element>::operator==(const IteratorVector<Element>& other) noexcept {
	return pozitieElement == other.pozitieElement;
}

template<typename Element>
bool IteratorVector<Element>::operator!=(const IteratorVector<Element>& other) noexcept {
	return !(*this == other);
}
