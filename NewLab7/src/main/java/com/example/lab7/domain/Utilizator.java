package com.example.lab7.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {

    private String prenume;
    private String nume;
    private String username;
    private String password;
    private String email;
    private List<Utilizator> prieteni = new ArrayList<>();

    public Utilizator(String prenume, String nume, String username, String password, String email) {
        this.prenume = prenume;
        this.nume = nume;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                getUsername().equals(that.getUsername()) &&
                getPassword().equals(that.getPassword()) &&
                getEmail().equals(that.getEmail()) &&
                getPrieteni().equals(that.getPrieteni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getPrenume(), getUsername(), getPassword(), getEmail(), getPrieteni());
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", prienteni=" + prieteni +
                '}';
    }
}
