import numpy as np

# NOT WORKING

class ConvolutionalLayer:
    def __init__(self, num_filters, filter_size, input_shape):
        self.num_filters = num_filters
        self.filter_size = filter_size
        self.input_shape = input_shape
        self.filters = np.random.randn(num_filters, filter_size, filter_size, input_shape[-1]) / (filter_size * filter_size)
        self.bias = np.zeros((num_filters, 1))

    def iterate_regions(self, image):
        h, w = image.shape[:2]
        for i in range(h - self.filter_size + 1):
            for j in range(w - self.filter_size + 1):
                region = image[i:(i + self.filter_size), j:(j + self.filter_size)]
                yield region, i, j

    def forward(self, input):
        self.last_input = input
        h, w = input.shape[:2]
        output = np.zeros((h - self.filter_size + 1, w - self.filter_size + 1, self.num_filters))

        for region, i, j in self.iterate_regions(input):
            output[i, j] = np.sum(region * self.filters, axis=(1, 2, 3)) + self.bias.flatten()

        return output

    def backward(self, dvalues, learning_rate):
        input_shape = self.last_input.shape
        dfilters = np.zeros_like(self.filters)
        dbias = np.sum(dvalues, axis=(0, 1, 2)).reshape(-1, 1)

        for region, i, j in self.iterate_regions(self.last_input):
            for f in range(self.num_filters):
                dfilters[f] += dvalues[i, j, f] * region

        self.filters -= learning_rate * dfilters
        self.bias -= learning_rate * dbias

        return np.zeros(input_shape)

class MaxPool2:
    def iterate_regions(self, image):
        h, w, _ = image.shape
        new_h = h // 2
        new_w = w // 2

        for i in range(new_h):
            for j in range(new_w):
                region = image[(i * 2):(i * 2 + 2), (j * 2):(j * 2 + 2)]
                yield region, i, j

    def forward(self, input):
        self.last_input = input
        h, w, num_filters = input.shape
        output = np.zeros((h // 2, w // 2, num_filters))

        for region, i, j in self.iterate_regions(input):
            output[i, j] = np.amax(region, axis=(0, 1))

        return output

class FullyConnectedLayer:
    def __init__(self, input_size, output_size):
        self.input_size = input_size
        self.weights = np.random.randn(input_size, output_size) * np.sqrt(2.0 / input_size)
        self.bias = np.zeros(output_size)

    def forward(self, input):
        self.last_input_shape = input.shape
        input = input.flatten()
        self.last_input = input
        output = np.dot(input, self.weights) + self.bias
        return output

    def backward(self, dvalues):
        self.dweights = np.dot(self.last_input.reshape(-1, 1), dvalues.reshape(1, -1))
        self.dbias = dvalues
        return np.dot(dvalues, self.weights.T).reshape(self.last_input_shape)

    def update(self, learning_rate):
        self.weights -= learning_rate * self.dweights
        self.bias -= learning_rate * self.dbias

class CNNModel:
    def __init__(self, input_shape, num_classes):
        self.input_shape = input_shape
        self.num_classes = num_classes
        self.conv_layer1 = ConvolutionalLayer(num_filters=8, filter_size=3, input_shape=input_shape)
        self.pooling_layer1 = MaxPool2()
        self.conv_layer2 = ConvolutionalLayer(num_filters=16, filter_size=3,
                                              input_shape=(input_shape[0] // 2, input_shape[1] // 2, 8))
        self.pooling_layer2 = MaxPool2()
        fc1_input_size = 3136  # Adjusted input size for fully connected layer 1
        self.fc_layer1 = FullyConnectedLayer(input_size=fc1_input_size, output_size=128)
        self.fc_layer2 = FullyConnectedLayer(input_size=128, output_size=num_classes)

    def forward(self, input):
        conv_out1 = self.conv_layer1.forward(input)
        pooled_out1 = self.pooling_layer1.forward(conv_out1)
        conv_out2 = self.conv_layer2.forward(pooled_out1)
        pooled_out2 = self.pooling_layer2.forward(conv_out2)
        # Reshape the output of the pooling layer before passing it to the fully connected layer
        fc1_input = pooled_out2.reshape(-1, pooled_out2.size)
        fc1_out = self.fc_layer1.forward(fc1_input)
        fc2_out = self.fc_layer2.forward(fc1_out)
        return fc2_out

    def backward(self, dvalues, learning_rate):
        fc2_dvalues = self.fc_layer2.backward(dvalues)
        fc1_dvalues = self.fc_layer1.backward(fc2_dvalues)
        # No backward pass for pooling layers
        conv_out2_dvalues = self.conv_layer2.backward(fc1_dvalues, learning_rate)
        conv_out1_dvalues = self.conv_layer1.backward(conv_out2_dvalues, learning_rate)

        self.fc_layer1.update(learning_rate)
        self.fc_layer2.update(learning_rate)

    def train(self, X, y, learning_rate, epochs):
        for epoch in range(epochs):
            for i in range(len(X)):
                input = X[i].reshape(self.input_shape)

                # Forward pass
                output = self.forward(input)

                # Compute loss
                loss = np.mean((output - y[i]) ** 2)

                # Backpropagation
                dvalues = 2 * (output - y[i])
                self.backward(dvalues, learning_rate)

    def predict(self, inputs):
        return np.argmax(self.forward(inputs), axis=1)