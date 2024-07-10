import itertools
import random
from random import sample
from math import factorial, perm, comb
from itertools import permutations, combinations

def ex2():
    my_list = 'word'
    word_permutations = list(itertools.permutations(my_list))
    print("2a) The permutations are: \n", word_permutations)

    totalPermutationsNumber = len(word_permutations)
    print("2b) The total permutations: \n", totalPermutationsNumber)

    randomPermutation = random.choice(word_permutations)
    print("2c) One random permutation: \n", randomPermutation)

def aranjamente(listOfCharacters, number, numar_total=False, aleator=False):
    list_permutations = list(itertools.permutations(listOfCharacters, number))
    if numar_total == True:
        return len(list_permutations)
    if aleator == True:
        return random.choice(list_permutations)
    return list_permutations

def combinari(listOfCharacters, number, numar_total=False, aleator=False):
    list_combinations = list(itertools.combinations(listOfCharacters, number))
    if numar_total == True:
        return len(list_combinations)
    if aleator == True:
        return random.choice(list_combinations)
    return list_combinations

def ex3():
    print(aranjamente('word', 2))
    print(aranjamente('word', 2, numar_total=True))
    print(aranjamente('word', 2, aleator=True))

    print(combinari('word', 2))
    print(combinari('word', 2, numar_total=True))
    print(combinari('word', 2, aleator=True))

if __name__ == '__main__':
    #ex2
    print("\nEx2)")
    ex2()

    #ex3
    print("\nEx3)")
    ex3()