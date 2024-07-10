from random import seed, random, randbytes, randrange, randint, choice, choices, shuffle, sample, uniform
from math import factorial, comb, perm, trunc, floor, ceil, dist
from itertools import combinations, combinations_with_replacement, permutations, count, cycle, groupby, product, repeat
from scipy.stats import binom, bernoulli, hypergeom, geom
from matplotlib import pyplot as plt

def randomFunction():
    seed_value = 42  # You can choose any integer value for the seed
    seed()
    # seed(seed_value)
    a = random()
    print(a)

    random_bytes = randbytes(4)
    print("random.randbytes: ")
    print(random_bytes)

    random_number = randrange(2, 10, 2)  # Returns an even random number in the range [0, 10)
    print("random.randrange: ")
    print(random_number)

    random_integer = randint(1, 100)  # Returns a random integer between 1 and 100 (inclusive)
    print("random.randint: ")
    print(random_integer)

    my_list = [1, 2, 3, 4, 5]
    random_element = choice(my_list)
    print("random.choice: ")
    print(random_element)

    weights = [0.1, 0.2, 0.4, 0.2, 0.1]
    random_elements = choices(my_list, weights=weights, k=3)
    print("random.choices: ")
    print(random_elements)

    shuffle(my_list) # Shuffle the list in-place
    print("random.shuffle: ")
    print(my_list)

    sampled_elements = sample(my_list, k=3) # Sample 3 unique elements without replacement
    print("random.sample: ")
    print(sampled_elements)

    random_float = random() # Generate a random float between 0 and 1
    print("random.random: ")
    print(random_float)

    random_float_uniform = uniform(2.5, 5.5) # Generate a random float between 2.5 and 5.5
    print("random.uniform: ")
    print(random_float_uniform)



def mathFunctions():
    factorial_result = factorial(5) # Calculate the factorial of 5
    print("math.factorial: ")
    print(factorial_result)

    combinations_result = comb(5, 2) # Calculate the number of combinations of 5 items taken 2 at a time
    print("math.comb: ")
    print(combinations_result)

    permutations_result = perm(5, 2) # Calculate the number of arrangements of 5 items taken 2 at a time
    print("math.perm: ")
    print(permutations_result)

    # Round down, round up, and truncate a floating-point number
    num = 5.7
    floor_result = floor(num)
    ceil_result = ceil(num)
    trunc_result = trunc(num)
    print("math.floor, math.ceil, math.trunc: ")
    print("Original number: ", num)
    print("Floor: ", floor_result)
    print("Ceil: ", ceil_result)
    print("Trunc: ", trunc_result)

    point1 = (1, 2)
    point2 = (4, 6)
    distance = dist(point1, point2) # Calculates the Euclidean distance between two points in dimensional space
    print("math.dist: \n", distance)



def itertoolsFunctions():
    my_list = [1, 2, 3, 4]

    combinations_result = list(combinations(my_list, 2)) # Combinations of 2 elements
    print("itertools.combinations:\n", combinations_result)

    combinations_with_replacement_result = list(combinations_with_replacement(my_list, 2)) # Combinations with replacement of 2 elements
    print("itertools.combinations_with_replacement:\n", combinations_with_replacement_result)

    permutations_result = list(permutations(my_list, 2)) # Permutations of 2 elements
    print("itertools.permutations:\n", permutations_result)

    counter = count(start=1, step=2) # Creates an iterator that generates an infinite sequence starting from 1 with a step of 2
    print("itertools.count:")
    for _ in range(5):
        print(next(counter))

    cycler = cycle(my_list) # Creates an iterator that cycles through the given iterable infinitely
    print("itertools.cycle:")
    for _ in range(8):
        print(next(cycler))

    list1 = [1, 2]
    list2 = ['a', 'b']
    cartesian_product = list(product(list1, list2)) # Cartesian product of the given iterables
    print("itertools.product:\n", cartesian_product)

    print("itertools.product2: \n", list(product(range(1, 7), repeat=3)))

    repeated_value = list(repeat("Hello", 3)) # Creates an iterator that repeats the specified object a specified number of times
    print("itertools.repeat:\n", repeated_value)



