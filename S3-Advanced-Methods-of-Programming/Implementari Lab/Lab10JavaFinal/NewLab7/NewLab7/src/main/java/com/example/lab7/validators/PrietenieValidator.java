package com.example.lab7.validators;

import com.example.lab7.domain.Prietenie;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.exceptions.UtilizatorInexistentException;
import com.example.lab7.repository.Repository;

public class PrietenieValidator implements Validator<Prietenie> {

    private Repository<Long, Utilizator> repository;

    public PrietenieValidator(Repository<Long, Utilizator> repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        Utilizator utilizator1 = repository.findOne(entity.getIdUtilizator1()).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));
        Utilizator utilizator2 = repository.findOne(entity.getIdUtilizator2()).orElseThrow(() -> new UtilizatorInexistentException("Utilizatorul nu exista!"));

        if (entity.equals(null))
            throw new ValidationException("Entitatea nu poate sa aiba valoarea null!");
        if (entity.getIdUtilizator1() == null || entity.getIdUtilizator2() == null)
            throw new ValidationException("ID-ul nu poate sa fie null!");
        if (utilizator1 == null || utilizator2 == null)
            throw new ValidationException("ID-ul nu exista!");
    }
}