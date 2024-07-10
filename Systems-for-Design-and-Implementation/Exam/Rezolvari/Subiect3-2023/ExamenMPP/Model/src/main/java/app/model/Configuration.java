package app.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "Configurations")
public class Configuration extends Entity<Long> {

    public Configuration() {
    }

    @Override
    public String toString() {
        return "Configuration{" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
