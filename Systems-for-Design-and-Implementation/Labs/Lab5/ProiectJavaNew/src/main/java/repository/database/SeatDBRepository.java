package repository.database;

import domain.Reservation;
import domain.Seat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.ISeatRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class SeatDBRepository implements ISeatRepository {

    private JdbcUtils dbUtils;
    private ReservationDBRepository reservationRepository;
    private static final Logger logger = LogManager.getLogger();

    public SeatDBRepository(Properties props) {
        logger.info("Initializing SeatDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
        reservationRepository = new ReservationDBRepository(props);
    }

    @Override
    public Optional<Seat> findOne(Long id) {
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Seats where id=?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long reservationId = result.getLong("reservation_id");
                    Reservation reservation = reservationRepository.findOne(reservationId).orElse(null);
                    if (reservation == null) {
                        return Optional.empty();
                    }
                    Long seatNumber = result.getLong("seat_number");

                    Seat seat = new Seat(reservation, seatNumber);
                    seat.setId(id);
                    return Optional.of(seat);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Seat> findAll() {
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Seat> seats = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Seats")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    Seat seat = findOne(id).orElse(null);
                    if (seat != null) {
                        seats.add(seat);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return seats;
    }

    @Override
    public Optional<Seat> save(Seat seat) {
        return Optional.empty();
    }

    @Override
    public Optional<Seat> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Seat> update(Seat seat) {
        return Optional.empty();
    }
}