# Stabiliti care este sentimentul transmis prin mesajul By choosing a bike over a car, I’m reducing my environmental
# footprint. Cycling promotes eco-friendly transportation, and I’m proud to be part of that movement..
# Folositi:
# clasificatorul bazat pe k-means antrenat de voi (folosind o biblioteca care implementeaza algoritmul k-means)

import warnings; warnings.simplefilter('ignore')

import csv
import os
os.environ["LOKY_MAX_CPU_COUNT"] = "4"
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score

# PASUL 1 - incarcare date
# load some data
crtDir = os.getcwd()
# fileName = os.path.join(crtDir, 'data', 'text_emotion.csv')
fileName = os.path.join(crtDir, 'data', 'reviews_mixed.csv')

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

print(labelNames[:])

# PASUL 2 - impartire date (antrenament si test)
# prepare data for training and testing
np.random.seed(5)
# noSamples = inputs.shape[0]
noSamples = len(inputs)
indexes = [i for i in range(noSamples)]
trainSample = np.random.choice(indexes, int(0.8 * noSamples), replace=False)
testSample = [i for i in indexes if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

print(trainInputs[:])

# PASUL 3 - extragere caracteristici
# extract some features from the raw text
# # representation 1: Bag of Words
vectorizer = CountVectorizer()

trainFeatures = vectorizer.fit_transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# vocabulary size
print("vocab size: ", len(vectorizer.vocabulary_),  " words")
# no of emails (Samples)
print("traindata size: ", len(trainInputs), " sentiments")
# shape of feature matrix
print("trainFeatures shape: ", trainFeatures.shape)

# vocabulary from the train data
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

print("Unique emotions: ", set(computedTestOutputs))

# PASUL 6 - calcul metrici de performanta
# just supposing that we have the true labels
print("Accuracy: ", accuracy_score(testOutputs, computedTestOutputs))

input = "By choosing a bike over a car, I’m reducing my environmental footprint. Cycling promotes eco-friendly transportation, and I’m proud to be part of that movement."
input_features = vectorizer.transform([input])

input_index = unsupervisedClassifier.predict(input_features)
input_output = labelNames[input_index[0]]

print(f"Document text: {input}")
print(f"Predicted sentiment: {input_output}")

from sklearn.manifold import TSNE
import matplotlib.pyplot as plt

# Use t-SNE to reduce the TF-IDF features to 2 dimensions
tsne = TSNE(n_components=2, random_state=0)
reduced_features = tsne.fit_transform(trainFeatures.toarray())

# Plot the reduced features
plt.scatter(reduced_features[:, 0], reduced_features[:, 1])
plt.show()