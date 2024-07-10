using System.Data;
using log4net;
using ProiectCSharp.domain;

namespace ProiectCSharp.repository.database;

public class ReservationDBRepository
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(ReservationDBRepository));
    private TripDBRepository tripRepository;
    
    public ReservationDBRepository()
    {
        logger.Info("Initializing ReservationDBRepository");
        tripRepository = new TripDBRepository();
    }

    public Reservation FindOne(long id)
    {
        logger.Info("Finding Reservation with id " + id);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "SELECT * FROM Reservations WHERE id = @id";
            IDataParameter idParam = command.CreateParameter();
            idParam.ParameterName = "@id";
            idParam.Value = id;
            command.Parameters.Add(idParam);
            using (IDataReader result = command.ExecuteReader())
            {
                if (result.Read())
                {
                    // TRIP COLUMN
                    long tripId = result.GetInt64(1);
                    Trip trip = tripRepository.FindOne(tripId);
                    if (trip == null)
                    {
                        return null;
                    }

                    // RESERVEDSEATS COLUMN
                    List<long> reservedSeats = new List<long>();
                    using (IDbCommand seatCommand = connection.CreateCommand())
                    {
                        seatCommand.CommandText = "SELECT * FROM Seats WHERE reservation_id = @id";
                        IDataParameter seatIdParam = seatCommand.CreateParameter();
                        seatIdParam.ParameterName = "@id";
                        seatIdParam.Value = id;
                        seatCommand.Parameters.Add(seatIdParam);
                        using (IDataReader seatResult = seatCommand.ExecuteReader())
                        {
                            while (seatResult.Read())
                            {
                                reservedSeats.Add(seatResult.GetInt64(0));
                            }
                        }
                    }
                    
                    // CLIENTNAME COLUMN
                    string clientName = result.GetString(3);
                    Reservation reservation = new Reservation(trip, reservedSeats, clientName);
                    reservation.Id = id;
                    return reservation;
                }
            }
        }
        return null;
    }
    
    public IEnumerable<Reservation> FindAll()
    {
        logger.Info("Finding all Reservations");
        IDbConnection connection = DbUtils.getConnection();
        List<Reservation> reservations = new List<Reservation>();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "SELECT * FROM Reservations";
            using (IDataReader result = command.ExecuteReader())
            {
                while (result.Read())
                {
                    long id = result.GetInt64(0);
                    Reservation reservation = FindOne(id);
                    if (reservation != null)
                    {
                        reservations.Add(reservation);
                    }
                }
            }
        }
        return reservations;
    }
    
    public Reservation Save(Reservation reservation)
    {
        logger.Info("Saving Reservation " + reservation);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "INSERT INTO Reservations (trip_id, reserved_seats, client_name) VALUES (@trip_id, @reserved_seats, @client_name)";
            IDataParameter tripIdParam = command.CreateParameter();
            tripIdParam.ParameterName = "@trip_id";
            tripIdParam.Value = reservation.Trip.Id;
            command.Parameters.Add(tripIdParam);
            IDataParameter seatsParam = command.CreateParameter();
            seatsParam.ParameterName = "@reserved_seats";
            seatsParam.Value = reservation.ReservedSeats.Count;
            command.Parameters.Add(seatsParam);
            IDataParameter clientNameParam = command.CreateParameter();
            clientNameParam.ParameterName = "@client_name";
            clientNameParam.Value = reservation.ClientName;
            command.Parameters.Add(clientNameParam);
            command.ExecuteNonQuery();

            // Get the last inserted id
            command.CommandText = "SELECT last_insert_rowid()";
            long lastId = (long)command.ExecuteScalar();
            reservation.Id = lastId;

            // Insert the seats in the Seats table
            foreach (long seat in reservation.ReservedSeats)
            {
                using (IDbCommand seatCommand = connection.CreateCommand())
                {
                    seatCommand.CommandText = "INSERT INTO Seats (reservation_id, seat_number) VALUES (@reservation_id, @seat_number)";
                    IDataParameter seatIdParam = seatCommand.CreateParameter();
                    seatIdParam.ParameterName = "@reservation_id";
                    seatIdParam.Value = reservation.Id;
                    seatCommand.Parameters.Add(seatIdParam);
                    IDataParameter seatNumberParam = seatCommand.CreateParameter();
                    seatNumberParam.ParameterName = "@seat_number";
                    seatNumberParam.Value = seat;
                    seatCommand.Parameters.Add(seatNumberParam);
                    seatCommand.ExecuteNonQuery();
                }
            }
        }
        return reservation;
    }
    
    public Reservation Delete(long id)
    {
        logger.Info("Deleting Reservation with id " + id);
        Reservation reservation = FindOne(id);
        if (reservation != null)
        {
            IDbConnection connection = DbUtils.getConnection();
            // Delete all the seats from the Seats table associated with the reservation
            using (IDbCommand seatCommand = connection.CreateCommand())
            {
                seatCommand.CommandText = "DELETE FROM Seats WHERE reservation_id = @id";
                IDataParameter seatIdParam = seatCommand.CreateParameter();
                seatIdParam.ParameterName = "@id";
                seatIdParam.Value = id;
                seatCommand.Parameters.Add(seatIdParam);
                seatCommand.ExecuteNonQuery();
            }

            // Delete the reservation from the Reservations table
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM Reservations WHERE id = @id";
                IDataParameter idParam = command.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                command.Parameters.Add(idParam);
                command.ExecuteNonQuery();
            }
        }
        return reservation;
    }
    
    public Reservation Update(Reservation reservation)
    {
        logger.Info("Updating Reservation " + reservation);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "UPDATE Reservations SET trip_id = @trip_id, reserved_seats = @reserved_seats, client_name = @client_name WHERE id = @id";
            IDataParameter tripIdParam = command.CreateParameter();
            tripIdParam.ParameterName = "@trip_id";
            tripIdParam.Value = reservation.Trip.Id;
            command.Parameters.Add(tripIdParam);
            IDataParameter seatsParam = command.CreateParameter();
            seatsParam.ParameterName = "@reserved_seats";
            seatsParam.Value = reservation.ReservedSeats.Count;
            command.Parameters.Add(seatsParam);
            IDataParameter clientNameParam = command.CreateParameter();
            clientNameParam.ParameterName = "@client_name";
            clientNameParam.Value = reservation.ClientName;
            command.Parameters.Add(clientNameParam);
            IDataParameter idParam = command.CreateParameter();
            idParam.ParameterName = "@id";
            idParam.Value = reservation.Id;
            command.Parameters.Add(idParam);
            command.ExecuteNonQuery();
        }

        // Delete all the old seats from the Seats table associated with the reservation
        using (IDbCommand seatCommand = connection.CreateCommand())
        {
            seatCommand.CommandText = "DELETE FROM Seats WHERE reservation_id = @id";
            IDataParameter seatIdParam = seatCommand.CreateParameter();
            seatIdParam.ParameterName = "@id";
            seatIdParam.Value = reservation.Id;
            seatCommand.Parameters.Add(seatIdParam);
            seatCommand.ExecuteNonQuery();
        }

        // Insert the new seats in the Seats table
        foreach (long seat in reservation.ReservedSeats)
        {
            using (IDbCommand newSeatCommand = connection.CreateCommand())
            {
                newSeatCommand.CommandText = "INSERT INTO Seats (reservation_id, seat_number) VALUES (@reservation_id, @seat_number)";
                IDataParameter newSeatIdParam = newSeatCommand.CreateParameter();
                newSeatIdParam.ParameterName = "@reservation_id";
                newSeatIdParam.Value = reservation.Id;
                newSeatCommand.Parameters.Add(newSeatIdParam);
                IDataParameter seatNumberParam = newSeatCommand.CreateParameter();
                seatNumberParam.ParameterName = "@seat_number";
                seatNumberParam.Value = seat;
                newSeatCommand.Parameters.Add(seatNumberParam);
                newSeatCommand.ExecuteNonQuery();
            }
        }
        return reservation;
    }
}