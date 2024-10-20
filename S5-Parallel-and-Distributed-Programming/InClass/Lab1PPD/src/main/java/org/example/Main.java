package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello, World!");
        int max = 10000;
        int numarThreaduri = 10;

        ArrayList<Double> vector1 = new ArrayList<Double>();
        initializareListe(vector1, max);

        ArrayList<Double> vector2 = new ArrayList<Double>();
        initializareListe(vector2, max);

        ArrayList<Double> sumaVectori = new ArrayList<Double>();
        initializareListe(sumaVectori, max);

        Long timpIn = System.nanoTime();
        sumaVectoriSecv(vector1, vector2, sumaVectori);
        Long timpOut = System.nanoTime();

        //afiseazaLista(sumaVectori);

        System.out.println("\nTimp de executie: " + (timpOut - timpIn) + " ns");



        ArrayList<Double> sumaVectoriPar = new ArrayList<Double>();
        initializareListe(sumaVectoriPar, max);

        timpIn = System.nanoTime();
        sumaVectoriPar(vector1, vector2, sumaVectoriPar, numarThreaduri);
        timpOut = System.nanoTime();

        //afiseazaLista(sumaVectoriPar);

        System.out.println("\nTimp de executie: " + (timpOut - timpIn) + " ns");

        if (comparaListe(vector1, vector2))
            System.out.println("Listele sunt egale!");
        else
            System.out.println("Listele nu sunt egale!");



        ArrayList<Double> sumaVectoriParCiclic = new ArrayList<Double>();
        initializareListe(sumaVectoriParCiclic, max);

        timpIn = System.nanoTime();
        sumaVectoriParCiclic(vector1, vector2, sumaVectoriParCiclic, numarThreaduri);
        timpOut = System.nanoTime();

        //afiseazaLista(sumaVectoriParCiclic);

        System.out.println("\nTimp de executie: " + (timpOut - timpIn) + " ns");

        if (comparaListe(vector1, vector2))
            System.out.println("Listele sunt egale!");
        else
            System.out.println("Listele nu sunt egale!");
    }

    public static boolean comparaListe(ArrayList<Double> vector1, ArrayList<Double> vector2) {
        for (int i = 0; i < vector1.size(); i++) {
            if (!Objects.equals(vector1.get(i), vector2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static void initializareListe(ArrayList<Double> vector, int max) {
        for (int i = 0; i < max; i++) {
            vector.add((double) i);
        }
    }

    public static void sumaVectoriSecv(ArrayList<Double> vector1, ArrayList<Double> vector2, ArrayList<Double> sumaVectori) {
        for (int i = 0; i < vector1.size(); i++) {
            sumaVectori.set(i, Math.sqrt(Math.pow(vector1.get(i), 3) + Math.pow(vector2.get(i), 3)));
        }
    }

    public static void sumaVectoriPar(ArrayList<Double> vector1, ArrayList<Double> vector2, ArrayList<Double> sumaVectori, int numarThreaduri) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();

        int lungime = sumaVectori.size();
        int start = 0;
        int end = 0;
        int pasi = lungime / numarThreaduri;
        int rest = lungime % numarThreaduri;

        while (start < lungime) {
            if (rest > 0) {
                end = start + pasi + 1;
                rest --;
            }
            else {
                 end = start + pasi;
            }
            Thread thread = new AddThread(vector1, vector2, sumaVectori, start, end);
            threads.add(thread);
            thread.start();
            start = end;
        }

        for (Thread thread: threads)
            thread.join();
    }

    public static void sumaVectoriParCiclic(ArrayList<Double> vector1, ArrayList<Double> vector2, ArrayList<Double> sumaVectori, int numarThreaduri) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();

        int lungime = sumaVectori.size();
        int start = 0;

        while (start < numarThreaduri) {
            Thread thread = new AddThreadCiclic(vector1, vector2, sumaVectori, start, numarThreaduri);
            threads.add(thread);
            thread.start();
            start += 1;
        }

        for (Thread thread: threads)
            thread.join();
    }

    public static void afiseazaLista(ArrayList<Double> sumaVectori) {
        for (int i = 0; i < sumaVectori.size(); i++) {
            System.out.print(sumaVectori.get(i) + " ");
        }
    }

}

class AddThread extends Thread {
    private ArrayList<Double> vector1;
    private ArrayList<Double> vector2;
    private ArrayList<Double> sumaVectori;
    private int start;
    private int end;

    public AddThread(ArrayList<Double> vector1, ArrayList<Double> vector2, ArrayList<Double> sumaVectori, int start, int end) {
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.sumaVectori = sumaVectori;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            sumaVectori.set(i, Math.sqrt(Math.pow(vector1.get(i), 3) + Math.pow(vector2.get(i), 3)));
        }
    }
}


class AddThreadCiclic extends Thread {
    private ArrayList<Double> vector1;
    private ArrayList<Double> vector2;
    private ArrayList<Double> sumaVectori;
    private int start;
    private int pas;

    public AddThreadCiclic(ArrayList<Double> vector1, ArrayList<Double> vector2, ArrayList<Double> sumaVectori, int start, int pas) {
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.sumaVectori = sumaVectori;
        this.start = start;
        this.pas = pas;
    }

    @Override
    public void run() {
        int lungime = sumaVectori.size();
        for (int i = start; i < lungime; i += pas) {
            sumaVectori.set(i, Math.sqrt(Math.pow(vector1.get(i), 3) + Math.pow(vector2.get(i), 3)));
        }
    }
}