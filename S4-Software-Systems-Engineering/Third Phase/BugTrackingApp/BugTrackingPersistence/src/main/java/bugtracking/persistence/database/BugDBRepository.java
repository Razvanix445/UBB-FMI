package bugtracking.persistence.database;

import bugtracking.model.Bug;
import bugtracking.model.BugStatus;
import bugtracking.model.Programmer;
import bugtracking.model.Tester;
import bugtracking.persistence.interfaces.IBugRepository;
import org.hibernate.Session;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class BugDBRepository implements IBugRepository {

    @Override
    public Optional<Bug> save(Bug entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return Optional.of(entity);
    }

    @Override
    public Optional<Bug> delete(Long id) {
        final AtomicReference<Bug> deletedBug = new AtomicReference<>();
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Bug bug = session.createQuery("FROM Bug WHERE id=?1", Bug.class)
                    .setParameter(1, id).uniqueResult();
            System.out.println("Found the Bug (delete repo hibernate): " + bug);
            if (bug != null) {
                session.remove(bug);
                session.flush();
                deletedBug.set(bug);
            }
        });
        return Optional.ofNullable(deletedBug.get());
    }

    @Override
    public Optional<Bug> update(Bug entity, Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Bug.class, aLong) != null) {
                System.out.println("Found the Bug (update repo hibernate): " + entity);
                session.merge(entity);
                session.flush();
            }
        });
        return Optional.of(entity);
    }

    @Override
    public Optional<Bug> findOne(Long aLong) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Bug WHERE id=:idM", Bug.class)
                    .setParameter("idM", aLong)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Bug> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Bug ", Bug.class).getResultList();
        }
    }

