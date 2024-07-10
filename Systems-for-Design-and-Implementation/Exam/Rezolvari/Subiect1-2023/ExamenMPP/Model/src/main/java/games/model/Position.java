package games.model;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "Positions")
public class Position extends Entity<Long> {

    private Game game;
    private Long coordinateX;
    private Long coordinateY;
    private Long positionIndex;

    public Position() {
    }

    public Position(Long coordinateX, Long coordinateY, Long positionIndex) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.positionIndex = positionIndex;
    }

    public Position(Game game, Long coordinateX, Long coordinateY, Long positionIndex) {
        this.game = game;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.positionIndex = positionIndex;
    }

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    @Column(name = "position_index", nullable = false)
    public Long getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Long positionIndex) {
        this.positionIndex = positionIndex;
    }

    @Override
    public String toString() {
        return "Position{" +
                "game=" + game +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", positionIndex=" + positionIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return game.equals(position.game) &&
                coordinateX.equals(position.coordinateX) &&
                coordinateY.equals(position.coordinateY) &&
                positionIndex.equals(position.positionIndex);
    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + coordinateX.hashCode();
        result = 31 * result + coordinateY.hashCode();
        result = 31 * result + positionIndex.hashCode();
        return result;
    }
}
