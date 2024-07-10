import csv
import os
os.environ["LOKY_MAX_CPU_COUNT"] = "4"
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score

import warnings; warnings.simplefilter('ignore')

# PASUL 1 - incarcare date
# load some data
crtDir = os.getcwd()
fileName = os.path.join(crtDir, 'data', 'spam.csv')

data = []
with open(fileName) as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            dataNames = row
        else:
            data.append(row)
        line_count += 1

inputs = [data[i][0] for i in range(len(data))][:100]
outputs = [data[i][1] for i in range(len(data))][:100]
labelNames = list(set(outputs))

print(inputs[:2])
print(labelNames[:2])

# PASUL 2 - impartire date (antrenament si test)
# prepare data for training and testing
np.random.seed(5)
# noSamples = inputs.shape[0]
noSamples = len(inputs)
indexes = [i for i in range(noSamples)]
trainSample = np.random.choice(indexes, int(0.8 * noSamples), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

print(trainInputs[:3])

# PASUL 3 - extragere caracteristici
# extract some features from the raw text
# # representation 1: Bag of Words
vectorizer = CountVectorizer()

trainFeatures = vectorizer.fit_transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# vocabulary size
print("vocab size: ", len(vectorizer.vocabulary_),  " words")
# no of emails (Samples)
print("traindata size: ", len(trainInputs), " emails")
# shape of feature matrix
print("trainFeatures shape: ", trainFeatures.shape)

# vocabbulary from the train data
print('some words of the vocab: ', vectorizer.get_feature_names_out()[-20:])
# extracted features
print('some features: ', trainFeatures.toarray()[:3])

# representation 2: tf-idf features - word granularity
vectorizer = TfidfVectorizer(max_features=50)

trainFeatures = vectorizer.fit_transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# vocabulary from the train data
print('vocab: ', vectorizer.get_feature_names_out()[:10])
# extracted features
print('features: ', trainFeatures.toarray()[:3])

# PASUL 4 - antrenare model de invatare nesupervizata (clustering)
# unsupervised classification ( = clustering) of data
unsupervisedClassifier = KMeans(n_clusters=2, random_state=0)
unsupervisedClassifier.fit(trainFeatures)

# PASUL 5 - testare model
computedTestIndexes = unsupervisedClassifier.predict(testFeatures)
computedTestOutputs = [labelNames[value] for value in computedTestIndexes]
for i in range(0, len(testInputs)):
    print(testInputs[i], " -> ", computedTestOutputs[i])

# PASUL 6 - calcul metrici de performanta
# just supposing that we have the true labels
print("acc: ", accuracy_score(testOutputs, computedTestOutputs))