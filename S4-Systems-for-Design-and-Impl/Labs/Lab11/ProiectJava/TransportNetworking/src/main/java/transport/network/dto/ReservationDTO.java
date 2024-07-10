package transport.network.dto;

import java.io.Serializable;
import java.util.List;

public class ReservationDTO implements Serializable {
    private Long tripId;
    private List<Long> reservedSeats;
    private String clientName;

    public ReservationDTO(Long tripId, List<Long> reservedSeats, String clientName) {
        this.tripId = tripId;
        this.reservedSeats = reservedSeats;
        this.clientName = clientName;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
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
        return "ReservationDTO[" + tripId + ' ' + reservedSeats.size() + ' ' + clientName + "]";
    }
}
