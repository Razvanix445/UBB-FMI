package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // Configurari ale sistemului
    private static final int U = 4;           // Numarul de generatoare de cereri
    private static final int Yu = 20;        // Numarul de cereri per generator
    private static final int tu = 30;        // Intervalul intre cereri (ms)
    private static final int R = 4;          // Numarul de bibliotecari
    private static final int B = 3;          // Numarul de departamente
    private static final int XrMin = 120;    // Timp minim procesare resurse (ms)
    private static final int XrMax = 180;    // Timp maxim procesare resurse (ms)
    private static final int Rm = 250;       // Interval monitorizare (ms)
    private static final int Dt = 6000;      // Durata de functionare a sistemului (ms)
    private static volatile boolean isShuttingDown = false;

    public static void main(String[] args) {
        List<Librarian> workerThreads = new ArrayList<>();

        // Coada principala de cereri (Qu)
        BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(50);

        // Cozi pentru departamente (B1, B2, B3)
        BlockingQueue<Request> booksQueue = new ArrayBlockingQueue<>(50);
        BlockingQueue<Request> articlesQueue = new ArrayBlockingQueue<>(50);
        BlockingQueue<Request> magazinesQueue = new ArrayBlockingQueue<>(50);

        // Pornire generatoare de cereri
        long startTime = System.currentTimeMillis();
        long endTime = startTime + Dt;
        for (int i = 0; i < U; i++) {
            new Thread(new RequestGenerator(i, requestQueue, Yu, tu, endTime)).start();
        }

        AtomicInteger completedRequests = new AtomicInteger(0);

        // Pornire bibliotecari
        for (int i = 0; i < R; i++) {
            Librarian librarian = new Librarian(requestQueue, booksQueue, articlesQueue, magazinesQueue, isShuttingDown);
            workerThreads.add(librarian);
            new Thread(librarian).start();
        }

        // Pornire departamente
        Department booksDepartment = new Department("Books", booksQueue, XrMin, XrMax, completedRequests, isShuttingDown);
        new Thread(booksDepartment).start();
        Department articlesDepartment = new Department("Articles", articlesQueue, XrMin, XrMax, completedRequests, isShuttingDown);
        new Thread(articlesDepartment).start();
        Department magazinesDepartment = new Department("Magazines", magazinesQueue, XrMin, XrMax, completedRequests, isShuttingDown);
        new Thread(magazinesDepartment).start();

        // Pornire sistem de monitorizare
        Monitor monitor = new Monitor(requestQueue, booksQueue, articlesQueue, magazinesQueue, Rm, completedRequests);
        long startTimeMonitor = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTimeMonitor < Dt) {
            monitor.displayStatus();
            try {
                Thread.sleep(Rm);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        isShuttingDown = true;
        booksDepartment.shutdown();
        articlesDepartment.shutdown();
        magazinesDepartment.shutdown();

        for (Librarian librarian: workerThreads) {
            librarian.shutdown();
        }

        // Oprire activitate dupa Dt ms
        try {
            Thread.sleep(Dt);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        monitor.stop();
        System.out.println("Sistemul s-a incheiat.");

        Map<Thread, StackTraceElement[]> allThreads = Thread.getAllStackTraces();
        System.out.println("Active threads at the end:");
        for (Thread thread : allThreads.keySet()) {
            System.out.println("Thread: " + thread.getName() + " (State: " + thread.getState() + ")");
        }

    }
}
