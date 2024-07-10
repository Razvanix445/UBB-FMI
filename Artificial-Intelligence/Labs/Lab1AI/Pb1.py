# 1. Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea într-un text care
# conține mai multe cuvinte separate prin ” ” (spațiu). De ex. ultimul (dpdv alfabetic) cuvânt din
# ”Ana are mere rosii si galbene” este cuvântul "si".

def pb1():
    string = "Ana are mere rosii si galbene"

    # Divizam textul intr-o lista de cuvinte
    parti = string.split(" ")

    # Parcurgem fiecare cuvant si verificam daca cuvantul prezent este mai mare (dpdv alfabetic) decat cuvantul
    # deja luat ca fiind cel mai mare retinut in variabila "ultimulCuvant"
    ultimulCuvant = ""
    for word in parti:
        if (ultimulCuvant < word):
            ultimulCuvant = word
    print(ultimulCuvant)

def pb1CuBot():
    text = "Ana are mere rosii si galbene"
    cuvinte = text.split()
    ultimul = sorted(cuvinte)[-1]
    print(ultimul)