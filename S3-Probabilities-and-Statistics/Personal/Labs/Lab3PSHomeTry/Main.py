import itertools
import random
from random import choices, sample, randrange
import math
from math import comb, perm
import matplotlib.pyplot
from matplotlib import pyplot as plt
from matplotlib.pyplot import bar, hist, grid, show, legend
import scipy.stats
from scipy.stats import binom

def ex1(bileUrna):
    for simulationNumber in N:
        A = 0
        B = 0
        for _ in range(simulationNumber):
            ballsAfterExtraction = sample(bileUrna, 3)
            if ballsAfterExtraction.__contains__('r'):
                A += 1
                if (ballsAfterExtraction[0] == ballsAfterExtraction[1] and ballsAfterExtraction[1] == ballsAfterExtraction[2]):
                    B += 1
        print(f"{simulationNumber} Simulations: ")
        print("Probability Through Simulations: \n", B / A)
        ATheoreticalProbability = 1 - (comb(5, 3) * comb(5, 0) / comb(10, 3))
        BTheoreticalProbability = comb(5, 3) * comb(3, 3) * comb(2, 0) / comb(10, 3)
        print("Theoretical Probability: \n", BTheoreticalProbability / ATheoreticalProbability)

def ex2():
    # Generate a list of random integers for data
    data = [randrange(1, 7) for _ in range(500)]
    # Shifts the bin edges by 0.5
    bin_edges = [k + 0.5 for k in range(7)]
    # alpha = transparency of the bars
    hist(data, bins=bin_edges, density=True, rwidth=0.9, color='green', edgecolor='black', alpha=0.5, label='frecvente relative')
    # Creates a dictionary representing the uniform probability distribution
    distribution = dict([(i, 1 / 6) for i in range(1, 7)])
    # Plots the bar chart for the probability distribution
    bar(distribution.keys(), distribution.values(), width=0.85, color='red', edgecolor='black', alpha=0.6, label='probabilitati')
    # Adds a legend to the plot
    legend(loc='lower left')
    grid()
    show()

def ex3(bileUrna):
    listValues = []
    for _ in range(1, 1000):
        extractedBalls = random.choices(bileUrna, k=5)
        listValues.append(sum(extractedBalls))
    print(listValues)

    # pmf calculates the probability of getting i successes in 5 trials with a success probability of 0.6
    theoreticalValues = [binom.pmf(i, 5, 0.6) for i in range(0, 6)] # theoretical probability mass function
    plt.hist(listValues, bins=range(0, 7), rwidth=0.45, density=True, alpha=0.5, color='blue', label='Empirical')
    plt.bar(range(0, 6), theoreticalValues, width=0.45, color='red', alpha=0.6, label='Theoretical')
    plt.title('Histogram of X')
    plt.xlabel('Sum of digits on balls')
    plt.ylabel('Relative Frequency')
    plt.legend()
    plt.show()

    valuesThatRespectCriteria = 0
    for i in listValues:
        if 2 < i <= 5:
            valuesThatRespectCriteria += 1
    n = 5
    p = 0.6
    theoreticalProbability = 0
    x = binom.rvs(n, p, size=1000)
    for i in range(3, 6):
        p2 = binom.pmf(i, n, p)
        theoreticalProbability += p2
    print("Probability Through Simulations: \n", valuesThatRespectCriteria / 1000)
    print("Theoretical Probability: \n", theoreticalProbability)

def ex4():
    # empirical
    dieNumbers = [1, 2, 3, 4, 5, 6]
    throws = [sum(random.choices(dieNumbers, k=3)) for _ in range(100000)]
    throwDictionaryEmpirical = {key: 0 for key in range(3, 19)}
    for i in throws:
        throwDictionaryEmpirical[i] += 1
    print("Maximum Value Through Simulations: \n", max(throwDictionaryEmpirical, key=lambda key: throwDictionaryEmpirical[key]))
    for throw in throwDictionaryEmpirical:
        throwDictionaryEmpirical[throw] /= 100000

    # theoretical
    throwDictionaryTheoretical = {key: 0 for key in range(3, 19)}
    possibilities = list(itertools.product(range(1, 7), repeat=3))
    for possibility in possibilities:
        total = sum(possibility)
        throwDictionaryTheoretical[total] += 1
    noOfPossibilities = len(possibilities)
    for key in throwDictionaryTheoretical:
        throwDictionaryTheoretical[key] /= noOfPossibilities
    print("Maximum Value Theoretically: \n", max(throwDictionaryTheoretical, key=lambda key: throwDictionaryTheoretical[key]))

    # histogram
    # Creates a igure with two subplots arranged in 2 rows and 1 column (fig - the entire figure, ax1/ax2 - the two subplot axes)
    fig, (ax1, ax2) = plt.subplots(2, 1)

    ax1.hist(throws, bins=range(3, 20), width=0.8, density=True, alpha=0.6, color='blue', label='Empirical')
    ax1.set_title("Empirical Histogram of Sum of 3 Dice Rolls")
    ax1.set_xlabel("Sum of Dice Rolls")
    ax1.set_ylabel("Relative Frequency")
    ax1.legend()

    ax2.bar(throwDictionaryTheoretical.keys(), throwDictionaryTheoretical.values(), width=0.8, color='red', alpha=0.6,
            label='Theoretical')
    ax2.set_title("Theoretical Probability Distribution of Sum of 3 Dice Rolls")
    ax2.set_xlabel("Sum of Dice Rolls")
    ax2.set_ylabel("Probability")
    ax2.legend()

    # Adjusts the layout of the subplots to prevent overlapping
    plt.tight_layout()
    plt.show()

if __name__ == '__main__':
    # ex1
    N = [5000]
    bileUrna1 = ['r', 'r', 'r', 'r', 'r', 'a', 'a', 'a', 'v', 'v']
    ex1(bileUrna1)

    # ex2
    #ex2()

    # ex3
    bileUrna2 = [1, 1, 1, 1, 1, 1, 0, 0, 0, 0]
    #ex3(bileUrna2)

    # ex4
    print("Ex4: ")
    ex4()