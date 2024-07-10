using System.Collections.Generic;
using System.Data;
using log4net;
using TransportModel.transport.model;

namespace TransportPersistence.transport.persistence
{
    public class ReservationManagerDBRepository: IReservationManagerRepository
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(ReservationManagerDBRepository));
    
    public ReservationManagerDBRepository()
    {
        logger.Info("Initializing ReservationManagerDBRepository");
    }

    public ReservationManager FindOne(long id)
    {
        logger.Info("Finding ReservationManager with id " + id);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "SELECT * FROM ReservationManagers WHERE id = @id";
            IDataParameter idParam = command.CreateParameter();
            idParam.ParameterName = "@id";
            idParam.Value = id;
            command.Parameters.Add(idParam);
            using (IDataReader result = command.ExecuteReader())
            {
                if (result.Read())
                {
                    string name = result.GetString(1);
                    string password = result.GetString(2);
                    ReservationManager reservationManager = new ReservationManager(name, password);
                    reservationManager.Id = id;
                    return reservationManager;
                }
            }
        }
        return null;
    }
    
    public IEnumerable<ReservationManager> FindAll()
    {
        logger.Info("Finding all Reservation Managers");
        IDbConnection connection = DbUtils.getConnection();
        IList<ReservationManager> reservationManagers = new List<ReservationManager>();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "SELECT * FROM ReservationManagers";
            using (IDataReader result = command.ExecuteReader())
            {
                while (result.Read())
                {
                    long id = result.GetInt64(0);
                    string name = result.GetString(1);
                    string password = result.GetString(2);
                    ReservationManager reservationManager = new ReservationManager(name, password);
                    reservationManager.Id = id;
                    reservationManagers.Add(reservationManager);
                }
            }
        }
        return reservationManagers;
    }
    
    public ReservationManager Save(ReservationManager reservationManager)
    {
        logger.Info("Saving ReservationManager " + reservationManager);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "INSERT INTO ReservationManagers (name, password) VALUES (@name, @password)";
            IDataParameter nameParam = command.CreateParameter();
            nameParam.ParameterName = "@name";
            nameParam.Value = reservationManager.Name;
            command.Parameters.Add(nameParam);
            IDataParameter passwordParam = command.CreateParameter();
            passwordParam.ParameterName = "@password";
            passwordParam.Value = reservationManager.Password;
            command.Parameters.Add(passwordParam);
            command.ExecuteNonQuery();
        }
        return reservationManager;
    }
    
    public ReservationManager Delete(long id)
    {
        logger.Info("Deleting ReservationManager with id " + id);
        ReservationManager reservationManager = FindOne(id);
        if (reservationManager != null)
        {
            IDbConnection connection = DbUtils.getConnection();
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM ReservationManagers WHERE id = @id";
                IDataParameter idParam = command.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                command.Parameters.Add(idParam);
                command.ExecuteNonQuery();
            }
        }
        return reservationManager;
    }
    
    public ReservationManager Update(ReservationManager reservationManager)
    {
        logger.Info("Updating ReservationManager " + reservationManager);
        IDbConnection connection = DbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "UPDATE ReservationManagers SET name = @name, password = @password WHERE id = @id";
            IDataParameter nameParam = command.CreateParameter();
            nameParam.ParameterName = "@name";
            nameParam.Value = reservationManager.Name;
            command.Parameters.Add(nameParam);
            IDataParameter passwordParam = command.CreateParameter();
            passwordParam.ParameterName = "@password";
            passwordParam.Value = reservationManager.Password;
            command.Parameters.Add(passwordParam);
            IDataParameter idParam = command.CreateParameter();
            idParam.ParameterName = "@id";
            idParam.Value = reservationManager.Id;
            command.Parameters.Add(idParam);
            command.ExecuteNonQuery();
        }
        return reservationManager;
    }
}
}