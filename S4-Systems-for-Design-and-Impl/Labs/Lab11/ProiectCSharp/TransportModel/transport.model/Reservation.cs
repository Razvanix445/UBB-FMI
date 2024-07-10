using System;
using System.Collections.Generic;

namespace TransportModel.transport.model
{
    [Serializable]
    public class Reservation: Entity<long>
    {
        public Trip trip { get; set; }
        public List<long> reservedSeats { get; set; }
        public string clientName { get; set; }
    
        public Reservation(Trip trip, List<long> reservedSeats, string clientName)
        {
            this.trip = trip;
            this.reservedSeats = reservedSeats;
            this.clientName = clientName;
        }
    
        public override string ToString()
        {
            return "Reservation{" +
                   "trip=" + trip +
                   ", reservedSeats=" + reservedSeats +
                   ", clientName='" + clientName + '\'' +
                   '}';
        }
    }
}