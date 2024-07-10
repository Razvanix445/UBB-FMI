package games.persistence.repository;

import games.persistence.IGameRepository;
import games.model.Game;
import games.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

@Component
public class GameDBRepositoryHibernate implements IGameRepository {

    private static final Logger logger = LogManager.getLogger();

    public GameDBRepositoryHibernate() {
        logger.info("Initializing GameDBRepositoryHibernate");
    }

    @Override
    public Iterable<Game> findAllByPlayer(Player player) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String hql = "FROM Game WHERE player.alias = :alias";
            return session.createQuery(hql, Game.class)
                    .setParameter("alias", player.getAlias())
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding games for player: " + player, e);
            return null;
        }
    }

    @Override
    public Optional<Game> findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Game WHERE id=:idM", Game.class)
                    .setParameter("idM", id)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Game> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Game ", Game.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Games", e);
            return null;
        }
    }

    @Override
    public Optional<Game> save(Game entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return Optional.of(entity);
    }

    @Override
    public Optional<Game> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Game> update(Game entity) {
        return Optional.empty();
    }
}
