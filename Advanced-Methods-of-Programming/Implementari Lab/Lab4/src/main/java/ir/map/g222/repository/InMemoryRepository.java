package ir.map.g222.repository;

import ir.map.g222.domain.Entity;
import ir.map.g222.exceptions.EntitateNull;
import ir.map.g222.validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    Map<ID, E> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<ID, E>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null)
            throw new EntitateNull("ID must not be null");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        if (entity == null)
            throw new EntitateNull("Entity must not be null");
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if (id == null)
            throw new EntitateNull("ID must not be null");
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity, ID id) {
        if (entity == null)
            throw new EntitateNull("Entity must not be null");

        return Optional.ofNullable(entities.computeIfPresent(id, (k, v) -> {
            entity.setId(id);
            return entity;
        }));
    }
}
