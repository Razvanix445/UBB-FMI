package app.model;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "Players")
public class Player extends Entity<Long> {

    private String alias;

    public Player() {
    }

    public Player(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Player{" + "alias=" + alias + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            return player.getAlias().equals(alias);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return alias.hashCode();
    }

}
