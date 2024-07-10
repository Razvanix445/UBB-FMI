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
        public ResponseType Type { get; set; }
        public string ErrorMessage { get; set; }
        public ReservationManager ReservationManager { get; set; }
        public Trip Trip { get; set; }
        public Seat Seat { get; set; }
        public Reservation Reservation { get; set; }
        public Trip[] Trips { get; set; }
        public List<Reservation> Reservations { get; set; }
        public List<Seat> Seats { get; set; }

        public override string ToString()
        {
            return $"Response{{type={Type}, reservationManager={ReservationManager}, trip={Trip}, reservation={Reservation}, seat={Seat}}}";
        }
    }
}