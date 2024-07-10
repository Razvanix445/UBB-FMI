from transformers import GPT2LMHeadModel, GPT2Tokenizer

tokenizer = GPT2Tokenizer.from_pretrained('gpt2')
model = GPT2LMHeadModel.from_pretrained('gpt2')

def generate_code(description):
    inputs = tokenizer.encode(description, return_tensors="pt")

    outputs = model.generate(inputs, max_length=150, num_return_sequences=1, temperature=0.9, repetition_penalty=1.0,
                             top_k=10, top_p=0.85, eos_token_id=tokenizer.eos_token_id,
                             pad_token_id=tokenizer.pad_token_id)

    generated_code = tokenizer.decode(outputs[0], skip_special_tokens=True)

    return generated_code

description = "Write a function in Python that takes two numbers as input and returns their sum."

generated_code = generate_code(description)
print("Generated code:\n", generated_code)