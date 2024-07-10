package transport.network.dto;

import java.io.Serializable;

public class SeatDTO implements Serializable {
    private String id;
    private String reservationId;
    private String seatNumber;

    public SeatDTO(String id, String reservationId, String seatNumber) {
        this.id = id;
        this.reservationId = reservationId;
        this.seatNumber = seatNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "SeatDTO[" + id + ' ' + reservationId + ' ' + seatNumber + "]";
    }
}
