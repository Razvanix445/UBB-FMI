# Generare în limba engleză:
# a. Folosiți biblioteca markovify (sau implementarea voastră de la problema 1) pentru a genera o strofă de poezie în
# limba engleză folosind unul din următoarele corpus-uri (sau orice altă sursă găsiți voi):
#
# b. Calculați emoția textului generat, puteți folosi una din următoarele resurse:
# Natural Language Toolkit (nltk) SentimentIntensityAnalyzer
# TextBlob sentiment
#
# c. Pentru a adresa limitările de creativitate în poezia generată înlocuiți aleator cuvinte cu sinonime.
# Se cere ca sinonimele să fie obținute folosind embedding-uri. (i.e. Cuvântul ales e transformat în forma
# sa embedded și se alege embedding-ul cel mai apropiat care este convertit la string)
#
# e. Calculați metrica BLEU (Bilingual Evaluation Understudy Score) pentru poezia aleasă


import random
import markovify
import sacrebleu
from nltk.translate.bleu_score import sentence_bleu
from textblob import TextBlob
from nltk.sentiment import SentimentIntensityAnalyzer
import nltk
from nltk.corpus import sentiwordnet as swn
from nltk.corpus import wordnet
from nltk.tokenize import word_tokenize
import spacy

def read_file(file_name):
    with open(file_name, 'r', encoding='utf-8') as file:
        return file.read()

def preprocess_text(text):
    cleaned_text = text.replace('\n', ' ').lower()
    cleaned_text = ''.join(char for char in cleaned_text if char.isalnum() or char.isspace())
    words = cleaned_text.split()
    return words

def build_markov_chain(words, state_size):
    markov_chain = {}
    for i in range(len(words) - state_size):
        state = tuple(words[i:i+state_size])
        next_word = words[i + state_size]
        if state in markov_chain:
            markov_chain[state].append(next_word)
        else:
            markov_chain[state] = [next_word]
    return markov_chain

def generate_text(markov_chain, length):
    current_state = random.choice(list(markov_chain.keys()))
    generated_text = list(current_state)
    for _ in range(length):
        if current_state in markov_chain:
            next_word = random.choice(markov_chain[current_state])
            generated_text.append(next_word)
            current_state = tuple(generated_text[-len(current_state):])
        else:
            break
    return ' '.join(generated_text)

def replace_words_with_synonyms(text, model):
    words = text.split()
    for i in range(len(words)):
        if words[i] in model:
            most_similar = model.most_similar(words[i])
            synonym = random.choice(most_similar)[0]
            words[i] = synonym
    return ' '.join(words)

def pb2():
    corpus = read_file('data/shakespeares_sonnets.txt')
    words = preprocess_text(corpus)
    markov_chain = build_markov_chain(words, state_size=2)

    print("=====================================================")
    print("\n a. Generate text: ")
    generated_text = generate_text(markov_chain, length=110)
    generated_text = generated_text.capitalize()
    generated_text = generated_text + '.'
    text = generated_text
    print("\n - With personal implementation: ")
    print(generated_text)

    print("\n - With markovify: ")
    text_model = markovify.Text(corpus)
    generated_sentences = []
    for _ in range(14):
        generated_text = None
        while generated_text is None:
            generated_text = text_model.make_sentence(max_words=8)
        generated_sentences.append(generated_text)

    generated_text = '\n'.join(generated_sentences)
    generated_text = generated_text.capitalize()
    generated_text = generated_text + '.'
    print(generated_text)

    print("\n=====================================================")
    print("\n b. Emotion analysis:")
    print("\n - With TextBlob: ")
    blob = TextBlob(generated_text)
    sentiment = blob.sentiment
    print(blob.sentiment)
    if sentiment.polarity > 0:
        print("Positive sentiment")
    elif sentiment.polarity < 0:
        print("Negative sentiment")
    else:
        print("Neutral sentiment")

    print("\n - With nltk SentimentIntensityAnalyzer: ")
    sia = SentimentIntensityAnalyzer()
    polarity_scores = sia.polarity_scores(generated_text)
    print(sia.polarity_scores(generated_text))
    if polarity_scores['compound'] > 0:
        print("Positive sentiment")
    elif polarity_scores['compound'] < 0:
        print("Negative sentiment")
    else:
        print("Neutral sentiment")

    print("\n=====================================================")
    print("\n c. Replace words with synonyms: ")
    print(text)

    words = word_tokenize(text)

    new_words = []
    for word in words:
        synonyms = wordnet.synsets(word)
        synonyms = [lem.name() for syn in synonyms for lem in syn.lemmas() if
                    lem.name() != word]

        if synonyms:
            new_word = synonyms[0]
            new_words.append(new_word)
        else:
            new_words.append(word)

    new_sentence = ' '.join(new_words)
    print(new_sentence)

    print("\n=====================================================")
    print("\n e. BLEU metric: ")
    candidate = generated_text.lower()
    print(candidate)
    candidate = word_tokenize(text)

    reference1 = """From fairest creatures we desire increase,
    That thereby beauty's rose might never die,
    But as the riper should by time decease,
    His tender heir might bear his memory:
    But thou contracted to thine own bright eyes,
    Feed'st thy light's flame with self-substantial fuel,
    Making a famine where abundance lies,
    Thy self thy foe, to thy sweet self too cruel:
    Thou that art now the world's fresh ornament,
    And only herald to the gaudy spring,
    Within thine own bud buriest thy content,
    And, tender churl, mak'st waste in niggarding:
    Pity the world, or else this glutton be,
    To eat the world's due, by the grave and thee."""
    reference1 = reference1.lower()

    reference2 = """When forty winters shall besiege thy brow,
    And dig deep trenches in thy beauty's field,
    Thy youth's proud livery so gazed on now,
    Will be a totter'd weed of small worth held:
    Then being asked, where all thy beauty lies,
    Where all the treasure of thy lusty days;
    To say, within thine own deep sunken eyes,
    Were an all-eating shame, and thriftless praise.
    How much more praise deserv'd thy beauty's use,
    If thou couldst answer 'This fair child of mine
    Shall sum my count, and make my old excuse,'
    Proving his beauty by succession thine!
    This were to be new made when thou art old,
    And see thy blood warm when thou feel'st it cold."""
    reference2 = reference2.lower()

    reference3 = """Look in thy glass and tell the face thou viewest
    Now is the time that face should form another;
    Whose fresh repair if now thou not renewest,
    Thou dost beguile the world, unbless some mother.
    For where is she so fair whose uneared womb
    Disdains the tillage of thy husbandry?
    Or who is he so fond will be the tomb
    Of his self-love, to stop posterity?
    Thou art thy mother's glass and she in thee
    Calls back the lovely April of her prime;
    So thou through windows of thine age shalt see,
    Despite of wrinkles, this thy golden time.
    But if thou live, remembered not to be,
    Die single and thine image dies with thee."""
    reference3 = reference3.lower()

    score = sacrebleu.raw_corpus_bleu(candidate, [reference1, reference2, reference3], .01).score

    print("BLEU Score: ", score)