package com.example.lab7.repository;

import com.example.lab7.domain.FriendRequest;
import com.example.lab7.domain.Prietenie;
import com.example.lab7.domain.ReplyMessage;
import com.example.lab7.domain.Utilizator;

import java.sql.*;
import java.util.*;

public class FriendRequestDBRepository implements FriendRequestRepository {

    private String url;
    private String username;
    private String password;

    public FriendRequestDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<FriendRequest> findOne(Long fromUserId, Long toUserId) {
        String selectSQL = "SELECT * FROM FriendRequests WHERE fromUserId = ? AND toUserId = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectSQL);
        ) {
            statement.setLong(1, fromUserId);
            statement.setLong(2, toUserId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String requestType = resultSet.getString("status");

                FriendRequest friendRequest = new FriendRequest(id, fromUserId, toUserId, requestType);
                return Optional.of(friendRequest);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding friend request.", e);
        }
    }

    @Override
    public Iterable<FriendRequest> findAll(String status) {
        Set<FriendRequest> friendRequestSet = new HashSet<>();
        String selectSQL = "SELECT * FROM FriendRequests WHERE status = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(selectSQL);
        ) {
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long fromUserId = resultSet.getLong("fromUserId");
                Long toUserId = resultSet.getLong("toUserId");
                String requestType = resultSet.getString("status");

                FriendRequest friendRequest = new FriendRequest(id, fromUserId, toUserId, requestType);
                friendRequestSet.add(friendRequest);
            }
            return friendRequestSet;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding friend request.", e);
        }
    }

    @Override
    public Optional<FriendRequest> save(FriendRequest friendRequest) {
        String insertSQL = "INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setLong(1, friendRequest.getFromUserId());
            statement.setLong(2, friendRequest.getToUserId());
            statement.setString(3, friendRequest.getRequestType());

            int result = statement.executeUpdate();
            if (result == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    friendRequest.setId(generatedKeys.getLong(1));
                    return Optional.of(friendRequest);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving friend request.", e);
        }
    }

    @Override
    public Optional<FriendRequest> delete(Long requestId) {
        String deleteSQL = "DELETE FROM FriendRequests WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL);
        ) {
            statement.setLong(1, requestId);

            int result = statement.executeUpdate();
            if (result == 1) {
                return Optional.of(new FriendRequest(requestId, 0L, 0L, "Sters"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting friend request.", e);
        }
    }

    @Override
    public void updateRequestType(Long fromUserId, Long toUserId, String newRequestType) {
        String updateSQL = "UPDATE FriendRequests SET status = ? WHERE fromUserId = ? AND toUserId = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);
        ) {
            statement.setString(1, newRequestType);
            statement.setLong(2, fromUserId);
            statement.setLong(3, toUserId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update friend request type.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating friend request type.", e);
        }
    }
}
