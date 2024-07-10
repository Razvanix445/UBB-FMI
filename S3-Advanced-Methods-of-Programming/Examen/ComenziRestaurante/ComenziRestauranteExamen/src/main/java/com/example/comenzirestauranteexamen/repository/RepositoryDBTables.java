package com.example.comenzirestauranteexamen.repository;

import com.example.comenzirestauranteexamen.domain.Table;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RepositoryDBTables implements Repository<Long, Table> {

    private String url;
    private String username;
    private String password;

    public RepositoryDBTables(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Table> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tables " +
                     "WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Table table = new Table(id);
                return Optional.of(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Table> findAll() {
        Set<Table> tables = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tables");
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Table table = new Table(id);
                tables.add(table);
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Table> save(Table table) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Tables (id) VALUES (?)");
        ) {
            statement.setInt(1, Math.toIntExact(table.getId()));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Table> delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Tables WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Table> update(Table table, Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE Tables SET id = ? WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(table.getId()));
            statement.setInt(2, Math.toIntExact(id));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