def binomialFunctions():
    n = 5  # Number of trials
    p = 0.5  # Probability of success
    k = 2  # Number of successes
    # Generates random variates representing the number of successes in n trials
    random_variates = binom.rvs(n, p, size=5)
    print("binom.rvs: ", random_variates)

    # The probability of getting {k} successes in {n} trials with success probability {p} is {probability:.4f}
    probability = binom.pmf(k, n, p)
    print('binom.pmf: ', probability)

    # The probability of getting at most {k} successes in {n} trials with success probability {p} is {cumulative_probability:.4f}
    cumulative_probability = binom.cdf(k, n, p)
    print('binom.cdf: ', cumulative_probability)


def bermoulliFunctions():
    x = bernoulli.rvs(0.5) # Takes value 0 or 1 depending on the probability
    print("Bernoulli distribution rvs: ", x)

    p = 0.3  # Probability of success
    pmf_at_0 = bernoulli.pmf(0, p)  # Probability of failure
    pmf_at_1 = bernoulli.pmf(1, p)  # Probability of success
    cdf_at_0 = bernoulli.cdf(0, p)  # Probability of being less than or equal to 0
    cdf_at_1 = bernoulli.cdf(1, p)  # Probability of being less than or equal to 1
    print('bernoulli.pmf: ', pmf_at_0, ' ', pmf_at_1)
    print('bernoulli.cdf: ', cdf_at_0, ' ', cdf_at_1)


def hypergeometricFunctions():
    # Parameters for a 6/49 lottery
    N = 49  # Total number of elements in the population
    K = 6  # Number of successes in the population (6 winning numbers)
    n = 6  # Sample size (6 numbers drawn)
    k = 3  # Number of successes in the sample (you want all 6 to be winning)
    random_samples = hypergeom.rvs(N, K, n, size=10) # List with the number of successes taken by {n} with {K} maximum successes from {N} elements
    print(f"Hypergeom.rvs: {random_samples}")

    probability = hypergeom.pmf(k, N, K, n) # Probability of getting {k} successes in {N} elements taken by {n} with {K} maximum successes
    print("Hypergeom.pmf: ", probability)

    cdfProbability = hypergeom.cdf(k, N, K, n) # Probability of getting at most {k} successes in {N} elements taken by {n} with {K} maximum successes
    print(f"Hypergeom.cdf: at k={k}: {cdfProbability:.4f}")


def geometricFunctions():
    # Used most for finding the number of tries until a success appears for a specific probability
    list = geom.rvs(0.25, size=5)
    print("geom.rvs: ", list)

    # Used for finding the probability a value appears for a specific probability
    probability2 = geom.pmf(3, 0.25)
    print("geom.pmf:", probability2)

    # Used for finding the probability a value less or equal to a specific value appears for a specific probability
    probability2 = geom.cdf(3, 0.25)
    print("geom.cdf:", probability2)



if __name__ == '__main__':
    # random.: seed, random, randbytes, randrange, randint, choice, choices, shuffle, sample, uniform
    print("\nrandom: ")
    randomFunction()

    # math.: factorial, comb, perm, floor, ceil, trunc
    print("\nmath: ")
    mathFunctions()

    # itertools.: combinations, combinations_with_replacement, permutations, count, cycle, groupby, product, repeat
    print("\nitertools: ")
    itertoolsFunctions()

    # binom.
    print("\nbinom: ")
    binomialFunctions()

    # bermoulli.
    print("\nbermoulli: ")
    bermoulliFunctions()

    # hypergeom.
    print("\nhypergeom: ")
    hypergeometricFunctions()

    # geom.
    print("\ngeom: ")
    geometricFunctions()