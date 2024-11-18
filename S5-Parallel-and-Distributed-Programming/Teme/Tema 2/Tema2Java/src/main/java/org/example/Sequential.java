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
        int[] prevRow = new int[M];
        int[] currentResultRow = new int[M];
        int[] nextRow = new int[M];

        System.arraycopy(matrix[0], 0, prevRow, 0, M);

        for (int row = 0; row < N; row++) {
            if (row < N - 1) {
                System.arraycopy(matrix[row + 1], 0, nextRow, 0, M);
            } else {
                System.arraycopy(matrix[row], 0, nextRow, 0, M);
            }

            for (int col = 0; col < M; col++) {
                int convolutionSum = 0;

                for (int convRow = 0; convRow < n; convRow++) {
                    for (int convCol = 0; convCol < n; convCol++) {
                        int neighborRow = row - n / 2 + convRow;
                        int neighborCol = col - n / 2 + convCol;

                        if (neighborRow < 0) neighborRow = 0;
                        if (neighborCol < 0) neighborCol = 0;
                        if (neighborRow >= N) neighborRow = N - 1;
                        if (neighborCol >= M) neighborCol = M - 1;

                        if (neighborRow == row - 1) {
                            convolutionSum += prevRow[neighborCol] * convolutionMatrix[convRow][convCol];
                        } else if (neighborRow >= row + 1) {
                            convolutionSum += nextRow[neighborCol] * convolutionMatrix[convRow][convCol];
                        } else {
                            convolutionSum += matrix[neighborRow][neighborCol] * convolutionMatrix[convRow][convCol];
                        }
                    }
                }
                currentResultRow[col] = convolutionSum;
            }
            System.arraycopy(matrix[row], 0, prevRow, 0, M);
            System.arraycopy(currentResultRow, 0, matrix[row], 0, M);
        }

        return matrix;
    }
}