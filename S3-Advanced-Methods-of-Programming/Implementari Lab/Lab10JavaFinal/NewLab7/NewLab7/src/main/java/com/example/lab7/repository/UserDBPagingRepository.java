package com.example.lab7.repository;

import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.PageImplementation;
import com.example.lab7.repository.paging.Pageable;
import com.example.lab7.repository.paging.PagingRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDBPagingRepository extends UserDBRepository implements PagingRepository<Long, Utilizator>
{

    public UserDBPagingRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Page<Utilizator> findAll(Pageable pageable) {
        Set<Utilizator> utilizatori = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users offset ? limit ?");
        ) {
            statement.setInt(1, pageable.getPageSize() * (pageable.getPageNumber() - 1));
            statement.setInt(2, pageable.getPageSize());
            ResultSet resultSet = statement.executeQuery();
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
            return new PageImplementation<Utilizator>(pageable, utilizatori.stream());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCorrectPassword(String userUsername, String userEnteredPassword) {
        String selectSQL = "SELECT id FROM Users WHERE username = ? AND password = crypt(?, password);";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(selectSQL);
        ) {
            statement.setString(1, userUsername);
            statement.setString(2, userEnteredPassword);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
