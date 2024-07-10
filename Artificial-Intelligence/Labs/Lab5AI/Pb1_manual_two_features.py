import csv
import numpy as np


def rezolvare_manuala_GDP_and_Freedom(fileName):
    inputVariabNames = ['Economy..GDP.per.Capita.', 'Freedom']
    outputVariabName = 'Happiness.Score'
    inputGDP, inputFreedom, outputs = loadData(fileName, inputVariabNames[0], inputVariabNames[1], outputVariabName)


    np.random.seed(5)
    indexes = [i for i in range(len(inputGDP))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputGDP)), replace=False)
    validationSample = [i for i in indexes if i not in trainSample]

    # Cream setul de training
    trainInputsGDP = [inputGDP[i] for i in trainSample]
    trainInputsFreedom = [inputFreedom[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]

    # Cream setul de validare
    validationInputsGDP = [inputGDP[i] for i in validationSample]
    validationInputsFreedom = [inputFreedom[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    trainInputs = list(zip(trainInputsGDP, trainInputsFreedom))
    validationInputs = list(zip(validationInputsGDP, validationInputsFreedom))

    w = trainModel(trainInputs, trainOutputs)
    print('The learnt model: f(x) = ', w[0], ' + ', w[1], ' * x1 + ', w[2], ' * x2')

    computedValidationOutputs = predict(validationInputs, w)

    error = computeError(validationOutputs, computedValidationOutputs)
    print('Error: ', error)

def loadData(fileName, inputVariabName1, inputVariabName2, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                data.append(row)
            line_count += 1
    selectedVariable1 = dataNames.index(inputVariabName1)
    inputs1 = [float(data[i][selectedVariable1]) if data[i][selectedVariable1] != '' else None for i in range(len(data))]
    selectedVariable2 = dataNames.index(inputVariabName2)
    inputs2 = [float(data[i][selectedVariable2]) if data[i][selectedVariable2] != '' else None for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) if data[i][selectedOutput] != '' else None for i in range(len(data))]

    # Stergem datele care nu sunt complete
    inputs1, inputs2, outputs = zip(*[(i1, i2, o) for i1, i2, o in zip(inputs1, inputs2, outputs) if i1 is not None and i2 is not None and o is not None])

    return list(inputs1), list(inputs2), list(outputs)

def add_ones_column(matrix):
    # Adaugam o coloana de 1 la inceputul matricei
    return [[1] + list(row) for row in matrix]

def transpose(matrix):
    # Transpunem matricea
    return list(map(list, zip(*matrix)))

def dot_product(a, b):
    # Calculam produsul scalar a doi vectori
    return sum(x * y for x, y in zip(a, b))

def matrix_multiply(a, b):
    # Inmultim doua matrice
    return [[dot_product(row, col) for col in transpose(b)] for row in a]

def inverse(matrix):
    # Calculam inversa unei matrice
    n = len(matrix)
    identity = [[float(i == j) for i in range(n)] for j in range(n)]
    for i in range(n):
        if matrix[i][i] == 0.0:
            for j in range(i + 1, n):
                if matrix[j][i] != 0.0:
                    matrix[i], matrix[j] = matrix[j], matrix[i]
                    identity[i], identity[j] = identity[j], identity[i]
                    break
            else:
                raise ValueError("Matrix is not invertible")
        pivot = matrix[i][i]
        for j in range(i, n):
            matrix[i][j] /= pivot
        for j in range(n):
            identity[i][j] /= pivot
        for j in range(n):
            if i != j:
                ratio = matrix[j][i]
                for k in range(i, n):
                    matrix[j][k] -= ratio * matrix[i][k]
                for k in range(n):
                    identity[j][k] -= ratio * identity[i][k]
    return identity

def trainModel(trainInputs, trainOutputs):
    X = add_ones_column(trainInputs)
    y = trainOutputs

    Xt = transpose(X)
    XtX = matrix_multiply(Xt, X)
    XtX_inv = inverse(XtX)
    XtX_inv_Xt = matrix_multiply(XtX_inv, Xt)

    # Calculam w cu formula (Xt * X)^(-1) * Xt * y
    w = [dot_product(row, y) for row in XtX_inv_Xt]

    return w

def predict(validationInputs, w):
    X = add_ones_column(validationInputs)
    computedValidationOutputs = [dot_product(row, w) for row in X]
    return computedValidationOutputs

def computeError(validationOutputs, computedValidationOutputs):
    # Calculam eroarea medie patratica
    error = sum((t1 - t2) ** 2 for t1, t2 in zip(computedValidationOutputs, validationOutputs)) / len(validationOutputs)
    return error