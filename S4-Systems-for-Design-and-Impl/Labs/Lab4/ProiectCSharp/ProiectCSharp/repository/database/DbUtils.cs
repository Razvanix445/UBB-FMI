using System.Collections.Specialized;
using System.Configuration;
using System.Data.SQLite;
using System.Data;
using log4net;

public static class DbUtils
{
    private static readonly ILog log = LogManager.GetLogger(typeof(DbUtils));

    public static IDbConnection getConnection()
    {
        string connectionString = ConfigurationManager.ConnectionStrings["Default"]?.ConnectionString;
        if (connectionString == null)
        {
            log.Error("Connection string is null. Exiting.");
            return null;
        }
        log.Info("Connection string: " + connectionString);
        IDbConnection instance = new SQLiteConnection(connectionString);
        instance.Open();
        return instance;
    }
}