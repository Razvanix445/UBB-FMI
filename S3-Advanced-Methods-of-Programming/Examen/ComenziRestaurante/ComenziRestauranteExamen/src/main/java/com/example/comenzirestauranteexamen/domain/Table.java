package com.example.comenzirestauranteexamen.domain;

public class Table extends Entity<Long> {

    public Table(Long id) {
        super(id);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                '}';
    }
}
