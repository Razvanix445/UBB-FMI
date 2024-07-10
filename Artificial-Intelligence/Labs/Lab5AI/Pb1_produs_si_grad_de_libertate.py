# prerequisites
import csv
import os
import pandas as pd

import matplotlib.pyplot as plt
import numpy as np
from sklearn import linear_model
from sklearn.metrics import mean_squared_error


# PASUL 1
# load data and consider a single feature (Economy..GDP.per.capita) and the output to be estimated (happiness)
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
    inputs1 = [float(data[i][selectedVariable1]) if data[i][selectedVariable1] != '' else np.nan for i in range(len(data))]
    selectedVariable2 = dataNames.index(inputVariabName2)
    inputs2 = [float(data[i][selectedVariable2]) if data[i][selectedVariable2] != '' else np.nan for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) if data[i][selectedOutput] != '' else np.nan for i in range(len(data))]

    df = pd.DataFrame({
        inputVariabName1: inputs1,
        inputVariabName2: inputs2,
        outputVariabName: outputs
    })

    missing_values = df.isnull().sum()
    print('=============================================================')
    print('Missing values: \n', missing_values)

    df.dropna(inplace=True)
    inputs1 = df[inputVariabName1].tolist()
    inputs2 = df[inputVariabName2].tolist()
    outputs = df[outputVariabName].tolist()

    return inputs1, inputs2, outputs

# see how the data looks (plot the histograms associated to input data - GDP feature - and output data - happiness)
def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()

