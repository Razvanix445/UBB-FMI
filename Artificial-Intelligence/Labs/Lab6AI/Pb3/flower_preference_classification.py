# Ce fel de floare preferi? Se consideră problema clasificării florilor de iris în diferite specii precum:
# setosa, versicolor și virginica. Pentru fiecare floare se cunosc caracteristici precum: lungimea și lățimea
# sepalei, lungimea și lățimea petalei. Mai multe detalii despre acest set se pot găsi aici. Folosindu-se
# aceste informații, să se decidă din ce specie aparține o anumită floare.
#
# Antrenati cate un clasificator pentru fiecare problema, pe care apoi sa ii utilizati pentru a stabili:
#
# specia unei flori de iris care are sepala lunga de 5.35 cm si lata de 3.85 cm, iar petala lunga de 1.25 cm si lata de 0.4cm

import csv
import os

import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, recall_score, precision_score
from sklearn.preprocessing import StandardScaler, LabelEncoder

from LogisticRegression.logistic_regression_v2 import MyLogisticRegression

def loadDataMoreInputs(fileName, inputVariabNames, outputVariabName, label_encoder):
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
    selectedVariables = [dataNames.index(var) for var in inputVariabNames]
    inputs = [[float(data[i][var]) for var in selectedVariables] for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [data[i][selectedOutput] for i in range(len(data))]

    # label_encoder = LabelEncoder()
    outputs_encoded = label_encoder.fit_transform(outputs)

    outputs_encoded = outputs_encoded.reshape(-1, 1)

    return inputs, outputs_encoded

def flower_preference_classification(modelType):
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'iris.csv')

    label_encoder = LabelEncoder()
    inputs, outputs = loadDataMoreInputs(filePath, ['SepalLength', 'SepalWidth', 'PetalLength', 'PetalWidth'],
                                         'Class', label_encoder)

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
        # Custom Logistic Regression model implementation can be used here
        model = MyLogisticRegression(learning_rate=0.01, num_iterations=1000, threshold=0.33)
        model.fit(trainInputs, trainOutputs)
        learned_coefficients = model.theta
        print('the learnt model: f(x) = ', learned_coefficients[0][0], ' + ', learned_coefficients[1][0], ' * x1 + ',
              learned_coefficients[2][0], ' * x2')


    computedTestOutputs = model.predict(testInputs)

    print('Accuracy: ', accuracy_score(testOutputs, computedTestOutputs))  # correct predictions / total predictions
    print('Precision: ', precision_score(testOutputs, computedTestOutputs, average='weighted'))  # positive predictions that were correct
    print('Recall: ', recall_score(testOutputs, computedTestOutputs, average='weighted'))  # correct positive predictions

    # Verification for a new input
    normalized_inputs = scaler.transform([[5.35, 3.85, 1.25, 0.4]])
    prediction = model.predict(np.array(normalized_inputs))
    predicted_species = label_encoder.inverse_transform(prediction)
    print("The predicted species for the flower is: ", predicted_species[0])