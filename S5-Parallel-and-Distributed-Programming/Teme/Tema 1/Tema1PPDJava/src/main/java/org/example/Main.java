package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {

    private static int N, M, n, p;
    private static int[][] matrix;
    private static int[][] convolutionMatrix;

    private static void readValues(Scanner data) {
        N = data.nextInt();
        M = data.nextInt();
        n = data.nextInt();
        p = data.nextInt();
    }

    private static void readOriginalMatrix(Scanner data) {
        matrix = new int[N][M];
        for (int row = 0; row < N; row++)
            for (int col = 0; col < M; col++)
                matrix[row][col] = data.nextInt();
    }

    private static void readConvolutionMatrix(Scanner data) {
        convolutionMatrix = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                convolutionMatrix[row][col] = data.nextInt();
    }

    private static void writeToFile(int[][] resultMatrix, String method) {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write("\nResult matrix for " + method + " with N = " + N + ", M = " + M + ", n = " + n + " and p = " + p + "\n");
            for (int[] row: resultMatrix) {
                for (int value: row)
                    writer.write(value + " ");
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occured while writing to the file.");
            e.printStackTrace();
        }
    }

    private static void generateMatrices(int N, int M, int n) {
        Random random = new Random();

        matrix = new int[N][M];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                matrix[row][col] = random.nextInt(21) + 40;
            }
        }

        // Generating the convolution matrix
        convolutionMatrix = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                convolutionMatrix[row][col] = random.nextInt(2);
            }
        }

        // Writing data to the file "date.txt"
        try (FileWriter writer = new FileWriter("N10000M10000n5.txt")) {
            writer.write(N + "\n" + M + "\n" + n + "\n" + p + "\n");

            // Writing original matrix
            for (int[] row : matrix) {
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.write("\n");
            }

            // Writing convolution matrix
            for (int[] row : convolutionMatrix) {
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.write("\n");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    private static int[][] borderMatrix() {
        int[][] borderedMatrix = new int[N + n - 1][M + n - 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                borderedMatrix[i + n / 2][j + n / 2] = matrix[i][j];
            }
        }

        for (int j = 0; j < M; j++) {
            for (int i = 0; i < n / 2; i++) {
                borderedMatrix[i][j + n / 2] = matrix[0][j];
                borderedMatrix[N + i + n / 2][j + n / 2] = matrix[N - 1][j];
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < n / 2; j++) {
                borderedMatrix[i + n / 2][j] = matrix[i][0];
                borderedMatrix[i + n / 2][M + j + n / 2] = matrix[i][M - 1];
            }
        }

        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                borderedMatrix[i][j] = matrix[0][0];
                borderedMatrix[i][M + j + n / 2] = matrix[0][M - 1];
                borderedMatrix[N + i + n / 2][j] = matrix[N - 1][0];
                borderedMatrix[N + i + n / 2][M + j + n / 2] = matrix[N - 1][M - 1];
            }
        }

        return borderedMatrix;
    }


    private static int[][] computeSequential(int[][] borderedMatrix) {
        int[][] resultMatrix;
        long inTime = System.nanoTime();

        Sequential sequentialSolve = new Sequential(N, M, n, borderedMatrix, convolutionMatrix);
        resultMatrix = sequentialSolve.run();
        writeToFile(resultMatrix, "sequential");

        long outTime = System.nanoTime();
        System.out.println("\nExecution Time: " + (outTime - inTime) + " ns");

        return resultMatrix;
    }

    private static int[][] computeVerticalThreads(int[][] borderedMatrix) throws InterruptedException {
        int[][] resultMatrix;
        long inTime = System.nanoTime();

        VerticalAllocatedThreads verticalSolve = new VerticalAllocatedThreads(N, M, n, p, borderedMatrix, convolutionMatrix);
        resultMatrix = verticalSolve.run();
        // writeToFile(resultMatrix, "vertical");

        long outTime = System.nanoTime();
        System.out.println("\nExecution Time: " + (outTime - inTime) + " ns");

        return resultMatrix;
    }

    private static int[][] computeHorizontalThreads(int[][] borderedMatrix) throws InterruptedException {
        int[][] resultMatrix;
        long inTime = System.nanoTime();

        HorizontalAllocatedThreads horizontalSolve = new HorizontalAllocatedThreads(N, M, n, p, borderedMatrix, convolutionMatrix);
        resultMatrix = horizontalSolve.run();
        // writeToFile(resultMatrix, "horizontal");

        long outTime = System.nanoTime();
        System.out.println("\nExecution Time: " + (outTime - inTime) + " ns");

        return resultMatrix;
    }

    private static void run() throws FileNotFoundException, InterruptedException {
        Scanner data = new Scanner(new File("N10000M10000n5.txt"));
        int[][] resultMatrix;

        readValues(data);
        readOriginalMatrix(data);
        readConvolutionMatrix(data);

        int[][] borderedMatrix = borderMatrix();
        // writeToFile(borderedMatrix, "sequential");

        // resultMatrix = computeSequential(borderedMatrix);

        resultMatrix = computeVerticalThreads(borderedMatrix);

        // resultMatrix = computeHorizontalThreads(borderedMatrix);
        //writeToFile(resultMatrix, "horizontal");
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        N = 10000;
        M = 10000;
        n = 5;

        p = Integer.parseInt(args[0]);

        // generateMatrices(N, M, n);

        run();
    }
}