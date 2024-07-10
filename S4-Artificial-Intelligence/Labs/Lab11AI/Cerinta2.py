import nltk
import numpy as np
from sklearn.metrics import accuracy_score
from transformers import RobertaForSequenceClassification, Trainer, TrainingArguments, DataCollatorWithPadding, \
    DataCollatorForSeq2Seq
import warnings

warnings.filterwarnings("ignore")

# PREGATIREA DATELOR

data = [
    {"code": "def add(a, b): return a + b", "comment": "This function adds two numbers, 'a' and 'b', and returns the result."},
    {"code": "def subtract(a, b): return a - b", "comment": "This function subtracts the second number, 'b', from the first number, 'a', and returns the result."},
    {"code": "def multiply(a, b): return a * b", "comment": "This function multiplies two numbers, 'a' and 'b', and returns the product."},
    {"code": "def divide(a, b): return a / b if b != 0 else 'Division by zero'", "comment": "This function divides 'a' by 'b' and returns the result. If 'b' is zero, it returns 'Division by zero'."},
    {"code": "def factorial(n): return 1 if n == 0 else n * factorial(n-1)", "comment": "This function calculates the factorial of 'n' using recursion and returns the result."},
    {"code": "def power(base, exp): return base ** exp", "comment": "This function raises 'base' to the power of 'exp' and returns the result."},
    {"code": "def is_even(num): return num % 2 == 0", "comment": "This function checks if 'num' is even. It returns True if 'num' is even and False otherwise."},
    {"code": "def max_of_two(a, b): return a if a > b else b", "comment": "This function returns the greater of the two numbers, 'a' and 'b'."},
    {"code": "def fibonacci(n): a, b = 0, 1 for _ in range(n): a, b = b, a + b return a", "comment": "This function calculates the nth Fibonacci number using an iterative approach and returns it."},
    {"code": "def reverse_string(s): return s[::-1]", "comment": "This function takes a string 's' and returns it reversed."},
    {"code": "def is_palindrome(s): s = s.lower() return s == s[::-1]", "comment": "This function checks if the string 's' is a palindrome, ignoring case, and returns True or False."},
    {"code": "def gcd(a, b): while b: a, b = b, a % b return a", "comment": "This function calculates the greatest common divisor (GCD) of 'a' and 'b' using the Euclidean algorithm and returns it."},
    {"code": "def lcm(a, b): return abs(a*b) // gcd(a, b)", "comment": "This function calculates the least common multiple (LCM) of 'a' and 'b' using the GCD function and returns it."},
    {"code": "def merge_lists(list1, list2): return sorted(list1 + list2)", "comment": "This function merges two lists, 'list1' and 'list2', and returns the sorted result."},
    {"code": "def find_median(lst): lst.sort() n = len(lst) mid = n // 2 return (lst[mid] + lst[~mid]) / 2", "comment": "This function finds the median of a list 'lst'. It sorts the list and returns the median value."},
    {"code": "def count_vowels(s): return sum(1 for char in s.lower() if char in 'aeiou')", "comment": "This function counts the number of vowels in the string 's' and returns the count."},
    {"code": "def flatten_list(nested_list): return [item for sublist in nested_list for item in sublist]", "comment": "This function flattens a nested list 'nested_list' and returns a single list with all the elements."},
    {"code": "def unique_elements(lst): return list(set(lst))", "comment": "This function returns a list of unique elements from the list 'lst'."},
    {"code": "def sort_dict_by_value(d): return dict(sorted(d.items(), key=lambda item: item[1]))", "comment": "This function sorts a dictionary 'd' by its values and returns the sorted dictionary."},
    {"code": "def square_elements(lst): return [x ** 2 for x in lst]", "comment": "This function returns a list with the square of each element in the list 'lst'."},
    {"code": "def filter_even_numbers(lst): return [x for x in lst if x % 2 == 0]", "comment": "This function filters the even numbers from the list 'lst' and returns a list of even numbers."},
    {"code": "def sum_of_squares(lst): return sum(x ** 2 for x in lst)", "comment": "This function calculates the sum of the squares of each element in the list 'lst' and returns the result."},
    {"code": "def capitalize_words(s): return ' '.join(word.capitalize() for word in s.split())", "comment": "This function capitalizes each word in the string 's' and returns the modified string."},
    {"code": "def count_occurrences(lst, x): return lst.count(x)", "comment": "This function counts the occurrences of the element 'x' in the list 'lst' and returns the count."},
    {"code": "def remove_duplicates(lst): return list(dict.fromkeys(lst))", "comment": "This function removes duplicate elements from the list 'lst' while preserving the order and returns the new list."},
    {"code": "def find_max(lst): return max(lst)", "comment": "This function finds and returns the maximum value in the list 'lst'."},
    {"code": "def find_min(lst): return min(lst)", "comment": "This function finds and returns the minimum value in the list 'lst'."},
    {"code": "def average(lst): return sum(lst) / len(lst)", "comment": "This function calculates the average of the elements in the list 'lst' and returns the result."},
    {"code": "def cumulative_sum(lst): return [sum(lst[:i+1]) for i in range(len(lst))]", "comment": "This function calculates the cumulative sum of the elements in the list 'lst' and returns a list of the cumulative sums."},
    {"code": "def find_second_largest(lst): return sorted(set(lst))[-2]", "comment": "This function finds the second largest number in the list 'lst' and returns it."},
    {"code": "def remove_whitespace(s): return s.replace(' ', '')", "comment": "This function removes all whitespace characters from the string 's' and returns the modified string."},
    {"code": "def is_anagram(s1, s2): return sorted(s1) == sorted(s2)", "comment": "This function checks if 's1' and 's2' are anagrams of each other and returns True or False."},
    {"code": "def find_duplicates(lst): return [item for item in set(lst) if lst.count(item) > 1]", "comment": "This function finds duplicate elements in the list 'lst' and returns a list of duplicates."},
    {"code": "def merge_dictionaries(d1, d2): return {**d1, **d2}", "comment": "This function merges two dictionaries, 'd1' and 'd2', and returns the merged dictionary."},
]

