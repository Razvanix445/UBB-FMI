package app.persistence.repository;

import app.model.Game;
import app.model.Position;
import app.persistence.IPositionRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PositionDBRepository implements IPositionRepository {

    private static final Logger logger = LogManager.getLogger(PositionDBRepository.class);

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
}
