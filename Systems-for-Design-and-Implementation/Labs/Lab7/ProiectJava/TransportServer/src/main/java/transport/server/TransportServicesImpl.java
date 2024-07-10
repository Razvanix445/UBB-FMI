package transport.server;

import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;
import transport.persistence.IReservationManagerRepository;
import transport.persistence.IReservationRepository;
import transport.persistence.ISeatRepository;
import transport.persistence.ITripRepository;
import transport.services.ITransportObserver;
import transport.services.ITransportServices;
import transport.services.TransportException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

public class TransportServicesImpl implements ITransportServices {

    private IReservationManagerRepository reservationManagerRepository;
    private IReservationRepository reservationRepository;
    private ITripRepository tripRepository;
    private ISeatRepository seatRepository;

    private Map<String, ITransportObserver> loggedClients;

    public TransportServicesImpl(IReservationManagerRepository reservationManagerRepository, IReservationRepository reservationRepository, ITripRepository tripRepository, ISeatRepository seatRepository) {
        this.reservationManagerRepository = reservationManagerRepository;
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
        loggedClients=new ConcurrentHashMap<>();
    }

    public synchronized void login(ReservationManager reservationManager, ITransportObserver client) throws TransportException {
        ReservationManager foundManager = searchReservationManagerByName(reservationManager.getName());
        if (foundManager != null){
            if(loggedClients.get(reservationManager.getName()) != null)
                throw new TransportException("User already logged in.");
            if (reservationManager.getPassword().equals(foundManager.getPassword())) {
                loggedClients.put(reservationManager.getName(), client);
            } else
                throw new TransportException("Authentication failed.");
        } else
            throw new TransportException("Authentication failed.");
    }

    public synchronized ReservationManager searchReservationManagerByName(String name) throws TransportException {
        Iterable<ReservationManager> reservationManagers = reservationManagerRepository.findAll();
        for (ReservationManager reservationManager : reservationManagers) {
            if (reservationManager.getName().equals(name)) {
                return reservationManager;
            }
        }
        return null;
    }

    public synchronized Trip[] getAllTrips() throws TransportException {
        Iterable<Trip> trips = tripRepository.findAll();
        System.out.println("Getting all trips... ");
        Set<Trip> result = new TreeSet<>();
        trips.forEach(result::add);
        return result.toArray(new Trip[result.size()]);
    }

    public synchronized Trip searchTripById(Long id) throws TransportException {
        return tripRepository.findOne(id).orElseThrow(() -> new TransportException("Error getting trip"));
    }

    public synchronized void addReservation(Reservation reservation) throws TransportException {
        Trip trip = searchTripById(reservation.getTrip().getId());
        trip.setNoOfAvailableSeats(trip.getNoOfAvailableSeats() - reservation.getReservedSeats().size());
        trip = tripRepository.update(trip).orElseThrow(() -> new TransportException("Error updating trip"));
        reservationRepository.save(reservation).orElseThrow(() -> new TransportException("Error adding reservation"));
        for (ITransportObserver client: loggedClients.values()) {
            try {
                client.reservationAdded(reservation);
            } catch (TransportException e) {
                System.err.println("Error notifying client in TransportServicesImpl: " + e);
            }
        }
    }

    public synchronized List<Reservation> getReservationsByTrip(Long tripId) throws TransportException {
        Iterable<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getTrip().getId().equals(tripId)) {
                result.add(reservation);
            }
        }
        return result;
    }

    public synchronized List<Seat> getSeatsByReservation(Long reservationId) throws TransportException {
        return StreamSupport.stream(seatRepository.findSeatsByReservationId(reservationId).spliterator(), false).toList();
    }

    public synchronized List<Seat> getSeatsByTripId(Long tripId) throws TransportException {
        return StreamSupport.stream(seatRepository.findSeatsByTripId(tripId).spliterator(), false).toList();
    }

    public synchronized void logout(ReservationManager reservationManager, ITransportObserver client) throws TransportException {
        ITransportObserver localClient = loggedClients.remove(reservationManager.getName());
        if (localClient == null)
            throw new TransportException("User " + reservationManager.getName() + " is not logged in.");
    }
}
