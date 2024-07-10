package ir.map.g222.sem7demo.domain;

import java.time.LocalDateTime;

public class UtilizatorDatePair {
    private Utilizator utilizator;
    private LocalDateTime date;

    public UtilizatorDatePair(Utilizator utilizator, LocalDateTime date) {
        this.utilizator = utilizator;
        this.date = date;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
