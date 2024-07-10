package ir.map.g222.sem7demo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {

    private String prenume;
    private String nume;
    private List<Utilizator> prieteni = new ArrayList<>();

    public Utilizator(String prenume, String nume) {
        this.prenume = prenume;
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void adaugaPrieten(Utilizator prieten) {
        this.prieteni.add(prieten);
    }

    public void stergePrieten(Utilizator prieten) {
        this.prieteni.remove(prieten);
    }

    public List<Utilizator> getPrieteni() {
        return this.prieteni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getPrenume().equals(that.getPrenume()) &&
                getNume().equals(that.getNume()) &&
                getPrieteni().equals(that.getPrieteni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getPrenume(), getPrieteni());
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", prienteni=" + prieteni +
                '}';
    }
}
