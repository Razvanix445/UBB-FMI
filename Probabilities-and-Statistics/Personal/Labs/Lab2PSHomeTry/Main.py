import matplotlib
from matplotlib import pyplot as plt
from matplotlib.pyplot import axis, plot
import math
from math import dist
import random
from random import randint
from numpy.random import randint

def ex1(noOfPeople, noOfSimulations):
    noOfSuccesses = 0
    for _ in range(noOfSimulations):
        zile_nastere = [random.randint(1, 365) for _ in range(noOfPeople)]
        if len(zile_nastere) != len(set(zile_nastere)):
            noOfSuccesses += 1
    probability = noOfSuccesses / noOfSimulations
    return probability

def ex2i():
    for simulationNumber in noOfSimulations:
        pointsInTheCircle = 0
        pointsOutsideTheCircle = 0
        xIn, yIn, xOut, yOut = [], [], [], []

        for _ in range(simulationNumber):
            x = random.uniform(0, 1)
            y = random.uniform(0, 1)
            EuclidianDistance = dist((x, y), (0.5, 0.5))
            if EuclidianDistance <= 0.5:
                pointsInTheCircle += 1
                xIn.append(x)
                yIn.append(y)
            else:
                pointsOutsideTheCircle += 1
                xOut.append(x)
                yOut.append(y)

        relativeFrequecyInTheCircle = pointsInTheCircle / simulationNumber
        relativeFrequecyOutsideTheCircle = pointsOutsideTheCircle / simulationNumber

        # Creates a new figure
        plt.figure()
        # Plots two scatter plots of points with coordinates (x, y)
        plt.scatter(xIn, yIn, label=f'In the circle: ({relativeFrequecyInTheCircle:.2%})', s=10, color='blue')
        plt.scatter(xOut, yOut, label=f'Outside the circle: ({relativeFrequecyOutsideTheCircle:.2%}', s=10, color='grey')
        plt.xlim(0, 1)
        plt.ylim(0, 1)
        # Sets the aspect ratio of the plot to be equal
        plt.gca().set_aspect('equal', adjustable='box')
        # Adds a legend to the plot
        plt.legend()
        # Sets the title for the plot
        plt.title(f'No. Of Simulations = {simulationNumber}')
        plt.show()

        print(f"Relative Frequency for {simulationNumber} Simulations: ", relativeFrequecyInTheCircle)
    print("Geometrical Probability: ", math.pi / 4)

def ex2ii():
    for simulationNumber in noOfSimulations:
        pointsCloserToTheCenter = 0
        pointsCloserToTheEdges = 0
        xIn, yIn, xOut, yOut = [], [], [], []

        for _ in range(simulationNumber):
            x = random.uniform(0, 1)
            y = random.uniform(0, 1)
            distanceCenter = dist((x, y), (0.5, 0.5))
            distanceEdge1 = dist((x, y), (0, 1))
            distanceEdge2 = dist((x, y), (1, 0))
            distanceEdge3 = dist((x, y), (1, 1))
            distanceEdge4 = dist((x, y), (0, 0))

            if distanceCenter < min(distanceEdge1, distanceEdge2, distanceEdge3, distanceEdge4):
                pointsCloserToTheCenter += 1
                xIn.append(x)
                yIn.append(y)
            else:
                pointsCloserToTheEdges += 1
                xOut.append(x)
                yOut.append(y)

        relativeFrequencyCloserToTheCenter = pointsCloserToTheCenter / simulationNumber
        relativeFrequencyCloserToTheEdges = pointsCloserToTheEdges / simulationNumber

        plt.figure()
        plt.scatter(xIn, yIn, label=f'Closer to the center: ({relativeFrequencyCloserToTheCenter:.2%})', s=10, color='blue')
        plt.scatter(xOut, yOut, label=f'Closer to the edges: ({relativeFrequencyCloserToTheEdges:.2%})', s=10, color='grey')
        plt.xlim(0, 1)
        plt.ylim(0, 1)
        plt.gca().set_aspect('equal', adjustable='box')
        plt.legend()
        plt.title(f'No. Of Simulations: {simulationNumber}')
        plt.show()

        print(f'Relative Frequency for {simulationNumber} Simulations: ', relativeFrequencyCloserToTheCenter)
    print('Geometrical Probability: ', 1 / 2)

def ex2iii():
    for simulationNumber in noOfSimulations:
        invalidPoints = 0
        validPoints = 0
        xIn, yIn, xOut, yOut = [], [], [], []

        for _ in range(simulationNumber):
            x = random.uniform(0, 1)
            y = random.uniform(0, 1)

            if (dist((x, y), (0, 0.5)) < 0.5 and dist((x, y), (0.5, 0)) < 0.5) or (
                    dist((x, y), (1, 0.5)) < 0.5 and dist((x, y), (0.5, 1)) < 0.5) or (
                    dist((x, y), (0.5, 0)) < 0.5 and dist((x, y), (1, 0.5)) < 0.5) or (
                    dist((x, y), (0.5, 1)) < 0.5 and dist((x, y), (0, 0.5)) < 0.5):
                validPoints += 1
                xIn.append(x)
                yIn.append(y)
            else:
                invalidPoints += 1
                xOut.append(x)
                yOut.append(y)

        relativeFrequencyValidPoints = validPoints / simulationNumber
        relativeFrequencyInvalidPoints = invalidPoints / simulationNumber

        plt.figure()
        plt.scatter(xIn, yIn, label=f'Valid Points: {relativeFrequencyValidPoints}', s=10, color='blue')
        plt.scatter(xOut, yOut, label=f'Invalid Points: {relativeFrequencyInvalidPoints}', s=10, color='grey')
        plt.xlim(0, 1)
        plt.ylim(0, 1)
        plt.gca().set_aspect('equal', adjustable='box')
        plt.legend()
        plt.title(f'No. Of Simulations: {simulationNumber} Simulations: {relativeFrequencyInvalidPoints}')
        plt.show()

        print("Relative Frequency: ", validPoints / simulationNumber)
    print('Geometrical probability: ', math.pi / 2 - 1)

if __name__ == '__main__':
    # ex1
    print("Ex1")
    probability = ex1(23, 1000)
    print("Probability through simulations: ", probability * 100 / 23)

    q = 1
    for i in range(0, 23):
        q = q * (365 - i) / 365
    p = 1 - q

    print("Theoretical probability: ", p * 100 / 23)

    # ex2: Afisati frecventa relativa a punctelor care:
    noOfSimulations = [10000]

    # a) sunt in interiorul cercului tangent laturilor patratului.
    print("Ex2i)")
    ex2i()

    # b) sunt mai apropiate de centrul patratului decat de varfurile patratului
    print("Ex2ii)")
    ex2ii()

    # c) formeaza cu varfurile patratului doua triunghiuri ascutitunghice si doua triunghiuri obtuzunghice
    print("Ex2iii)")
    ex2iii()
