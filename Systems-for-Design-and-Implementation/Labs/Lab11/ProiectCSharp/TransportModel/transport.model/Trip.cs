using System;

namespace TransportModel.transport.model
{
    [Serializable]
    public class Trip: Entity<long>
    {
        public string destination { get; set; }
        public DateTime dateDeparture { get; set; }
        public long noOfAvailableSeats { get; set; }
    
        public Trip()
        {
        }
        
        public Trip(string destination, DateTime dateDeparture, long noOfAvailableSeats)
        {
            this.destination = destination;
            this.dateDeparture = dateDeparture;
            this.noOfAvailableSeats = noOfAvailableSeats;
        }
    
        public override string ToString()
        {
            return "Trip{" +
                   "destination='" + destination + '\'' +
                   ", dateDeparture=" + dateDeparture +
                   ", noOfAvailableSeats=" + noOfAvailableSeats +
                   '}';
        }
    }
}