package com.example.repository.database;

import com.example.domain.Tester;
import com.example.repository.interfaces.ITesterRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TesterDBRepository implements ITesterRepository {

    protected String url;
    protected String dbUsername;
    protected String dbPassword;

    public TesterDBRepository(String url, String dbUsername, String dbPassword) {
        this.url = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    @Override
    public Optional<Tester> findOne(Long testerID) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Testers " +
                     "WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(testerID));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                Tester tester = new Tester(username, password, email, name);
                tester.setId(testerID);
                return Optional.of(tester);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Tester> findAll() {
        Set<Tester> testers = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Testers");
             ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                Tester tester = new Tester(username, password, email, name);
                tester.setId(id);
                testers.add(tester);
            }
            return testers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Tester> save(Tester entity) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Testers(username, password, email, name) " +
                     "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tester> delete(Long testerID) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Testers " +
                     "WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(testerID));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tester> update(Tester entity, Long testerID) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("UPDATE Testers " +
                     "SET username = ?, password = ?, email = ?, name = ? " +
                     "WHERE id = ?");
        ) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getName());
            statement.setInt(5, Math.toIntExact(testerID));
            statement.executeUpdate();
            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
