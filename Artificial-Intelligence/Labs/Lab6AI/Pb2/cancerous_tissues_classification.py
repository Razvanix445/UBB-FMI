# Clasificarea țesuturilor cancerigene Se consideră informații despre cancerul de sân la femei, informații extrase
# din ecografii mamare precum: - Tipul malformației identificate (țesut benign sau țesut malign) -
# Caracteristici numerice ale nucleului celulelor din aceste țesuturi: - raza (media distanțelor între centru si
# punctele de pe contur) - textura (măsurată prin deviația standard a nivelelor de gri din imaginea asociată țesutului
# analizat) Folosindu-se aceste date, să se decidă dacă țesutul dintr-o nouă ecografie (pentru care se cunosc cele 2
# caracteristici numerice – raza și textura –) va fi etichetat ca fiind malign sau benign.

# Antrenati cate un clasificator pentru fiecare problema, pe care apoi sa ii utilizati pentru a stabili:
#
# daca o leziune (dintr-o mamografie) caracterizata printr-o textura de valoare 10 si o raza de valoare 18
# este leziune maligna sau benigna.

import csv
import os

import numpy as np
from matplotlib import pyplot as plt
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, precision_score, recall_score, roc_curve, auc
from sklearn.preprocessing import StandardScaler

from LogisticRegression.logistic_regression import MyLogisticRegression


def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()

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

def plotROCCurve(fpr, tpr, roc_auc):
    plt.figure()
    plt.plot(fpr, tpr, color='darkorange', lw=2, label='ROC curve (area = %0.2f)' % roc_auc)
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title('Receiver Operating Characteristic')
    plt.legend(loc="lower right")
    plt.show()

def cancerous_tissues_classification(modelType):
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'wdbc.csv')

    inputs, outputs = loadDataMoreInputs(filePath, ['Radius', 'Texture'], 'Diagnosis')

    # PASUL 2: split data into training data (80%) and testing data (20%) and normalise data
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    # Normalization
    scaler = StandardScaler()
    if not isinstance(trainInputs[0], list):
        trainInputs = [[d] for d in trainInputs]
        testInputs = [[d] for d in testInputs]

        scaler.fit(trainInputs)
        trainInputs = scaler.transform(trainInputs)
        testInputs = scaler.transform(testInputs)

        trainInputs = [el[0] for el in trainInputs]
        testInputs = [el[0] for el in testInputs]
    else:
        scaler.fit(trainInputs)
        trainInputs = scaler.transform(trainInputs)
        testInputs = scaler.transform(testInputs)

    # PASUL 3: training step
    if modelType == "tool":
        model = LogisticRegression()
        model.fit(trainInputs, trainOutputs)
        w0, w1 = model.intercept_, model.coef_[0]
        print('the learnt model: f(x) = ', w0[0], ' + ', w1[0], ' * x1 + ', w1[1], ' * x2')
    else:
        model = MyLogisticRegression(thresholds=[0.2, 0.5, 0.9])
        trainOutputs = np.array(trainOutputs)
        model.fit(trainInputs, trainOutputs)
        w0 = model.theta[0]
        w1 = model.theta[1]
        w2 = model.theta[2]
        print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x1 + ', w2, ' * x2')

    computedTestOutputs = model.predict(testInputs)

    print('Accuracy: ', accuracy_score(testOutputs, computedTestOutputs))  # correct predictions / total predictions
    print('Precision: ', precision_score(testOutputs, computedTestOutputs))  # positive predictions that were correct
    print('Recall: ', recall_score(testOutputs, computedTestOutputs))  # correct positive predictions

    # Verification for a new input
    normalized_inputs = scaler.transform([[18, 10]])
    prediction = model.predict(np.array(normalized_inputs))
    if prediction[0] == 0:
        print("The lesion is predicted to be benign.")
    else:
        print("The lesion is predicted to be malignant.")

    # fpr, tpr, thresholds = roc_curve(testOutputs, computedTestOutputs)
    # roc_auc = auc(fpr, tpr)
    # plotROCCurve(fpr, tpr, roc_auc)