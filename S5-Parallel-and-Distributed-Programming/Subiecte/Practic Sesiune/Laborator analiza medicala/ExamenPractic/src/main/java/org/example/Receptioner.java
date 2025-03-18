package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Receptioner implements Runnable {

    BlockingQueue<Request> analizeQueue;
    BlockingQueue<Request> radiografiiQueue;
    BlockingQueue<Request> testeQueue;
    BlockingQueue<Request> ecografiiQueue;

    public Receptioner(BlockingQueue<Request> analizeQueue, BlockingQueue<Request> radiografiiQueue, BlockingQueue<Request> testeQueue, BlockingQueue<Request> ecografiiQueue) {
        this.analizeQueue = analizeQueue;
        this.radiografiiQueue = radiografiiQueue;
        this.testeQueue = testeQueue;
        this.ecografiiQueue = ecografiiQueue;
    }

    @Override
    public void run() {
        String filePath = "C:/Users/razva/IdeaProjects/PPD/ExamenPractic/cereri.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    line = line.strip();
                    String[] parts = line.split(" ");
                    int id_tichet = Integer.parseInt(parts[0]);
                    int id_pacient = Integer.parseInt(parts[1]);
                    int id_analiza = Integer.parseInt(parts[2]);
                    Request request = new Request(id_tichet, id_pacient, id_analiza, -1);

                    try (FileWriter writer = new FileWriter("logs.txt", true)) {
                        switch (request.getId_analiza()) {
                            case 1 ->  {
                                if (analizeQueue.size() < 10)
                                    analizeQueue.put(request);
                                else {
                                    writer.write("Cererea " + request.getId_tichet() + " a fost refuzata (coada plina) " + LocalDateTime.now() + "\n");
                                    writer.flush();
                                }
                            }
                            case 2 -> {
                                if (radiografiiQueue.size() < 7)
                                    radiografiiQueue.put(request);
                                else {
                                    writer.write("Cererea " + request.getId_tichet() + " a fost refuzata (coada plina) " + LocalDateTime.now() + "\n");
                                    writer.flush();
                                }
                            }
                            case 3 -> {
                                if (testeQueue.size() < 6)
                                    testeQueue.put(request);
                                else {
                                    writer.write("Cererea " + request.getId_tichet() + " a fost refuzata (coada plina) " + LocalDateTime.now() + "\n");
                                    writer.flush();
                                }
                            }
                            case 4 -> {
                                if (ecografiiQueue.size() < 8)
                                    ecografiiQueue.put(request);
                                else {
                                    writer.write("Cererea " + request.getId_tichet() + " a fost refuzata (coada plina) " + LocalDateTime.now() + "\n");
                                    writer.flush();
                                }
                            }
                        }
                    } catch (InterruptedException | IOException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
