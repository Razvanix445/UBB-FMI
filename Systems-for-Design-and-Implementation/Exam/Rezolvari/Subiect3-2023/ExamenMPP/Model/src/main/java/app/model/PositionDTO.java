package app.model;

import java.util.Objects;

public class PositionDTO {

    private Long coordinateX;
    private Long coordinateY;
    private Long positionIndex;

    public PositionDTO(Long coordinateX, Long coordinateY, Long positionIndex) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.positionIndex = positionIndex;
    }

    public Long getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Long coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Long getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Long coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Long getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Long positionIndex) {
        this.positionIndex = positionIndex;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", positionIndex=" + positionIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionDTO that = (PositionDTO) o;
        return Objects.equals(coordinateX, that.coordinateX) && Objects.equals(coordinateY, that.coordinateY) && Objects.equals(positionIndex, that.positionIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateX, coordinateY, positionIndex);
    }
}
