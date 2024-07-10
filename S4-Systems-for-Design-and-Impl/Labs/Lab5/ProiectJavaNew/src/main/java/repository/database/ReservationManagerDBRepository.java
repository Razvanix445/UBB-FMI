package repository.database;

import domain.ReservationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.IReservationManagerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ReservationManagerDBRepository implements IReservationManagerRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ReservationManagerDBRepository(Properties props) {
        logger.info("Initializing ReservationManagerDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<ReservationManager> findOne(Long id) {
        // method to find a reservation manager by id
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from ReservationManagers where id=?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("name");
                    String password = result.getString("password");
                    ReservationManager reservationManager = new ReservationManager(name, password);
                    reservationManager.setId(id);
                    return Optional.of(reservationManager);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<ReservationManager> findAll() {
        // method to find all reservation managers
        //logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<ReservationManager> reservationManagers = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from ReservationManagers")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    String name = result.getString("name");
                    String password = result.getString("password");
                    ReservationManager reservationManager = new ReservationManager(name, password);
                    reservationManager.setId(id);
                    reservationManagers.add(reservationManager);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return reservationManagers;
    }

    @Override
    public Optional<ReservationManager> update(ReservationManager reservationManager) {
        // method to update a reservation manager
        logger.traceEntry("updating task {}", reservationManager);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update ReservationManagers set name=?, password=? where id=?")) {
            preStmt.setString(1, reservationManager.getName());
            preStmt.setString(2, reservationManager.getPassword());
            preStmt.setLong(3, reservationManager.getId());
            int result = preStmt.executeUpdate();
            if (result > 0) {
                return Optional.of(reservationManager);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ReservationManager> delete(Long id) {
        // method to delete a reservation manager by id
        logger.traceEntry("deleting task with {}", id);
        Connection con = dbUtils.getConnection();
        Optional<ReservationManager> reservationManager = findOne(id);
        if (reservationManager.isPresent()) {
            try (PreparedStatement preStmt = con.prepareStatement("delete from ReservationManagers where id=?")) {
                preStmt.setLong(1, id);
                int result = preStmt.executeUpdate();
                if (result > 0) {
                    return reservationManager;
                }
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("Error DB " + e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ReservationManager> save(ReservationManager reservationManager) {
        logger.traceEntry("saving task {}", reservationManager);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preStmt = connection.prepareStatement("insert into ReservationManagers (name, password) values (?, ?)")) {
            preStmt.setString(1, reservationManager.getName());
            preStmt.setString(2, reservationManager.getPassword());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
            return Optional.of(reservationManager);
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
        return Optional.empty();
    }
}
