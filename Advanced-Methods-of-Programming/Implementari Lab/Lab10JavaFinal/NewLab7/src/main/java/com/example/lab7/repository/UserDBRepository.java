package com.example.lab7.repository;

import com.example.lab7.domain.Utilizator;
import com.example.lab7.exceptions.EntitateNull;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserDBRepository implements Repository<Long, Utilizator> {

    protected String url;
    protected String username;
    protected String password;

    public UserDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Utilizator> findOne(Long utilizatorID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users " +
                     "where id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(utilizatorID));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String prenume = resultSet.getString("first_name");
                String nume = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Utilizator utilizator = new Utilizator(prenume, nume, username, password, email);
                utilizator.setId(utilizatorID);
                return Optional.of(utilizator);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> utilizatori = new HashSet<>();
        String selectUsersSQL = "SELECT * FROM Users";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectUsersSQL);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String prenume = resultSet.getString("first_name");
                String nume = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                Utilizator utilizator = new Utilizator(prenume, nume, username, password, email);
                utilizator.setId(id);
                utilizatori.add(utilizator);
            }
            return utilizatori;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must not be null!");

        String insertSQL = "insert into users (first_name, last_name, username, password, email) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL);
        ) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getEmail());
            int result = statement.executeUpdate();
            return result == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> delete(Long utilizatorID) {
        String deleteSQL = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL);
        ) {
            statement.setLong(1, utilizatorID);
            int result = statement.executeUpdate();
            return result == 0 ? Optional.empty() :
                    Optional.of(new Utilizator("Utilizator", "Sters", "Sters", "Sters", "Sters"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity, Long utilizatorID) {
        if (entity == null) throw new EntitateNull("Entity must not be null!");

        if (utilizatorID == null)
            throw new IllegalArgumentException("ID must not be null!");

        String updateSQL = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setString(1, entity.getPrenume());
            statement.setString(2, entity.getNume());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getEmail());
            statement.setLong(6, utilizatorID);
            int result = statement.executeUpdate();
            return result == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}