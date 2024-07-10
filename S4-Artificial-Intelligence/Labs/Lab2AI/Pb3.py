# Se da un fisier care contine un text (format din mai multe propozitii) in limba romana - a se vedea fisierul ”data/texts.txt”.
# Se cere sa se determine si sa se vizualizeze:
#
# numarul de propozitii din text;
# numarul de cuvinte din text
# numarul de cuvinte diferite din text
# cel mai scurt si cel mai lung cuvant (cuvinte)
# textul fara diacritice
# sinonimele celui mai lung cuvant din text

import rowordnet
from rowordnet import RoWordNet
import nltk
import spacy
import unicodedata
from nltk.corpus import wordnet as wn
from nltk import word_tokenize, sent_tokenize
# nltk.download('omw')
# nltk.download('wordnet')
# nltk.download('punkt')
#nltk.data.path.append("C:\\Users\\razva\\AppData\\Roaming\\nltk_data")

def pb3():
    with open("texts.txt", "r", encoding="utf-8") as f:
        text = f.read()

    pb3a(text)
    pb3b(text)
    pb3c(text)
    pb3d(text)
    pb3e(text)
    pb3f(text)

    f.close()

def pb3a(text):
    # numarul de propozitii din text;
    propozitii = sent_tokenize(text)
    print("Numarul de propozitii din text:", len(propozitii), "\n")

def pb3b(text):
    # numarul de cuvinte din text
    cuvinte = word_tokenize(text)
    tokensWithoutPunctuation = [word for word in cuvinte if word.isalnum() and not word.isdigit()]
    print("Numarul de cuvinte din text:", len(tokensWithoutPunctuation), "\n")

def pb3c(text):
    # numarul de cuvinte diferite din text
    cuvinte = word_tokenize(text)
    tokensWithoutPunctuation = [word for word in cuvinte if word.isalnum() and not word.isdigit()]
    tokensWithoutPunctuationDistinct = set(tokensWithoutPunctuation)
    print("Numarul de cuvinte distincte din text:", len(tokensWithoutPunctuationDistinct), "\n")

def pb3d(text):
    # cel mai scurt si cel mai lung cuvant (cuvinte)
    cuvinte = word_tokenize(text)
    tokensWithoutPunctuation = [word for word in cuvinte if word.isalnum() and not word.isdigit()]
    tokensWithoutPunctuationDistinct = set(tokensWithoutPunctuation)
    allTheShortestWords = [word for word in tokensWithoutPunctuationDistinct if len(word) == len(min(tokensWithoutPunctuationDistinct, key=len))]
    allTheLongestWords = [word for word in tokensWithoutPunctuationDistinct if len(word) == len(max(tokensWithoutPunctuationDistinct, key=len))]
    print("Cele mai scurte cuvinte:", allTheShortestWords)
    print("Cele mai lungi cuvinte:", allTheLongestWords, "\n")

def pb3e(text):
    # textul fara diacritice
    # folosim forma de normalizare D (Decomposition) pentru a obtine diacriticele sub forma de caractere separate
    print("Textul fara diacritice: ", ''.join(c for c in unicodedata.normalize('NFD', text) if unicodedata.category(c) != 'Mn'), "\n")

def pb3f(text):
    # sinonimele celui mai lung cuvant din text
    nlp = spacy.load("ro_core_news_sm")
    doc = nlp("laboratoarele")
    singularForm = ""
    for token in doc:
        singularForm += token.lemma_ + " "
    word = singularForm.strip()
    rown = RoWordNet()
    synsetIds = rown.synsets(literal=word)
    for synsetId in synsetIds:
        synset = rown.synset(synsetId)
        synonyms = [literal for literal in synset.literals]
        print(synonyms)