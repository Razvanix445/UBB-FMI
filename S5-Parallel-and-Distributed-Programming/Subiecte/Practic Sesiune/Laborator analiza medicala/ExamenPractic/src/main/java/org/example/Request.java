package org.example;

public class Request {
    private final int id_tichet;
    private final int id_pacient;
    private final int id_analiza;
    private int stare;

    public Request(int id_tichet, int id_pacient, int id_analiza, int stare) {
        this.id_tichet = id_tichet;
        this.id_pacient = id_pacient;
        this.id_analiza = id_analiza;
        this.stare = stare;

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public int getId_tichet() {
        return id_tichet;
    }

    public int getId_pacient() {
        return id_pacient;
    }

    public int getId_analiza() {
        return id_analiza;
    }

    public int getStare() {
        return stare;
    }

    public void setStare(int stare) {
        this.stare = stare;
    }
}
