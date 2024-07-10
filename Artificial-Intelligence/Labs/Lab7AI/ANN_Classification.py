import itertools
import os

import cv2
import numpy as np
from matplotlib import pyplot as plt
from sklearn.metrics import confusion_matrix, accuracy_score, precision_score, recall_score, f1_score
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import StandardScaler

from ANN_manual.ANN import MyMLPClassifier
from CNN_manual.CNN import CNNModel


def load_image_data(folder, target_size=(256, 256)):
    _inputs = []
    _outputs = []

    for filename in os.listdir(folder):
        source_file = os.path.join(folder, filename)
        if os.path.isfile(source_file) and any(
                filename.lower().endswith(image_extension) for image_extension in ['.jpg', '.jpeg', '.png']):
            if folder == 'normal_anime_data':
                _outputs.append(0)
            elif folder == 'sepia_anime_data':
                _outputs.append(1)

            image = cv2.imread(source_file)
            if image is not None:
                resized_image = cv2.resize(image, target_size)
                _inputs.append(resized_image)
            else:
                print('Could not read image:', source_file)

    return _inputs, _outputs

def plot_histogram(_outputs, title):
    plt.hist(_outputs, bins=[-0.5, 0.5, 1.5], rwidth=0.8, align='mid')
    plt.xticks([0, 1])
    plt.title(title)
    plt.show()

def split_data(_inputs, _outputs, ratio=0.8):
    data_size = len(_inputs)
    indices = np.random.permutation(data_size)
    train_size = int(data_size * ratio)
    train_indices, _test_indices = indices[:train_size], indices[train_size:]
    _train_inputs, _test_inputs = np.array([_inputs[i] for i in train_indices]), np.array(
        [_inputs[i] for i in _test_indices])
    _train_outputs, _test_outputs = np.array([_outputs[i] for i in train_indices]), np.array(
        [_outputs[i] for i in _test_indices])
    return _train_inputs, _train_outputs, _test_inputs, _test_outputs

def flatten_images(data):
    return np.array(data).reshape(len(data), -1)

def normalize_data(train_data, test_data):
    scaler = StandardScaler()
    train_data_stacked = train_data.reshape(len(train_data), -1)
    test_data_stacked = test_data.reshape(len(test_data), -1)

    scaler.fit(train_data_stacked)
    normalized_train_data = scaler.transform(train_data_stacked)
    normalized_test_data = scaler.transform(test_data_stacked)

    return normalized_train_data, normalized_test_data

def predict_image(model, image_path):
    image = cv2.imread(image_path)
    image = cv2.resize(image, (256, 256))
    image = image.reshape((1, 256*256*3))
    prediction = model.predict(image)
    return "Normal" if prediction[0] == 0 else "Sepia"

def ann_classification(real_images_paths, sepia_images_paths, modelType):
    # Step 1: Load the images
    normal_inputs, normal_outputs = load_image_data(real_images_paths)
    sepia_inputs, sepia_outputs = load_image_data(sepia_images_paths)

    _output_names = ["real_images", "sepia_images"]

    inputs = normal_inputs + sepia_inputs
    outputs = normal_outputs + sepia_outputs

    # Step 2: Display the hsitogram of the output distribution
    plot_histogram(outputs, 'Output Distribution')

    # Step 3: Split the data into training and testing sets
    train_inputs, train_outputs, test_inputs, test_outputs = split_data(inputs, outputs)

    print("Shape of train_inputs:", train_inputs.shape)
    print("Shape of test_inputs:", test_inputs.shape)

    train_inputs = flatten_images(train_inputs)
    test_inputs = flatten_images(test_inputs)

    # Step 4: Normalize the data
    train_inputs, test_inputs = normalize_data(train_inputs, test_inputs)

    # Step 5: Train the ANN model on the training data
    if (modelType == 'tool'):
        model = MLPClassifier(hidden_layer_sizes=(15,), activation='tanh', max_iter=100, solver='sgd',
                                   verbose=1, random_state=1, learning_rate_init=.001)
        model.fit(train_inputs, train_outputs)
    else:
        model = MyMLPClassifier(hidden_layer_sizes=(15,), activation='relu', max_iter=30, verbose=5, random_state=1,
                                learning_rate_init=.01)
        model.fit(train_inputs, train_outputs)

    # input_shape = (256, 256, 3)
    # num_classes = 2
    # model = CNNModel(input_shape, num_classes)
    # model.train(train_inputs, train_outputs, learning_rate=0.001, epochs=10)
    # CNN NOT WORKING

    predicted_labels = model.predict(test_inputs)

    accuracy = accuracy_score(test_outputs, predicted_labels)
    precision = precision_score(test_outputs, predicted_labels, average='weighted', zero_division=1)
    recall = recall_score(test_outputs, predicted_labels, average='weighted', zero_division=1)
    f1 = f1_score(test_outputs, predicted_labels, average='weighted', zero_division=1)

    print(f'Accuracy: {accuracy}')
    print(f'Precision: {precision}')
    print(f'Recall: {recall}')
    print(f'F1 Score: {f1}')

    print(predict_image(model, 'normal_animal_data/Dolphin/Dolphin_1.jpg'))
    print(predict_image(model, 'normal_animal_data/Horse/Horse_4.jpg'))
    print(predict_image(model, 'normal_animal_data/Dog/Dog_4_1.jpg'))
    print(predict_image(model, 'sepia_animal_data/Kangaroo/Kangaroo_2.jpg'))
    print(predict_image(model, 'sepia_animal_data/Giraffe/Giraffe_2.jpg'))
    print(predict_image(model, 'sepia_animal_data/Cow/Cow_2.jpg'))