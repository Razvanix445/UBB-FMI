package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Librarian implements Runnable {
    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Request> booksQueue;
    private final BlockingQueue<Request> articlesQueue;
    private final BlockingQueue<Request> magazinesQueue;
    private volatile boolean isShuttingDown;

    public Librarian(BlockingQueue<Request> requestQueue,
                     BlockingQueue<Request> booksQueue,
                     BlockingQueue<Request> articlesQueue,
                     BlockingQueue<Request> magazinesQueue, boolean isShuttingDown) {
        this.requestQueue = requestQueue;
        this.booksQueue = booksQueue;
        this.articlesQueue = articlesQueue;
        this.magazinesQueue = magazinesQueue;
        this.isShuttingDown = isShuttingDown;
    }

    @Override
    public void run() {
        while (!isShuttingDown || !requestQueue.isEmpty()) {
            try {
                Request request = requestQueue.poll(100, TimeUnit.MILLISECONDS);
                if (request == null) continue;

                System.out.println("Bibliotecarul a preluat cererea: " + request.getId());
                request.setStatus(0);
                switch (request.getResourceType()) {
                    case 1 -> booksQueue.put(request);
                    case 2 -> articlesQueue.put(request);
                    case 3 -> magazinesQueue.put(request);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void shutdown() {
        isShuttingDown = true;
    }
}
