using System;

namespace TransportModel.transport.model
{
    [Serializable]
    public class ReservationManager: Entity<long>
    {
        public string name { get; set; }
        public string password { get; set; }
    
        public ReservationManager()
        {
        }
        
        public ReservationManager(string cityName, string password)
        {
            name = cityName;
            this.password = password;
        }
    
        public override string ToString()
        {
            return "ReservationManager{" +
                   "CityName='" + name + '\'' +
                   ", password='" + password + '\'' +
                   '}';
        }
    }
}