using TransportModel.transport.model;

namespace Transport.Model.transport.model
{
    public class Seat : Entity<long>
    {
        public Reservation reservation { get; set; }
        public long seatNumber { get; set; }

        public Seat(Reservation reservation, long seatNumber)
        {
            this.reservation = reservation;
            this.seatNumber = seatNumber;
        }

        public override string ToString()
        {
            return $"Seat{{reservation={reservation}, seatNumber={seatNumber}}}";
        }

        public override bool Equals(object obj)
        {
            if (this == obj) return true;
            if (!(obj is Seat)) return false;

            Seat seat = (Seat)obj;

            if (!reservation.Equals(seat.reservation)) return false;
            return seatNumber.Equals(seat.seatNumber);
        }

        public override int GetHashCode()
        {
            int result = reservation.GetHashCode();
            result = 31 * result + seatNumber.GetHashCode();
            return result;
        }
    }
}