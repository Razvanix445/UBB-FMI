package ir.map.g222.repository;

import ir.map.g222.domain.Entity;
import ir.map.g222.exceptions.EntitateNull;
import ir.map.g222.validators.Validator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    Map<ID, E> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<ID, E>();
    }

    @Override
    public E findOne(ID id) {
        if (id == null)
            throw new EntitateNull("ID must not be null");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if (entity == null)
            throw new EntitateNull("Entity must not be null");
        if (entities.get(entity.getId()) != null) {
            return entity;
        } else {
            entities.put(entity.getId(), entity);
            //System.out.println("<Repository> Am adaugat entitatea " + entity.toString() + " cu id-ul " + entity.getId());
        }
        return null;
    }

    @Override
    public E delete(ID id) {
        if (id == null)
            throw new EntitateNull("ID must not be null");
        if (entities.get(id) == null)
            return null;
        else {
            return entities.remove(id);
        }
    }

    @Override
    public E update(E entity, ID id) {
        if (entity == null)
            throw new EntitateNull("Entity must not be null");

        if (entities.get(id) != null) {
            entities.put(id, entity);
            entities.get(id).setId(id);
            return null;
        }
        return entity;
    }
}
