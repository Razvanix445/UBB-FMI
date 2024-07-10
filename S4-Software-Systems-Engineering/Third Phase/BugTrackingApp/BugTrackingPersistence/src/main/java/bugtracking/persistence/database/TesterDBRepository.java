package bugtracking.persistence.database;

import bugtracking.model.Tester;
import bugtracking.persistence.interfaces.ITesterRepository;
import org.hibernate.Session;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class TesterDBRepository implements ITesterRepository {

    @Override
    public Optional<Tester> save(Tester tester) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(tester));
        return Optional.of(tester);
    }

    @Override
    public Optional<Tester> delete(Long id) {
        final AtomicReference<Tester> deletedTester = new AtomicReference<>();
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Tester tester = session.createQuery("FROM Tester WHERE id=?1", Tester.class)
                    .setParameter(1, id).uniqueResult();
            System.out.println("Found the Tester (delete repo hibernate): " + tester);
            if (tester != null) {
                session.remove(tester);
                session.flush();
                deletedTester.set(tester);
            }
        });
        return Optional.ofNullable(deletedTester.get());
    }

    @Override
    public Optional<Tester> update(Tester tester, Long id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Tester.class, tester.getId()) != null) {
                System.out.println("Found the Tester (update repo hibernate): " + tester);
                session.merge(tester);
                session.flush();
            }
        });
        return Optional.of(tester);
    }

    @Override
    public Optional<Tester> findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Tester WHERE id=:idM", Tester.class)
                    .setParameter("idM", id)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Tester> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Tester ", Tester.class).getResultList();
        }
    }

//    protected String url;
//    protected String dbUsername;
//    protected String dbPassword;

//    public TesterDBRepository(String url, String dbUsername, String dbPassword) {
//        this.url = url;
//        this.dbUsername = dbUsername;
//        this.dbPassword = dbPassword;
//    }
//
//    @Override
//    public Optional<Tester> findOne(Long testerID) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Testers " +
//                     "WHERE id = ?");
//        ) {
//            statement.setInt(1, Math.toIntExact(testerID));
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                String username = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                String email = resultSet.getString("email");
//                String name = resultSet.getString("name");
//                Tester tester = new Tester(username, password, email, name);
//                tester.setId(testerID);
//                return Optional.of(tester);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Iterable<Tester> findAll() {
//        Set<Tester> testers = new HashSet<>();
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Testers");
//             ResultSet resultSet = statement.executeQuery();
//        ) {
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String username = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                String email = resultSet.getString("email");
//                String name = resultSet.getString("name");
//                Tester tester = new Tester(username, password, email, name);
//                tester.setId(id);
//                testers.add(tester);
//            }
//            return testers;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Optional<Tester> save(Tester entity) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO Testers(username, password, email, name) " +
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
//    public Optional<Tester> delete(Long testerID) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM Testers " +
//                     "WHERE id = ?");
//        ) {
//            statement.setInt(1, Math.toIntExact(testerID));
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Tester> update(Tester entity, Long testerID) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("UPDATE Testers " +
//                     "SET username = ?, password = ?, email = ?, name = ? " +
//                     "WHERE id = ?");
//        ) {
//            statement.setString(1, entity.getUsername());
//            statement.setString(2, entity.getPassword());
//            statement.setString(3, entity.getEmail());
//            statement.setString(4, entity.getName());
//            statement.setInt(5, Math.toIntExact(testerID));
//            statement.executeUpdate();
//            return Optional.of(entity);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
