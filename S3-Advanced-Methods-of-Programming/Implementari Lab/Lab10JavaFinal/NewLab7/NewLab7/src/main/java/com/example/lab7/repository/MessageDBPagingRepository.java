package com.example.lab7.repository;

import com.example.lab7.domain.Message;
import com.example.lab7.domain.ReplyMessage;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.repository.paging.Page;
import com.example.lab7.repository.paging.PageImplementation;
import com.example.lab7.repository.paging.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDBPagingRepository extends MessageDBRepository {

    public MessageDBPagingRepository(String url, String username, String password) {
        super(url, username, password);
    }

    public Page<Message> findAll(Pageable pageable, Long senderID, Long receiverID) {
        Set<Message> messages = new HashSet<>();
        String messageSQL = "SELECT M.* FROM Messages M " +
                "JOIN MessagesReceivers MR ON M.id = MR.messageID " +
                "WHERE (M.senderID = ? AND MR.receiverID = ?) " +
                "OR (M.senderID = ? AND MR.receiverID = ?) " +
                "ORDER BY M.id OFFSET ? LIMIT ?";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(messageSQL);
        ) {
            statement.setLong(1, senderID);
            statement.setLong(2, receiverID);
            statement.setLong(3, receiverID);
            statement.setLong(4, senderID);
            statement.setInt(5, pageable.getPageSize() * (pageable.getPageNumber() - 1));
            statement.setInt(6, pageable.getPageSize());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long senderId = resultSet.getLong("senderID");
                String messageText = resultSet.getString("messageText");
                LocalDateTime sentAt = resultSet.getTimestamp("sentAt").toLocalDateTime();
                String type = resultSet.getString("messageType");
                Long repliedMessageId = resultSet.getLong("repliedMessageId");

                Message message;
                Utilizator sender = null;
                Optional<Utilizator> senderOptional = getUserById(senderId);
                if (senderOptional.isPresent())
                    sender = senderOptional.get();
                List<Utilizator> receivers = getReceiversForMessage(id);

                if ("ReplyMessage".equals(type)) {
                    Optional<Message> repliedMessageOptional = findOne(repliedMessageId);
                    if (repliedMessageOptional.isPresent()) {
                        Message repliedMessage = repliedMessageOptional.get();
                        message = new ReplyMessage(sender, receivers, messageText, sentAt, repliedMessage);
                        ((ReplyMessage) message).setRepliedMessageId(resultSet.getLong("repliedMessageId"));
                    } else {
                        message = new Message(sender, receivers, messageText, sentAt);
                    }
                } else {
                    message = new Message(sender, receivers, messageText, sentAt);
                }
                message.setId(id);
                messages.add(message);
            }
            messages.forEach(System.out::println);
            return new PageImplementation<>(pageable, messages.stream());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Utilizator> getReceiversForMessage(Long messageId) throws SQLException {
        String selectReceiversSQL = "SELECT * FROM MessagesReceivers WHERE messageID = ?";
        List<Utilizator> receivers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statementReceivers = connection.prepareStatement(selectReceiversSQL);
        ) {
            statementReceivers.setLong(1, messageId);
            ResultSet resultSet = statementReceivers.executeQuery();

            while (resultSet.next()) {
                Long receiverId = resultSet.getLong("receiverID");
                Optional<Utilizator> receiver = getUserById(receiverId);
                receiver.ifPresent(receivers::add);
            }
        }
        return receivers;
    }

    private Optional<Utilizator> getUserById(Long utilizatorID) {
        String selectUserSQL = "SELECT * FROM Users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(selectUserSQL);
        ) {
            statement.setLong(1, utilizatorID);
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