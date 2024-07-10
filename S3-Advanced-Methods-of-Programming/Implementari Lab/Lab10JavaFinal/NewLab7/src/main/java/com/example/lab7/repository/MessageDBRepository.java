package com.example.lab7.repository;

import com.example.lab7.domain.Message;
import com.example.lab7.domain.ReplyMessage;
import com.example.lab7.domain.Utilizator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDBRepository implements Repository<Long, Message> {

    protected String url;
    protected String username;
    protected String password;

    public MessageDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Message> findOne(Long messageID) {
        String selectMessageSQL = "SELECT * FROM Messages" + " WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statementMessage = connection.prepareStatement(selectMessageSQL);
        ) {
            statementMessage.setInt(1, Math.toIntExact(messageID));
            ResultSet resultSet = statementMessage.executeQuery();
            if (resultSet.next()) {
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
                List<Utilizator> receivers = getReceiversForMessage(messageID);

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
                message.setId(messageID);

                return Optional.of(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll() {
        String selectMessageSQL = "SELECT * FROM Messages";
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statementMessage = connection.prepareStatement(selectMessageSQL);
             ResultSet resultSet = statementMessage.executeQuery();
        ) {
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
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> save(Message entity) {
        String selectMessageSQL = "INSERT INTO Messages (senderId, messageText, sentAt, messageType, repliedMessageId) VALUES (?, ?, ?, ?, ?)";
        String selectMessageReceiversSQL = "INSERT INTO MessagesReceivers (messageId, receiverId) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statementMessage = connection.prepareStatement(selectMessageSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementMessageReceivers = connection.prepareStatement(selectMessageReceiversSQL);
        ) {
            statementMessage.setLong(1, entity.getFrom().getId());
            statementMessage.setString(2, entity.getMessage());
            statementMessage.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            statementMessage.setString(4, entity instanceof ReplyMessage ? "ReplyMessage" : "Message");
            if (entity instanceof ReplyMessage) {
                statementMessage.setLong(5, ((ReplyMessage) entity).getRepliedMessage().getId());
            } else {
                statementMessage.setNull(5, Types.BIGINT);
            }

            int result = statementMessage.executeUpdate();
            if (result == 0)
                return Optional.empty();

            try (ResultSet generatedKeys = statementMessage.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    entity.setId(generatedId);
                    for (Utilizator user: entity.getTo()) {
                        statementMessageReceivers.setLong(1, entity.getId());
                        statementMessageReceivers.setLong(2, user.getId());
                        int resultReceivers = statementMessageReceivers.executeUpdate();
                        if (resultReceivers == 0)
                            return Optional.empty();
                    }
                    return Optional.of(entity);
                } else
                    throw new SQLException("Failed to retrieve the generated ID!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> delete(Long messageId) {
        String deleteMessageSQL = "DELETE FROM Messages WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statementDeleteMessage = connection.prepareStatement(deleteMessageSQL);
        ) {
            Optional<Message> deletedMessage = findOne(messageId);
            statementDeleteMessage.setLong(1, messageId);
            int resultDeleteMessage = statementDeleteMessage.executeUpdate();
            return resultDeleteMessage == 1 ? deletedMessage: Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> update(Message entity, Long aLong) {
        return Optional.empty();
    }


    private List<Utilizator> getReceiversForMessage(Long messageId) throws SQLException {
        String selectReceiversSQL = "SELECT * FROM MessagesReceivers WHERE messageID = ?";
        List<Utilizator> receivers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
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
        try (Connection connection = DriverManager.getConnection(url, username, password);
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
