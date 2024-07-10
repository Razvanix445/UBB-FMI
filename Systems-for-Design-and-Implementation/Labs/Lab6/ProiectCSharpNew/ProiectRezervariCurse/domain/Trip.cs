using System;

namespace ProiectRezervariCurse.domain
{
    public class Trip: Entity<long>
    {
        public string Destination { get; set; }
        public DateTime DateDeparture { get; set; }
        public long NoOfAvailableSeats { get; set; }
    
        public Trip(string destination, DateTime dateDeparture, long noOfAvailableSeats)
        {
            Destination = destination;
            DateDeparture = dateDeparture;
            NoOfAvailableSeats = noOfAvailableSeats;
        }
    
        public override string ToString()
        {
            return "Trip{" +
                   "destination='" + Destination + '\'' +
                   ", dateDeparture=" + DateDeparture +
                   ", noOfAvailableSeats=" + NoOfAvailableSeats +
                   '}';
        }
    }
}