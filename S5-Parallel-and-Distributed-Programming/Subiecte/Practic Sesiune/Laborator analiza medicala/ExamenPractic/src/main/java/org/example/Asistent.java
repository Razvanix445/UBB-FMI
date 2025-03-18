package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Asistent implements Runnable {
    private final BlockingQueue<Request> queue;
    private final BlockingQueue<Request> queueInterpretare;
    private volatile boolean isShuttingDown;

    public Asistent(BlockingQueue<Request> queue, BlockingQueue<Request> queueInterpretare, boolean isShuttingDown) {
        this.queue = queue;
        this.queueInterpretare = queueInterpretare;
        this.isShuttingDown = isShuttingDown;
    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter("log_global.txt", true)) {
            while (!isShuttingDown) {
                Request request = queue.poll(100, TimeUnit.MILLISECONDS);
                if (request == null) continue;

                request.setStare(0);
                Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20));
                request.setStare(1);
                if (request.getId_analiza() == 2 || request.getId_analiza() == 4)
                    queueInterpretare.put(request);
                writer.write(request.getId_tichet() + " " + request.getId_pacient() + " " + request.getId_analiza() + " " + request.getStare() + "\n");
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
