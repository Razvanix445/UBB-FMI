package transport.persistence;

import transport.model.Seat;

import java.util.List;

public interface ISeatRepository extends Repository<Long, Seat> {

    Iterable<Seat> findSeatsByReservationId(long reservationId);

    List<Seat> findSeatsByTripId(Long tripId);
}
