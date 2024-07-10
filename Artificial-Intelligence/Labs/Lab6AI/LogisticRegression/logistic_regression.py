import numpy as np


class MyLogisticRegression:
    def __init__(self, learning_rate=0.01, num_iterations=100000, verbose=True, thresholds=[0.5]):
        self.learning_rate = learning_rate
        self.num_iterations = num_iterations
        self.verbose = verbose
        self.theta = None
        self.thresholds = thresholds

    def __sigmoid(self, z):
        return 1 / (1 + np.exp(-z))

    def __binary_cross_entropy_loss(self, h, y): # Binary Cross-Entropy Loss
        return (-y * np.log(h) - (1 - y) * np.log(1 - h)).mean()

    def __hinge_loss(self, h, y, threshold): # Hinge Loss
        y_pred = (h >= threshold).astype(int) * 2 - 1
        return np.maximum(0, 1 - y * y_pred).mean()

    def __mean_squared_error(self, h, y, threshold): # Mean Squared Error
        y_pred = (h >= threshold).astype(int)
        return ((y_pred - y) ** 2).mean()

    def fit(self, X, y):
        m, n = X.shape
        self.theta = np.zeros(n + 1)

        X = np.concatenate((np.ones((m, 1)), X), axis=1)

        for i in range(self.num_iterations):
            z = np.dot(X, self.theta)
            h = self.__sigmoid(z)
            gradient = np.dot(X.T, (h - y)) / m
            self.theta -= self.learning_rate * gradient

            if self.verbose and i % 10000 == 0:
                z = np.dot(X, self.theta)
                h = self.__sigmoid(z)
                # investigarea diferitelor funcții de loss (optional)
                for threshold in self.thresholds:
                    print(f'Loss at iteration {i}: {self.__binary_cross_entropy_loss(h, y)} (Binary Cross-Entropy Loss)')
                    print(f'Loss at iteration {i} with threshold {threshold}: {self.__hinge_loss(h, y, threshold)} (Hinge Loss)')
                    print(f'Loss at iteration {i} with threshold {threshold}: {self.__mean_squared_error(h, y, threshold)} (Mean Squared Error)')
                print()

    def predict(self, X):
        m = X.shape[0]
        X = np.concatenate((np.ones((m, 1)), X), axis=1)
        z = np.dot(X, self.theta)
        return np.round(self.__sigmoid(z))