package com.example.faptebuneexamen.service;

import com.example.faptebuneexamen.domain.Nevoie;
import com.example.faptebuneexamen.domain.Persoana;
import com.example.faptebuneexamen.events.ChangeEventType;
import com.example.faptebuneexamen.events.NevoieChangeEvent;
import com.example.faptebuneexamen.observer.Observable;
import com.example.faptebuneexamen.observer.Observer;
import com.example.faptebuneexamen.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service implements Observable<NevoieChangeEvent> {

    private List<Observer> observers = new ArrayList<>();

    private Repository<Long, Persoana> repoPersoane;
    private Repository<Long, Nevoie> repoNevoi;

    public Service(Repository<Long, Persoana> repoPersoane, Repository<Long, Nevoie> repoNevoi) {
        this.repoPersoane = repoPersoane;
        this.repoNevoi = repoNevoi;
    }

    public void adaugaPersoana(Persoana p) {
        repoPersoane.save(p);
    }

    public void adaugaNevoie(Nevoie n) {
        repoNevoi.save(n);
        notifyObservers(new NevoieChangeEvent(ChangeEventType.ADD));
    }

    public Iterable<Persoana> getPersoane() {
        return repoPersoane.findAll();
    }

    public Iterable<Nevoie> getNevoi() {
        return repoNevoi.findAll();
    }

    public void stergePersoana(Long id) {
        repoPersoane.delete(id).orElse(null);
    }

    public void stergeNevoie(Long id) {
        repoNevoi.delete(id).orElse(null);
        notifyObservers(new NevoieChangeEvent(ChangeEventType.DELETE));
    }

    public void modificaPersoana(Persoana p, Long id) {
        repoPersoane.update(p, id).orElse(null);
    }

    public void modificaNevoie(Nevoie n, Long id) {
        repoNevoi.update(n, id).orElse(null);
    }

    public Persoana cautaPersoana(Long id) {
        return repoPersoane.findOne(id).orElse(null);
    }

    public Nevoie cautaNevoie(Long id) {
        return repoNevoi.findOne(id).orElse(null);
    }

    public Persoana cautaPersoanaDupaUsername(String username) {
        for (Persoana p : repoPersoane.findAll()) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }

    public List<String> getExistingUsernames() {
        List<String> usernames = new ArrayList<>();
        for (Persoana p : repoPersoane.findAll()) {
            usernames.add(p.getUsername());
        }
        return usernames;
    }

    public List<Nevoie> getNevoiForCity(String oras, Persoana persoana) {
        List<Nevoie> nevoi = new ArrayList<>();
        for (Nevoie nevoie : repoNevoi.findAll()) {
            Persoana omInNevoie = repoPersoane.findOne(nevoie.getOmInNevoie()).orElse(null);
            if (omInNevoie != null && omInNevoie.getOras().toString().equals(oras) && !omInNevoie.equals(persoana) && Objects.equals(nevoie.getStatus(), "Caut erou!")) {
                nevoi.add(nevoie);
            }
        }
        return nevoi;
    }

    public void claimNevoie(Nevoie nevoie, Long persoanaId) {
        nevoie.setOmSalvator(persoanaId);
        nevoie.setStatus("Erou gasit!");
        repoNevoi.update(nevoie, nevoie.getId());
        notifyObservers(new NevoieChangeEvent(ChangeEventType.UPDATE));
    }

    public List<Nevoie> getFapteBune(Persoana persoana) {
        List<Nevoie> nevoi = new ArrayList<>();
        for (Nevoie nevoie : repoNevoi.findAll()) {
            Persoana omSalvator = repoPersoane.findOne(nevoie.getOmSalvator()).orElse(null);
            if (omSalvator != null && omSalvator.getId().equals(persoana.getId())) {
                nevoi.add(nevoie);
            }
        }
        return nevoi;
    }

    @Override
    public void addObserver(Observer<NevoieChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<NevoieChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(NevoieChangeEvent event) {
        for (Observer<NevoieChangeEvent> observer: observers) {
            observer.update(event);
        }
    }
}
