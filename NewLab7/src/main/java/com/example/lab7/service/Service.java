package com.example.lab7.service;

import com.example.lab7.Session;
import com.example.lab7.domain.Prietenie;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.domain.UtilizatorDatePair;
import com.example.lab7.exceptions.IDuriIdenticeException;
import com.example.lab7.exceptions.PrietenieExistentaException;
import com.example.lab7.exceptions.PrietenieInexistentaException;
import com.example.lab7.exceptions.UtilizatorInexistentException;
import com.example.lab7.repository.Repository;
import com.example.lab7.validators.PrietenieValidator;
import com.example.lab7.validators.UtilizatorValidator;
import com.example.lab7.validators.Validator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {

    private Repository<Long, Utilizator> repositoryUtilizatori;
    private Repository<Long, Prietenie> repositoryPrietenii;
    private Validator<Utilizator> validatorUtilizator;
    private Validator<Prietenie> validatorPrietenie;

    private static long urmatorulIdDisponibilUtilizatori = 1;
    private static long urmatorulIdDisponibilPrietenii = 1;

    public Service(Repository<Long, Utilizator> utilizatorRepository, Repository<Long, Prietenie> prietenieRepository) {
        this.repositoryUtilizatori = utilizatorRepository;
        this.repositoryPrietenii = prietenieRepository;
        this.validatorUtilizator = new UtilizatorValidator();
        this.validatorPrietenie = new PrietenieValidator(repositoryUtilizatori);
    }

    private Long genereazaIdUnicUtilizator() {
        return urmatorulIdDisponibilUtilizatori++;
    }

    private Long genereazaIdUnicPrietenie() {
        return urmatorulIdDisponibilPrietenii++;
    }

    public Utilizator adaugaUtilizator(Utilizator utilizator) {
        validatorUtilizator.validate(utilizator);
        utilizator.setId(genereazaIdUnicUtilizator());
        repositoryUtilizatori.save(utilizator);
        return utilizator;
    }

    public Utilizator stergeUtilizator(Utilizator utilizator) {
        validatorUtilizator.validate(utilizator);
        List<Long> deSters = new ArrayList<>();

        getAllPrietenii().forEach(prietenie -> {
            if (prietenie.getIdUtilizator1().equals(utilizator.getId()) || prietenie.getIdUtilizator2().equals(utilizator.getId()))
                deSters.add(prietenie.getId());
        });

        deSters.forEach(repositoryPrietenii::delete);
        Optional<Utilizator> utilizatorStersOptional = repositoryUtilizatori.delete(utilizator.getId());

        if (utilizatorStersOptional.isPresent()) {
            Utilizator utilizatorSters = utilizatorStersOptional.get();
            utilizator.getPrieteni().forEach(prieten -> {
                prieten.stergePrieten(utilizatorSters);
            });
            return utilizatorSters;
        } else {
            throw new UtilizatorInexistentException("Utilizatorul nu exista!");
        }
    }

    public Utilizator modificaUtilizator(Utilizator utilizator) {
        validatorUtilizator.validate(utilizator);
        Utilizator utilizatorModificat = new Utilizator(utilizator.getPrenume(), utilizator.getNume(),
                utilizator.getUsername(), utilizator.getPassword(), utilizator.getEmail());
        validatorUtilizator.validate(utilizatorModificat);

        utilizatorModificat.setId(utilizator.getId());
        utilizator.getPrieteni().forEach(prieten -> {
            utilizatorModificat.adaugaPrieten(prieten);
            prieten.getPrieteni().remove(utilizator);
            prieten.getPrieteni().add(utilizatorModificat);
        });
        repositoryUtilizatori.update(utilizatorModificat, utilizator.getId());
        return utilizator;
    }

    public Utilizator cautaUtilizator(Long utilizatorID) {
        return repositoryUtilizatori.findOne(utilizatorID).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
    }

    public Utilizator cautaUtilizatorDupaUsername(String username) {
        Iterable<Utilizator> utilizatori = repositoryUtilizatori.findAll();
        for (Utilizator utilizator: utilizatori) {
            if (utilizator.getUsername().equals(username))
                return utilizator;
        }
        return null;
    }

    public List<Utilizator> cautaUtilizatoriDupaCeva(String data) {
        Iterable<Utilizator> utilizatori = repositoryUtilizatori.findAll();
        return StreamSupport.stream(utilizatori.spliterator(), false)
                .filter(utilizator ->
                        (data.isEmpty() || utilizator.getPrenume().toLowerCase().contains(data.toLowerCase())) ||
                        (data.isEmpty() || utilizator.getNume().toLowerCase().contains(data.toLowerCase())) ||
                        (data.isEmpty() || utilizator.getUsername().toLowerCase().contains(data.toLowerCase()))
                )
                .collect(Collectors.toList());
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return repositoryUtilizatori.findAll();
    }

    public void adaugaPrietenie(Long ID1, Long ID2) {
        Prietenie prietenie;
        if (ID1 < ID2)
            prietenie = new Prietenie(ID1, ID2);
        else
            prietenie = new Prietenie(ID2, ID1);
        validatorPrietenie.validate(prietenie);
        Utilizator utilizator1 = repositoryUtilizatori.findOne(prietenie.getIdUtilizator1()).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
        Utilizator utilizator2 = repositoryUtilizatori.findOne(prietenie.getIdUtilizator2()).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
        if (getAllPrietenii() != null) {
            getAllPrietenii().forEach(p -> {
                if (p.getIdUtilizator1().equals(prietenie.getIdUtilizator1()) && p.getIdUtilizator2().equals(prietenie.getIdUtilizator2())) {
                    throw new PrietenieExistentaException("Prietenia exista deja!");
                }
            });
            if (repositoryUtilizatori.findOne(prietenie.getIdUtilizator1()).isEmpty() || repositoryUtilizatori.findOne(prietenie.getIdUtilizator2()).isEmpty()) {
                throw new UtilizatorInexistentException("Utilizatorul nu exista!");
            }
            if (prietenie.getIdUtilizator1().equals(prietenie.getIdUtilizator2())) {
                throw new IDuriIdenticeException("ID-urile oferite sunt identice!");
            }
        }
        prietenie.setId(genereazaIdUnicPrietenie());
        repositoryPrietenii.save(prietenie);
        utilizator1.adaugaPrieten(utilizator2);
        utilizator2.adaugaPrieten(utilizator1);
    }

    public void stergePrietenie(Long ID1, Long ID2) {
        Utilizator utilizator1 = repositoryUtilizatori.findOne(ID1).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
        Utilizator utilizator2 = repositoryUtilizatori.findOne(ID2).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));

        Long ID = 0L;
        for (Prietenie p: repositoryPrietenii.findAll()) {
            if ((p.getIdUtilizator1().equals(ID1) && p.getIdUtilizator2().equals(ID2)) || (p.getIdUtilizator1().equals(ID2) && p.getIdUtilizator2().equals(ID1)))
                ID = p.getId();
        }
        if (ID == 0L)
            throw new PrietenieInexistentaException("Prietenia nu exista!");
        repositoryPrietenii.delete(ID);
        utilizator1.stergePrieten(utilizator2);
        utilizator2.stergePrieten(utilizator1);
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return repositoryPrietenii.findAll();
    }

    public Map<Utilizator, List<Utilizator>> getAllUtilizatoriCuPrietenii() {
        Map<Utilizator, List<Utilizator>> utilizatoriCuPrieteni = new HashMap<>();
        Iterable<Utilizator> utilizatori = repositoryUtilizatori.findAll();
        for (Utilizator utilizator: utilizatori) {
            List<Utilizator> prieteni = getPrieteniUtilizator(utilizator.getId());
            utilizatoriCuPrieteni.put(utilizator, prieteni);
        }
        return utilizatoriCuPrieteni;
    }

    public List<UtilizatorDatePair> getPrieteniDataPentruUtilizatorInLunaSpecifica(Long utilizatorID, int luna) {
        Utilizator utilizator = cautaUtilizator(utilizatorID);
        return getPrieteniiUtilizator(utilizator.getId())
                .stream()
                .filter(prietenie -> prietenie.getDate().getMonth().getValue() == luna)
                .map(prietenie -> {
                    Utilizator prieten = (prietenie.getIdUtilizator1().equals(utilizatorID)) ?
                            cautaUtilizator(prietenie.getIdUtilizator2()) : cautaUtilizator(prietenie.getIdUtilizator1());
                    return new UtilizatorDatePair(prieten, prietenie.getDate());
                })
                .collect(Collectors.toList());
    }

    private List<Prietenie> getPrieteniiUtilizator(Long utilizatorID) {
        List<Prietenie> prieteniiUtilizator = new ArrayList<>();
        Iterable<Prietenie> prietenii = repositoryPrietenii.findAll();
        for (Prietenie prietenie: prietenii) {
            if (prietenie.getIdUtilizator1().equals(utilizatorID) || prietenie.getIdUtilizator2().equals(utilizatorID))
                prieteniiUtilizator.add(prietenie);
        }
        return prieteniiUtilizator;
    }

    public List<Utilizator> getPrieteniUtilizator(Long utilizatorID) {
        List<Utilizator> prieteni = new ArrayList<>();
        Utilizator utilizator = repositoryUtilizatori.findOne(utilizatorID).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
        Iterable<Prietenie> prietenii = repositoryPrietenii.findAll();
        for (Prietenie prietenie: prietenii) {
            if (prietenie.getIdUtilizator1().equals(utilizatorID)) {
                prieteni.add(repositoryUtilizatori.findOne(prietenie.getIdUtilizator2()).orElse(null));
            } else if (prietenie.getIdUtilizator2().equals(utilizatorID)) {
                prieteni.add(repositoryUtilizatori.findOne(prietenie.getIdUtilizator1()).orElse(null));
            }
        }
        prieteni.removeIf(Objects::isNull);
        return prieteni;
    }

    public List<Utilizator> getCommunityOfLoggedUser() {
        List<List<Utilizator>> allCommunities = getAllComunitati();
        Long loggedUserID = Session.getLoggedUser().getId();

        for (List<Utilizator> community: allCommunities) {
            if (community.stream().anyMatch(user -> user.getId().equals(loggedUserID)))
                return community;
        }
        return Collections.emptyList();
    }

    public List<List<Utilizator>> getAllComunitati() {
        Map<Long, List<Long>> listaAdiacenta = creareListaAdiacenta();
        Set<Long> vizitat = new HashSet<>();
        List<List<Utilizator>> toateComunitatile = new ArrayList<>();

        getAllUtilizatori().forEach(utilizator -> {
            Long utilizatorID = utilizator.getId();
            if (!vizitat.contains(utilizatorID)) {
                List<Utilizator> comunitateCurenta = new ArrayList<>();
                DFS(listaAdiacenta, utilizatorID, vizitat, comunitateCurenta);
                toateComunitatile.add(comunitateCurenta);
            }
        });
        return toateComunitatile;
    }

    public int getNumarComunitati() {
        return (int) getAllComunitati().stream().count();
    }

    public List<Utilizator> ceaMaiSociabilaComunitate() {
        return getAllComunitati()
                .stream()
                .max(Comparator.comparingInt(List::size))
                .orElseGet(ArrayList::new);
    }

    private void DFS(Map<Long, List<Long>> listaAdiacenta, Long userId, Set<Long> vizitat, List<Utilizator> comunitateCurenta) {
        vizitat.add(userId);
        Utilizator utilizator = repositoryUtilizatori.findOne(userId).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
        comunitateCurenta.add(utilizator);

        List<Long> vecini = listaAdiacenta.get(userId);
        if (vecini != null) {
            vecini.forEach(vecin -> {
                if (!vizitat.contains(vecin)) {
                    DFS(listaAdiacenta, vecin, vizitat, comunitateCurenta);
                }
            });
        }
    }

    private Map<Long, List<Long>> creareListaAdiacenta() {
        Map<Long, List<Long>> listaAdiacenta = new HashMap<>();
        getAllPrietenii().forEach(prietenie -> {
            Long user1ID = prietenie.getIdUtilizator1();
            Long user2ID = prietenie.getIdUtilizator2();

            listaAdiacenta.computeIfAbsent(user1ID, k -> new ArrayList<>()).add(user2ID);
            listaAdiacenta.computeIfAbsent(user2ID, k -> new ArrayList<>()).add(user1ID);
        });
        return listaAdiacenta;
    }


    public Utilizator login(String username, String password) {
        Utilizator user = cautaUtilizatorDupaUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public void logout() {
        Session.clearSession();
    }

    public Utilizator getLoggedUser() {
        return Session.getLoggedUser();
    }
}
