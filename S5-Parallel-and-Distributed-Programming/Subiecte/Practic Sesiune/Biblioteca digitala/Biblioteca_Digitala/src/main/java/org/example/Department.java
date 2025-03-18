package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Department implements Runnable {
    private final String name;
    private final BlockingQueue<Request> departmentQueue;
    private final int minProcessingTime;
    private final int maxProcessingTime;
    private volatile boolean isShuttingDown;
    private AtomicInteger completedRequests;

    public Department(String name, BlockingQueue<Request> departmentQueue, int minProcessingTime, int maxProcessingTime, AtomicInteger completedRequests, boolean isShuttingDown) {
        this.name = name;
        this.departmentQueue = departmentQueue;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.completedRequests = completedRequests;
        this.isShuttingDown = isShuttingDown;
    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter("output.txt", true)) {
            while (!isShuttingDown || !departmentQueue.isEmpty()) {
                Request request = departmentQueue.poll(100, TimeUnit.MILLISECONDS);
                if (request == null) continue;

                request.setStatus(1);
                System.out.println(name + " proceseaza cererea: " + request.getId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(minProcessingTime, maxProcessingTime + 1));
                request.setStatus(2);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                request.setTimestamp(LocalDateTime.now().format(formatter));
                completedRequests.incrementAndGet();
                writer.write("Cererea " + request.getId() + " procesata la " + LocalDateTime.now() + "\n");
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
