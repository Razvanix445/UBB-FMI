using TransportModel.transport.model;

namespace TransportServices.transport.services
{
    public interface ITransportObserver
    {
        void ReservationAdded(Reservation reservation);
    }
}