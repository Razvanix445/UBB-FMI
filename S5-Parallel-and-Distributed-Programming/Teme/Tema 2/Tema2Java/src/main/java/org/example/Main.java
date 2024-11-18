package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    private static void writeToFile(int[][] resultMatrix, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // writer.write("\nResult matrix for " + method + " with N = " + N + ", M = " + M + ", n = " + n + " and p = " + p + "\n");
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    writer.write(resultMatrix[row][col] + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occured while writing to the file.");
            e.printStackTrace();
        }
    }

    public static boolean compareMatrices(String file1, String file2) throws FileNotFoundException {
        try (Scanner scanner1 = new Scanner(new File(file1));
             Scanner scanner2 = new Scanner(new File(file2))) {

            int rowNumber = 0;

            while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
                rowNumber++;

                String[] line1 = scanner1.nextLine().trim().split("\\s+");
                String[] line2 = scanner2.nextLine().trim().split("\\s+");

                if (line1.length != line2.length) {
                    System.out.println("Mismatch in matrix dimensions at row " + rowNumber);
                    // return false;
                }

                for (int col = 0; col < line1.length; col++) {
                    if (!line1[col].equals(line2[col])) {
                        System.out.println("Mismatch at row " + rowNumber + ", column " + (col + 1) +
                                ": value in file1 = " + line1[col] + ", value in file2 = " + line2[col]);
                        return false;
                    }
                }
            }

            if (scanner1.hasNextLine() || scanner2.hasNextLine()) {
                System.out.println("Mismatch in matrix dimensions after row " + rowNumber);
                return false;
            }
        }

        System.out.println("The matrices are identical");
        return true;
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
        try (FileWriter writer = new FileWriter("notSpecified.txt")) {
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

    private static void computeSequential(String inputFile, String comparationMatrixFile) throws FileNotFoundException {
        Scanner data = new Scanner(new File(inputFile));
        int[][] resultMatrix;

        readValues(data);
        readOriginalMatrix(data);
        readConvolutionMatrix(data);

        long inTime = System.nanoTime();

        Sequential sequentialSolve = new Sequential(N, M, n, matrix, convolutionMatrix);
        resultMatrix = sequentialSolve.run();

        long outTime = System.nanoTime();
        System.out.println("\nExecution Time: " + (outTime - inTime) + " ns");

        writeToFile(resultMatrix, "outputSequential.txt");

        try {
            boolean areEqual = compareMatrices(comparationMatrixFile, "outputSequential.txt");
            String resultMessage = areEqual ? "Matrices are equal!" : "Matrices are NOT equal!";
            System.out.println(resultMessage);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static void computeHorizontalThreads(String inputFile, String comparationMatrixFile) throws InterruptedException, FileNotFoundException {
        Scanner data = new Scanner(new File(inputFile));
        int[][] resultMatrix;

        readValues(data);
        readOriginalMatrix(data);
        readConvolutionMatrix(data);

        long inTime = System.nanoTime();

        HorizontalAllocatedThreads horizontalSolve = new HorizontalAllocatedThreads(N, M, n, p, matrix, convolutionMatrix);
        resultMatrix = horizontalSolve.run();

        long outTime = System.nanoTime();
        System.out.println("\nExecution Time: " + (outTime - inTime) + " ns");

        writeToFile(resultMatrix, "outputHorizontal.txt");

        try {
            boolean areEqual = compareMatrices(comparationMatrixFile, "outputHorizontal.txt");
            String resultMessage = areEqual ? "Matrices are equal!" : "Matrices are NOT equal!";
            System.out.println(resultMessage);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        // generateMatrices(N, M, n);

        if (args.length < 1) {
            System.out.println("Error: Please provide the number of threads and the input file name.");
            return;
        }

        try {
            p = Integer.parseInt(args[0]); // Number of threads
        } catch (NumberFormatException e) {
            System.out.println("Error: The number of threads must be an integer.");
            return;
        }



        // => RUNNING PROGRAM ===================================================================
        String case1 = "N10M10n3.txt";
        String case2 = "N1000M1000n5.txt";
        String case3 = "N10000M10000n5.txt";

        String output1 = "outputN10M10.txt";
        String output2 = "outputN1000M1000.txt";
        String output3 = "outputN10000M10000.txt";

        computeSequential(case1, output1);
        // computeHorizontalThreads(case1, output1);
        // <= END ===============================================================================
    }
}