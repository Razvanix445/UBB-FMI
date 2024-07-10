package com.example.comenzirestauranteexamen.domain;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {

    protected ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Entity<?> entity)) return false;
        return id != null && id.equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
