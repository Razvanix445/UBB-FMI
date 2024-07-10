import domain.Reservation;
import domain.ReservationManager;
import domain.Trip;
import repository.database.ReservationDBRepository;
import repository.database.ReservationManagerDBRepository;
import repository.database.SeatDBRepository;
import repository.database.TripDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ConsoleMain {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        //reservationManagerTesting(props);
        tripTesting(props);
        //reservationTesting(props);
        //seatTesting(props);
    }

    private static void reservationManagerTesting(Properties props) {
        /* ReservationManager */
        ReservationManagerDBRepository reservationManagerDBRepository = new ReservationManagerDBRepository(props);

        /* ADD */
        ReservationManager rmForAdding = new ReservationManager("newName", "newPassword");
        Optional<ReservationManager> rmAdded = reservationManagerDBRepository.save(rmForAdding);
        if (rmAdded.isPresent()) {
            System.out.println("Added new reservation manager: " + rmAdded.get());
        } else {
            System.out.println("No reservation manager added");
        }
        System.out.println("=======================================================");

//        /* DELETE */
//        Optional<ReservationManager> rmDeleted = reservationManagerDBRepository.delete(8L);
//        if (rmDeleted.isPresent()) {
//            System.out.println("Deleted reservation manager: " + rmDeleted.get());
//        } else {
//            System.out.println("No reservation manager found with id 5");
//        }
//        System.out.println("=======================================================");
//
//        /* UPDATE */
//        ReservationManager rmForUpdating = new ReservationManager("updatedName", "updatedPassword");
//        rmForUpdating.setId(7L);
//        Optional<ReservationManager> rmUpdated = reservationManagerDBRepository.update(rmForUpdating);
//        if (rmUpdated.isPresent()) {
//            System.out.println("Updated reservation manager: " + rmUpdated.get());
//        } else {
//            System.out.println("No reservation manager found with id 5");
//        }
//        System.out.println("=======================================================");

        /* FIND ALL */
        System.out.println("All reservation managers:");
        reservationManagerDBRepository.findAll().forEach(System.out::println);
        System.out.println("=======================================================");

        /* FIND ONE */
        System.out.println("A reservation manager by id:");
        if (reservationManagerDBRepository.findOne(2L).isPresent()) {
            System.out.println(reservationManagerDBRepository.findOne(2L).get());
        } else {
            System.out.println("No reservation manager found with id 2");
        }
        System.out.println("=======================================================");
    }

    private static void tripTesting(Properties props) {
        /* Trip */
        TripDBRepository tripDBRepository = new TripDBRepository(props);

        /* ADD */
//        LocalDateTime date = LocalDateTime.of(2029, 11, 11, 18, 0);
//        Trip tripForAdding = new Trip("Alba", date, 18L);
//        Optional<Trip> tripAdded = tripDBRepository.save(tripForAdding);
//        if (tripAdded.isPresent()) {
//            System.out.println("Added new trip: " + tripAdded.get());
//        } else {
//            System.out.println("No trip added");
//        }
//        System.out.println("=======================================================");

//        /* DELETE */
//        Optional<Trip> tripDeleted = tripDBRepository.delete(4L);
//        if (tripDeleted.isPresent()) {
//            System.out.println("Deleted trip: " + tripDeleted.get());
//        } else {
//            System.out.println("No trip found with id 5");
//        }
//        System.out.println("=======================================================");

        /* UPDATE */
//        LocalDateTime dateForUpdating = LocalDateTime.of(2029, 11, 11, 18, 0);
//        Trip tripForUpdating = new Trip("updatedDestination", dateForUpdating, 18L);
//        tripForUpdating.setId(7L);
//        Optional<Trip> tripUpdated = tripDBRepository.update(tripForUpdating);
//        if (tripUpdated.isPresent()) {
//            System.out.println("Updated trip: " + tripUpdated.get());
//        } else {
//            System.out.println("No trip found with id 5");
//        }
//        System.out.println("=======================================================");

        /* FIND ALL */
        System.out.println("All trips:");
        tripDBRepository.findAll().forEach(System.out::println);
        System.out.println("=======================================================");

        /* FIND ONE */
        System.out.println("A trip by id:");
        if (tripDBRepository.findOne(2L).isPresent()) {
            System.out.println(tripDBRepository.findOne(2L).get());
        } else {
            System.out.println("No trip found with id 2");
        }
        System.out.println("=======================================================");
    }

    private static void reservationTesting(Properties props) {
        /* Reservation */
        ReservationDBRepository reservationDBRepository = new ReservationDBRepository(props);
        TripDBRepository tripDBRepository = new TripDBRepository(props);

        /* ADD */
        LocalDateTime date = LocalDateTime.of(2029, 11, 11, 18, 0);
        Trip trip = tripDBRepository.findOne(5L).orElse(null);
        if (trip == null) {
            System.out.println("Trip not found!");
            return;
        }
        List<Long> reservedSeats = new ArrayList<>();
        reservedSeats.add(15L);
        reservedSeats.add(16L);
        Reservation reservationForAdding = new Reservation(trip, reservedSeats, "newClientName");
        Optional<Reservation> reservationAdded = reservationDBRepository.save(reservationForAdding);
        if (reservationAdded.isPresent()) {
            System.out.println("Added new reservation: " + reservationAdded.get());
        } else {
            System.out.println("No reservation added");
        }
        System.out.println("=======================================================");

        /* DELETE */
        Optional<Reservation> reservationDeleted = reservationDBRepository.delete(4L);
        if (reservationDeleted.isPresent()) {
            System.out.println("Deleted reservation: " + reservationDeleted.get());
        } else {
            System.out.println("No reservation found with id 5");
        }
        System.out.println("=======================================================");

        /* UPDATE */
        Trip tripForUpdating = tripDBRepository.findOne(5L).orElse(null);
        if (tripForUpdating == null) {
            System.out.println("Trip not found!");
            return;
        }
        List<Long> reservedSeatsForUpdating = new ArrayList<>();
        reservedSeatsForUpdating.add(12L);
        reservedSeatsForUpdating.add(13L);
        reservedSeatsForUpdating.add(14L);
        Reservation reservationForUpdating = new Reservation(tripForUpdating, reservedSeatsForUpdating, "updatedClientName");
        reservationForUpdating.setId(9L);
        Optional<Reservation> reservationUpdated = reservationDBRepository.update(reservationForUpdating);
        if (reservationUpdated.isPresent()) {
            System.out.println("Updated reservation: " + reservationUpdated.get());
        } else {
            System.out.println("No reservation found with id 5");
        }
        System.out.println("=======================================================");

        /* FIND ALL */
        System.out.println("All reservations:");
        reservationDBRepository.findAll().forEach(System.out::println);
        System.out.println("=======================================================");

        /* FIND ONE */
        System.out.println("A reservation by id:");
        if (reservationDBRepository.findOne(2L).isPresent()) {
            System.out.println(reservationDBRepository.findOne(2L).get());
        } else {
            System.out.println("No reservation found with id 2");
        }
        System.out.println("=======================================================");
    }

    private static void seatTesting(Properties props) {
        /* Seat */
        SeatDBRepository seatDBRepository = new SeatDBRepository(props);
        ReservationDBRepository reservationDBRepository = new ReservationDBRepository(props);

        /* FIND ALL */
        System.out.println("All seats:");
        seatDBRepository.findAll().forEach(System.out::println);
        System.out.println("=======================================================");

        /* FIND ONE */
        System.out.println("A reservation by id:");
        if (seatDBRepository.findOne(2L).isPresent()) {
            System.out.println(seatDBRepository.findOne(2L).get());
        } else {
            System.out.println("No seat found with id 2");
        }
        System.out.println("=======================================================");
    }
}