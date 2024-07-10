package transport.persistence.repository;

import transport.model.Reservation;
import transport.model.Trip;
import transport.persistence.IReservationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ReservationDBRepository implements IReservationRepository {

    private JdbcUtils dbUtils;
    //private static final Logger logger = LogManager.getLogger();

    public ReservationDBRepository(Properties props) {
        //logger.info("Initializing ReservationDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        // method to find a reservation by id
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Reservations where id=?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    // TRIP COLUMN
                    Long tripId = result.getLong("trip_id");
                    Trip trip = null;
                    try (PreparedStatement tripStmt = con.prepareStatement("select * from Trips where id=?")) {
                        tripStmt.setLong(1, tripId);
                        try (ResultSet tripResult = tripStmt.executeQuery()) {
                            if (tripResult.next()) {
                                String destination = tripResult.getString("destination");
                                Timestamp dateDeparture = tripResult.getTimestamp("dateDeparture");
                                Long noOfAvailableSeats = tripResult.getLong("noOfAvailableSeats");
                                trip = new Trip(destination, dateDeparture.toLocalDateTime(), noOfAvailableSeats);
                                trip.setId(tripId);
                            }
                        }
                    }
                    if (trip == null) {
                        return Optional.empty();
                    }

                    // RESERVEDSEATS COLUMN
                    List<Long> reservedSeats = new ArrayList<>();
                    try (PreparedStatement seatStmt = con.prepareStatement("select * from Seats where reservation_id=?")) {
                        seatStmt.setLong(1, id);
                        try (ResultSet seatResult = seatStmt.executeQuery()) {
                            while (seatResult.next()) {
                                reservedSeats.add(seatResult.getLong("seat_number"));
                            }
                        }
                    }

                    // CLIENTNAME COLUMN
                    String clientName = result.getString("client_name");

                    Reservation reservation = new Reservation(trip, reservedSeats, clientName);
                    reservation.setId(id);
                    return Optional.of(reservation);
                }
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Reservation> findAll() {
        // method to find all reservations
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Reservations")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    Reservation reservation = findOne(id).orElse(null);
                    if (reservation != null) {
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return reservations;
    }

    @Override
    public Optional<Reservation> update(Reservation reservation) {
        // method to update a reservation
        //logger.traceEntry("updating task {}", reservation);
        Connection con = dbUtils.getConnection();
        try {
            // Update the reservation in the Reservations table
            try (PreparedStatement preStmt = con.prepareStatement("update Reservations set trip_id=?, reserved_seats=?, client_name=? where id=?")) {
                preStmt.setLong(1, reservation.getTrip().getId());
                preStmt.setLong(2, reservation.getReservedSeats().size());
                preStmt.setString(3, reservation.getClientName());
                preStmt.setLong(4, reservation.getId());
                preStmt.executeUpdate();
            }

            // Delete all the old seats from the Seats table associated with the reservation
            try (PreparedStatement seatStmt = con.prepareStatement("delete from Seats where reservation_id=?")) {
                seatStmt.setLong(1, reservation.getId());
                seatStmt.executeUpdate();
            }

            // Insert the new seats in the Seats table
            for (Long seat : reservation.getReservedSeats()) {
                try (PreparedStatement newSeatStmt = con.prepareStatement("insert into Seats (reservation_id, seat_number) values (?, ?)")) {
                    newSeatStmt.setLong(1, reservation.getId());
                    newSeatStmt.setLong(2, seat);
                    newSeatStmt.executeUpdate();
                }
            }

            return Optional.of(reservation);
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> save(Reservation reservation) {
        // method to save a reservation
        //logger.traceEntry("saving task {}", reservation);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Reservations (trip_id, reserved_seats, client_name) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            preStmt.setLong(1, reservation.getTrip().getId());
            preStmt.setLong(2, reservation.getReservedSeats().size());
            preStmt.setString(3, reservation.getClientName());
            preStmt.executeUpdate();

            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select last_insert_rowid()")) {
                if (rs.next()) {
                    reservation.setId(rs.getLong(1));

                    // Insert the seats in the Seats table
                    for (Long seat : reservation.getReservedSeats()) {
                        try (PreparedStatement seatStmt = con.prepareStatement("insert into Seats (reservation_id, seat_number) values (?, ?)")) {
                            seatStmt.setLong(1, reservation.getId());
                            seatStmt.setLong(2, seat);
                            seatStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.of(reservation);
    }

    @Override
    public Optional<Reservation> delete(Long id) {
        // method to delete a reservation by id
        //logger.traceEntry("deleting task with {}", id);
        Connection con = dbUtils.getConnection();
        Optional<Reservation> reservation = findOne(id);
        if (reservation.isPresent()) {
            try {
                // Delete all the seats from the Seats table associated with the reservation
                try (PreparedStatement seatStmt = con.prepareStatement("delete from Seats where reservation_id=?")) {
                    seatStmt.setLong(1, id);
                    seatStmt.executeUpdate();
                }

                // Delete the reservation from the Reservations table
                try (PreparedStatement preStmt = con.prepareStatement("delete from Reservations where id=?")) {
                    preStmt.setLong(1, id);
                    preStmt.executeUpdate();
                }

                return reservation;
            } catch (SQLException e) {
                //logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        return Optional.empty();
    }
}
