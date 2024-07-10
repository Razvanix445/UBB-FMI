package service;

import domain.Reservation;
import domain.ReservationManager;
import domain.Seat;
import domain.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.IReservationManagerRepository;
import repository.interfaces.IReservationRepository;
import repository.interfaces.ISeatRepository;
import repository.interfaces.ITripRepository;

import java.util.ArrayList;
import java.util.List;

public class Service {

    private static final Logger logger = LogManager.getLogger();

    private IReservationManagerRepository reservationManagerRepository;
    private IReservationRepository reservationRepository;
    private ITripRepository tripRepository;
    private ISeatRepository seatRepository;

    public Service(IReservationManagerRepository reservationManagerRepository, IReservationRepository reservationRepository, ITripRepository tripRepository, ISeatRepository seatRepository) {
        this.reservationManagerRepository = reservationManagerRepository;
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
    }

    public ReservationManager addReservationManager(ReservationManager reservationManager) {
        return reservationManagerRepository.save(reservationManager).orElseThrow(() -> new RuntimeException("Error adding reservation manager"));
    }

    public ReservationManager deleteReservationManager(Long id) {
        return reservationManagerRepository.delete(id).orElseThrow(() -> new RuntimeException("Error deleting reservation manager"));
    }

    public ReservationManager updateReservationManager(ReservationManager reservationManager) {
        return reservationManagerRepository.update(reservationManager).orElseThrow(() -> new RuntimeException("Error updating reservation manager"));
    }

    public Iterable<ReservationManager> getAllReservationManagers() {
        return reservationManagerRepository.findAll();
    }

    public ReservationManager searchReservationManagerById(Long id) {
        return reservationManagerRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error getting reservation manager"));
    }

    public ReservationManager searchReservationManagerByName(String name) {
        //logger.traceEntry();
        Iterable<ReservationManager> reservationManagers = reservationManagerRepository.findAll();
        for (ReservationManager reservationManager : reservationManagers) {
            if (reservationManager.getName().equals(name)) {
                return reservationManager;
            }
        }
        logger.traceExit();
        return null;
    }

    public Trip addTrip(Trip trip) {
        return tripRepository.save(trip).orElseThrow(() -> new RuntimeException("Error adding trip"));
    }

    public Trip deleteTrip(Long id) {
        return tripRepository.delete(id).orElseThrow(() -> new RuntimeException("Error deleting trip"));
    }

    public Trip updateTrip(Trip trip) {
        return tripRepository.update(trip).orElseThrow(() -> new RuntimeException("Error updating trip"));
    }

    public Iterable<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip searchTripById(Long id) {
        return tripRepository.findOne(id).orElseThrow(() -> new RuntimeException("Error getting trip"));
    }

    public Reservation addReservation(Long tripId, List<Long> seats, String clientName) {
        logger.traceEntry();
        Trip trip = searchTripById(tripId);
        trip.setNoOfAvailableSeats(trip.getNoOfAvailableSeats() - seats.size());
        trip = tripRepository.update(trip).orElseThrow(() -> new RuntimeException("Error updating trip"));
        Reservation reservation = new Reservation(trip, seats, clientName);
        reservation = reservationRepository.save(reservation).orElseThrow(() -> new RuntimeException("Error adding reservation"));
        logger.traceExit();
        return reservation;
    }

    public List<Reservation> getReservationsByTrip(Long tripId) {
        //logger.traceEntry();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> reservationsList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getTrip().getId().equals(tripId)) {
                reservationsList.add(reservation);
            }
        }
        logger.traceExit();
        return reservationsList;
    }

    public List<Seat> getSeatsByReservation(Long reservationId) {
        //logger.traceEntry();
        Iterable<Seat> seats = seatRepository.findAll();
        List<Seat> seatsList = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getReservation().getId().equals(reservationId)) {
                seatsList.add(seat);
            }
        }
        //logger.traceExit();
        return seatsList;
    }
}
