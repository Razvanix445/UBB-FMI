using log4net;
using System.Collections.Generic;
using System.Data;
using log4net.Repository.Hierarchy;
using ProiectCSharp.domain;

namespace ProiectCSharp.repository.database;

public class TripDBRepository: ITripRepository
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(TripDBRepository));

	public TripDBRepository() {
        logger.Info("Initializing TripDBRepository");
    }
    
    public Trip FindOne(long id)
    {
        logger.Info("Finding Trip with id " + id);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "SELECT * FROM Trips WHERE id = @id";
            IDataParameter idParam = command.CreateParameter();
            idParam.ParameterName = "@id";
            idParam.Value = id;
            command.Parameters.Add(idParam);
            using (IDataReader result = command.ExecuteReader())
            {
                if (result.Read())
                {
                    string destination = result.GetString(1);
                    DateTime departureTime = result.GetDateTime(2);
                    long numberOfSeats = result.GetInt64(3);
                    Trip trip = new Trip(destination, departureTime, numberOfSeats);
                    trip.Id = id;
                    return trip;
                }
            }
        }
        return null;
    }
    
    public IEnumerable<Trip> FindAll()
    {
        logger.Info("Finding all Trips");
        IDbConnection connection = DbUtils.getConnection();
        List<Trip> trips = new List<Trip>();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "SELECT * FROM Trips";
            using (IDataReader result = command.ExecuteReader())
            {
                while (result.Read())
                {
                    long id = result.GetInt64(0);
                    string destination = result.GetString(1);
                    DateTime departureTime = result.GetDateTime(2);
                    long numberOfSeats = result.GetInt64(3);
                    Trip trip = new Trip(destination, departureTime, numberOfSeats);
                    trip.Id = id;
                    trips.Add(trip);
                }
            }
        }
        return trips;
    }
    
    public Trip Save(Trip trip)
    {
        logger.Info("Saving Trip " + trip);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "INSERT INTO Trips (destination, dateDeparture, noOfAvailableSeats) VALUES (@destination, @dateDeparture, @noOfAvailableSeats)";
            IDataParameter destinationParam = command.CreateParameter();
            destinationParam.ParameterName = "@destination";
            destinationParam.Value = trip.Destination;
            command.Parameters.Add(destinationParam);
            IDataParameter departureParam = command.CreateParameter();
            departureParam.ParameterName = "@dateDeparture";
            departureParam.Value = trip.DateDeparture;
            command.Parameters.Add(departureParam);
            IDataParameter seatsParam = command.CreateParameter();
            seatsParam.ParameterName = "@noOfAvailableSeats";
            seatsParam.Value = trip.NoOfAvailableSeats;
            command.Parameters.Add(seatsParam);
            command.ExecuteNonQuery();
        }
        return trip;
    }
    
    public Trip Delete(long id)
    {
        logger.Info("Deleting Trip with id " + id);
        Trip trip = FindOne(id);
        if (trip != null)
        {
            IDbConnection connection = DbUtils.getConnection();
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM Trips WHERE id = @id";
                IDataParameter idParam = command.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                command.Parameters.Add(idParam);
                command.ExecuteNonQuery();
            }
        }
        return trip;
    }
    
    public Trip Update(Trip trip)
    {
        logger.Info("Updating Trip " + trip);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "UPDATE Trips SET destination = @destination, dateDeparture = @dateDeparture, noOfAvailableSeats = @noOfAvailableSeats WHERE id = @id";
            IDataParameter destinationParam = command.CreateParameter();
            destinationParam.ParameterName = "@destination";
            destinationParam.Value = trip.Destination;
            command.Parameters.Add(destinationParam);
            IDataParameter departureParam = command.CreateParameter();
            departureParam.ParameterName = "@dateDeparture";
            departureParam.Value = trip.DateDeparture;
            command.Parameters.Add(departureParam);
            IDataParameter seatsParam = command.CreateParameter();
            seatsParam.ParameterName = "@noOfAvailableSeats";
            seatsParam.Value = trip.NoOfAvailableSeats;
            command.Parameters.Add(seatsParam);
            IDataParameter idParam = command.CreateParameter();
            idParam.ParameterName = "@id";
            idParam.Value = trip.Id;
            command.Parameters.Add(idParam);
            command.ExecuteNonQuery();
        }
        return trip;
    }
}