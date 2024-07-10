class MyBGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # batch gradient descent
    def fit(self, x, y, learningRate = 0.001, noEpochs = 1000, batches = 20):
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]
        for epoch in range(noEpochs):
            for i in range(0, len(x), batches):
                ycomputed = [self.eval(xi) for xi in x[i:i + batches]]
                crtErrors = [yc - yi for yc, yi in
                             zip(ycomputed, y[i:i + batches])]
                for j in range(0, len(x[0])):
                    self.coef_[j] = self.coef_[j] - learningRate * sum(
                        [crtError * xi[j] for crtError, xi in zip(crtErrors, x[i:i + batches])])
                self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * sum(crtErrors)

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def eval(self, xi):
        yi = self.coef_[-1]
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed