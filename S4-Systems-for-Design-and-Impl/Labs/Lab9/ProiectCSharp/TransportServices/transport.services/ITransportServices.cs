using System.Collections.Generic;
using Transport.Model.transport.model;
using TransportModel.transport.model;

namespace TransportServices.transport.services
{
    public interface ITransportServices
    {
        ReservationManager Login(ReservationManager reservationManager, ITransportObserver client);

        ReservationManager SearchReservationManagerByName(string name);

        Trip[] GetAllTrips();

        Trip SearchTripById(long id);

        Reservation AddReservation(Reservation reservation);

        List<Reservation> GetReservationsByTrip(Trip trip);

        List<Seat> GetSeatsByReservation(long reservationId);

        List<Seat> GetSeatsByTrip(Trip trip);

        ReservationManager Logout(ReservationManager reservationManager, ITransportObserver client);
    }
}