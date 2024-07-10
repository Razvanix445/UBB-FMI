package com.example.lab7.repository;

import com.example.lab7.Session;
import com.example.lab7.domain.Prietenie;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.PageImplementation;
import com.example.lab7.repository.paging.Pageable;
import com.example.lab7.repository.paging.PagingRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipDBPagingRepository extends FriendshipDBRepository {

    public FriendshipDBPagingRepository(String url, String username, String password) {
        super(url, username, password);
    }

    public Page<Utilizator> findAll(Pageable pageable, Long userID) {
        Set<Utilizator> prietenii = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Friendships WHERE userID1 = ? OR userID2 = ? ORDER BY id offset ? limit ?");
        ) {
            statement.setLong(1, userID);
            statement.setLong(2, userID);
            statement.setInt(3, pageable.getPageSize() * (pageable.getPageNumber() - 1));
            statement.setInt(4, pageable.getPageSize());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long userID1 = resultSet.getLong("userID1");
                Long userID2 = resultSet.getLong("userID2");
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                String status = resultSet.getString("status");
                Utilizator prieten = null;
                Long friendID = (userID1.equals(userID)) ? userID2 : userID1;
                Optional<Utilizator> friend = findOneUser(friendID);
                if (friend.isPresent()) {
                    prieten = friend.get();
                    prieten.setId(id);
                }
                prietenii.add(prieten);
            }
            return new PageImplementation<>(pageable, prietenii.stream());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Utilizator> findOneUser(Long utilizatorID) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
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
}