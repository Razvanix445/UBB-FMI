package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

// id_tichet, id_pacient, id_analiza
public class GenerateInput {
    static Random random = new Random();

    public static void generate() {
        String filePath = "C:/Users/razva/IdeaProjects/PPD/ExamenPractic/cereri.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int id_tichet = 0; id_tichet < 50; id_tichet++) {
                int id_pacient = random.nextInt(100);
                int id_analiza = random.nextInt(1, 5);
                Request request = new Request(id_tichet, id_pacient, id_analiza, -1);
                writer.write(id_tichet + " " + id_pacient + " " + id_analiza);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}