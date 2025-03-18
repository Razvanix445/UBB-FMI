package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Medic implements Runnable {
    private final BlockingQueue<Request> queueInterpretare;
    private volatile boolean isShuttingDown;

    public Medic(BlockingQueue<Request> queueInterpretare) {
        this.queueInterpretare = queueInterpretare;
        this.isShuttingDown = false;
    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter("log_global.txt", true)) {
            while (!isShuttingDown) {
                Request request = queueInterpretare.poll(100, TimeUnit.MILLISECONDS);
                if (request == null) continue;

                Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20));
                request.setStare(2);
                writer.write(request.getId_tichet() + " " + request.getId_pacient() + " " + request.getId_analiza() + " " + "Rezultat interpretat de medic" + "\n");
                writer.flush();
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        isShuttingDown = true;
    }
}