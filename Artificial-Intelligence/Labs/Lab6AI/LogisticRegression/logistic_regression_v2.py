import numpy as np

class MyLogisticRegression:
    def __init__(self, learning_rate=0.01, num_iterations=1000, threshold=0.33):
        self.learning_rate = learning_rate
        self.num_iterations = num_iterations
        self.threshold = threshold
        self.theta = None

    def sigmoid(self, z):
        return 1 / (1 + np.exp(-z))

    def cost_function(self, X, y, theta):
        m = len(y)
        h = self.sigmoid(np.dot(X, theta))
        epsilon = 1e-5
        cost = (1 / m) * (-np.dot(y.T, np.log(h + epsilon)) - np.dot((1 - y).T, np.log(1 - h + epsilon)))
        return cost

    def gradient_descent(self, X, y):
        m, n = X.shape
        self.theta = np.zeros((n, 1))

        for _ in range(self.num_iterations):
            h = self.sigmoid(np.dot(X, self.theta))
            gradient = np.dot(X.T, (h - y)) / m
            self.theta -= self.learning_rate * gradient

    def fit(self, X, y):
        intercept = np.ones((X.shape[0], 1))
        X = np.concatenate((intercept, X), axis=1)

        self.gradient_descent(X, y)

    def predict(self, X):
        intercept = np.ones((X.shape[0], 1))
        X = np.concatenate((intercept, X), axis=1)

        predicted_probs = self.sigmoid(np.dot(X, self.theta))
        predicted_labels = (predicted_probs >= self.threshold).astype(int)

        return predicted_labels