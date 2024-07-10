# Se cunosc date despre angajatii unei companii, date salvate in fisierul "data/employees.csv".
#
# 1.a. Sa se stabileasca:
#
# numarul de angajati
# numar si tipul informatiilor (proprietatilor) detinute pentru un angajat
# numarul de angajati pentru care se detin date complete
# valorile minime, maxime, medii pentru fiecare proprietate
# in cazul proprietatilor nenumerice, cate valori posibile are fiecare astfel de proprietate
# daca sunt valori lipsa si cum se poate rezolva aceasta problema
#
# 1.b. Sa se vizualizeze:
#
# distributia salariilor acestor angajati pe categorii de salar
# distributia salariilor acestor angajati pe categorii de salar si echipa din care fac parte
# angajatii care pot fi considerati "outlieri"

import pandas as pd
import matplotlib.pyplot as plt
import sns


def pb1a():
    employees = pd.read_csv("employees.csv", delimiter=',', header='infer')
    # print(employees)

    # numarul de angajati
    noOfEmployees = employees.shape[0]
    print("Numarul de angajati: ", noOfEmployees, "\n")

    # numar si tipul informatiilor (proprietatilor) detinute pentru un angajat
    noOfProperties = employees.shape[1]
    print("Numarul de proprietati: ", noOfProperties)
    print("Tipul informatiilor: \n", employees.iloc[0].apply(type))
    print()

    # numarul de angajati pentru care se detin date complete
    noOfEmployeesWithCompleteData = employees.dropna().shape[0]
    print("Numarul de angajati pentru care se detin date complete: ", noOfEmployeesWithCompleteData, "\n")

    # valorile minime, maxime, medii pentru fiecare proprietate
    minMaxMeanValues(employees)

    # in cazul proprietatilor nenumerice, cate valori posibile are fiecare astfel de proprietate
    howManyPossibleValues(employees)

    # daca sunt valori lipsa si cum se poate rezolva aceasta problema
    employeesCopy1 = employees.copy()
    variantaPrinInlocuireCuMedia(employeesCopy1) # inlocuieste cu media valorile lipsa
    noOfEmployeesWithCompleteData = employees.shape[0]
    print("Numarul de angajati pentru care se detin date complete: ", noOfEmployeesWithCompleteData, "\n")
    employeesCopy2 = employees.copy()
    variantaPrinStergere(employeesCopy2) # sterge randurile cu valori lipsa


def minMaxMeanValues(employees):
    minSalary = employees['Salary'].min()
    maxSalary = employees['Salary'].max()
    meanSalary = employees['Salary'].mean()

    minBonus = employees['Bonus %'].min()
    maxBonus = employees['Bonus %'].max()
    meanBonus = employees['Bonus %'].mean()

    minSeniorManagement = employees['Senior Management'].min()
    maxSeniorManagement = employees['Senior Management'].max()
    meanSeniorManagement = employees['Senior Management'].mean()

    print("Valorile minime, maxime, medii pentru fiecare proprietate: ")
    statistics = pd.DataFrame({
        'Salary': [minSalary, maxSalary, meanSalary],
        'Bonus %': [minBonus, maxBonus, meanBonus],
        'Senior Management': [minSeniorManagement, maxSeniorManagement, meanSeniorManagement]
    }, index=['Min', 'Max', 'Mean'])
    print(statistics, "\n")

def howManyPossibleValues(employees):
    print("\nCate valori posibile are fiecare proprietate nenumerica: ")
    print("First Name: ", employees['First Name'].dropna().unique().shape[0])
    print("Gender: ", employees['Gender'].dropna().unique().shape[0])
    print("Start Date: ", employees['Start Date'].dropna().unique().shape[0])
    print("Last Login Time: ", employees['Last Login Time'].dropna().unique().shape[0])
    print("Senior Management: ", employees['Senior Management'].dropna().unique().shape[0])
    print("Team: ", employees['Team'].dropna().unique().shape[0])
    print()

def variantaPrinInlocuireCuMedia(employees):
    employees['First Name'].fillna(employees['First Name'].mode()[0])
    employees['Gender'].fillna(employees['Gender'].mode()[0])
    employees['Start Date'].fillna(employees['Start Date'].mode()[0])
    employees['Last Login Time'].fillna(employees['Last Login Time'].mode()[0])
    employees['Salary'].fillna(employees.Salary.mean())
    employees['Bonus %'].fillna(employees['Bonus %'].mean())
    employees['Senior Management'].fillna(employees['Senior Management'].mode()[0])
    employees['Team'].fillna(employees['Team'].mode()[0])

def variantaPrinStergere(employees):
    noOfEmployeesWithCompleteData = employees.dropna().shape[0]
    print("Numarul de angajati pentru care se detin date complete: ", noOfEmployeesWithCompleteData, "\n")


def pb1b():
    employees = pd.read_csv("employees.csv", delimiter=',', header='infer')
    variantaPrinInlocuireCuMedia(employees)

    # distributia salariilor acestor angajati pe categorii de salar
    print("Distributia salariilor acestor angajati pe categorii de salar: ")

    # Histograma distributiei salariilor
    salaryData = employees["Salary"]
    fig = plt.figure(figsize=(10, 4))
    plt.hist(salaryData, color="blue")
    plt.title("Salary Distribution")
    plt.xlabel("Salary")
    plt.ylabel("No. Of Employees")
    plt.grid(color='#95a5a6', linestyle='--', linewidth=1, axis='y', alpha=0.7)
    plt.xticks(rotation=90)
    fig.show()

    # Calculul distributiei salariilor pe categorii
    print(pd.cut(employees['Salary'], 5).value_counts())
    print()

    # distributia salariilor acestor angajati pe categorii de salar si echipa din care fac parte
    print("Distributia salariilor acestor angajati pe categorii de salar si echipa din care fac parte: ")
    # plt.bar(x=employees["Team"], height=employees["Salary"], color='purple')
    # plt.title("Salary - Team distribution")
    # plt.xlabel("Team")
    # plt.ylabel("Salary")
    # plt.xticks(rotation=90)
    # plt.show()

    # Calculul distributiei salariilor pe categorii si echipa din care fac parte
    print(employees.groupby(['Team', pd.cut(employees['Salary'], 5)]).size())
    print()

    # angajatii care pot fi considerati "outlieri"
    print("Angajatii care pot fi considerati 'outlieri': ")

    # Calculul angajatilor care pot fi considerati "outlieri"
    employees2 = pd.read_csv("employees.csv", delimiter=',', header='infer')

    # Calculul valorilor Q1 si Q3
    Q1 = employees2['Salary'].quantile(0.25)
    Q3 = employees2['Salary'].quantile(0.75)

    col1 = employees2[employees["Salary"] < Q1]
    col2 = employees2[employees["Salary"] > Q3]
    col = pd.concat([col1, col2]) # Toti outlierii concatenati intr-o lista
    print(col)

    # Afisarea angajatilor care pot fi considerati "outlieri"
    # plt.bar(x=col["First Name"], height=col["Salary"], color='purple')
    # plt.title("Outliers")
    # plt.xlabel("Salary")
    # plt.ylabel("No. Of Employees")
    # plt.xticks(rotation=90)
    # plt.show()