# [START analyze_sentiment]
import csv
import os
from azure.core.credentials import AzureKeyCredential
from azure.ai.textanalytics import TextAnalyticsClient
from sklearn.metrics import accuracy_score

# endpoint = os.environ["LANGUAGE_ENDPOINT"]
# key = os.environ["LANGUAGE_KEY"]
key = ""
endpoint = ""

client = TextAnalyticsClient(endpoint=endpoint, credential=AzureKeyCredential(key))

crtDir = os.getcwd()
fileName = os.path.join(crtDir, 'data', 'reviews_mixed.csv')

texts = []
sentiment_labels = []
with open(fileName) as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    for row in csv_reader:
        texts.append(row[0])
        sentiment_labels.append(row[1])

batches = [texts[i:i + 10] for i in range(0, len(texts), 10)]
predicted_labels = []
for batch in batches:
    result = client.analyze_sentiment(batch, show_opinion_mining=True)
    docs = [doc for doc in result if not doc.is_error]

    for doc in docs:
        predicted_labels.append(doc.sentiment)

accuracy = accuracy_score(sentiment_labels, predicted_labels)
print(f"Accuracy: {accuracy}")

for batch in batches:
    result = client.extract_key_phrases(batch)
    docs = [doc for doc in result if not doc.is_error]

    for doc in docs:
        print(f"Document text: {doc}")
        print(f"Key phrases: {doc.key_phrases}")

input = "By choosing a bike over a car, I’m reducing my environmental footprint. Cycling promotes eco-friendly transportation, and I’m proud to be part of that movement."
result = client.analyze_sentiment([input], show_opinion_mining=True)
doc = result[0]

if not doc.is_error:
    print(f"Document text: {input}")
    print(f"Overall sentiment: {doc.sentiment}")
else:
    print(f"An error occurred: {doc.error}")

