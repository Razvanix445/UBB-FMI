namespace ProiectCSharp.domain;

public class Reservation: Entity<long>
{
    public Trip Trip { get; set; }
    public List<long> ReservedSeats { get; set; }
    public string ClientName { get; set; }
    
    public Reservation(Trip trip, List<long> reservedSeats, string clientName)
    {
        Trip = trip;
        ReservedSeats = reservedSeats;
        ClientName = clientName;
    }
    
    public override string ToString()
    {
        return "Reservation{" +
               "trip=" + Trip +
               ", reservedSeats=" + ReservedSeats +
               ", clientName='" + ClientName + '\'' +
               '}';
    }
}