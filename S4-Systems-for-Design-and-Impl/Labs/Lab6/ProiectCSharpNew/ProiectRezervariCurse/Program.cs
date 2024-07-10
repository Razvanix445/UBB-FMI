using System;
using System.Collections.Generic;
using System.Diagnostics.Eventing.Reader;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows.Forms;
using log4net;
using log4net.Config;
using ProiectRezervariCurse.domain;
using ProiectRezervariCurse.repository.database;
using ProiectRezervariCurse.repository.interfaces;
using ProiectRezervariCurse.service;

/*
Problema 4.
O firma de transport are mai multe oficii prin tara unde clientii pot suna si pot rezerva locuri pentru diferite
destinatii. Firma foloseste un sistem soft pentru gestiunea rezervarilor. Persoana de la oficiu foloseste o aplicatie
desktop cu urmatoarele functionalitati:
1. Login. Dupa autentificarea cu succes, o noua fereastra se deschide in care sunt afisate toate cursele
(destinatia, data si ora plecarii si numarul de locuri disponibile). Pentru o anumita destinatie pot exista
mai multe curse.
2. Cautare. Dupa autentificarea cu succes, persoana de la oficiu poate cauta o anumita cursa introducand
destinatia, data si ora plecarii. Aplicatia va afisa in alta lista/alt tabel toate locurile pentru cursa
respectiva: numarul locului si numele clientului care a rezervat locul respectiv (daca locul este deja
rezervat). Daca un loc nu este rezervat se va afisa '-'. Fiecare autocar are 18 locuri pentru clienti.
3. Rezervare. Persoana de la oficiu poate rezerva locuri pentru clienti la o anumita cursa. Cand se face o
rezervare se introduce numele clientului si numarul de locuri pe care doreste sa le rezerve. Dupa ce s-a
facut o rezervare, toate persoanele de la celelalte oficii vad lista actualizata a curselor si a numarului de
locuri disponibile. De asemenea numele clientului apare la rezultatul afisat la cerinta 2.
4. Logout
* */

namespace ProiectRezervariCurse
{
    static class Program
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));
        private static readonly IReservationManagerRepository reservationManagerRepository = new ReservationManagerDBRepository();
        private static readonly IReservationRepository reservationRepository = new ReservationDBRepository();
        private static readonly ITripRepository tripRepository = new TripDBRepository();
        private static readonly Service service = new Service(reservationManagerRepository, reservationRepository, tripRepository);
        
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
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
            
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginForm(service));
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
            /*
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
            */
            
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
}