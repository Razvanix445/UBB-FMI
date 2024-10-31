package org.example;

public class HorizontalAllocatedThreads {
    int N, M, n, p;
    int[][] matrix;
    int[][] convolutionMatrix;
    int[][] resultMatrix;

    public HorizontalAllocatedThreads(int N, int M, int n, int p, int[][] matrix, int[][] convolutionMatrix) {
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

        int rowsPerThread = N / p;
        int remainingRows = N % p;

        int startingRow = 0;

        for (int threadNo = 0; threadNo < p; threadNo++) {
            int endingRow = startingRow + rowsPerThread;
            if (remainingRows > 0) {
                endingRow++;
                remainingRows--;
            }

            threads[threadNo] = new HorizontalThread(startingRow, endingRow);
            threads[threadNo].start();
            startingRow = endingRow;
        }

        for (int threadNo = 0; threadNo < p; threadNo++) {
            threads[threadNo].join();
        }

        return resultMatrix;
    }

    class HorizontalThread extends Thread {
        final private int start, end;

        public HorizontalThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            for (int row = start; row < end; row++) {
                for (int col = (n / 2); col < M + (n / 2); col++) {
                    int resultRow = row;
                    int resultCol = col - (n / 2);

                    if (resultRow >= 0 && resultRow < N && resultCol >= 0 && resultCol < M) {
                        int sum = 0;

                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < n; j++) {
                                int matrixRow = row - (n / 2) + i;
                                int matrixCol = col - (n / 2) + j;

                                if (matrixRow >= 0 && matrixRow < N && matrixCol >= 0 && matrixCol < M) {
                                    sum += matrix[matrixRow][matrixCol] * convolutionMatrix[i][j];
                                }
                            }
                        }
                        resultMatrix[resultRow][resultCol] = sum;
                    }
                }
            }
        }
    }
}
