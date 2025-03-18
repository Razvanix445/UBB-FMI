package org.example;

import java.sql.SQLOutput;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class RequestGenerator implements Runnable {
    private final int generatorId;
    private final BlockingQueue<Request> requestQueue;
    private final int numRequests;
    private final int interval;
    private final long endTime;

    public RequestGenerator(int generatorId, BlockingQueue<Request> requestQueue, int numRequests, int interval, long endTime) {
        this.generatorId = generatorId;
        this.requestQueue = requestQueue;
        this.numRequests = numRequests;
        this.interval = interval;
        this.endTime = endTime;
    }

    @Override
    public void run() {
        for (int i = 0; i < numRequests; i++) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("Generator " + generatorId + " se opreste (timp expirat).");
                break;
            }
            try {
                int resourceType = ThreadLocalRandom.current().nextInt(1, 4); // Tip resursa: 1, 2, sau 3
                Request request = new Request(generatorId * 100 + i, resourceType);
                requestQueue.put(request);
                System.out.println("Generator " + generatorId + " a produs: " + request.getId() + " (Tip: " + resourceType + ")");
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
