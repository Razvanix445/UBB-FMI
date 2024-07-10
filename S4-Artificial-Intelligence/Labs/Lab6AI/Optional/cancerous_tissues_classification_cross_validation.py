# Rezolvarea unei probleme de regresie/clasificare prin:
#
# folosirea validarii încrucișate (cross-validation) pentru a evalua performanța modelului

import csv
import os

import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import cross_val_score, KFold
from sklearn.preprocessing import StandardScaler

from LogisticRegression.logistic_regression import MyLogisticRegression


def loadDataMoreInputs(fileName, inputVariabNames, outputVariabName):
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
    selectedVariable1 = dataNames.index(inputVariabNames[0])
    selectedVariable2 = dataNames.index(inputVariabNames[1])
    inputs = [[float(data[i][selectedVariable1]), float(data[i][selectedVariable2])] for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [1 if data[i][selectedOutput] == 'M' else 0 for i in range(len(data))]

    return inputs, outputs

def cancerous_tissues_classification_cross_validation():
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'wdbc.csv')

    inputs, outputs = loadDataMoreInputs(filePath, ['Radius', 'Texture'], 'Diagnosis')

    # Normalization
    scaler = StandardScaler()
    inputs = scaler.fit_transform(inputs)

    # PASUL 3: training step
    model = LogisticRegression()

    kf = KFold(n_splits=5, shuffle=True, random_state=10)

    scores = cross_val_score(model, inputs, outputs, cv=kf, scoring='accuracy')

    print("Accuracy for each fold: ", scores)
    print("Mean accuracy: ", scores.mean())

    model.fit(inputs, outputs)
    print("the learnt model: f(x) = ", model.intercept_[0], " + ", model.coef_[0][0], " * x1 + ", model.coef_[0][1], " * x2")

    # Verification for a new input
    normalized_inputs = scaler.transform([[18, 10]])
    prediction = model.predict(np.array(normalized_inputs))
    if prediction[0] == 0:
        print("The lesion is predicted to be benign.")
    else:
        print("The lesion is predicted to be malignant.")