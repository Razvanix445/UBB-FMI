package games.persistence.repository;

import games.model.Game;
import games.model.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import games.persistence.IPositionRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;

@Repository
public class PositionDBRepository implements IPositionRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public PositionDBRepository(Properties props) {
        logger.info("Initializing PositionDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Position> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAll() {
        return null;
    }

    @Override
    public Optional<Position> save(Position position) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(position));
        return Optional.of(position);
    }

    @Override
    public Optional<Position> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Position> update(Position entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAllForGame(Game game) {
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
