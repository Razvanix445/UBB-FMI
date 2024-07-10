package app.persistence.repository;

import app.model.Configuration;
import app.model.ConfigurationWord;
import app.model.Word;
import app.persistence.IConfigurationWordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
public class ConfigurationWordDBRepository implements IConfigurationWordRepository {

    private static final Logger logger = LogManager.getLogger(ConfigurationWordDBRepository.class);

    public ConfigurationWordDBRepository() {
        logger.info("Initializing ConfigurationWordDBRepository");
    }

    @Override
    public Optional<ConfigurationWord> findOne(Long id) {
        final AtomicReference<Optional<ConfigurationWord>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            ConfigurationWord configurationWord = session.get(ConfigurationWord.class, id);
            result.set(Optional.ofNullable(configurationWord));
        });

        return result.get();
    }

    @Override
    public Iterable<ConfigurationWord> findConfigurationWordsByConfiguration(Configuration configuration) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<ConfigurationWord> configurationWords = session.createQuery(
                            "SELECT cw " +
                                    "FROM ConfigurationWord cw " +
                                    "WHERE cw.configuration.id = :configId " +
                                    "ORDER BY cw.wordNumber ASC", ConfigurationWord.class)
                    .setParameter("configId", configuration.getId())
                    .getResultList();

            return configurationWords;
        } catch (Exception e) {
            logger.error("Error finding configuration words by configuration", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Iterable<Word> findWordsByConfiguration(Configuration configuration) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<ConfigurationWord> configurationWords = session.createQuery(
                            "SELECT cw " +
                                    "FROM ConfigurationWord cw " +
                                    "WHERE cw.configuration.id = :configId " +
                                    "ORDER BY cw.wordNumber ASC", ConfigurationWord.class)
                    .setParameter("configId", configuration.getId())
                    .getResultList();

            return configurationWords.stream()
                    .map(ConfigurationWord::getWord)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error finding words by configuration", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Iterable<ConfigurationWord> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM ConfigurationWord ", ConfigurationWord.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all ConfigurationsWords", e);
            return null;
        }
    }

    @Override
    public Optional<ConfigurationWord> save(ConfigurationWord configurationWord) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(configurationWord));
        return Optional.of(configurationWord);
    }

    @Override
    public Optional<ConfigurationWord> delete(Long id) {
        final AtomicReference<Optional<ConfigurationWord>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            ConfigurationWord configurationWord = session.get(ConfigurationWord.class, id);
            if (configurationWord != null) {
                session.delete(configurationWord);
                result.set(Optional.of(configurationWord));
            } else {
                result.set(Optional.empty());
            }
        });

        return result.get();
    }

    @Override
    public Optional<ConfigurationWord> update(ConfigurationWord entity) {
        final AtomicReference<Optional<ConfigurationWord>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            ConfigurationWord updatedConfigurationWord = (ConfigurationWord) session.merge(entity);
            result.set(Optional.ofNullable(updatedConfigurationWord));
        });
        return result.get();
    }
}
