#pragma once

typedef int TCheie;
typedef int TValoare;

#define NULL_TVALOARE -1

#include <utility>
#include <vector>

typedef std::pair<TCheie, TValoare> TElem;
class Iterator;
typedef bool(*Relatie)(TCheie, TCheie);


class Nod {
private:

    TElem element;
    int st, dr;

public:

    friend class DO;

    Nod(TElem e, int  st, int dr);
    Nod() {};
    TElem element();
    int stanga();
    int dreapta();
};

class DO {

    friend class Iterator;

private:
    int capacitate, dimensiune;
    Nod* elemente;
    int radacina;
    int primLiber, prim;

    int aloca();
    int dealoca(int i);

    Relatie R;

public:

    //constructorul implicit al dictionarului
    DO(Relatie r);

    //adauga o pereche (cheie, valoare) in dictionar
    //daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
    //daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
    TValoare adauga(TCheie c, TValoare v);

    //cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
    TValoare cauta(TCheie c) const;


    //sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
    TValoare sterge(TCheie c);
    int sterge_recursiv(int node, TCheie e, TValoare& val, bool& sters);
    int GetMax(int subtree_root);
    int GetMin(int subtree_root);
    int GetNode(int subtree_root);
    //returneaza numarul de perechi (cheie, valoare) din dictionar
    int dim() const;

    //verifica daca dictionarul e vid
    bool vid() const;

    //se returneaza iterator pe dictionar
    //iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
    Iterator iterator() const;

    std::vector<TCheie> mulțimeaCheilor() const;

    void redimensioneaza();

    int creeazaNod(TCheie cheie, TValoare valoare);

    //destructorul dictionarului
    ~DO();
};