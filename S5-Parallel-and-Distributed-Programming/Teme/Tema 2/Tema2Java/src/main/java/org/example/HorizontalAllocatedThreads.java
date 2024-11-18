package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HorizontalAllocatedThreads {

    int N, M, n, p;
    int[][] matrix;
    int[][] convolutionMatrix;
    CyclicBarrier barrier;

    public HorizontalAllocatedThreads(int N, int M, int n, int p, int[][] matrix, int[][] convolutionMatrix) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.p = p;
        this.matrix = matrix;
        this.convolutionMatrix = convolutionMatrix;
        this.barrier = new CyclicBarrier(p);
    }

    public int[][] run() throws InterruptedException {
        Thread[] threads = new Thread[p];

        int rowsPerThread = N / p;
        int remainingRows = N % p;

        int currentStartRow = 0;

        for (int threadNo = 0; threadNo < p; threadNo++) {
            int currentEndRow = currentStartRow + rowsPerThread + (remainingRows > 0 ? 1 : 0);
            remainingRows--;

            ConvolutionTask task = new ConvolutionTask(currentStartRow, currentEndRow, barrier);
            threads[threadNo] = new Thread(task);
            threads[threadNo].start();

            currentStartRow = currentEndRow;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return matrix;
    }

    private class ConvolutionTask implements Runnable {
        int startRow, endRow;
        CyclicBarrier barrier;

        public ConvolutionTask(int startRow, int endRow, CyclicBarrier barrier) {
            this.startRow = startRow;
            this.endRow = endRow;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            int[] prevRow = new int[M];
            int[] currentResultRow = new int[M];
            int[] nextRow = new int[M];

            if (startRow > 0) {
                System.arraycopy(matrix[startRow - 1], 0, prevRow, 0, M);
            } else {
                System.arraycopy(matrix[startRow], 0, prevRow, 0, M);
            }

            if (endRow < N) {
                System.arraycopy(matrix[endRow], 0, nextRow, 0, M);
            }

            try {
                barrier.await();

                for (int row = startRow; row < endRow; row++) {
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
                                } else if (neighborRow >= endRow) {
                                    convolutionSum += nextRow[neighborCol] * convolutionMatrix[convRow][convCol];
                                } else {
                                    convolutionSum += matrix[neighborRow][neighborCol] * convolutionMatrix[convRow][convCol];
                                }
                            }
                        }
                        currentResultRow[col] = convolutionSum;
                    }
                    if (row < N - 1) {
                        System.arraycopy(matrix[row], 0, prevRow, 0, M);
                    }
                    System.arraycopy(currentResultRow, 0, matrix[row], 0, M);
                }

            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}