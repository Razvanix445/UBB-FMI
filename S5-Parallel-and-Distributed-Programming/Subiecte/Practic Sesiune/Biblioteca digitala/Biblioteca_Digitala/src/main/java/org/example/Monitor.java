package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Monitor {
    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Request> booksQueue;
    private final BlockingQueue<Request> articlesQueue;
    private final BlockingQueue<Request> magazinesQueue;
    private final int Rm;
    private volatile boolean running = true;
    private AtomicInteger completedRequests;

    public Monitor(BlockingQueue<Request> requestQueue,
                   BlockingQueue<Request> booksQueue,
                   BlockingQueue<Request> articlesQueue,
                   BlockingQueue<Request> magazinesQueue, int Rm, AtomicInteger completedRequests) {
        this.requestQueue = requestQueue;
        this.booksQueue = booksQueue;
        this.articlesQueue = articlesQueue;
        this.magazinesQueue = magazinesQueue;
        this.Rm = Rm;
        this.completedRequests = completedRequests;
    }

    public void displayStatus() {
        try {
            int inAsteptare = 0;
            int inProcesare = 0;
            int finalizate = 0;

            // Coada principala
            for (Request request : requestQueue) {
                switch (request.getStatus()) {
                    case 0 -> inAsteptare++;
                    case 1 -> inProcesare++;
                }
            }

            // Coada pentru carti
            for (Request request : booksQueue) {
                switch (request.getStatus()) {
                    case 0 -> inAsteptare++;
                    case 1 -> inProcesare++;
                }
            }

            // Coada pentru articole
            for (Request request : articlesQueue) {
                switch (request.getStatus()) {
                    case 0 -> inAsteptare++;
                    case 1 -> inProcesare++;
                }
            }

            // Coada pentru reviste
            for (Request request : magazinesQueue) {
                switch (request.getStatus()) {
                    case 0 -> inAsteptare++;
                    case 1 -> inProcesare++;
                }
            }

            // Afisam informatiile de monitorizare
            System.out.println("Monitorizare:");
            System.out.println(" - Cereri in asteptare: " + inAsteptare);
            System.out.println(" - Cereri in procesare: " + inProcesare);
            System.out.println(" - Cereri finalizate: " + completedRequests.get());
        } catch (Exception e) {
            System.err.println("Eroare in monitorizare: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
