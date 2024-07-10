package ir.map.g222.sem7demo.repository;

import ir.map.g222.sem7demo.domain.Prietenie;
import ir.map.g222.sem7demo.exceptions.EntitateNull;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipDBRepository implements Repository<Long, Prietenie> {

    private String url;
    private String username;
    private String password;

    public FriendshipDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Prietenie> findOne(Long prietenieID) {
        String selectSQL = "SELECT * FROM friendships " + "WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectSQL);
        ) {
            statement.setLong(1, prietenieID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userID1 = resultSet.getLong("userID1");
                Long userID2 = resultSet.getLong("userID2");
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                Prietenie prietenie = new Prietenie(userID1, userID2);
                prietenie.setId(prietenieID);
                return Optional.ofNullable(prietenie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prietenii = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long userID1 = resultSet.getLong("userID1");
                Long userID2 = resultSet.getLong("userID2");
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                Prietenie prietenie = new Prietenie(userID1, userID2);
                prietenie.setId(id);
                prietenii.add(prietenie);
            }
            return prietenii;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Prietenie> save(Prietenie entity) {
        if (entity == null)
            throw new EntitateNull("Entity must not be null!");

        String insertSQL = "INSERT INTO friendships (userID1, userID2, friendsFrom) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL);
        ) {
            statement.setLong(1, entity.getIdUtilizator1());
            statement.setLong(2, entity.getIdUtilizator2());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            int result = statement.executeUpdate();
            return result == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Prietenie> delete(Long prietenieID) {
        String deleteSQL = "DELETE FROM friendships WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL);
        ) {
            statement.setLong(1, prietenieID);
            int result = statement.executeUpdate();
            return result == 0 ? Optional.of(new Prietenie(0L, 0L)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity, Long prietenieID) {
        if (entity == null) throw new EntitateNull("Entity must not be null!");

        String updateSQL = "UPDATE friendships SET userID1 = ?, userID2 = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setLong(1, entity.getIdUtilizator1());
            statement.setLong(2, entity.getIdUtilizator2());
            statement.setLong(3, prietenieID);
            int result = statement.executeUpdate();
            return result == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
