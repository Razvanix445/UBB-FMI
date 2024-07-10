package transport.services;

import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;
import transport.model.Reservation;

import java.util.List;

public interface ITransportServices {
    void login(ReservationManager reservationManager, ITransportObserver client) throws TransportException;

    ReservationManager searchReservationManagerByName(String name) throws TransportException;

    Trip[] getAllTrips() throws TransportException;

    Trip searchTripById(Long id) throws TransportException;

    void addReservation(Reservation reservation) throws TransportException;

    List<Reservation> getReservationsByTrip(Long tripId) throws TransportException;

    List<Seat> getSeatsByReservation(Long reservationId) throws TransportException;

    List<Seat> getSeatsByTripId(Long tripId) throws TransportException;

    void logout(ReservationManager reservationManager, ITransportObserver client) throws TransportException;
}
