using System;
using System.Collections.Generic;
using Transport.Model.transport.model;
using TransportModel.transport.model;
using TransportNetworking.transport.network.jsonprotocol;

namespace TransportNetworking.transport.network.jsonprotocol
{
    [Serializable]
    public class Response
    {
        public ResponseType type { get; set; }
        public string errorMessage { get; set; }
        public ReservationManager ReservationManager { get; set; }
        public Trip trip { get; set; }
        public Seat Seat { get; set; }
        public Reservation reservation { get; set; }
        public Trip[] trips { get; set; }
        public List<Reservation> reservations { get; set; }
        public List<Seat> seats { get; set; }

        public override string ToString()
        {
            return $"Response{{type={type}, reservationManager={ReservationManager}, trip={trip}, reservation={reservation}, seat={Seat}}}";
        }
    }
}