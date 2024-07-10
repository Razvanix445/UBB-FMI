package transport.services;

import transport.model.Reservation;

public interface ITransportObserver {
    void reservationAdded(Reservation reservation) throws TransportException;
}