from transformers import AutoTokenizer, AutoModelForSeq2SeqLM, Trainer, TrainingArguments

tokenizer = AutoTokenizer.from_pretrained("SEBIS/code_trans_t5_base_code_comment_generation_java")
model = AutoModelForSeq2SeqLM.from_pretrained("SEBIS/code_trans_t5_base_code_comment_generation_java")

train_dataset = [
    {
        "code_snippet": "public class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}",
        "comment": "// This program prints \"Hello, World!\" to the console"
    },
    {
        "code_snippet": "def add(a, b):\n    return a + b",
        "comment": "# Function to add two numbers"
    },
    {
        "code_snippet": "class Rectangle {\n    int length;\n    int width;\n    \n    Rectangle(int l, int w) {\n        length = l;\n        width = w;\n    }\n    \n    int calculateArea() {\n        return length * width;\n    }\n}",
        "comment": "// Represents a rectangle and calculates its area"
    },
    {
        "code_snippet": "def factorial(n):\n    if n == 0:\n        return 1\n    else:\n        return n * factorial(n-1)",
        "comment": "# Function to calculate the factorial of a number"
    },
    {
        "code_snippet": "class LinkedListNode {\n    int data;\n    LinkedListNode next;\n}",
        "comment": "// Represents a node in a linked list"
    },
    {
        "code_snippet": "def linear_search(arr, x):\n    for i in range(len(arr)):\n        if arr[i] == x:\n            return i\n    return -1",
        "comment": "# Function to perform linear search in an array"
    },
    {
        "code_snippet": "public class QuickSort {\n    int partition(int arr[], int low, int high) {\n        int pivot = arr[high];\n        int i = (low-1);\n        for (int j=low; j<high; j++) {\n            if (arr[j] < pivot) {\n                i++;\n                int temp = arr[i];\n                arr[i] = arr[j];\n                arr[j] = temp;\n            }\n        }\n        int temp = arr[i+1];\n        arr[i+1] = arr[high];\n        arr[high] = temp;\n        return i+1;\n    }\n\n    void sort(int arr[], int low, int high) {\n        if (low < high) {\n            int pi = partition(arr, low, high);\n            sort(arr, low, pi-1);\n            sort(arr, pi+1, high);\n        }\n    }\n}",
        "comment": "// Implementation of the Quick Sort algorithm"
    },
    {
        "code_snippet": "def binary_search(arr, x):\n    low = 0\n    high = len(arr) - 1\n    while low <= high:\n        mid = (low + high) // 2\n        if arr[mid] < x:\n            low = mid + 1\n        elif arr[mid] > x:\n            high = mid - 1\n        else:\n            return mid\n    return -1",
        "comment": "# Function to perform binary search in a sorted array"
    },
    {
        "code_snippet": "public class Stack {\n    private int maxSize;\n    private long[] stackArray;\n    private int top;\n\n    public Stack(int size) {\n        maxSize = size;\n        stackArray = new long[maxSize];\n        top = -1;\n    }\n\n    public void push(long j) {\n        stackArray[++top] = j;\n    }\n\n    public long pop() {\n        return stackArray[top--];\n    }\n\n    public long peek() {\n        return stackArray[top];\n    }\n\n    public boolean isEmpty() {\n        return (top == -1);\n    }\n}",
        "comment": "// Implementation of a stack data structure"
    },
    {
        "code_snippet": "def selection_sort(arr):\n    n = len(arr)\n    for i in range(n):\n        min_idx = i\n        for j in range(i+1, n):\n            if arr[j] < arr[min_idx]:\n                min_idx = j\n        arr[i], arr[min_idx] = arr[min_idx], arr[i]",
        "comment": "# Function to perform selection sort on an array"
    },
    {
        "code_snippet": "class Node {\n    constructor(data) {\n        this.data = data;\n        this.left = null;\n        this.right = null;\n    }\n}",
        "comment": "// Represents a node in a binary tree"
    },
    {
        "code_snippet": "public class Queue {\n    private int maxSize;\n    private long[] queueArray;\n    private int front;\n    private int rear;\n    private int nItems;\n\n    public Queue(int size) {\n        maxSize = size;\n        queueArray = new long[maxSize];\n        front = 0;\n        rear = -1;\n        nItems = 0;\n    }\n\n    public void insert(long j) {\n        if (rear == maxSize-1)\n            rear = -1;\n        queueArray[++rear] = j;\n        nItems++;\n    }\n\n    public long remove() {\n        long temp = queueArray[front++];\n        if (front == maxSize)\n            front = 0;\n        nItems--;\n        return temp;\n    }\n\n    public long peekFront() {\n        return queueArray[front];\n    }\n\n    public boolean isEmpty() {\n        return (nItems == 0);\n    }\n}",
        "comment": "// Implementation of a queue data structure"
    },
    {
        "code_snippet": "def bubble_sort(arr):\n    n = len(arr)\n    for i in range(n-1):\n        for j in range(0, n-i-1):\n            if arr[j] > arr[j+1]:\n                arr[j], arr[j+1] = arr[j+1], arr[j]",
        "comment": "# Function to perform bubble sort on an array"
    },
    {
        "code_snippet": "class BinarySearchTree {\n    constructor() {\n        this.root = null;\n    }\n}",
        "comment": "// Represents a binary search tree"
    },
]

