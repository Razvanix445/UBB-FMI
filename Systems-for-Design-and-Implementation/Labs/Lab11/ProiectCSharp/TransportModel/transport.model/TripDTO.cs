using System;

namespace TransportModel.transport.model
{
    public class TripDTO
    {
        public long id { get; set; }
        public string destination { get; set; }
        public string dateDeparture { get; set; }
        public long noOfAvailableSeats { get; set; }

        public TripDTO()
        {
        }

        public TripDTO(string destination, string dateDeparture, long noOfAvailableSeats)
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