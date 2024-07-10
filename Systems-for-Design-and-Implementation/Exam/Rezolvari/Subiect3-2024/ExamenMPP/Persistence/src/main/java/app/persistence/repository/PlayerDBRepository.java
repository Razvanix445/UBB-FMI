package app.persistence.repository;

import app.model.Player;
import app.persistence.IPlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PlayerDBRepository implements IPlayerRepository {

    private static final Logger logger = LogManager.getLogger(PlayerDBRepository.class);

    public PlayerDBRepository() {
        logger.info("Initializing PlayerDBRepository");
    }

    @Override
    public Optional<Player> findOne(Long id) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player player = session.get(Player.class, id);
            result.set(Optional.ofNullable(player));
        });

        return result.get();
    }

    @Override
    public Iterable<Player> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Player ", Player.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Players", e);
            return null;
        }
    }

    @Override
    public Optional<Player> findOneByAlias(String alias) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Query<Player> query = session.createQuery("FROM Player WHERE alias = :alias", Player.class);
            query.setParameter("alias", alias);
            Player player = query.uniqueResult();
            result.set(Optional.ofNullable(player));
        });

        return result.get();
    }

    @Override
    public Optional<Player> save(Player player) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(player));
        return Optional.of(player);
    }

    @Override
    public Optional<Player> delete(Long id) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player player = session.get(Player.class, id);
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
    public Optional<Player> update(Player entity) {
        final AtomicReference<Optional<Player>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player updatedPlayer = (Player) session.merge(entity);
            result.set(Optional.ofNullable(updatedPlayer));
        });

        return result.get();
    }
}
