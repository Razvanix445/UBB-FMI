using System.Collections.Generic;
using Transport.Model.transport.model;
using TransportModel.transport.model;

namespace TransportNetworking.transport.network.jsonprotocol
{
    public class Request
    {
        public RequestType Type { get; set; }
        public string String { get; set; }
        public long? Long { get; set; }
        public ReservationManager ReservationManager { get; set; }

        public Trip Trip { get; set; }

        public Seat Seat { get; set; }
        public Reservation Reservation { get; set; }
        public Trip[] Trips { get; set; }
        public List<Reservation> Reservations { get; set; }
        public List<Seat> Seats { get; set; }

        public override string ToString()
        {
            return $"Request{{type={Type}, reservationManager={ReservationManager}, trip={Trip}, reservation={Reservation}, seat={Seat}}}";
        }
    }
}