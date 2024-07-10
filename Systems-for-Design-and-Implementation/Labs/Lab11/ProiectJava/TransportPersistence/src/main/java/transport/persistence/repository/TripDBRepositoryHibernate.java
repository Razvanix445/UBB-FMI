package transport.persistence.repository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import transport.model.Trip;
import transport.persistence.ITripRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class TripDBRepositoryHibernate implements ITripRepository {

//    @Autowired
//    public TripsDBRepositoryHibernate() {
//    }

    @Override
    public Optional<Trip> save(Trip trip) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(trip));
        return Optional.of(trip);
    }

    @Override
    public Optional<Trip> delete(Long id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Trip trip = session.createQuery("FROM Trip WHERE id=?1", Trip.class)
                    .setParameter(1, id).uniqueResult();
            System.out.println("Found the Trip (delete repo hibernate): " + trip);
            if (trip != null) {
                session.remove(trip);
                session.flush();
            }
        });
        return Optional.empty();
    }

    @Override
    public Optional<Trip> update(Trip trip) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Trip.class, trip.getId()) != null) {
                System.out.println("Found the Trip (update repo hibernate): " + trip);
                session.merge(trip);
                session.flush();
            }
        });
        return Optional.of(trip);
    }

    @Override
    public Optional<Trip> findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Trip WHERE id=:idM", Trip.class)
                    .setParameter("idM", id)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Trip> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Trip ", Trip.class).getResultList();
        }
    }
}