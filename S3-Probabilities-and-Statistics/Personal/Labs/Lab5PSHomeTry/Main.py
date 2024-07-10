from numpy import cumsum, zeros, unique
from scipy.stats import uniform, expon
import matplotlib.pyplot as plt
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks, yticks, plot
from math import log

def ex1(values, probabilities, noOfSimulations):
    randomValues = uniform.rvs(size=noOfSimulations)
    cumulativeProbabilities = cumsum(probabilities)
    simulatedBloodTypes = zeros(noOfSimulations, dtype=int) # Create a numpy array of integers of length noOfSimulations

    for i in range(noOfSimulations):
        for j in range(len(cumulativeProbabilities)):
            if cumulativeProbabilities[j - 1] < randomValues[i] <= cumulativeProbabilities[j]:
                simulatedBloodTypes[i] = values[j]
                break

    # print("randomValues: ", randomValues)
    # print("cumulativeProbabilities: ", cumulativeProbabilities)
    # print("result: ", result)

    uniqueBloodTypes, counts = unique(simulatedBloodTypes, return_counts=True)
    observedFrequencies = counts / noOfSimulations

    for bloodType, frequency in zip(uniqueBloodTypes, observedFrequencies):
        print(f"Grupa sanguina {bloodType} are frecventa relativa: {frequency:.2f}")

    plt.bar(uniqueBloodTypes, observedFrequencies, label="Observed")
    plt.bar(values, probabilities, label="Probabilidad Theoretical")
    plt.xlabel("Grupa sanguina")
    plt.ylabel("Frecventa relativa")
    plt.legend()
    plt.show()

def ex2(noOfSimulations):
    alpha = 1 / 12

    randomUniformValues = uniform.rvs(size=noOfSimulations)
    randomExponentialValues = [-1 / alpha * log(1 - randomUniformValues[i]) for i in range(noOfSimulations)]

    data = randomExponentialValues
    hist(data, bins=12, density=True, range=(0, 60))

    x = range(60)
    plot(x, expon.pdf(x, loc=0, scale=1 / alpha), 'r--')

    xticks(range(0, 60, 5))
    grid()
    show()

    # ii)
    k = 5
    probability = expon.cdf(k, loc=0, scale=1 / alpha)
    print(f'Theoretical Probability {1 - probability:.4f}')

    count = 0
    for i in randomExponentialValues:
        if i >= k:
            count += 1
    simulatedProbability = count / noOfSimulations
    print(f'Simulated Probability {simulatedProbability:.4f}')

if __name__ == '__main__':
    print("Ex1: Exercise 1")
    ex1([0, 1, 2, 3], [0.46, 0.40, 0.1, 0.04], 1000)

    print("Ex2: Exercise 2")
    ex2(1000)