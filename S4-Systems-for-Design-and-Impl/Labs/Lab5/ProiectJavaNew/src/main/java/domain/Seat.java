package domain;

public class Seat extends Entity<Long> {

    private Reservation reservation;
    private Long seatNumber;

    public Seat(Reservation reservation, Long seatNumber) {
        this.reservation = reservation;
        this.seatNumber = seatNumber;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Long getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Long seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "reservation=" + reservation +
                ", seatNumber=" + seatNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;

        if (!reservation.equals(seat.reservation)) return false;
        return seatNumber.equals(seat.seatNumber);
    }

    @Override
    public int hashCode() {
        int result = reservation.hashCode();
        result = 31 * result + seatNumber.hashCode();
        return result;
    }
}