eval_dataset = [
    {"code_snippet": "public int square(int x) { return x * x; }", "comment": "// This method squares the input number and returns the result."},
    {"code_snippet": "public int cube(int x) { return x * x * x; }", "comment": "// This method cubes the input number and returns the result."},
    {"code_snippet": "public int absolute(int x) { return Math.abs(x); }", "comment": "// This method returns the absolute value of the input number."},
    {"code_snippet": "public String greet(String name) { return \"Hello, \" + name + \"!\"; }", "comment": "// This method greets the person with the input name."},
    {"code_snippet": "public boolean isPositive(int x) { return x > 0; }", "comment": "// This method checks if the input number is positive."},
    {"code_snippet": "public boolean isNegative(int x) { return x < 0; }", "comment": "// This method checks if the input number is negative."},
    {"code_snippet": "public boolean isZero(int x) { return x == 0; }", "comment": "// This method checks if the input number is zero."},
    {"code_snippet": "public boolean isOdd(int x) { return x % 2 != 0; }", "comment": "// This method checks if the input number is odd."},
    {"code_snippet": "public boolean isEven(int x) { return x % 2 == 0; }", "comment": "// This method checks if the input number is even."},
    {"code_snippet": "public int factorial(int n) { return (n == 0) ? 1 : n * factorial(n - 1); }", "comment": "// This method calculates the factorial of the input number using recursion."},
]

