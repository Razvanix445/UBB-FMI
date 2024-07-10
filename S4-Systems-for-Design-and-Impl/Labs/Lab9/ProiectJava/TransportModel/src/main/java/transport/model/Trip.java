package transport.model;

import java.time.LocalDateTime;

public class Trip extends Entity<Long> implements Comparable<Trip> {

    private String destination;
    private LocalDateTime dateDeparture;
    private String dateDepartureString;
    private Long noOfAvailableSeats;

    public Trip(Long id) {
        this.id = id;
    }

    public Trip(String destination, LocalDateTime dateDeparture, Long noOfAvailableSeats) {
        this.destination = destination;
        this.dateDeparture = dateDeparture;
        this.noOfAvailableSeats = noOfAvailableSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDateDeparture() {
        if (dateDeparture == null && dateDepartureString != null) {
            dateDeparture = LocalDateTime.parse(dateDepartureString);
        }
        return dateDeparture;
    }

    public void setDateDeparture(LocalDateTime dateDeparture) {
        this.dateDeparture = dateDeparture;
        this.dateDepartureString = dateDeparture.toString();
    }

    public Long getNoOfAvailableSeats() {
        return noOfAvailableSeats;
    }

    public void setNoOfAvailableSeats(Long noOfAvailableSeats) {
        this.noOfAvailableSeats = noOfAvailableSeats;
    }

    @Override
    public String toString() {
        return "Trip:" +
                "destination: " + destination +
                "; dateDeparture: " + dateDeparture +
                "; noOfAvailableSeats: " + noOfAvailableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;
        Trip trip = (Trip) o;
        return destination.equals(trip.destination) &&
                dateDeparture.equals(trip.dateDeparture) &&
                noOfAvailableSeats.equals(trip.noOfAvailableSeats);
    }

    @Override
    public int hashCode() {
        int result = destination.hashCode();
        result = 31 * result + dateDeparture.hashCode();
        result = 31 * result + noOfAvailableSeats.hashCode();
        return result;
    }

    @Override
    public int compareTo(Trip o) {
        return this.destination.compareTo(o.getDestination());
    }
}