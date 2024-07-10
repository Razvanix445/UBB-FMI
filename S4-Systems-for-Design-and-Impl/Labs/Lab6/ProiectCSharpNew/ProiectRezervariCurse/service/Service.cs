using System;
using System.Collections.Generic;
using log4net;
using ProiectRezervariCurse.domain;
using ProiectRezervariCurse.repository.interfaces;

namespace ProiectRezervariCurse.service
{
    public class Service
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Service));

        private IReservationManagerRepository reservationManagerRepository;
        private IReservationRepository reservationRepository;
        private ITripRepository tripRepository;

        public Service(IReservationManagerRepository reservationManagerRepository, IReservationRepository reservationRepository, ITripRepository tripRepository)
        {
            this.reservationManagerRepository = reservationManagerRepository;
            this.reservationRepository = reservationRepository;
            this.tripRepository = tripRepository;
        }
        
        public void AddReservationManager(ReservationManager reservationManager)
        {
            log.Info("Adding reservation manager " + reservationManager);
            reservationManagerRepository.Save(reservationManager);
        }

        public ReservationManager FindReservationManagerByName(string name)
        {
            log.Info("Finding reservation manager by name " + name);
            IEnumerable<ReservationManager> reservationManagers = reservationManagerRepository.FindAll();
            foreach (var reservationManager in reservationManagers)
            {
                if (reservationManager.Name.Equals(name))
                {
                    return reservationManager;
                }
            }
            return null;
        }

        public IEnumerable<Trip> getAllTrips()
        {
            log.Info("Getting all trips");
            return tripRepository.FindAll();
        }
        
        public Trip FindTripById(long id)
        {
            log.Info("Finding trip by id " + id);
            Trip trip = tripRepository.FindOne(id);
            if (trip != null)
            {
                return trip;
            }
            throw new Exception("Trip not found");
        }
        
        public Reservation AddReservation(long tripId, List<long> noOfSeats, string clientName)
        {
            log.Info("Adding reservation ");
            Trip trip = FindTripById(tripId);
            trip.NoOfAvailableSeats -= noOfSeats.Count;
            trip = tripRepository.Update(trip) ?? throw new Exception("Error updating trip");
            Reservation reservation = new Reservation(trip, noOfSeats, clientName);
            reservation = reservationRepository.Save(reservation) ?? throw new Exception("Error saving reservation");
            log.Info("Exiting AddReservation");
            return reservation;
        }
        
        public List<Reservation> getReservationsByTripId(long tripId)
        {
            log.Info("Getting reservations by trip id " + tripId);
            List<Reservation> reservations = new List<Reservation>();
            IEnumerable<Reservation> allReservations = reservationRepository.FindAll();
            foreach (var reservation in allReservations)
            {
                if (reservation.Trip.Id == tripId)
                {
                    reservations.Add(reservation);
                }
            }
            log.Info("Exiting getReservationsByTripId");
            return reservations;
        }
    }
}