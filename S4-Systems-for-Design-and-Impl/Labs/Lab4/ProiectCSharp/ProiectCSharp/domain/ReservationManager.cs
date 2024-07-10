namespace ProiectCSharp.domain;

public class ReservationManager: Entity<long>
{
    public string Name { get; set; }
    public string Password { get; set; }
    
    public ReservationManager(string cityName, string password)
    {
        Name = cityName;
        this.Password = password;
    }
    
    public override string ToString()
    {
        return "ReservationManager{" +
               "CityName='" + Name + '\'' +
               ", password='" + Password + '\'' +
               '}';
    }
}