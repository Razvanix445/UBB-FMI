package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Administrator implements Runnable {
    private final BlockingQueue<Request> analizeQueue;
    private final BlockingQueue<Request> radiografiiQueue;
    private final BlockingQueue<Request> testeQueue;
    private final BlockingQueue<Request> ecografiiQueue;
    private String timestamp;
    private boolean running = false;

    public Administrator(BlockingQueue<Request> analizeQueue,
                   BlockingQueue<Request> radiografiiQueue,
                   BlockingQueue<Request> testeQueue,
                   BlockingQueue<Request> ecografiiQueue, boolean running) {
        this.analizeQueue = analizeQueue;
        this.radiografiiQueue = radiografiiQueue;
        this.testeQueue = testeQueue;
        this.ecografiiQueue = ecografiiQueue;
        this.running = running;
    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter("log_administrator.txt", true)) {
            while (running) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 200));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                timestamp = LocalDateTime.now().format(formatter);
                int analize = 10 - analizeQueue.remainingCapacity();
                int radiografii = 7 - radiografiiQueue.remainingCapacity();
                int teste = 6 - testeQueue.remainingCapacity();
                int ecografii = 8 - ecografiiQueue.remainingCapacity();
                writer.write(timestamp + " " + analize + " " + radiografii + " " + teste + " " + ecografii + "\n");
                writer.flush();
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
    }
}
