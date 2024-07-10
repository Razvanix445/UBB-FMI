package com.example.faptebuneexamen.repository;

import com.example.faptebuneexamen.domain.Oras;
import com.example.faptebuneexamen.domain.Persoana;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RepositoryDBPersoana implements Repository<Long, Persoana> {

    private final String url;
    private final String username;
    private final String password;

    public RepositoryDBPersoana(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Persoana> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Persoane " +
                     "WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                String orasString = resultSet.getString("oras");
                Oras oras = Oras.valueOf(orasString);
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarStrada");
                String telefon = resultSet.getString("telefon");
                Persoana persoana = new Persoana(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
                persoana.setId(id);
                return Optional.of(persoana);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Persoana> findAll() {
        Set<Persoana> persoane = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Persoane");
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                String orasString = resultSet.getString("oras");
                Oras oras = Oras.valueOf(orasString);
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarStrada");
                String telefon = resultSet.getString("telefon");
                Persoana persoana = new Persoana(nume, prenume, username, parola, oras, strada, numarStrada, telefon);
                persoana.setId(id);
                persoane.add(persoana);
            }
            return persoane;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> save(Persoana entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Persoane(nume, prenume, username, parola, oras, strada, numarStrada, telefon) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        ) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getParola());
            statement.setString(5, entity.getOras().toString());
            statement.setString(6, entity.getStrada());
            statement.setString(7, entity.getNumarStrada());
            statement.setString(8, entity.getTelefon());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Persoane WHERE id = ?");
        ) {
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> update(Persoana entity, Long id) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE Persoane SET nume = ?, prenume = ?, username = ?, parola = ?, oras = ?, strada = ?, numarStrada = ?, telefon = ? WHERE id = ?");
        ) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getParola());
            statement.setString(5, entity.getOras().toString());
            statement.setString(6, entity.getStrada());
            statement.setString(7, entity.getNumarStrada());
            statement.setString(8, entity.getTelefon());
            statement.setInt(9, Math.toIntExact(id));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
