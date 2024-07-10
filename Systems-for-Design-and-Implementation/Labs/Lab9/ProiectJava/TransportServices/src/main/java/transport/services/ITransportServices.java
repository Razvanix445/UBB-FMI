package transport.services;

import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;
import transport.model.Reservation;

import java.util.List;

public interface ITransportServices {
    ReservationManager login(ReservationManager reservationManager, ITransportObserver client) throws TransportException;

    ReservationManager searchReservationManagerByName(String name) throws TransportException;

    Trip[] getAllTrips() throws TransportException;

    Trip searchTripById(Long id) throws TransportException;

    Reservation addReservation(Reservation reservation) throws TransportException;

    List<Reservation> getReservationsByTrip(Trip trip) throws TransportException;

    List<Seat> getSeatsByReservation(Long reservationId) throws TransportException;

    List<Seat> getSeatsByTrip(Trip trip) throws TransportException;

    ReservationManager logout(ReservationManager reservationManager, ITransportObserver client) throws TransportException;
}
