import random
from random import choice
from scipy.stats import bernoulli, hypergeom, geom
from matplotlib.pyplot import hist, legend, grid, show

def ex1(noOfSteps):
    results = []
    for _ in range(10):

        steps = [0]
        for _ in range(noOfSteps):
            zeroOrOne = bernoulli.rvs(0.5)
            step = 2 * zeroOrOne - 1
            steps.append(step + steps[-1])
        print(steps)

        results.append(steps[-1])

    # histogram
    bin_edges = [k for k in range(-noOfSteps, noOfSteps)]
    hist(results, bins=bin_edges, density=True, width=0.45, color='blue', alpha=0.9, label='Simulations')
    legend()
    grid()
    show()

    nodes = 50
    circleResults = [x % nodes for x in results]
    bin_edges = [k for k in range(nodes)]
    hist(circleResults, bins=bin_edges, density=True, width=0.45, color='blue', alpha=0.9, label='Simulations')
    legend()
    grid()
    show()

def ex2():
    probability = 0
    for k in range(3, 7):
        probability += hypergeom.pmf(k, 49, 6, 6)
    list = geom.rvs(probability, size=1000)
    print(list)

    # cel putin 10 bilete succesive sunt necastigatoare
    # pana cand jucatorul nimereste un bilet castigator
    valuesThatRespectCriteria = 0
    for value in list:
        if value >= 10:
            valuesThatRespectCriteria += 1
    print("Probability Through Simulations: ", valuesThatRespectCriteria / 1000)

    theoreticalProbability = 1 - geom.cdf(9, probability)
    print("Theoretical Probability: ", theoreticalProbability)

if __name__ == '__main__':
    # ex1
    print("Ex1:")
    ex1(500)

    # ex2
    print("Ex2:")
    ex2()
    