from datasets import Dataset, load_metric

train_dataset_dict = {key: [d[key] for d in train_dataset] for key in train_dataset[0]}
dataset = Dataset.from_dict(train_dataset_dict)

eval_dataset_dict = {key: [d[key] for d in eval_dataset] for key in eval_dataset[0]}
eval_dataset = Dataset.from_dict(eval_dataset_dict)

rouge = load_metric("rouge")
def compute_metrics(eval_pred):
    logits, labels = eval_pred
    predictions = np.argmax(logits, axis=-1).tolist()
    decoded_preds = [tokenizer.decode(pred, skip_special_tokens=True) for pred in predictions]
    labels = np.where(labels != -100, labels, tokenizer.pad_token_id)
    decoded_labels = [tokenizer.decode(label, skip_special_tokens=True) for label in labels.tolist()]

    decoded_preds = ["\n".join(nltk.sent_tokenize(pred.strip())) for pred in decoded_preds]
    decoded_labels = ["\n".join(nltk.sent_tokenize(label.strip())) for label in decoded_labels]

    result = rouge.compute(predictions=decoded_preds, references=decoded_labels, rouge_types=["rouge2"])["rouge2"].mid

    return {
        "rouge2_precision": result.precision,
        "rouge2_recall": result.recall,
        "rouge2_fmeasure": result.fmeasure,
        'accuracy': accuracy_score(labels.tolist(), predictions),
    }

def preprocess_function(examples):
    inputs = tokenizer(examples["code_snippet"], padding="max_length", truncation=True, max_length=512)
    targets = tokenizer(examples["comment"], padding="max_length", truncation=True, max_length=512)
    inputs["labels"] = targets["input_ids"]
    return inputs

tokenized_dataset = dataset.map(preprocess_function, batched=True)
tokenized_eval_dataset = eval_dataset.map(preprocess_function, batched=True)

data_collator = DataCollatorForSeq2Seq(tokenizer, model=model)

training_args = TrainingArguments(
    output_dir="./results",
    evaluation_strategy="no",
    learning_rate=2e-5,
    per_device_train_batch_size=2,
    per_device_eval_batch_size=2,
    num_train_epochs=1,
    weight_decay=0.01,
)

trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=tokenized_dataset,
    eval_dataset=tokenized_eval_dataset,
    data_collator=data_collator,
)

trainer.train()

eval_results = trainer.evaluate()
print(f'Evaluation results: {eval_results}')

model.save_pretrained("fine_tuned_code_trans_model")
tokenizer.save_pretrained("fine_tuned_code_trans_model")


def generate_comment(code_snippet):
    inputs = tokenizer(code_snippet, return_tensors="pt", padding="max_length", truncation=True, max_length=512)
    outputs = model.generate(input_ids=inputs["input_ids"], max_length=150, num_beams=5, early_stopping=True)
    comment = tokenizer.decode(outputs[0], skip_special_tokens=True)
    return comment

