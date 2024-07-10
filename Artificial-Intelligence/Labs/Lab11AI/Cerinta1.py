import pandas as pd
from datasets import Dataset
from transformers import RobertaForSequenceClassification, Trainer, TrainingArguments, RobertaTokenizer
from sklearn.model_selection import train_test_split
import warnings

warnings.filterwarnings("ignore")

# COLECTAREA SI PREGATIREA DATELOR

data = [
    {"code": "def add(a, b): return a + b", "label": "addition function"},
    {"code": "def add_numbers(x, y): return x + y", "label": "addition function"},
    {"code": "def sum_two_numbers(num1, num2): return num1 + num2", "label": "addition function"},
    {"code": "def subtract(a, b): return a - b", "label": "subtraction function"},
    {"code": "def subtract_numbers(x, y): return x - y", "label": "subtraction function"},
    {"code": "def difference(num1, num2): return num1 - num2", "label": "subtraction function"},
    {"code": "def multiply(a, b): return a * b", "label": "multiplication function"},
    {"code": "def multiply_numbers(x, y): return x * y", "label": "multiplication function"},
    {"code": "def product(num1, num2): return num1 * num2", "label": "multiplication function"},
    {"code": "def addition(a, b): return a + b", "label": "addition function"},
    {"code": "def add_two_numbers(x, y): return x + y", "label": "addition function"},
    {"code": "def add_values(num1, num2): return num1 + num2", "label": "addition function"},
    {"code": "def plus(a, b): return a + b", "label": "addition function"},
    {"code": "def add_together(x, y): return x + y", "label": "addition function"},
    {"code": "def add_nums(num1, num2): return num1 + num2", "label": "addition function"},
    {"code": "def add_two_values(a, b): return a + b", "label": "addition function"},
    {"code": "def sum(a, b): return a + b", "label": "addition function"},
    {"code": "def sum_values(x, y): return x + y", "label": "addition function"},
    {"code": "def total(num1, num2): return num1 + num2", "label": "addition function"},
    {"code": "def accumulate(a, b): return a + b", "label": "addition function"},
    {"code": "def subtract_two_numbers(a, b): return a - b", "label": "subtraction function"},
    {"code": "def minus(x, y): return x - y", "label": "subtraction function"},
    {"code": "def subtract_values(num1, num2): return num1 - num2", "label": "subtraction function"},
    {"code": "def sub(a, b): return a - b", "label": "subtraction function"},
    {"code": "def take_away(x, y): return x - y", "label": "subtraction function"},
    {"code": "def subtract_nums(num1, num2): return num1 - num2", "label": "subtraction function"},
    {"code": "def difference_between(a, b): return a - b", "label": "subtraction function"},
    {"code": "def reduce(x, y): return x - y", "label": "subtraction function"},
    {"code": "def deduct(num1, num2): return num1 - num2", "label": "subtraction function"},
    {"code": "def dec(a, b): return a - b", "label": "subtraction function"},
    {"code": "def decrement(x, y): return x - y", "label": "subtraction function"},
    {"code": "def multiply_two_numbers(a, b): return a * b", "label": "multiplication function"},
    {"code": "def times(x, y): return x * y", "label": "multiplication function"},
    {"code": "def multiply_values(num1, num2): return num1 * num2", "label": "multiplication function"},
    {"code": "def mul(a, b): return a * b", "label": "multiplication function"},
    {"code": "def multiply_together(x, y): return x * y", "label": "multiplication function"},
    {"code": "def multiply_nums(num1, num2): return num1 * num2", "label": "multiplication function"},
    {"code": "def product_of(a, b): return a * b", "label": "multiplication function"},
    {"code": "def times_two_numbers(x, y): return x * y", "label": "multiplication function"},
    {"code": "def prod(num1, num2): return num1 * num2", "label": "multiplication function"},
    {"code": "def multiplication(a, b): return a * b", "label": "multiplication function"},
    {"code": "def multiply_pair(x, y): return x * y", "label": "multiplication function"},
    {"code": "def scale(num1, num2): return num1 * num2", "label": "multiplication function"},
]

df = pd.DataFrame(data)
dataset = Dataset.from_pandas(df)

num_labels = len(df['label'].unique())
print(f"Number of unique labels: {num_labels}")


# FINE-TUNING A MODELULUI 'GaraphCodeBERT'

train_data, test_data = train_test_split(df, test_size=0.2, random_state=42)
train_dataset = Dataset.from_pandas(train_data)
test_dataset = Dataset.from_pandas(test_data)

model = RobertaForSequenceClassification.from_pretrained("microsoft/graphcodebert-base", num_labels=num_labels)
tokenizer = RobertaTokenizer.from_pretrained("microsoft/graphcodebert-base")

training_args = TrainingArguments(
    output_dir='./results',
    eval_strategy="epoch",
    learning_rate=2e-5,
    per_device_train_batch_size=8,
    per_device_eval_batch_size=8,
    num_train_epochs=6,
    weight_decay=0.01,
)

def preprocess_function(examples):
    tokenized_inputs = tokenizer(examples['code'], padding="max_length", truncation=True)
    tokenized_inputs["labels"] = [df['label'].unique().tolist().index(label) for label in examples['label']]
    return tokenized_inputs

from sklearn.metrics import accuracy_score

def compute_metrics(eval_pred):
    predictions, labels = eval_pred
    predictions = predictions.argmax(axis=-1)
    return {'accuracy': accuracy_score(labels, predictions)}

train_dataset = train_dataset.map(preprocess_function, batched=True)
test_dataset = test_dataset.map(preprocess_function, batched=True)

train_dataset = train_dataset.remove_columns(["code", "label"])
test_dataset = test_dataset.remove_columns(["code", "label"])

trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=train_dataset,
    eval_dataset=test_dataset,
    tokenizer=tokenizer,
    compute_metrics=compute_metrics,
)

trainer.train()


# EVALUAREA MODELULUI

eval_results = trainer.evaluate()
print(f'Evaluation results: {eval_results}')

code = "def multiply(a, b): return a * b"
inputs = tokenizer(code, return_tensors="pt")
outputs = model(**inputs)
predictions = outputs.logits.argmax(dim=-1)

label_map = {i: label for i, label in enumerate(df['label'].unique())}
predicted_label = label_map[predictions.item()]
print(f"The predicted purpose of the code is: {predicted_label}")