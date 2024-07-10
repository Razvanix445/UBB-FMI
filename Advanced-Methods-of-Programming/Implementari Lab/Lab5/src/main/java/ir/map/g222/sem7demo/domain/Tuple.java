package ir.map.g222.sem7demo.domain;

import java.util.Objects;

public class Tuple<Entity1, Entity2> {

    private Entity1 entity1;
    private Entity2 entity2;

    public Tuple(Entity1 entity1, Entity2 entity2) {
        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    public Entity1 getLeft() {
        return entity1;
    }

    public void setLeft(Entity1 entity1) {
        this.entity1 = entity1;
    }

    public Entity2 getRight() {
        return entity2;
    }

    public void setRight(Entity2 entity2) {
        this.entity2 = entity2;
    }

    @Override
    public boolean equals(Object o) {
        return this.entity1.equals(((Tuple) o).entity1) && this.entity2.equals(((Tuple) o).entity2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity1, entity2);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                entity1 +
                ", " + entity2 +
                '}';
    }
}
