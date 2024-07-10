package transport.model;

public class TripDTO {
    private String id;
    private String destination;
    private String dateDeparture;
    private String noOfAvailableSeats;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public String getNoOfAvailableSeats() {
        return noOfAvailableSeats;
    }

    public void setNoOfAvailableSeats(String noOfAvailableSeats) {
        this.noOfAvailableSeats = noOfAvailableSeats;
    }

    @Override
    public String toString() {
        return "TripDTO:" +
                "id: " + id +
                "; destination: " + destination +
                "; dateDeparture: " + dateDeparture +
                "; noOfAvailableSeats: " + noOfAvailableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;
        TripDTO trip = (TripDTO) o;
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
}