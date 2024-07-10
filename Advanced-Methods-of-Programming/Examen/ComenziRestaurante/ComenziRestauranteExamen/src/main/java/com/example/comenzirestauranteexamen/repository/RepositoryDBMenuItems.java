package com.example.comenzirestauranteexamen.repository;

import com.example.comenzirestauranteexamen.domain.MenuItem;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RepositoryDBMenuItems implements Repository<Long, MenuItem> {

    private String url;
    private String username;
    private String password;

    public RepositoryDBMenuItems(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<MenuItem> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM MenuItems " +
                     "WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String category = resultSet.getString("category");
                String item = resultSet.getString("item");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");
                MenuItem menuItem = new MenuItem(id, category, item, price, currency);
                menuItem.setId(id);
                return Optional.of(menuItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<MenuItem> findAll() {
        Set<MenuItem> menuItems = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM MenuItems");
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String category = resultSet.getString("category");
                String item = resultSet.getString("item");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");
                MenuItem menuItem = new MenuItem(id, category, item, price, currency);
                menuItems.add(menuItem);
            }
            return menuItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<MenuItem> save(MenuItem entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO MenuItems (id, category, item, price, currency) VALUES (?, ?, ?, ?, ?)");
        ) {
            statement.setInt(1, Math.toIntExact(entity.getId()));
            statement.setString(2, entity.getCategory());
            statement.setString(3, entity.getItem());
            statement.setFloat(4, entity.getPrice());
            statement.setString(5, entity.getCurrency());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<MenuItem> delete(Long aLong) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM MenuItems WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(aLong));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<MenuItem> update(MenuItem entity, Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE MenuItems SET category = ?, item = ?, price = ?, currency = ? WHERE id = ?");
        ) {
            statement.setString(1, entity.getCategory());
            statement.setString(2, entity.getItem());
            statement.setFloat(3, entity.getPrice());
            statement.setString(4, entity.getCurrency());
            statement.setInt(5, Math.toIntExact(id));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
