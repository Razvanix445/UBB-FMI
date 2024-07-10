package games.persistence.repository;

import games.persistence.IConfigurationRepository;
import games.model.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationDBRepository implements IConfigurationRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ConfigurationDBRepository(Properties props) {
        logger.info("Initializing ConfigurationDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Configuration> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Configuration> findAll() {
        // method to find all configurations
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Configuration> configurations = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Configurations")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    Long coordinateX = result.getLong("coordinateX");
                    Long coordinateY = result.getLong("coordinateY");
                    String hint = result.getString("hint");
                    Configuration configuration = new Configuration(hint, coordinateX, coordinateY);
                    configuration.setId(id);
                    configurations.add(configuration);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return configurations;
    }

    @Override
    public Optional<Configuration> save(Configuration configuration) {
        // method to save a configuration
        logger.traceEntry("saving task {}", configuration);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Configurations (coordinateX, coordinateY, hint) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            preStmt.setLong(1, configuration.getCoordinateX());
            preStmt.setLong(2, configuration.getCoordinateY());
            preStmt.setString(3, configuration.getHint());
            preStmt.executeUpdate();

            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select last_insert_rowid()")) {
                if (rs.next()) {
                    configuration.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return Optional.of(configuration);
    }

    @Override
    public Optional<Configuration> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Configuration> update(Configuration entity) {
        return Optional.empty();
    }
}
