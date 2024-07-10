# Sa se normalizeze informatiile de la problema 1 si 2 folosind diferite metode de normalizare astfel:
#
# problema 1 - salariul, bonusul, echipa
# problema 2 - valorile pixelilor din imagini
# problema 3 - numarul de aparitii a cuvintelor la nivelul unei propozitii.

import pandas as pd
from sklearn.preprocessing import MinMaxScaler, OneHotEncoder
import matplotlib.pyplot as plt

def pb4a():
    # Sa se normalizeze informatiile de la problema 1 astfel:
    # problema 1 - salariul, bonusul, echipa
    df = pd.read_csv("employees.csv")
    scaler = MinMaxScaler()
    df[['Salary', 'Bonus %']] = scaler.fit_transform(df[['Salary', 'Bonus %']])
    encoder = OneHotEncoder()
    oneHot = encoder.fit_transform(df[['Team']]).toarray()
    oneHotDf= pd.DataFrame(oneHot, columns=encoder.get_feature_names_out(['Team']))
    df = pd.concat([df, oneHotDf], axis=1)
    df = df.drop(['Team'], axis=1)
    print(df)

    plt.figure(figsize=(10, 5))
    plt.hist(df['Salary'], bins=50, alpha=0.5, label='Salary')
    plt.hist(df['Bonus %'], bins=50, alpha=0.5, label='Bonus %')
    plt.legend(loc='upper right')
    plt.title('Salary and Bonus')
    plt.xlabel('Value')
    plt.ylabel('Frequency')
    plt.grid(color='#95a5a6', linestyle='--', linewidth=1, axis='y', alpha=0.7)
    plt.show()

    varianta1(scaler, oneHotDf)
    varianta2(scaler, oneHotDf)

def varianta1(scaler, oneHotDf):
    teamCounts = oneHotDf.sum().sort_values(ascending=False)

    plt.figure(figsize=(10, 5))
    plt.bar(teamCounts.index, teamCounts.values)
    plt.title('Teams')
    plt.xlabel('Team')
    plt.ylabel('Frequency')
    plt.xticks(rotation=90)
    plt.show()

def varianta2(scaler, oneHotDf):
    teamCounts = oneHotDf.sum().sort_values(ascending=False)
    teamCounts = pd.DataFrame(teamCounts, columns=['Frequency'])
    teamCounts['Frequency'] = scaler.fit_transform(teamCounts[['Frequency']])  # Normalize the team counts

    plt.figure(figsize=(10, 5))
    plt.bar(teamCounts.index, teamCounts['Frequency'])
    plt.title('Teams')
    plt.xlabel('Team')
    plt.ylabel('Frequency')
    plt.xticks(rotation=90)
    plt.show()