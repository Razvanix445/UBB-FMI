
using System.Data.SQLite;
using System.Reflection;
using log4net;
using log4net.Config;
using ProiectCSharp.domain;
using ProiectCSharp.repository.database;

class Program
{
    private static readonly ILog log = LogManager.GetLogger(typeof(Program));

    static void Main(string[] args)
    {
        //log4net.Util.LogLog.InternalDebugging = true;

        var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
        XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));

        log.Info("Hello, World!");

        if (TestConnection())
            log.Info("Test connection successful");
        else
            log.Error("Test connection failed");

        //reservationManagerTesting();
        //tripTesting();
        //reservationTesting();
    }

    private static void reservationManagerTesting()
    {
        ReservationManagerDBRepository repository = new ReservationManagerDBRepository();

        /* ADD */
        ReservationManager reservationManagerForAdding = new ReservationManager("newNameCSharp", "newPasswordCSharp");
        try
        {
            ReservationManager rmAdded = repository.Save(reservationManagerForAdding);
            Console.WriteLine("Added reservation manager: " + rmAdded);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error adding reservation manager: " + ex.Message);
        }
        Console.WriteLine("=======================================================");

        /* UPDATE */
        /*
        reservationManagerForAdding.Password = "updatedPasswordCSharp";
        reservationManagerForAdding.Id = 24;
        try
        {
            repository.Update(reservationManagerForAdding);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error updating reservation manager: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
        */
        
        /* DELETE */
        /*
        try
        {
            repository.Delete(19);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error deleting reservation manager: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
        */

        /* FIND ALL */
        Console.WriteLine("Finding all reservation managers:");
        foreach (var reservation in repository.FindAll())
        {
            Console.WriteLine(reservation);
        }
        Console.WriteLine("=======================================================");
        
        /* FIND ONE */
        Console.WriteLine("Finding reservation manager with id 1:");
        try
        {
            ReservationManager rmFound = repository.FindOne(1);
            Console.WriteLine("Found reservation manager: " + rmFound);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error finding reservation manager: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
    }

    private static void tripTesting()
    {
        TripDBRepository repository = new TripDBRepository();

        /* ADD */
        DateTime date = new DateTime(2029, 11, 11, 18, 0, 0);
        Trip tripForAdding = new Trip("newDestinationCSharp", date, 100);

        try
        {
            Trip tripAdded = repository.Save(tripForAdding);
            Console.WriteLine("Added trip: " + tripAdded);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error adding trip: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
        
        
        /* UPDATE */
        /*
        DateTime dateForUpdating = new DateTime(2029, 11, 11, 18, 0, 0);
        Trip tripForUpdating = new Trip("updatedDestination", dateForUpdating, 15L);
        tripForAdding.Id = 13;
        try
        {
            repository.Update(tripForAdding);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error updating trip: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
        */
        
        /* DELETE */
        /*
        try
        {
            repository.Delete(10);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error deleting trip: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
        */
        
        /* FIND ALL */
        Console.WriteLine("Finding all trips:");
        foreach (var trip in repository.FindAll())
        {
            Console.WriteLine(trip);
        }
        Console.WriteLine("=======================================================");
        
        /* FIND ONE */
        Console.WriteLine("Finding trip with id 1:");
        try
        {
            Trip tripFound = repository.FindOne(1);
            Console.WriteLine("Found trip: " + tripFound);
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error finding trip: " + ex.Message);
        }
        Console.WriteLine("=======================================================");
    }

    private static void reservationTesting()
    {
        ReservationDBRepository reservationDBRepository = new ReservationDBRepository();
        TripDBRepository tripDBRepository = new TripDBRepository();

        /* ADD */
        DateTime date = new DateTime(2029, 11, 11, 18, 0, 0);
        Trip trip = tripDBRepository.FindOne(5);
        if (trip == null)
        {
            Console.WriteLine("Trip not found!");
            return;
        }
        List<long> reservedSeats = new List<long> { 15, 16 };
        Reservation reservationForAdding = new Reservation(trip, reservedSeats, "newClientNameCSharp");
        /*
        Reservation reservationAdded = reservationDBRepository.Save(reservationForAdding);
        if (reservationAdded != null)
        {
            Console.WriteLine("Added new reservation: " + reservationAdded);
        }
        else
        {
            Console.WriteLine("No reservation added");
        }
        Console.WriteLine("=======================================================");
        */
        
        /* DELETE */
        /*
        Reservation reservationDeleted = reservationDBRepository.Delete(10);
        if (reservationDeleted != null)
        {
            Console.WriteLine("Deleted reservation: " + reservationDeleted);
        }
        else
        {
            Console.WriteLine("No reservation found with id 5");
        }
        Console.WriteLine("=======================================================");
        */

        /* UPDATE */
        /*
        Trip tripForUpdating = tripDBRepository.FindOne(5);
        if (tripForUpdating == null)
        {
            Console.WriteLine("Trip not found!");
            return;
        }
        List<long> reservedSeatsForUpdating = new List<long> { 12, 13, 14 };
        Reservation reservationForUpdating = new Reservation(tripForUpdating, reservedSeatsForUpdating, "updatedClientNameCSharp");
        reservationForUpdating.Id = 9;
        Reservation reservationUpdated = reservationDBRepository.Update(reservationForUpdating);
        if (reservationUpdated != null)
        {
            Console.WriteLine("Updated reservation: " + reservationUpdated);
        }
        else
        {
            Console.WriteLine("No reservation found with id 5");
        }
        Console.WriteLine("=======================================================");
        */

        /* FIND ALL */
        Console.WriteLine("All reservations:");
        foreach (var reservation in reservationDBRepository.FindAll())
        {
            Console.WriteLine(reservation);
        }
        Console.WriteLine("=======================================================");

        /* FIND ONE */
        Console.WriteLine("A reservation by id:");
        Reservation reservationFound = reservationDBRepository.FindOne(2);
        if (reservationFound != null)
        {
            Console.WriteLine(reservationFound);
        }
        else
        {
            Console.WriteLine("No reservation found with id 2");
        }
        Console.WriteLine("=======================================================");
    }
    
    public static bool TestConnection()
    {
        try
        {
            using (var connection = DbUtils.getConnection())
            {
                return true;
            }
        }
        catch (Exception ex)
        {
            log.Error("Test connection failed: " + ex.Message);
            return false;
        }
    }
}