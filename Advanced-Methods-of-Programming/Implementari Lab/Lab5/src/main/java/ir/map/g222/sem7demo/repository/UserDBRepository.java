package ir.map.g222.sem7demo.repository;

import ir.map.g222.sem7demo.domain.Utilizator;
import ir.map.g222.sem7demo.exceptions.EntitateNull;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserDBRepository implements Repository<Long, Utilizator> {

    private String url;
    private String username;
    private String password;

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
                Utilizator utilizator = new Utilizator(prenume, nume);
                utilizator.setId(utilizatorID);
                return Optional.ofNullable(utilizator);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> utilizatori = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from users");
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String prenume = resultSet.getString("first_name");
                String nume = resultSet.getString("last_name");
                Utilizator utilizator = new Utilizator(prenume, nume);
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

        String insertSQL = "insert into users (first_name, last_name) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL);
        ) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
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
            return result == 0 ? Optional.empty() : Optional.of(new Utilizator("Utilizator", "Sters"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity, Long utilizatorID) {
        if (entity == null) throw new EntitateNull("Entity must not be null!");

        String updateSQL = "UPDATE users SET first_name = ?, last_name = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setString(1, entity.getPrenume());
            statement.setString(2, entity.getNume());
            statement.setLong(3, utilizatorID);
            int result = statement.executeUpdate();
            return result == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}