def pb1_produs_si_grad_de_libertate(fileName):
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', fileName)

    inputsGDP, inputsFreedom, outputs = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')
    print('=============================================================')
    print('in1:  ', inputsGDP[:5])
    print('in2:  ', inputsFreedom[:5])
    print('out: ', outputs[:5])

    plotDataHistogram(inputsGDP, 'capita GDP')
    plotDataHistogram(inputsFreedom, 'Freedom')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variables (x1 = capita, x2 = Freedom).)
    plt.plot(inputsGDP, outputs, 'ro')
    plt.xlabel('GDP capita')
    plt.ylabel('Happiness score')
    plt.title('GDP capita vs. Happiness score')
    plt.show()

    plt.plot(inputsFreedom, outputs, 'ro')
    plt.xlabel('Freedom')
    plt.ylabel('Happiness score')
    plt.title('Freedom vs. Happiness score')
    plt.show()

    # PASUL 2
    # Split the Data Into Training and Test Subsets
    # In this step we will split our dataset into training and testing subsets (in proportion 80/20%).

    # Training data set is used for learning the linear model. Testing dataset is used for validating of the model. All data from testing dataset will be new to model and we may check how accurate the model predictions are.

    np.random.seed(5)  # ensure the random numbers are predictable
    indexes = [i for i in range(len(inputsGDP))]  # create a list of indexes for the input data
    trainSample = np.random.choice(indexes, int(0.8 * len(inputsGDP)),
                                   replace=False)  # select randomly 80% of the indexes to be used for the training set
    validationSample = [i for i in indexes if
                        not i in trainSample]  # the rest of the indexes will be used for the validation set

    # create the training sets
    trainInputsGDP = [inputsGDP[i] for i in trainSample]
    trainInputsFreedom = [inputsFreedom[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]

    # create the validation sets
    validationInputsGDP = [inputsGDP[i] for i in validationSample]
    validationInputsFreedom = [inputsFreedom[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    # plot the GDP capita training and validation data
    plt.plot(trainInputsGDP, trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(validationInputsGDP, validationOutputs, 'g^',
             label='validation data')  # test data are plotted by green and a triangle sign
    plt.title('train and validation data')
    plt.xlabel('GDP capita')
    plt.ylabel('Happiness Score')
    plt.legend()
    plt.show()

    # plot the Freedom training and validation data
    plt.plot(trainInputsFreedom, trainOutputs, 'ro',
             label='training data')  # train data are plotted by red and circle sign
    plt.plot(validationInputsFreedom, validationOutputs, 'g^',
             label='validation data')  # test data are plotted by green and a triangle sign
    plt.title('train and validation data')
    plt.xlabel('Freedom')
    plt.ylabel('Happiness Score')
    plt.legend()
    plt.show()

    # PASUL 3
    # learning step: init and train a linear regression model y = f(x) = w0 + w1 * x
    # Prediction step: used the trained model to estimate the output for a new input

    # using sklearn
    # training data preparation (the sklearn linear model requires as input training data as noSamples x noFeatures array; in the current case, the input must be a matrix of len(trainInputs) lines and two columns (two features are used in this problem))
    xx = [[el1, el2] for el1, el2 in zip(trainInputsGDP, trainInputsFreedom)]

    # model initialisation
    regressor = linear_model.LinearRegression()
    # training the model by using the training inputs and known training outputs
    regressor.fit(xx, trainOutputs)
    # save the model parameters
    # w0 - y-intercept (regressor.intercept_); w1, w2 - slopes (regressor.coef_)
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    print('=============================================================')
    print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x1 + ', w2, ' * x2')

    # # using developed code
    # from myRegression import MyLinearUnivariateRegression

    # # model initialisation
    # regressor = MyLinearUnivariateRegression()
    # # training the model by using the training inputs and known training outputs
    # regressor.fit(trainInputs, trainOutputs)
    # # save the model parameters
    # w0, w1 = regressor.intercept_, regressor.coef_
    # print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

    # PASUL 4
    # plot the learnt model
    # prepare some synthetic data (inputs are random, while the outputs are computed by the learnt model)
    noOfPoints = 1000
    xrefGDP = np.linspace(min(trainInputsGDP), max(trainInputsGDP), noOfPoints)
    xrefFreedom = np.linspace(min(trainInputsFreedom), max(trainInputsFreedom), noOfPoints)
    yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xrefGDP, xrefFreedom)]

    plt.plot(trainInputsGDP, trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(xrefGDP, yref, 'b-', label='learnt model')  # model is plotted by a blue line
    plt.title('train data and the learnt model')
    plt.xlabel('GDP capita')
    plt.ylabel('Happiness Score')
    plt.legend()
    plt.show()

    plt.plot(trainInputsFreedom, trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(xrefFreedom, yref, 'b-', label='learnt model')  # model is plotted by a blue line
    plt.title('train data and the learnt model')
    plt.xlabel('Freedom')
    plt.ylabel('Happiness Score')
    plt.legend()
    plt.show()

    # use the trained model to predict new inputs

    # makes predictions for test data (manual)
    # computedTestOutputs = [w0 + w1 * el for el in testInputs]

    # makes predictions for test data (by tool)
    computedValidationOutputs = regressor.predict([[x1, x2] for x1, x2 in zip(validationInputsGDP, validationInputsFreedom)])

    # plot the computed outputs (see how far they are from the real outputs)
    plt.plot(validationInputsGDP, computedValidationOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(validationInputsGDP, validationOutputs, 'g^',
             label='real test data')  # real test data are plotted by green triangles
    plt.title('computed validation and real validation data')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.legend()
    plt.show()

    # plot the computed outputs (see how far they are from the real outputs)
    plt.plot(validationInputsFreedom, computedValidationOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(validationInputsFreedom, validationOutputs, 'g^',
             label='real test data')  # real test data are plotted by green triangles
    plt.title('computed validation and real validation data')
    plt.xlabel('Freedom')
    plt.ylabel('Happiness Score')
    plt.legend()
    plt.show()

    # PASUL 5
    # compute the differences between the predictions and real outputs
    # "manual" computation
    error = 0.0
    for t1, t2 in zip(computedValidationOutputs, validationOutputs):
        error += (t1 - t2) ** 2
    error = error / len(validationOutputs)
    print('=============================================================')
    print('prediction error (manual): ', error)

    # by using sklearn
    error = mean_squared_error(validationOutputs, computedValidationOutputs)
    print('prediction error (tool):  ', error)



    # PASUL 6
    # correlation and missing data
    df = pd.DataFrame({
        'GDP': inputsGDP,
        'Freedom': inputsFreedom,
        'Happiness': outputs
    })

    correlation_matrix = df.corr()
    print('=============================================================')
    print('Correlation matrix: \n', correlation_matrix)
    print('=============================================================')