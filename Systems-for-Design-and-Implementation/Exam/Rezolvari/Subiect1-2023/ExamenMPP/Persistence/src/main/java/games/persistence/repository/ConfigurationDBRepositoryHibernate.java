package games.persistence.repository;

import games.persistence.IConfigurationRepository;
import games.model.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class ConfigurationDBRepositoryHibernate implements IConfigurationRepository {

    private static final Logger logger = LogManager.getLogger();

    public ConfigurationDBRepositoryHibernate() {
        logger.info("Initializing ConfigurationDBRepositoryHibernate");
    }

    @Override
    public Optional<Configuration> findOne(Long id) {
        final AtomicReference<Optional<Configuration>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Configuration configuration = session.get(Configuration.class, id);
            result.set(Optional.ofNullable(configuration));
        });

        return result.get();
    }

    @Override
    public Iterable<Configuration> findAll() {
        return null;
    }

    @Override
    public Optional<Configuration> save(Configuration configuration) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(configuration));
        return Optional.of(configuration);
    }

    @Override
    public Optional<Configuration> delete(Long id) {
        final AtomicReference<Optional<Configuration>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Configuration configuration = session.get(Configuration.class, id);
            if (configuration != null) {
                session.delete(configuration);
                result.set(Optional.of(configuration));
            } else {
                result.set(Optional.empty());
            }
        });

        return result.get();
    }

    @Override
    public Optional<Configuration> update(Configuration entity) {
        final AtomicReference<Optional<Configuration>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Configuration updatedConfiguration = (Configuration) session.merge(entity);
            result.set(Optional.ofNullable(updatedConfiguration));
        });

        return result.get();
    }
}
