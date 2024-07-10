package ir.map.g222.validators;

import ir.map.g222.domain.Prietenie;
import ir.map.g222.domain.Utilizator;
import ir.map.g222.repository.InMemoryRepository;

public class PrietenieValidator implements Validator<Prietenie> {

    private InMemoryRepository<Long, Utilizator> repository;

    public PrietenieValidator(InMemoryRepository<Long, Utilizator> repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        Utilizator utilizator1 = repository.findOne(entity.getIdUtilizator1());
        Utilizator utilizator2 = repository.findOne(entity.getIdUtilizator2());

        if (entity.equals(null))
            throw new ValidationException("Entitatea nu poate sa aiba valoarea null!");
        if (entity.getIdUtilizator1() == null || entity.getIdUtilizator2() == null)
            throw new ValidationException("ID-ul nu poate sa fie null!");
        if (utilizator1 == null || utilizator2 == null)
            throw new ValidationException("ID-ul nu exista!");
    }
}
