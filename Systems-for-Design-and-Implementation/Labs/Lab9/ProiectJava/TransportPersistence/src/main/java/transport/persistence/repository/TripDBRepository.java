package transport.persistence.repository;

import transport.model.Trip;
import transport.persistence.ITripRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class TripDBRepository implements ITripRepository {

    private JdbcUtils dbUtils;
    //private static final Logger logger = LogManager.getLogger();

    public TripDBRepository(Properties props) {
        //logger.info("Initializing TripDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Trip> findOne(Long id) {
        // method to find a trip by id
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Trips where id=?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String destination = result.getString("destination");
                    Timestamp timestamp = result.getTimestamp("dateDeparture");
                    LocalDateTime departureTime = timestamp.toLocalDateTime();
                    Long numberOfSeats = result.getLong("noOfAvailableSeats");
                    Trip trip = new Trip(destination, departureTime, numberOfSeats);
                    trip.setId(id);
                    return Optional.of(trip);
                }
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Trip> findAll() {
        // method to find all trips
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Trip> trips = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Trips")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    String destination = result.getString("destination");
                    Timestamp timestamp = result.getTimestamp("dateDeparture");
                    LocalDateTime departureTime = timestamp.toLocalDateTime();
                    Long numberOfSeats = result.getLong("noOfAvailableSeats");
                    Trip trip = new Trip(destination, departureTime, numberOfSeats);
                    trip.setId(id);
                    trips.add(trip);
                }
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return trips;
    }

    @Override
    public Optional<Trip> update(Trip trip) {
        // method to update a trip
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Trips set destination=?, dateDeparture=?, noOfAvailableSeats=? where id=?")) {
            preStmt.setString(1, trip.getDestination());
            LocalDateTime departureTime = trip.getDateDeparture();
            Timestamp timestamp = Timestamp.valueOf(departureTime);
            preStmt.setTimestamp(2, timestamp);
            preStmt.setLong(3, trip.getNoOfAvailableSeats());
            preStmt.setLong(4, trip.getId());
            int result = preStmt.executeUpdate();
            if (result > 0) {
                return Optional.of(trip);
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Trip> save(Trip trip) {
        // method to save a trip
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Trips (destination, dateDeparture, noOfAvailableSeats) values (?,?,?)")) {
            preStmt.setString(1, trip.getDestination());
            LocalDateTime departureTime = trip.getDateDeparture();
            Timestamp timestamp = Timestamp.valueOf(departureTime);
            preStmt.setTimestamp(2, timestamp);
            preStmt.setLong(3, trip.getNoOfAvailableSeats());
            int result = preStmt.executeUpdate();
            if (result > 0) {
                return Optional.of(trip);
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Trip> delete(Long id) {
        // method to delete a trip by id
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Optional<Trip> trip = findOne(id);
        if (trip.isPresent()) {
            try (PreparedStatement preStmt = con.prepareStatement("delete from Trips where id=?")) {
                preStmt.setLong(1, id);
                int result = preStmt.executeUpdate();
                if (result > 0) {
                    return trip;
                }
            } catch (SQLException e) {
                //logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        return Optional.empty();
    }
}
