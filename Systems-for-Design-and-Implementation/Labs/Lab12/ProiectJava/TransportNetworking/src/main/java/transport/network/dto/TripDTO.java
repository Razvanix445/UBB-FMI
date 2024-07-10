package transport.network.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TripDTO implements Serializable {
    private String destination;
    private LocalDateTime dateDeparture;
    private Long noOfAvailableSeats;

    public TripDTO(String destination, LocalDateTime dateDeparture, Long noOfAvailableSeats) {
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
        return dateDeparture;
    }

    public void setDateDeparture(LocalDateTime dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public Long getNoOfAvailableSeats() {
        return noOfAvailableSeats;
    }

    public void setNoOfAvailableSeats(Long noOfAvailableSeats) {
        this.noOfAvailableSeats = noOfAvailableSeats;
    }

    @Override
    public String toString() {
        return "TripDTO[" + destination + ' ' + dateDeparture + ' ' + noOfAvailableSeats + "]";
    }
}
