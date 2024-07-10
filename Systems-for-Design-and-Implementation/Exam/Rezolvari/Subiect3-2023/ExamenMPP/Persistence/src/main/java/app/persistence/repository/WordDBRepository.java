package app.persistence.repository;

import app.model.Word;
import app.persistence.IWordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class WordDBRepository implements IWordRepository {

    private static final Logger logger = LogManager.getLogger(WordDBRepository.class);

    public WordDBRepository() {
        logger.info("Initializing WordDBRepository");
    }

    @Override
    public Optional<Word> findOne(Long id) {
        final AtomicReference<Optional<Word>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Word word = session.get(Word.class, id);
            result.set(Optional.ofNullable(word));
        });

        return result.get();
    }

    @Override
    public Iterable<Word> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Word ", Word.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Words", e);
            return null;
        }
    }

    @Override
    public Optional<Word> save(Word word) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(word));
        return Optional.of(word);
    }

    @Override
    public Optional<Word> delete(Long id) {
        final AtomicReference<Optional<Word>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Word word = session.get(Word.class, id);
            if (word != null) {
                session.delete(word);
                result.set(Optional.of(word));
            } else {
                result.set(Optional.empty());
            }
        });

        return result.get();
    }

    @Override
    public Optional<Word> update(Word entity) {
        final AtomicReference<Optional<Word>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Word updatedWord = (Word) session.merge(entity);
            result.set(Optional.ofNullable(updatedWord));
        });

        return result.get();
    }
}