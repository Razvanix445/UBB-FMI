using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Transport.Model.transport.model;
using TransportModel.transport.model;
using TransportPersistence.transport.persistence;
using TransportServices.transport.services;

namespace TransportServer.transport.server
{
    public class TransportServiceImpl : ITransportServices
    {
        private IReservationManagerRepository reservationManagerRepository;
        private IReservationRepository reservationRepository;
        private ITripRepository tripRepository;
        private ISeatRepository seatRepository;

        private readonly IDictionary<string, ITransportObserver> loggedClients;
        
        public TransportServiceImpl(IReservationManagerRepository reservationManagerRepository, IReservationRepository reservationRepository, ITripRepository tripRepository, ISeatRepository seatRepository)
        {
            this.reservationManagerRepository = reservationManagerRepository;
            this.reservationRepository = reservationRepository;
            this.tripRepository = tripRepository;
            this.seatRepository = seatRepository;
            this.loggedClients = new Dictionary<string, ITransportObserver>();
        }
        
        public ReservationManager Login(ReservationManager reservationManager, ITransportObserver client)
        {
            lock (this)
            {
                Console.WriteLine("Reservation Manager in ServiceImpl: " + reservationManager);
                IEnumerable<ReservationManager> reservationManagers = reservationManagerRepository.FindAll();
                ReservationManager foundManager = reservationManagers.FirstOrDefault(rm => rm.Name == reservationManager.Name);
                Console.WriteLine("Found ReservationManager in ServiceImpl: " + foundManager);
                if (foundManager != null)
                {
                    if (loggedClients.ContainsKey(reservationManager.Name))
                        throw new TransportException("User already logged in.");
                    if (reservationManager.Password == foundManager.Password)
                    {
                        loggedClients[foundManager.Name] = client;
                        return foundManager;
                    }
                    else
                        throw new TransportException("Authentication failed.");
                }
                else
                    throw new TransportException("Authentication failed.");
            }
        }

        public ReservationManager SearchReservationManagerByName(string name)
        {
            lock (this)
            {
                IEnumerable<ReservationManager> reservationManagers = reservationManagerRepository.FindAll();
                return reservationManagers.FirstOrDefault(reservationManager => reservationManager.Name == name);
            }
        }

        public Trip[] GetAllTrips()
        {
            lock (this)
            {
                IEnumerable<Trip> trips = tripRepository.FindAll();
                Console.WriteLine("Getting all trips... ");
                IList<Trip> tripsList = trips.ToList();
                return tripsList.ToArray();
            }
        }

        public Trip SearchTripById(long id)
        {
            lock (this)
            {
                return tripRepository.FindOne(id) ?? throw new TransportException("Error getting trip");
            }
        }

        public Reservation AddReservation(Reservation reservation)
        {
            lock (this)
            {
                Trip trip = SearchTripById(reservation.Trip.Id);
                trip.NoOfAvailableSeats -= reservation.ReservedSeats.Count;
                trip = tripRepository.Update(trip) ?? throw new TransportException("Error updating trip");
                var savedReservation = reservationRepository.Save(reservation);
                if (savedReservation == null)
                {
                    throw new TransportException("Error adding reservation");
                }
                
                foreach (ITransportObserver client in loggedClients.Values)
                {
                    try
                    {
                        Task.Run(() => client.ReservationAdded(reservation));
                    }
                    catch (TransportException e)
                    {
                        Console.Error.WriteLine("Error notifying client in TransportServicesImpl: " + e);
                    }
                }
                return savedReservation;
            }
        }

        public List<Reservation> GetReservationsByTrip(Trip trip)
        {
            lock (this)
            {
                IEnumerable<Reservation> reservations = reservationRepository.FindAll();
                List<Reservation> reservationsList = reservations.Where(reservation => reservation.Trip.Id == trip.Id).ToList();
                return reservationsList;
            }
        }

        public List<Seat> GetSeatsByReservation(long reservationId)
        {
            lock (this)
            {
                return seatRepository.FindSeatsByReservationId(reservationId).ToList();
            }
        }

        public List<Seat> GetSeatsByTrip(Trip trip)
        {
            lock (this)
            {
                return seatRepository.FindSeatsByTripId(trip.Id).ToList();
            }
        }

        public ReservationManager Logout(ReservationManager reservationManager, ITransportObserver client)
        {
            lock (this)
            {
            ITransportObserver localClient = loggedClients[reservationManager.Name];
            if (localClient == null)
                throw new TransportException("User " + reservationManager.Name + " is not logged in.");
            }
            loggedClients.Remove(reservationManager.Name);
            return reservationManager;
        }
    }
}