using System;

namespace TransportModel.transport.model
{
    [Serializable]
    public class ReservationManager: Entity<long>
    {
        public string Name { get; set; }
        public string Password { get; set; }
    
        public ReservationManager()
        {
        }
        
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
}