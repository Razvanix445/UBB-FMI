package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static int M1 = 10;
    private static int M2 = 7;
    private static int M3 = 6;
    private static int M4 = 8;
    private static int T_sange = 10;
    private static int T_radiografie = 20;
    private static int T_teste_genetice = 30;
    private static int T_ecografie = 25;
    private static int T_interpretare = 15;
    private static int N1 = 3;
    private static int N2 = 2;
    private static int N3 = 2;
    private static int N4 = 3;
    private static int MI = 2;

    public static void main(String[] args) {
        List<Receptioner> workerThreads = new ArrayList<>();

        BlockingQueue<Request> analizeQueue = new ArrayBlockingQueue<>(M1);
        BlockingQueue<Request> radiografiiQueue = new ArrayBlockingQueue<>(M2);
        BlockingQueue<Request> testeQueue = new ArrayBlockingQueue<>(M3);
        BlockingQueue<Request> ecografiiQueue = new ArrayBlockingQueue<>(M4);

        BlockingQueue<Request> interpretareQueue = new ArrayBlockingQueue<>(1000);

        // Generare date
        GenerateInput.generate();

        // Pornire receptioneri
        Receptioner receptioner1 = new Receptioner(analizeQueue, radiografiiQueue, testeQueue, ecografiiQueue);
        new Thread(receptioner1).start();
        Receptioner receptioner2 = new Receptioner(analizeQueue, radiografiiQueue, testeQueue, ecografiiQueue);
        new Thread(receptioner2).start();

        // Pornire asistenti
        Asistent asistent1 = new Asistent(analizeQueue, interpretareQueue, false);
        new Thread(asistent1).start();
        Asistent asistent2 = new Asistent(radiografiiQueue, interpretareQueue, false);
        new Thread(asistent2).start();
        Asistent asistent3 = new Asistent(testeQueue, interpretareQueue, false);
        new Thread(asistent3).start();
        Asistent asistent4 = new Asistent(ecografiiQueue, interpretareQueue, false);
        new Thread(asistent4).start();

        Medic medic1 = new Medic(interpretareQueue);
        new Thread(medic1).start();
        Medic medic2 = new Medic(interpretareQueue);
        new Thread(medic2).start();

        Administrator administrator = new Administrator(analizeQueue, radiografiiQueue, testeQueue, ecografiiQueue, true);
        new Thread(administrator).start();

        asistent1.shutdown();
        asistent2.shutdown();
        asistent3.shutdown();
        asistent4.shutdown();

        medic1.shutdown();
        medic2.shutdown();

        administrator.stop();
    }
}