//    protected String url;
//    protected String dbUsername;
//    protected String dbPassword;
//
//    public BugDBRepository(String url, String dbUsername, String dbPassword) {
//        this.url = url;
//        this.dbUsername = dbUsername;
//        this.dbPassword = dbPassword;
//    }
//
//    @Override
//    public Optional<Bug> findOne(Long bugID) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Bugs " +
//                     "WHERE id = ?");
//             PreparedStatement testerStatement = connection.prepareStatement("SELECT * FROM Testers " +
//                     "WHERE id = ?");
//             PreparedStatement programmerStatement = connection.prepareStatement("SELECT * FROM Programmers " +
//                     "WHERE id = ?");
//        ) {
//            statement.setInt(1, Math.toIntExact(bugID));
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String description = resultSet.getString("description");
//                Long reportedById = resultSet.getLong("reported_by_id");
//                Long assignedToId = resultSet.getLong("assigned_to_id");
//                String statusStr = resultSet.getString("status");
//                BugStatus status = BugStatus.valueOf(statusStr);
//                Timestamp timestamp = resultSet.getTimestamp("timestamp");
//                LocalDateTime date = timestamp.toLocalDateTime();
//
//                // TAKE TESTER BY ID FROM DATABASE
//                testerStatement.setLong(1, reportedById);
//                ResultSet testerResultSet = testerStatement.executeQuery();
//                Tester tester = null;
//                if (testerResultSet.next()) {
//                    String testerUsername = testerResultSet.getString("username");
//                    String testerPassword = testerResultSet.getString("password");
//                    String testerEmail = testerResultSet.getString("email");
//                    String testerName = testerResultSet.getString("name");
//                    tester = new Tester(testerUsername, testerPassword, testerEmail, testerName);
//                }
//
//                // TAKE PROGRAMMER BY ID FROM DATABASE
//                programmerStatement.setLong(1, assignedToId);
//                ResultSet programmerResultSet = programmerStatement.executeQuery();
//                Programmer programmer = null;
//                if (programmerResultSet.next()) {
//                    String programmerUsername = programmerResultSet.getString("username");
//                    String programmerPassword = programmerResultSet.getString("password");
//                    String programmerEmail = programmerResultSet.getString("email");
//                    String programmerName = programmerResultSet.getString("name");
//                    programmer = new Programmer(programmerUsername, programmerPassword, programmerEmail, programmerName);
//                }
//
//                Bug bug = new Bug(name, description, tester, programmer);
//                bug.setStatus(status);
//                bug.setTimestamp(date);
//                bug.setId(bugID);
//                return Optional.of(bug);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Iterable<Bug> findAll() {
//        Set<Bug> bugs = new HashSet<>();
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Bugs");
//             PreparedStatement testerStatement = connection.prepareStatement("SELECT * FROM Testers " +
//                     "WHERE id = ?");
//             PreparedStatement programmerStatement = connection.prepareStatement("SELECT * FROM Programmers " +
//                     "WHERE id = ?");
//        ) {
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Long bugID = resultSet.getLong("id");
//                String name = resultSet.getString("name");
//                String description = resultSet.getString("description");
//                Long reportedById = resultSet.getLong("reported_by_id");
//                Long assignedToId = resultSet.getLong("assigned_to_id");
//                String statusStr = resultSet.getString("status");
//                BugStatus status = BugStatus.valueOf(statusStr);
//                Timestamp timestamp = resultSet.getTimestamp("timestamp");
//                LocalDateTime date = timestamp.toLocalDateTime();
//
//                // TAKE TESTER BY ID FROM DATABASE
//                testerStatement.setLong(1, reportedById);
//                ResultSet testerResultSet = testerStatement.executeQuery();
//                Tester tester = null;
//                if (testerResultSet.next()) {
//                    String testerUsername = testerResultSet.getString("username");
//                    String testerPassword = testerResultSet.getString("password");
//                    String testerEmail = testerResultSet.getString("email");
//                    String testerName = testerResultSet.getString("name");
//                    tester = new Tester(testerUsername, testerPassword, testerEmail, testerName);
//                }
//
//                // TAKE PROGRAMMER BY ID FROM DATABASE
//                programmerStatement.setLong(1, assignedToId);
//                ResultSet programmerResultSet = programmerStatement.executeQuery();
//                Programmer programmer = null;
//                if (programmerResultSet.next()) {
//                    String programmerUsername = programmerResultSet.getString("username");
//                    String programmerPassword = programmerResultSet.getString("password");
//                    String programmerEmail = programmerResultSet.getString("email");
//                    String programmerName = programmerResultSet.getString("name");
//                    programmer = new Programmer(programmerUsername, programmerPassword, programmerEmail, programmerName);
//                }
//
//                Bug bug = new Bug(name, description, tester, programmer);
//                bug.setStatus(status);
//                bug.setTimestamp(date);
//                bug.setId(bugID);
//                bugs.add(bug);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return bugs;
//    }
//
//    @Override
//    public Optional<Bug> save(Bug entity) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO Bugs(name, description, reported_by_id, assigned_to_id, status, timestamp) " +
//                     "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//        ) {
//            statement.setString(1, entity.getName());
//            statement.setString(2, entity.getDescription());
//            statement.setLong(3, entity.getReportedBy().getId());
//            statement.setLong(4, entity.getAssignedTo().getId());
//            statement.setString(5, entity.getStatus().toString());
//            statement.setTimestamp(6, Timestamp.valueOf(entity.getTimestamp()));
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                entity.setId(generatedKeys.getLong(1));
//                return Optional.of(entity);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Bug> delete(Long aLong) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM Bugs " +
//                     "WHERE id = ?");
//        ) {
//            statement.setLong(1, aLong);
//            int result = statement.executeUpdate();
//            if (result > 0) {
//                return findOne(aLong);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Bug> update(Bug entity, Long aLong) {
//        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//             PreparedStatement statement = connection.prepareStatement("UPDATE Bugs " +
//                     "SET name = ?, description = ?, reported_by_id = ?, assigned_to_id = ?, status = ?, timestamp = ? " +
//                     "WHERE id = ?");
//        ) {
//            statement.setString(1, entity.getName());
//            statement.setString(2, entity.getDescription());
//            statement.setLong(3, entity.getReportedBy().getId());
//            statement.setLong(4, entity.getAssignedTo().getId());
//            statement.setString(5, entity.getStatus().toString());
//            statement.setTimestamp(6, Timestamp.valueOf(entity.getTimestamp()));
//            statement.setLong(7, aLong);
//            int result = statement.executeUpdate();
//            if (result > 0) {
//                return Optional.of(entity);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
}
