package transport.model;

public class SeatReservation {

    private Long seatNumber;
    private String clientName;

    public SeatReservation(Long seatNumber, String clientName) {
        this.seatNumber = seatNumber;
        this.clientName = clientName;
    }

    public Long getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Long seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
