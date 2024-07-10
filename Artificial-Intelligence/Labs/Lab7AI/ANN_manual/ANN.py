import numpy as np


class MyMLPClassifier:
    def __init__(self, hidden_layer_sizes=(100,), activation='relu', alpha=0.0001,
                 learning_rate_init=0.001, max_iter=200, random_state=None, verbose=False):
        self.hidden_layer_sizes = hidden_layer_sizes
        self.activation = activation
        self.alpha = alpha
        self.learning_rate_init = learning_rate_init
        self.max_iter = max_iter
        self.random_state = random_state
        self.verbose = verbose
        self.weights = []
        self.biases = []

    def _initialize_weights_and_biases(self, input_size, output_size):
        layer_sizes = [input_size] + list(self.hidden_layer_sizes) + [output_size]
        for i in range(1, len(layer_sizes)):
            self.weights.append(np.random.randn(layer_sizes[i - 1], layer_sizes[i]) * 0.01)
            self.biases.append(np.zeros((1, layer_sizes[i])))

    def _activation_function(self, z):
        if self.activation == 'relu':
            return np.maximum(0, z)
        elif self.activation == 'tanh':
            return np.tanh(z)
        elif self.activation == 'sigmoid':
            return 1 / (1 + np.exp(-z))
        else:
            raise ValueError("Invalid activation function. Supported activations: 'relu', 'tanh', 'sigmoid'.")

    def _derivative_activation_function(self, z):
        if self.activation == 'relu':
            return np.where(z > 0, 1, 0)
        elif self.activation == 'tanh':
            return 1 - np.tanh(z) ** 2
        elif self.activation == 'sigmoid':
            return np.exp(-z) / (1 + np.exp(-z)) ** 2
        else:
            raise ValueError("Invalid activation function. Supported activations: 'relu', 'tanh', 'sigmoid'.")

    def fit(self, X, y):
        self._initialize_weights_and_biases(X.shape[1], 1)

        y = np.reshape(y, (-1, 1))

        for i in range(self.max_iter):
            # Forward Propagation
            a = [X]
            for weight, bias in zip(self.weights, self.biases):
                z = np.dot(a[-1], weight) + bias
                a.append(self._activation_function(z))

            # Backward Propagation
            dz = a[-1] - y
            for j in range(len(self.weights) - 1, -1, -1):
                dw = (1 / X.shape[0]) * np.dot(a[j].T, dz)
                db = (1 / X.shape[0]) * np.sum(dz, axis=0, keepdims=True)
                dz = np.dot(dz, self.weights[j].T) * self._derivative_activation_function(a[j])
                self.weights[j] -= self.learning_rate_init * dw
                self.biases[j] -= self.learning_rate_init * db

            if self.verbose and i % 10 == 0:
                loss = np.mean(np.square(y - a[-1]))

    def predict(self, X):
        a = X
        for weight, bias in zip(self.weights, self.biases):
            z = np.dot(a, weight) + bias
            a = self._activation_function(z)

        binary_output = np.where(a >= 0.5, 1, 0)

        return binary_output
