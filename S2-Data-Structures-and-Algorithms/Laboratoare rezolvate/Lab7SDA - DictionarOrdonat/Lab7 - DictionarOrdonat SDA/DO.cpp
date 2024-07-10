#include "Iterator.h"
#include "DO.h"
#include <iostream>

#include <exception>

using namespace std;

DO::DO(Relatie r) {
    /* de adaugat */
    this->R = r;
    this->capacitate = 30000;
    this->prim = -1;
    this->radacina = -1;
    this->elemente = new Nod[capacitate];
    this->dimensiune = 0;
    for (int i = 0; i < this->capacitate - 1; i++)
        elemente[i].st = i + 1;
    elemente[capacitate - 1].st = -1;
    this->primLiber = 0;
}

int DO::creeazaNod(TCheie c, TValoare val) {
    /** se creaza un nou nod care va fi inserat pe prima pozitie libera gasita
     * daca nu sunt pozitii libere(vectorul e plin) se va redimensiona
     * subalgoritm creeazaNod(Tcheie c,TValoare val)
     *  daca primLiber=-1 atunci
     *      redimensionare()
     *  sf daca
     *  i<-aloc()
     *  daca i!=-1 atunci
     *  elem[i].first<-c
     *  elem[i].second<-val
     *  urm[i]=-1
     *  pre[i]=-1
     *  sf daca
     *  creeazaNod<-i
     * sf subalgoritm
     * @complexity   θ(1)
     * */
    if (primLiber == -1)
        redimensioneaza();
    int i = aloca(); // aloc un nod unde se gaseste spatiu liber;
    if (i != -1) {
        this->elemente[i].element.first = c;
        this->elemente[i].element.second = val;
        this->elemente[i].st = -1;
        this->elemente[i].dr = -1;
        //urm[i] = -1;// nu stii cine cu cine se leaga cand creezi un nou nou
    }
    return i; // return pozitia pe care a fost creat nodul
}

int DO::aloca() {
    int i = this->primLiber;
    this->primLiber = elemente[primLiber].st;// actualizam prim liber
    return i;
}

// adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
TValoare DO::adauga(TCheie c, TValoare v) {
    /* de adaugat */
    int i = radacina, parent = -1;
    while (i != -1) {
        if (elemente[i].element.first == c) {
            int old_value = elemente[i].element.second;
            elemente[i].element.second = v;
            return old_value;
        }
        if (R(c, elemente[i].element.first))
            i = elemente[i].st;
        else
            i = elemente[i].dr;
    }
    i = radacina;
    parent = -1;
    while (i != -1) {
        parent = i;
        if (R(c, elemente[i].element.first))
            i = elemente[i].st;
        else
            i = elemente[i].dr;
    }
    int p = creeazaNod(c, v);
    if (parent == -1)// nu avem nimic in tree, punem radacina
    {
        radacina = p;
    }
    else
        //inseamna ca trebuie sa l introducem pe pozitia ... (care va fi ori st,ori dr)
    {
        if (R(c, elemente[parent].element.first))// il introducem in stanga
        {
            elemente[parent].st = p;//nodul nou creat;
        }
        else
            elemente[parent].dr = p;

    }
    dimensiune++;
    return NULL_TVALOARE;
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const {
    /* de adaugat */
    int i = radacina;
    while (i != -1) {
        if (elemente[i].element.first == c)
            return elemente[i].element.second;
        if (R(c, elemente[i].element.first))
            i = elemente[i].st;
        else
            i = elemente[i].dr;
    }
    return NULL_TVALOARE;
}

int DO::sterge_recursiv(int node, TCheie e, int& val, bool& removed) {

    if (node == -1) {
        return node;
    }
    if (e == elemente[node].element.first) {
        if (!removed) {
            val = elemente[node].element.second, removed = true;
        }
        if (elemente[node].st == -1) {
            int fiu = elemente[node].dr;
            elemente[node] = Nod{};
            return fiu;
        }
        else if (elemente[node].dr == -1) {
            int fiu = elemente[node].st;
            elemente[node] = Nod{};
            return fiu;
        }
        else {
            int maxN = GetMax(elemente[node].st);
            elemente[node].element = elemente[maxN].element;
            elemente[node].st = sterge_recursiv(elemente[node].st, elemente[maxN].element.first, val, removed);
        }
    }
    else if (R(e, elemente[node].element.first)) {
        this->elemente[node].st = sterge_recursiv(elemente[node].st, e, val, removed);
    }
    else {
        this->elemente[node].dr = sterge_recursiv(elemente[node].dr, e, val, removed);
    }
    return node;
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {
    /* de adaugat */
    TValoare val = 0;
    bool sters = false;
    radacina = sterge_recursiv(radacina, c, val, sters);
    if (sters)
    {
        dimensiune--;
        return val;
    }
    return NULL_TVALOARE;
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
    /* de adaugat */
    return dimensiune;
}

//verifica daca dictionarul e vid
bool DO::vid() const {
    /* de adaugat */
    if (dimensiune == 0)
        return true;
    return false;
}

Iterator DO::iterator() const {
    return Iterator(*this);
}

DO::~DO() {
    /* de adaugat */
    delete[]elemente;
}


int DO::dealoca(int i) {
    elemente[i].st = primLiber; //redirectionam unde avem liber
    primLiber = i;// si marcam noul ca liber
    return 0;
}

void DO::redimensioneaza() {
    /**
     * Redimensioneaza vectorii
     * subalgoritm redimensionarez()
     *  [new_elems]:TElem[cap*2]
     *
     *  pentru i<-0,cap*2-1 executa
     *  new_urm<-urm[i]
     *  new_pre<-pre[i]
     *  new_elems<-elems[i]
     *  sf pentru
     *
     *  delete elems
     *  elems <-new_elems
     *
     *  primLiber<-cap
     *  pentru i<-primLiber,cap*2-1 executa // initializam si spatiul liber aici
     * urm[i]<-i+1
     * sf pentru
     * cap<-cap*2
     * urm[cap-1]<- -1
     * sf subalgoritm
     *
     * @complexity  n= capacity
     *              θ(n)
     *              n+2*n =3*n =>  θ(n)
     *
    Node *new_elems = new Node[this->cap * 2]; //// loc nou pentru elems
    int *pre_new = new int[this->cap * 2];
    int *urm_new = new int[this->cap * 2];

    for (int i = 0; i < this->cap; i++) {
        new_elems[i] = this->elems[i];
        urm_new[i] = this->urm[i];
    }
    delete[] elems;
    delete[] urm;

    this->elems = new_elems;
    this->urm = urm_new;

    primLiber = this->cap; // reinitalizam prim liber
    this->cap = this->cap * 2;
    for (int i = primLiber; i < this->cap -
                                1; i++) ///reinitializere spatiu liber ///old_cap-1 pentru a-l include si pe cel pe care avem -1
        urm[i] = i + 1;
    urm[cap - 1] = -1;
*/
    Nod* new_elems = new Nod[capacitate * 2];
    for (int i = 0; i < capacitate - 1; i++)
        new_elems[i] = elemente[i];
    delete[]elemente;
    elemente = new_elems;
    for (int i = this->capacitate; i < this->capacitate * 2 - 1; i++)
        elemente[i].st = i + 1;

    primLiber = this->capacitate;
    this->capacitate = this->capacitate * 2;
    elemente[capacitate - 1].st = -1;
}

int DO::GetMin(int subtree_root) {
    int nod = subtree_root;
    int min_nod = subtree_root;
    while (nod != -1) {
        min_nod = nod;
        nod = elemente[nod].st;
    }
    return min_nod;

}

int DO::GetMax(int subtree_root) {
    int nod = subtree_root;
    int max_nod = subtree_root;
    while (nod != -1) {
        max_nod = nod;
        nod = elemente[nod].dr;
    }
    return max_nod;
}

int DO::GetNode(int subtree_root) {
    /// sa verific care relatie de ordine este aici
    int nod = radacina;
    if (R(1, 2))
        return GetMin(subtree_root);
    else
        return GetMax(subtree_root);
}

std::vector<TCheie> DO::mulțimeaCheilor() const {
    /**
     *  returnează un vector cu toate cheile dicționarului
     *  subalg multimeaCheilor()
     *  it:Iterator
     *  vcet:vctor<TChei>
     *  cat timp it.valid() executa
     *  cheie:Tcheie
     *  cheie<-it.elem().first
     *  vect.push_back(cheie)
     *  it.urmator();
     *  sf cattimp
     *  mulțimeaCheilor<-vect
     *  sfsubalg
     *  @complexity:theta h cred...
     * */
    Iterator it = this->iterator();
    vector<TCheie > vect;
    while (it.valid())
    {
        TCheie cheie = it.element().first;
        vect.push_back(cheie);
        it.urmator();
    }
    return vect;

}


Nod::Nod(TElem e, int st, int dr) {
    e.first = -1;
    e.second = -1;
    st = -1;
    dr = -1;
}

TElem Nod::element() {
    return element;
}

int Nod::stanga() {
    return st;
}

int Nod::dreapta() {
    return dr;
}
