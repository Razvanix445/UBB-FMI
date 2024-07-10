using System.Collections.Generic;
using Transport.Model.transport.model;

namespace TransportPersistence.transport.persistence
{
    public interface ISeatRepository : IRepository<long, Seat>
    {
        IEnumerable<Seat> FindSeatsByReservationId(long reservationId);

        List<Seat> FindSeatsByTripId(long tripId);
    }
}