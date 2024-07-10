package games.persistence.repository;

import games.persistence.IPlayerRepository;
import games.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository
public class PlayerDBRepository implements IPlayerRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public PlayerDBRepository(Properties props) {
        logger.info("Initializing PlayerDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Autowired
    public PlayerDBRepository(
            @Value("${jdbc.driver}") String driver,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.user}") String user,
            @Value("${jdbc.pass}") String pass) {
        Properties props = new Properties();
        props.setProperty("jdbc.driver", driver);
        props.setProperty("jdbc.url", url);
        props.setProperty("jdbc.user", user);
        props.setProperty("jdbc.pass", pass);
        logger.info("Initializing repository.database.PlayerDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Player> findOne(Long id) {
        // method to find a player by id
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Players WHERE id=?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String alias = result.getString("alias");
                    Player player = new Player(alias);
                    player.setId(id);
                    return Optional.of(player);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Player> findAll() {
        // method to find all players
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Player> players = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Players")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    String alias = result.getString("alias");
                    Player player = new Player(alias);
                    player.setId(id);
                    players.add(player);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return players;
    }

    @Override
    public Optional<Player> save(Player entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Player> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Player> update(Player entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Player> findOneByAlias(String alias) {
        // method to find a player by its alias
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Players WHERE alias=?")) {
            preStmt.setString(1, alias);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("id");
                    Player player = new Player(alias);
                    player.setId(id);
                    return Optional.of(player);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return Optional.empty();
    }
}
