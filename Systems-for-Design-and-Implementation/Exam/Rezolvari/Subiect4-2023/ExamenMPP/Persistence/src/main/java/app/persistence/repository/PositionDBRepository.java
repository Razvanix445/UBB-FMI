package app.persistence.repository;

import app.model.Game;
import app.model.Position;
import app.persistence.IPositionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PositionDBRepository implements IPositionRepository {

    private static final Logger logger = LogManager.getLogger();

    public PositionDBRepository() {
        logger.info("Initializing PositionDBRepository");
    }

    @Override
    public Optional<Position> findOne(Long id) {
        final AtomicReference<Optional<Position>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Position player = session.get(Position.class, id);
            result.set(Optional.ofNullable(player));
        });

        return result.get();
    }

    @Override
    public Iterable<Position> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Position ", Position.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Positions", e);
            return null;
        }
    }

    @Override
    public Iterable<Position> findAllByGame(Game game) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String hql = "FROM Position WHERE game = :game";
            return session.createQuery(hql, Position.class)
                    .setParameter("game", game)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding positions for game: " + game, e);
            return null;
        }
    }

    @Override
    public Optional<Position> save(Position position) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(position));
        return Optional.of(position);
    }

    @Override
    public Optional<Position> delete(Long id) {
        final AtomicReference<Optional<Position>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Position player = session.get(Position.class, id);
            if (player != null) {
                session.delete(player);
                result.set(Optional.of(player));
            } else {
                result.set(Optional.empty());
            }
        });

        return result.get();
    }

    @Override
    public Optional<Position> update(Position entity) {
        final AtomicReference<Optional<Position>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Position updatedPosition = (Position) session.merge(entity);
            result.set(Optional.ofNullable(updatedPosition));
        });

        return result.get();
    }
}



    /*@Autowired
    public PositionDBRepository(
            @Value("${jdbc.driver}") String driver,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.user}") String user,
            @Value("${jdbc.pass}") String pass) {
        Properties props = new Properties();
        props.setProperty("jdbc.driver", driver);
        props.setProperty("jdbc.url", url);
        props.setProperty("jdbc.user", user);
        props.setProperty("jdbc.pass", pass);
        logger.info("Initializing repository.database.PositionDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Position> findOne(Long id) {
        // method to find a position by id
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Positions WHERE id=?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long gameId = result.getLong("game_id");
                    Long positionIndex = result.getLong("position_index");
                    Long coordinateX = result.getLong("coordinateX");
                    Long coordinateY = result.getLong("coordinateY");

                    Game game = findGameById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
                    if (game != null) {
                        Position position = new Position(game, positionIndex, coordinateX, coordinateY);
                        position.setId(id);
                        return Optional.of(position);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAll() {
        // method to find all positions
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Position> positions = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Positions")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    Long gameId = result.getLong("game_id");
                    Long positionIndex = result.getLong("position_index");
                    Long coordinateX = result.getLong("coordinateX");
                    Long coordinateY = result.getLong("coordinateY");

                    Game game = findGameById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
                    if (game != null) {
                        Position position = new Position(game, positionIndex, coordinateX, coordinateY);
                        position.setId(id);
                        positions.add(position);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return positions;
    }

    @Override
    public Iterable<Position> findAllByGame(Game game) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Position> positions = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Positions WHERE game_id = ?")) {
            preStmt.setLong(1, game.getId());
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    Long positionIndex = result.getLong("position_index");
                    Long coordinateX = result.getLong("coordinateX");
                    Long coordinateY = result.getLong("coordinateY");

                    Position position = new Position(game, positionIndex, coordinateX, coordinateY);
                    position.setId(id);
                    positions.add(position);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return positions;
    }

    @Override
    public Optional<Position> save(Position position) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "INSERT INTO Positions (game_id, position_index, coordinateX, coordinateY) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            preStmt.setLong(1, position.getGame().getId());
            preStmt.setLong(2, position.getPositionIndex());
            preStmt.setLong(3, position.getCoordinateX());
            preStmt.setLong(4, position.getCoordinateY());
            int result = preStmt.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = preStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        position.setId(rs.getLong(1));
                    }
                }
                return Optional.of(position);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Position> delete(Long id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Optional<Position> position = findOne(id);
        if (position.isPresent()) {
            try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Positions WHERE id = ?")) {
                preStmt.setLong(1, id);
                preStmt.executeUpdate();
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("Error Database: " + e);
                return Optional.empty();
            }
        }
        return position;
    }

    @Override
    public Optional<Position> update(Position position) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "UPDATE Positions SET game_id = ?, position_index = ?, coordinateX = ?, coordinateY = ? WHERE id = ?")) {
            preStmt.setLong(1, position.getGame().getId());
            preStmt.setLong(2, position.getPositionIndex());
            preStmt.setLong(3, position.getCoordinateX());
            preStmt.setLong(4, position.getCoordinateY());
            preStmt.setLong(5, position.getId());
            int result = preStmt.executeUpdate();

            if (result > 0) {
                return Optional.of(position);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }
        return Optional.empty();
    }

    public Optional<Game> findGameById(Long gameId) {
        logger.traceEntry("find game with id {}", gameId);
        Connection con = dbUtils.getConnection();
        Game game = null;

        try (PreparedStatement gameStmt = con.prepareStatement("SELECT * FROM Games WHERE id = ?")) {
            gameStmt.setLong(1, gameId);

            try (ResultSet gameResult = gameStmt.executeQuery()) {
                if (gameResult.next()) {
                    Long playerId = gameResult.getLong("player_id");

                    // Find the player by ID
                    Player player = findPlayerById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

                    Long id = gameResult.getLong("id");
                    Long score = gameResult.getLong("score");
                    Long noOfSeconds = gameResult.getLong("no_of_seconds");

                    game = new Game(player, score, noOfSeconds);
                    game.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }

        return Optional.of(game);
    }

    public Optional<Player> findPlayerById(Long playerId) {
        logger.traceEntry("find player with id {}", playerId);
        Connection con = dbUtils.getConnection();
        Player player = null;

        try (PreparedStatement gameStmt = con.prepareStatement("SELECT * FROM Players WHERE id = ?")) {
            gameStmt.setLong(1, playerId);

            try (ResultSet gameResult = gameStmt.executeQuery()) {
                if (gameResult.next()) {
                    Long id = gameResult.getLong("id");
                    String alias = gameResult.getString("alias");

                    player = new Player(alias);
                    player.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error Database: " + e);
        }

        return Optional.of(player);
    }
    }*/

