package com.example.comenzirestauranteexamen.repository;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Order;
import com.example.comenzirestauranteexamen.domain.OrderStatus;
import com.example.comenzirestauranteexamen.domain.Table;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class RepositoryDBOrders implements Repository<Long, Order> {

    private String url;
    private String username;
    private String password;

    public RepositoryDBOrders(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Order> findOne(Long orderId) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement orderStatement = connection.prepareStatement("SELECT * FROM Orders " +
                     "WHERE id = ?");
             PreparedStatement menuItemStatement = connection.prepareStatement("SELECT mi.* FROM MenuItems mi " +
                     "JOIN OrdersMenuItems omi ON mi.id = omi.menuItemId " +
                     "WHERE omi.orderId = ?");
        ) {
            orderStatement.setInt(1, Math.toIntExact(orderId));
            ResultSet orderResultSet = orderStatement.executeQuery();
            if (orderResultSet.next()) {
                Long id = orderResultSet.getLong("id");
                Table table = new Table(orderResultSet.getLong("tableId"));
                LocalDateTime date = orderResultSet.getTimestamp("date").toLocalDateTime();
                OrderStatus status = OrderStatus.valueOf(orderResultSet.getString("status"));

                menuItemStatement.setLong(1, orderId);
                ResultSet menuItemResultSet = menuItemStatement.executeQuery();

                List<MenuItem> menuItems = new ArrayList<>();
                while (menuItemResultSet.next()) {
                    Long menuItemId = menuItemResultSet.getLong("id");
                    String category = menuItemResultSet.getString("category");
                    String item = menuItemResultSet.getString("item");
                    Float price = menuItemResultSet.getFloat("price");
                    String currency = menuItemResultSet.getString("currency");

                    MenuItem menuItem = new MenuItem(menuItemId, category, item, price, currency);
                    menuItems.add(menuItem);
                }

                Order order = new Order(id, table, menuItems, date, status);
                order.setId(id);
                return Optional.of(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Order> findAll() {
        Set<Order> orders = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement orderStatement = connection.prepareStatement("SELECT * FROM Orders");
             PreparedStatement menuItemStatement = connection.prepareStatement("SELECT mi.* FROM MenuItems mi " +
                     "JOIN OrdersMenuItems omi ON mi.id = omi.menuItemId " +
                     "WHERE omi.orderId = ?");
             ResultSet orderResultSet = orderStatement.executeQuery()
        ) {
            while (orderResultSet.next()) {
                Long id = orderResultSet.getLong("id");
                Table table = new Table(orderResultSet.getLong("tableId"));
                LocalDateTime date = orderResultSet.getTimestamp("date").toLocalDateTime();
                OrderStatus status = OrderStatus.valueOf(orderResultSet.getString("status"));

                menuItemStatement.setLong(1, id);
                ResultSet menuItemResultSet = menuItemStatement.executeQuery();

                List<MenuItem> menuItems = new ArrayList<>();
                while (menuItemResultSet.next()) {
                    Long menuItemId = menuItemResultSet.getLong("id");
                    String category = menuItemResultSet.getString("category");
                    String item = menuItemResultSet.getString("item");
                    Float price = menuItemResultSet.getFloat("price");
                    String currency = menuItemResultSet.getString("currency");

                    MenuItem menuItem = new MenuItem(menuItemId, category, item, price, currency);
                    menuItems.add(menuItem);
                }

                Order order = new Order(id, table, menuItems, date, status);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> save(Order entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement orderStatement = connection.prepareStatement("INSERT INTO Orders (tableId, date, status) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement menuItemStatement = connection.prepareStatement("INSERT INTO OrdersMenuItems (orderId, menuItemId) VALUES (?, ?)");
        ) {
            orderStatement.setInt(1, Math.toIntExact(entity.getTable().getId()));
            orderStatement.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            orderStatement.setString(3, entity.getStatus().toString());
            int affectedRows = orderStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

            for (MenuItem menuItem : entity.getMenuItems()) {
                menuItemStatement.setInt(1, Math.toIntExact(entity.getId()));
                menuItemStatement.setInt(2, Math.toIntExact(menuItem.getId()));
                menuItemStatement.executeUpdate();
            }

            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> delete(Long aLong) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement orderStatement = connection.prepareStatement("DELETE FROM Orders WHERE id = ?");
             PreparedStatement menuItemStatement = connection.prepareStatement("DELETE FROM OrdersMenuItems WHERE orderId = ?");
        ) {
            orderStatement.setInt(1, Math.toIntExact(aLong));
            orderStatement.executeUpdate();

            menuItemStatement.setInt(1, Math.toIntExact(aLong));
            menuItemStatement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> update(Order entity, Long aLong) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement orderStatement = connection.prepareStatement("UPDATE Orders SET table = ?, date = ?, status = ? WHERE id = ?");
             PreparedStatement menuItemStatement = connection.prepareStatement("DELETE FROM OrdersMenuItems WHERE orderId = ?");
        ) {
            orderStatement.setInt(1, Math.toIntExact(entity.getTable().getId()));
            orderStatement.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            orderStatement.setString(3, entity.getStatus().toString());
            orderStatement.setInt(4, Math.toIntExact(aLong));
            orderStatement.executeUpdate();

            menuItemStatement.setInt(1, Math.toIntExact(aLong));
            menuItemStatement.executeUpdate();

            for (MenuItem menuItem : entity.getMenuItems()) {
                menuItemStatement.setInt(1, Math.toIntExact(entity.getId()));
                menuItemStatement.setInt(2, Math.toIntExact(menuItem.getId()));
                menuItemStatement.executeUpdate();
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
