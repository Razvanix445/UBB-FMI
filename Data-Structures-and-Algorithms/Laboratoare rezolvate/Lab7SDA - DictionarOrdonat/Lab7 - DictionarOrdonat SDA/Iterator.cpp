#include "Iterator.h"
#include "DO.h"

using namespace std;

/**
 * parcurgem in inordine*/
Iterator::Iterator(const DO& d) : dict(d) {
    /* de adaugat */
    this->stack = std::stack<int>{};
    prim();
}

void Iterator::prim() {
    /* de adaugat */
    curent = dict.radacina;
    if (!this->stack.empty()) {
        while (!this->stack.empty()) {
            this->stack.pop();
        }
    }
    while (curent != -1)
    {
        stack.push(curent);
        curent = dict.elemente[curent].stanga();
    }
    if (!stack.empty())
    {
        curent = stack.top();
    }
    else
        curent = -1;


}

void Iterator::urmator() {
    /* de adaugat */
    int i = -1;
    curent = dict.elemente[dict.radacina].stanga();
    int node = this->stack.top();
    this->stack.pop();
    if (this->dict.elemente[node].dreapta() != -1) {
        node = this->dict.elemente[node].dreapta();
        while (node != -1) {
            this->stack.push(node);
            node = this->dict.elemente[node].stanga();
        }
    }
    if (!stack.empty()) {
        this->curent = this->stack.top();
    }
    else
        curent = -1;
}

bool Iterator::valid() const {
    /* de adaugat */
    if (curent == -1)return false;
    return true;
}

TElem Iterator::element() const {
    /* de adaugat */
    return dict.elemente[curent].element();
}