example_code_snippet = """
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
"""

generated_comment = generate_comment(example_code_snippet)
print("Generated Comment:", generated_comment)










# from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
#
# # Load the tokenizer and model
# tokenizer = AutoTokenizer.from_pretrained("SEBIS/code_trans_t5_base_code_comment_generation_java")
# model = AutoModelForSeq2SeqLM.from_pretrained("SEBIS/code_trans_t5_base_code_comment_generation_java")
#
# # Define a function to generate comments for code snippets
# def generate_comment(code):
#     # Tokenize the code
#     inputs = tokenizer(code, return_tensors="pt", max_length=512, truncation=True)
#
#     # Generate comment using the model
#     outputs = model.generate(**inputs)
#
#     # Decode the output to obtain the generated comment
#     generated_comment = tokenizer.decode(outputs[0], skip_special_tokens=True)
#
#     return generated_comment
#
# # Example usage
# code_snippet = """
# public class HelloWorld {
#     public static void main(String[] args) {
#         System.out.println("Hello, World!");
#     }
# }
# """
#
# comment = generate_comment(code_snippet)
# print("Generated comment:", comment)










# from transformers import GPT2LMHeadModel, GPT2Tokenizer
#
# class CustomDataset(Dataset):
#     def __init__(self, data, tokenizer, max_length):
#         self.data = data
#         self.tokenizer = tokenizer
#         self.max_length = max_length
#
#     def __len__(self):
#         return len(self.data)
#
#     def __getitem__(self, idx):
#         code = self.data[idx]['code']
#         comment = self.data[idx]['comment']
#         inputs = self.tokenizer.encode(code, return_tensors="pt", max_length=self.max_length, padding="max_length",
#                                        truncation=True)
#         labels = self.tokenizer.encode(comment, return_tensors="pt", max_length=self.max_length, padding="max_length",
#                                        truncation=True)
#         return {
#             'input_ids': inputs.flatten(),
#             'labels': labels.flatten()
#         }
#
# max_length = 128
# batch_size = 8
# epochs = 3
# learning_rate = 5e-5
#
# tokenizer = GPT2Tokenizer.from_pretrained('gpt2')
# model = GPT2LMHeadModel.from_pretrained('gpt2')
#
# tokenizer.add_special_tokens({'pad_token': '[PAD]'})
#
# tokenizer.pad_token = tokenizer.eos_token
#
# train_dataset = CustomDataset(data, tokenizer, max_length)
# train_dataloader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True)
#
# device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
# model.to(device)
#
# optimizer = AdamW(model.parameters(), lr=learning_rate)
#
# model.train()
# for epoch in range(epochs):
#     total_loss = 0
#     for batch in train_dataloader:
#         input_ids = batch['input_ids'].to(device)
#         labels = batch['labels'].to(device)
#
#         optimizer.zero_grad()
#         outputs = model(input_ids, labels=labels)
#         loss = outputs.loss
#         loss.backward()
#         optimizer.step()
#
#         total_loss += loss.item()
#
#     avg_loss = total_loss / len(train_dataloader)
#     print(f"Epoch {epoch+1}/{epochs}, Average Loss: {avg_loss}")
#
# model.save_pretrained('custom_gpt_model')
#
# def generate_comment(code_snippet):
#     inputs = tokenizer.encode(code_snippet, return_tensors="pt", max_length=max_length, padding="max_length", truncation=True)
#     output_ids = model.generate(inputs, max_length=max_length + 50, num_beams=5, early_stopping=True, pad_token_id=tokenizer.eos_token_id)
#     generated_comment = tokenizer.decode(output_ids[0], skip_special_tokens=True)
#
#     return generated_comment


# code_snippet = "def add(a, b): return a + b"
# comment = generate_comment(code_snippet)
# print("Generated Comment:", comment)
