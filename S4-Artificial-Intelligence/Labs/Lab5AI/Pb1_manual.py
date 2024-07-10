import csv

import numpy as np

def rezolvare_manuala_family(fileName):
    inputVariabName = 'Family'
    outputVariabName = 'Happiness.Score'
    inputs, outputs = loadData(fileName, inputVariabName, outputVariabName)

    trainRatio = 0.8
    trainInputs, trainOutputs, validationInputs, validationOutputs = splitData(inputs, outputs, trainRatio)

    w0, w1 = trainModel(trainInputs, trainOutputs)
    print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

    computedValidationOutputs = predict(validationInputs, w0, w1)

    error = computeError(validationOutputs, computedValidationOutputs)
    print('Error: ', error)

def rezolvare_manuala_produs_intern_brut(fileName):
    inputVariabName = 'Economy..GDP.per.Capita.'
    outputVariabName = 'Happiness.Score'
    inputs, outputs = loadData(fileName, inputVariabName, outputVariabName)

    trainRatio = 0.8
    trainInputs, trainOutputs, validationInputs, validationOutputs = splitData(inputs, outputs, trainRatio)

    w0, w1 = trainModel(trainInputs, trainOutputs)
    print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

    computedValidationOutputs = predict(validationInputs, w0, w1)

    error = computeError(validationOutputs, computedValidationOutputs)
    print('Error: ', error)

def loadData(fileName, inputVariabName, outputVariabName):
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
    selectedVariable = dataNames.index(inputVariabName)
    inputs = [float(data[i][selectedVariable]) if data[i][selectedVariable] != '' else None for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) if data[i][selectedOutput] != '' else None for i in range(len(data))]

    # Stergem datele cu valori lipsa
    inputs, outputs = zip(*[(i, o) for i, o in zip(inputs, outputs) if i is not None and o is not None])

    return list(inputs), list(outputs)

def splitData(inputs, outputs, trainRatio):
    # Impartim datele in set de antrenare si set de validare
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(trainRatio * len(inputs)), replace=False)
    validationSample = [i for i in indexes if i not in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [inputs[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    return trainInputs, trainOutputs, validationInputs, validationOutputs

def trainModel(trainInputs, trainOutputs):
    # Antrenam modelul
    n = len(trainInputs)
    sum_x = sum(trainInputs)
    sum_y = sum(trainOutputs)
    sum_xy = sum(i * j for i, j in zip(trainInputs, trainOutputs))
    sum_xx = sum(i ** 2 for i in trainInputs)

    w1 = (n * sum_xy - sum_x * sum_y) / (n * sum_xx - sum_x ** 2)
    w0 = (sum_y - w1 * sum_x) / n

    return w0, w1

def predict(validationInputs, w0, w1):
    # Realizam predictia
    computedValidationOutputs = [w0 + w1 * x for x in validationInputs]
    return computedValidationOutputs

def computeError(validationOutputs, computedValidationOutputs):
    # Calculam eroarea medie patratica
    error = sum((t1 - t2) ** 2 for t1, t2 in zip(computedValidationOutputs, validationOutputs)) / len(validationOutputs)
    return error