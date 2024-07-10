# 2. Să se determine distanța Euclideană între două locații identificate prin perechi de numere.
# De ex. distanța între (1,5) și (4,1) este 5.0.
import math


def pb2():
    # Initializam punctele cerute
    x = [1, 5]
    y = [4, 1]

    # Folosim formula pentru calcularea distantei Euclidiene intre doua puncte
    EuclideanDistance = math.dist(x, y)
    print(EuclideanDistance)

def pb2CuBot():
    p1 = (1, 5)
    p2 = (4, 1)
    x1, y1 = p1
    x2, y2 = p2
    distanta = math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)
    print(distanta)