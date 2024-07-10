package domain;

import java.util.List;
import java.util.Objects;

public class Reservation extends Entity<Long> {

    private Trip trip;
    private List<Long> reservedSeats;
    private String clientName;

    public Reservation(Trip trip, List<Long> reservedSeats, String clientName) {
        this.trip = trip;
        this.reservedSeats = reservedSeats;
        this.clientName = clientName;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Long> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<Long> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "trip=" + trip +
                ", reservedSeats=" + reservedSeats +
                ", clientName='" + clientName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;

        Reservation that = (Reservation) o;

        if (!Objects.equals(trip, that.trip)) return false;
        if (!Objects.equals(reservedSeats, that.reservedSeats)) return false;
        return Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        int result = trip != null ? trip.hashCode() : 0;
        result = 31 * result + (reservedSeats != null ? reservedSeats.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        return result;
    }
}
