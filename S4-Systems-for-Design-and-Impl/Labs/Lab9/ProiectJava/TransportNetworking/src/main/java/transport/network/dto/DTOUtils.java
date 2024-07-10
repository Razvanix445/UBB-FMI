package transport.network.dto;

import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Trip;

import java.time.LocalDateTime;
import java.util.List;

public class DTOUtils {
    public static ReservationManager getFromDTO(ReservationManagerDTO reservationManagerDTO) {
        String name = reservationManagerDTO.getName();
        String password = reservationManagerDTO.getPassword();
        return new ReservationManager(name, password);
    }

    public static ReservationManagerDTO getDTO(ReservationManager reservationManager) {
        String name = reservationManager.getName();
        String password = reservationManager.getPassword();
        return new ReservationManagerDTO(name, password);
    }

    public static Reservation getFromDTO(ReservationDTO reservationDTO) {
        Trip trip = new Trip(reservationDTO.getTripId());
        List<Long> reservedSeats = reservationDTO.getReservedSeats();
        String clientName = reservationDTO.getClientName();
        return new Reservation(trip, reservedSeats, clientName);
    }

    public static ReservationDTO getDTO(Reservation reservation) {
        Long tripId = reservation.getTrip().getId();
        List<Long> reservedSeats = reservation.getReservedSeats();
        String clientName = reservation.getClientName();
        return new ReservationDTO(tripId, reservedSeats, clientName);
    }

    public static Reservation[] getFromDTO(ReservationDTO[] reservationDTOS){
        Reservation[] reservations = new Reservation[reservationDTOS.length];
        for(int i=0; i<reservationDTOS.length; i++){
            reservations[i] = getFromDTO(reservationDTOS[i]);
        }
        return reservations;
    }

    public static ReservationDTO[] getDTO(Reservation[] reservations){
        ReservationDTO[] resDTO = new ReservationDTO[reservations.length];
        for(int i=0; i<reservations.length; i++)
            resDTO[i] = getDTO(reservations[i]);
        return resDTO;
    }

    public static Trip getFromDTO(TripDTO tripDTO) {
        String destination = tripDTO.getDestination();
        LocalDateTime dateDeparture = tripDTO.getDateDeparture();
        Long noOfAvailableSeats = tripDTO.getNoOfAvailableSeats();
        return new Trip(destination, dateDeparture, noOfAvailableSeats);
    }

    public static TripDTO getDTO(Trip trip) {
        String destination = trip.getDestination();
        LocalDateTime dateDeparture = trip.getDateDeparture();
        Long noOfAvailableSeats = trip.getNoOfAvailableSeats();
        return new TripDTO(destination, dateDeparture, noOfAvailableSeats);
    }

    public static Trip[] getFromDTO(TripDTO[] tripDTOS){
        Trip[] trips = new Trip[tripDTOS.length];
        for(int i=0; i<tripDTOS.length; i++){
            trips[i] = getFromDTO(tripDTOS[i]);
        }
        return trips;
    }

    public static TripDTO[] getDTO(Trip[] trips){
        TripDTO[] tripDTO = new TripDTO[trips.length];
        for(int i=0; i<trips.length; i++)
            tripDTO[i] = getDTO(trips[i]);
        return tripDTO;
    }
}
