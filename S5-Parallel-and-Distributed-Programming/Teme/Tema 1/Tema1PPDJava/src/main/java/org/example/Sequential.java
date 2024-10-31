package org.example;

public class Sequential {
    int N, M, n;
    int[][] matrix;
    int[][] convolutionMatrix;

    public Sequential(int N, int M, int n, int[][] matrix, int[][] convolutionMatrix) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.matrix = matrix;
        this.convolutionMatrix = convolutionMatrix;
    }

    public int[][] run() {
        int[][] resultMatrix = new int[N][M];
        for (int row = (n / 2); row < N + (n / 2); row++)
            for (int col = (n / 2); col < M + (n / 2); col++) {
                int sum = 0;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        sum += matrix[row - (n / 2) + i][col - (n / 2) + j] * convolutionMatrix[i][j];
                resultMatrix[row - (n / 2)][col - (n / 2)] = sum;
            }

        return resultMatrix;
    }
}
