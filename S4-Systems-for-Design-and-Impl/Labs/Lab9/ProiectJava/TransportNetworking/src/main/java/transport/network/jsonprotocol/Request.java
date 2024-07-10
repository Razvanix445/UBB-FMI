package transport.network.jsonprotocol;

import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;

import java.util.List;

public class Request {
    private RequestType type;
    private ReservationManager reservationManager;
    private Trip trip;
    private Seat seat;
    private Reservation reservation;
    private String stringR;
    private Long aLong;
    private Trip[] trips;
    private List<Reservation> reservations;
    private List<Seat> seats;

    public Request(){}
    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getStringR() {
        return stringR;
    }

    public void setStringR(String stringR) {
        this.stringR = stringR;
    }

    public Long getLong() {
        return aLong;
    }

    public void setLong(Long aLong) {
        this.aLong = aLong;
    }

    public ReservationManager getReservationManager() {
        return reservationManager;
    }

    public void setReservationManager(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Trip[] getTrips() {
        return trips;
    }

    public void setTrips(Trip[] trips) {
        this.trips = trips;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", reservationManager=" + reservationManager +
                ", trip=" + trip +
                ", reservation=" + reservation +
                ", seat=" + seat +
                '}';
    }
}
