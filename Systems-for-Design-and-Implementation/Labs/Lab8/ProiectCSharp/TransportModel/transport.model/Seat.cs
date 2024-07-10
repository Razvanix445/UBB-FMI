using TransportModel.transport.model;

namespace Transport.Model.transport.model
{
    public class Seat : Entity<long>
    {
        public Reservation Reservation { get; set; }
        public long SeatNumber { get; set; }

        public Seat(Reservation reservation, long seatNumber)
        {
            this.Reservation = reservation;
            this.SeatNumber = seatNumber;
        }

        public override string ToString()
        {
            return $"Seat{{reservation={Reservation}, seatNumber={SeatNumber}}}";
        }

        public override bool Equals(object obj)
        {
            if (this == obj) return true;
            if (!(obj is Seat)) return false;

            Seat seat = (Seat)obj;

            if (!Reservation.Equals(seat.Reservation)) return false;
            return SeatNumber.Equals(seat.SeatNumber);
        }

        public override int GetHashCode()
        {
            int result = Reservation.GetHashCode();
            result = 31 * result + SeatNumber.GetHashCode();
            return result;
        }
    }
}