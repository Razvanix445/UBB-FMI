package ir.map.g222.service;

import ir.map.g222.domain.Prietenie;
import ir.map.g222.domain.Utilizator;
import ir.map.g222.exceptions.IDuriIdenticeException;
import ir.map.g222.exceptions.PrietenieExistentaException;
import ir.map.g222.exceptions.PrietenieInexistentaException;
import ir.map.g222.exceptions.UtilizatorInexistentException;
import ir.map.g222.repository.InMemoryRepository;
import ir.map.g222.repository.Repository;
import ir.map.g222.validators.PrietenieValidator;
import ir.map.g222.validators.UtilizatorValidator;
import ir.map.g222.validators.Validator;

import java.util.*;

public class Service {

    private InMemoryRepository<Long, Utilizator> repositoryUtilizatori;
    private InMemoryRepository<Long, Prietenie> repositoryPrietenii;
    private Validator<Utilizator> validatorUtilizator;
    private Validator<Prietenie> validatorPrietenie;

    private static long urmatorulIdDisponibilUtilizatori = 1;
    private static long urmatorulIdDisponibilPrietenii = 1;

    private HashMap<Long, List<Long>> lista;

    public Service(Repository<Long, Utilizator> utilizatorRepository, Repository<Long, Prietenie> prietenieRepository) {
        this.repositoryUtilizatori = new InMemoryRepository<>();
        this.repositoryPrietenii = new InMemoryRepository<>();
        this.validatorUtilizator = new UtilizatorValidator();
        this.validatorPrietenie = new PrietenieValidator(repositoryUtilizatori);
    }

    private Long genereazaIdUnicUtilizator() {
        return urmatorulIdDisponibilUtilizatori++;
    }

    private Long genereazaIdUnicPrietenie() {
        return urmatorulIdDisponibilPrietenii++;
    }

    public void adaugaUtilizator(String prenume, String nume) {
        Utilizator utilizator = new Utilizator(prenume, nume);
        validatorUtilizator.validate(utilizator);
        utilizator.setId(genereazaIdUnicUtilizator());
        repositoryUtilizatori.save(utilizator);
    }

    public void stergeUtilizator(Long utilizatorID) {
        Utilizator utilizator = repositoryUtilizatori.findOne(utilizatorID).orElseThrow(() -> new UtilizatorInexistentException(("Utilizatorul nu exista!")));
        List<Long> deSters = new ArrayList<>();

        getAllPrietenii().forEach(prietenie -> {
            if (prietenie.getIdUtilizator1().equals(utilizatorID) || prietenie.getIdUtilizator2().equals(utilizatorID))
                deSters.add(prietenie.getId());
        });

        deSters.forEach(repositoryPrietenii::delete);
        repositoryUtilizatori.delete(utilizatorID);

        utilizator.getPrieteni().forEach(prieten -> {
            prieten.stergePrieten(utilizator);
        });
    }

    public void modificaUtilizator(String prenume, String nume, Long ID) {
        Utilizator utilizator = repositoryUtilizatori.findOne(ID).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));

        if (utilizator == null) {
            throw new UtilizatorInexistentException("Utilizatorul nu a fost gasit!");
        }
        Utilizator utilizatorModificat = new Utilizator(prenume, nume);
        validatorUtilizator.validate(utilizatorModificat);

        utilizatorModificat.setId(ID);
        utilizator.getPrieteni().forEach(prieten -> {
            utilizatorModificat.adaugaPrieten(prieten);
            prieten.getPrieteni().remove(utilizator);
            prieten.getPrieteni().add(utilizatorModificat);
        });
        repositoryUtilizatori.update(utilizatorModificat, ID);
    }

    public Utilizator cautaUtilizator(Long utilizatorID) {
        return repositoryUtilizatori.findOne(utilizatorID).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return repositoryUtilizatori.findAll();
    }

    public void adaugaPrietenie(Long ID1, Long ID2) {
        Prietenie prietenie = new Prietenie(ID1, ID2);
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
}
