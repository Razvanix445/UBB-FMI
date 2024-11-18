package org.example;

public class VerticalAllocatedThreads {
    int N, M, n, p;
    int[][] matrix;
    int[][] convolutionMatrix;
    int[][] resultMatrix;

    public VerticalAllocatedThreads(int N, int M, int n, int p, int[][] matrix, int[][] convolutionMatrix) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.p = p;
        this.matrix = matrix;
        this.convolutionMatrix = convolutionMatrix;
    }

    public int[][] run() throws InterruptedException {
        resultMatrix = new int[N][M];
        Thread[] threads = new Thread[p];

        int columnsPerThread = M / p;
        int remainingColumns = M % p;

        int startingColumn = 0;

        for (int threadNo = 0; threadNo < p; threadNo++) {
            int endingColumn = startingColumn + columnsPerThread;
            if (remainingColumns > 0) {
                endingColumn++;
                remainingColumns--;
            }

            threads[threadNo] = new VerticalThread(startingColumn, endingColumn);
            threads[threadNo].start();
            startingColumn = endingColumn;
        }

        for (int threadNo = 0; threadNo < p; threadNo++) {
            threads[threadNo].join();
        }

        return resultMatrix;
    }

    class VerticalThread extends Thread {
        final private int start, end;

        public VerticalThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            for (int row = (n / 2); row < N + (n / 2); row++)
                for (int col = (n / 2); col < M + (n / 2); col++) {
                    int sum = 0;
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            sum += matrix[row - (n / 2) + i][col - (n / 2) + j] * convolutionMatrix[i][j];
                    resultMatrix[row - (n / 2)][col - (n / 2)] = sum;
                }
        }

    }
}
