package games.model;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "Configurations")
public class Configuration extends Entity<Long> {

    private String hint;
    private Long coordinateX;
    private Long coordinateY;

    public Configuration() {
    }

    public Configuration(String hint, Long coordinateX, Long coordinateY) {
        this.hint = hint;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    @Column(name = "hint", nullable = false)
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Column(name = "coordinateX", nullable = false)
    public Long getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Long coordinateX) {
        this.coordinateX = coordinateX;
    }

    @Column(name = "coordinateY", nullable = false)
    public Long getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Long coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Override
    public String toString() {
        return "Configuration: " +
                "Hint: " + hint +
                "; CoordinateX: " + coordinateX +
                "; CoordinateY: " + coordinateY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration)) return false;
        Configuration that = (Configuration) o;
        return hint.equals(that.hint) &&
                coordinateX.equals(that.coordinateX) &&
                coordinateY.equals(that.coordinateY);
    }

    @Override
    public int hashCode() {
        int result = hint.hashCode();
        result = 31 * result + coordinateX.hashCode();
        result = 31 * result + coordinateY.hashCode();
        return result;
    }
}
