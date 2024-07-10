# Generare în limba română: Implementați un sistem care transformă un text (corpus) într-un lanț Markov
# și folosiți-l pentru a genera un proverb sau o poezie în limba română (folosiți fișierele proverbRo.txt
# sau poezieRo.txt)
# Varianta 1 – Implementați un lanț Markov cu o singură stare sau
# Varianta 2 – Implementați un lanț Markov cu n-stări

import random

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

def pb1():
    corpus = read_file('data/proverbe.txt')
    words = preprocess_text(corpus)
    markov_chain = build_markov_chain(words, state_size=2)

    generated_text = generate_text(markov_chain, length=15)
    generated_text = generated_text.capitalize()
    generated_text = generated_text + '.'
    print(generated_text)