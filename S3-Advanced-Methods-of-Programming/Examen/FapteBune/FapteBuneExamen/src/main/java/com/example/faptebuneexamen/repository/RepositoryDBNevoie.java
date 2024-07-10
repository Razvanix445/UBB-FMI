package com.example.faptebuneexamen.repository;

import com.example.faptebuneexamen.domain.Nevoie;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RepositoryDBNevoie implements Repository<Long, Nevoie> {

    private final String url;
    private final String username;
    private final String password;

    public RepositoryDBNevoie(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*Se gaseste din baza de date o nevoie care are id-ul id. Se extrag toate datele: titlu, descriere, deadline, onInNevoie, omSalvator, status*/
    @Override
    public Optional<Nevoie> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Nevoi " +
                     "WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long onInNevoie = resultSet.getLong("omInNevoie");
                Long omSalvator = resultSet.getLong("omSalvator");
                String status = resultSet.getString("status");
                Nevoie nevoie = new Nevoie(titlu, descriere, deadline, onInNevoie, omSalvator, status);
                nevoie.setId(id);
                return Optional.of(nevoie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Nevoie> findAll() {
        Set<Nevoie> nevoi = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Nevoi");
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long onInNevoie = resultSet.getLong("omInNevoie");
                Long omSalvator = resultSet.getLong("omSalvator");
                String status = resultSet.getString("status");
                Nevoie nevoie = new Nevoie(titlu, descriere, deadline, onInNevoie, omSalvator, status);
                nevoie.setId(id);
                nevoi.add(nevoie);
            }
            return nevoi;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Nevoie> save(Nevoie nevoie) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Nevoi (titlu, descriere, deadline, omInNevoie, status) VALUES (?, ?, ?, ?, ?)");
        ) {
            statement.setString(1, nevoie.getTitlu());
            statement.setString(2, nevoie.getDescriere());
            statement.setTimestamp(3, Timestamp.valueOf(nevoie.getDeadline()));
            statement.setInt(4, Math.toIntExact(nevoie.getOmInNevoie()));
            statement.setString(5, nevoie.getStatus());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Nevoie> delete(Long id) {
        Optional<Nevoie> nevoie = findOne(id);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Nevoi WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
            return nevoie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Nevoie> update(Nevoie nevoie, Long id) {
        Optional<Nevoie> nevoie1 = findOne(id);
        if (nevoie1.isEmpty()) {
            return Optional.of(nevoie);
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE Nevoi SET titlu = ?, descriere = ?, deadline = ?, omInNevoie = ?, omSalvator = ?, status = ? WHERE id = ?");
        ) {
            statement.setString(1, nevoie.getTitlu());
            statement.setString(2, nevoie.getDescriere());
            statement.setTimestamp(3, Timestamp.valueOf(nevoie.getDeadline()));
            statement.setInt(4, Math.toIntExact(nevoie.getOmInNevoie()));
            statement.setInt(5, Math.toIntExact(nevoie.getOmSalvator()));
            statement.setString(6, nevoie.getStatus());
            statement.setInt(7, Math.toIntExact(id));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
