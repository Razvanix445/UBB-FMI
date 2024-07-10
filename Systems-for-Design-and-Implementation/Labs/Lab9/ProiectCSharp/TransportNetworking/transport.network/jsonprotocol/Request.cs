using System.Collections.Generic;
using Transport.Model.transport.model;
using TransportModel.transport.model;

namespace TransportNetworking.transport.network.jsonprotocol
{
    public class Request
    {
        public RequestType type { get; set; }
        public string stringR { get; set; }
        public long? aLong { get; set; }
        public ReservationManager reservationManager { get; set; }

        public Trip trip { get; set; }

        public Seat seat { get; set; }
        public Reservation reservation { get; set; }
        public Trip[] trips { get; set; }
        public List<Reservation> reservations { get; set; }
        public List<Seat> seats { get; set; }

        public override string ToString()
        {
            return $"Request{{type={type}, reservationManager={reservationManager}, trip={trip}, reservation={reservation}, seat={seat}}}";
        }
    }
}