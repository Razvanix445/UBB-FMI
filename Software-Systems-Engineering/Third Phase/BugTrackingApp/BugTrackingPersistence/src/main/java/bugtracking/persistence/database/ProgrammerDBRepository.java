package bugtracking.persistence.database;

import bugtracking.model.Programmer;
import bugtracking.model.Tester;
import bugtracking.persistence.interfaces.IProgrammerRepository;
import org.hibernate.Session;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ProgrammerDBRepository implements IProgrammerRepository {

    @Override
    public Optional<Programmer> save(Programmer programmer) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(programmer));
        return Optional.of(programmer);
    }

    @Override
    public Optional<Programmer> delete(Long id) {
        final AtomicReference<Programmer> deletedProgrammer = new AtomicReference<>();
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Programmer programmer = session.createQuery("FROM Programmer WHERE id=?1", Programmer.class)
                    .setParameter(1, id).uniqueResult();
            System.out.println("Found the Programmer (delete repo hibernate): " + programmer);
            if (programmer != null) {
                session.remove(programmer);
                session.flush();
                deletedProgrammer.set(programmer);
            }
        });
        return Optional.ofNullable(deletedProgrammer.get());
    }

    @Override
    public Optional<Programmer> update(Programmer programmer, Long id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Programmer.class, programmer.getId()) != null) {
                System.out.println("Found the Programmer (update repo hibernate): " + programmer);
                session.merge(programmer);
                session.flush();
            }
        });
        return Optional.of(programmer);
    }

    @Override
    public Optional<Programmer> findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Programmer WHERE id=:idM", Programmer.class)
                    .setParameter("idM", id)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Programmer> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Programmer ", Programmer.class).getResultList();
        }
    }

//    protected String url;
//    protected String dbUsername;
//    protected String dbPassword;
//
//    public ProgrammerDBRepository(String url, String dbUsername, String dbPassword) {
//        this.url = url;
//        this.dbUsername = dbUsername;
//        this.dbPassword = dbPassword;
//    }
//
//    @Override
//    public Optional<Programmer> findOne(Long programmerID) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Programmers " +
//                     "WHERE id = ?");
//        ) {
//            statement.setInt(1, Math.toIntExact(programmerID));
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                String username = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                String email = resultSet.getString("email");
//                String name = resultSet.getString("name");
//                Programmer programmer = new Programmer(username, password, email, name);
//                programmer.setId(programmerID);
//                return Optional.of(programmer);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Iterable<Programmer> findAll() {
//        Set<Programmer> programmers = new HashSet<>();
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Programmers");
//             ResultSet resultSet = statement.executeQuery();
//        ) {
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String username = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                String email = resultSet.getString("email");
//                String name = resultSet.getString("name");
//                Programmer programmer = new Programmer(username, password, email, name);
//                programmer.setId(id);
//                programmers.add(programmer);
//            }
//            return programmers;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Optional<Programmer> save(Programmer entity) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO Programmers(username, password, email, name) " +
//                     "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//        ) {
//            statement.setString(1, entity.getUsername());
//            statement.setString(2, entity.getPassword());
//            statement.setString(3, entity.getEmail());
//            statement.setString(4, entity.getName());
//            statement.executeUpdate();
//            ResultSet resultSet = statement.getGeneratedKeys();
//            if (resultSet.next()) {
//                entity.setId(resultSet.getLong(1));
//                return Optional.of(entity);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Programmer> delete(Long aLong) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM Programmers " +
//                     "WHERE id = ?");
//        ) {
//            statement.setInt(1, Math.toIntExact(aLong));
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Programmer> update(Programmer entity, Long aLong) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("UPDATE Programmers " +
//                     "SET username = ?, password = ?, email = ?, name = ? " +
//                     "WHERE id = ?");
//        ) {
//            statement.setString(1, entity.getUsername());
//            statement.setString(2, entity.getPassword());
//            statement.setString(3, entity.getEmail());
//            statement.setString(4, entity.getName());
//            statement.setInt(5, Math.toIntExact(aLong));
//            statement.executeUpdate();
//            return Optional.of(entity);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
