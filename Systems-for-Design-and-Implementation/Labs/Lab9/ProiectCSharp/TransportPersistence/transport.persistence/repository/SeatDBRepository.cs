using System.Collections.Generic;
using System.Data;
using log4net;
using Transport.Model.transport.model;
using TransportModel.transport.model;

namespace TransportPersistence.transport.persistence
{
    public class SeatDBRepository : ISeatRepository
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(SeatDBRepository));
        private ReservationDBRepository reservationRepository;

        public SeatDBRepository()
        {
            logger.Info("Initializing SeatDBRepository");
            reservationRepository = new ReservationDBRepository();
        }

        public Seat FindOne(long id)
        {
            logger.Info("Finding Seat with id " + id);
            IDbConnection connection = DbUtils.getConnection();
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Seats WHERE id = @id";
                IDataParameter idParam = command.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                command.Parameters.Add(idParam);
                using (IDataReader result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        // RESERVATION COLUMN
                        long reservationId = result.GetInt64(1);
                        Reservation reservation = reservationRepository.FindOne(reservationId);
                        if (reservation == null)
                            return null;
                        
                        long seatNumber = result.GetInt64(2);
                        Seat seat = new Seat(reservation, seatNumber);
                        seat.id = id;
                        return seat;
                    }
                }
            }
            return null;
        }

        public IEnumerable<Seat> FindAll()
        {
            logger.Info("Finding all Seats");
            IDbConnection connection = DbUtils.getConnection();
            List<Seat> seats = new List<Seat>();
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Seats";
                using (IDataReader result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        long id = result.GetInt64(0);
                        Seat seat = FindOne(id);
                        if (seat != null)
                            seats.Add(seat);
                    }
                }
            }
            return seats;
        }
        
        public IEnumerable<Seat> FindSeatsByReservationId(long reservationId)
        {
            logger.Info("Finding Seats by reservation id " + reservationId);
            IDbConnection connection = DbUtils.getConnection();
            List<Seat> seats = new List<Seat>();
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Seats WHERE reservation_id = @reservationId";
                IDataParameter reservationIdParam = command.CreateParameter();
                reservationIdParam.ParameterName = "@reservationId";
                reservationIdParam.Value = reservationId;
                command.Parameters.Add(reservationIdParam);
                using (IDataReader result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        Reservation reservation = reservationRepository.FindOne(reservationId);
                        if (reservation == null)
                            return null;

                        long id = result.GetInt64(0);
                        long seatNumber = result.GetInt64(2);
                        Seat seat = new Seat(reservation, seatNumber);
                        seat.id = id;
                        seats.Add(seat);
                    }
                }
            }
            return seats;
        }

        public List<Seat> FindSeatsByTripId(long tripId)
        {
            logger.Info("Finding Seats by trip id " + tripId);
            IDbConnection connection = DbUtils.getConnection();
            List<Seat> seats = new List<Seat>();
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "SELECT Seats.* FROM Seats INNER JOIN Reservations ON Seats.reservation_id = Reservations.id WHERE Reservations.trip_id = @tripId";
                IDataParameter tripIdParam = command.CreateParameter();
                tripIdParam.ParameterName = "@tripId";
                tripIdParam.Value = tripId;
                command.Parameters.Add(tripIdParam);
                using (IDataReader result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        long reservationId = result.GetInt64(1);
                        Reservation reservation = reservationRepository.FindOne(reservationId);
                        if (reservation == null)
                            return null;

                        long id = result.GetInt64(0);
                        long seatNumber = result.GetInt64(2);
                        Seat seat = new Seat(reservation, seatNumber);
                        seat.id = id;
                        seats.Add(seat);
                    }
                }
            }
            return seats;
        }

        public Seat Save(Seat seat)
        {
            logger.Info("Not Implemented!");
            return null;
        }

        public Seat Delete(long id)
        {
            logger.Info("Not Implemented!");
            return null;
        }

        public Seat Update(Seat seat)
        {
            logger.Info("Not Implemented!");
            return null;
        }
    }
}