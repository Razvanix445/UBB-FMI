package com.example.lab7.gui;

import com.example.lab7.Session;
import com.example.lab7.domain.Message;
import com.example.lab7.domain.ReplyMessage;
import com.example.lab7.domain.Utilizator;
import com.example.lab7.service.Service;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatController {

    private Service service;

    private Utilizator selectedUser;

    @FXML
    private VBox friendMessageContainer;
    @FXML
    private VBox userMessageContainer;
    @FXML
    private HBox messageContainer;

    @FXML
    private Button exitButton;
    @FXML
    private VBox leftVBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private TextField sendMessageField;
    @FXML
    private Label friendUsername;
    @FXML
    private Pane spacer;

    public void setService(Service service) {
        this.service = service;
    }

    public void initialize() {
        chatScrollPane.setPrefWidth(Double.MAX_VALUE);
        chatScrollPane.setMaxWidth(Double.MAX_VALUE);
        leftVBox.getChildren().addListener((ListChangeListener<Node>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (Node node: c.getAddedSubList()) {
                        if (node instanceof Pane userPane) {
                            userPane.setOnMouseClicked(event -> handleUserClick(userPane));
                        }
                    }
                }
            }
        });
    }

    @FXML
    private void handleSendMessage() {
        String messageText = sendMessageField.getText();
        List<Utilizator> to = new ArrayList<>();
        to.add(selectedUser);
        Message message = service.sendMessage(Session.getLoggedUser(), to, messageText);

        HBox newMessageBox = createMessageBox(message);
        userMessageContainer.getChildren().add(newMessageBox);
        Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMinHeight(50);
        pane.setPrefHeight(50);
        pane.setMaxHeight(50);
        friendMessageContainer.getChildren().add(pane);
        messageContainer.getChildren().setAll(friendMessageContainer, userMessageContainer);
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchTerm = searchTextField.getText().trim();
        if (!searchTerm.isEmpty()) {
            List<Utilizator> searchResults = service.cautaUtilizatoriDupaCeva(searchTerm);
            leftVBox.getChildren().clear();

            for (Utilizator utilizator : searchResults) {
                Pane userPane = new Pane();
                userPane.getStyleClass().addAll("user-background", "radius");

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setLayoutX(17.0);
                hBox.setLayoutY(-1.0);
                hBox.setPrefHeight(60.0);
                hBox.setPrefWidth(178.0);

                ImageView profileImage = new ImageView(new Image("com/example/lab7/gui/icons/Profile.png"));
                profileImage.setFitHeight(57.0);
                profileImage.setFitWidth(50.0);

                Label usernameLabel = new Label(utilizator.getUsername());
                usernameLabel.setFont(Font.font("Comic Sans MS", 12.0));

                hBox.getChildren().addAll(profileImage, usernameLabel);
                userPane.getChildren().add(hBox);

                VBox.setMargin(userPane, new Insets(5.0));
                leftVBox.getChildren().add(userPane);
            }
        }
    }

    private void handleUserClick(Pane userPane) {
        leftVBox.getChildren().forEach(node -> node.getStyleClass().remove("user-pressed-background"));
        userPane.getStyleClass().add("user-pressed-background");
        String username = ((Label) ((HBox) userPane.getChildren().get(0)).getChildren().get(1)).getText();
        friendUsername.setText(username);
        Utilizator selectedUser = service.cautaUtilizatorDupaUsername(username);
        this.selectedUser = selectedUser;
        List<Message> messages = service.getMessagesBetweenUsers(Session.getLoggedUser(), selectedUser);
        displayMessages(messages);
    }

    private void displayMessages(List<Message> messages) {
        friendMessageContainer.getChildren().clear();
        friendMessageContainer.setSpacing(10.0);
        userMessageContainer.getChildren().clear();
        userMessageContainer.setSpacing(10.0);

        for (Message message : messages) {
            if (message instanceof ReplyMessage) {
                Message repliedMessage = ((ReplyMessage) message).getRepliedMessage();
                HBox replyMessageBox = createMessageBox(message);
                Label repliedMessageLabel = new Label("<Reply: " + repliedMessage.getMessage() + "> ");
                repliedMessageLabel.getStyleClass().add("transparent-background");

                HBox combinedMessageBox = new HBox();
                combinedMessageBox.setSpacing(10.0);

                spacer = new Pane();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                spacer.setMaxWidth(Double.MAX_VALUE);
                spacer.setMinHeight(50);
                spacer.setPrefHeight(50);
                spacer.setMaxHeight(50);
                HBox.setMargin(spacer, new Insets(5.0));

                if (message.getFrom().equals(service.getLoggedUser())) {
                    combinedMessageBox.setAlignment(Pos.CENTER_LEFT);
                    combinedMessageBox.getChildren().addAll(repliedMessageLabel, replyMessageBox);
                    userMessageContainer.getChildren().add(combinedMessageBox);
                    friendMessageContainer.getChildren().add(spacer);
                } else {
                    combinedMessageBox.setAlignment(Pos.CENTER_RIGHT);
                    combinedMessageBox.getChildren().addAll(repliedMessageLabel, replyMessageBox);
                    friendMessageContainer.getChildren().add(combinedMessageBox);
                    userMessageContainer.getChildren().add(spacer);
                }
            } else {
                HBox messageBox = createMessageBox(message);

                spacer = new Pane();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                spacer.setMaxWidth(Double.MAX_VALUE);
                spacer.setMinHeight(50);
                spacer.setPrefHeight(50);
                spacer.setMaxHeight(50);
                HBox.setMargin(spacer, new Insets(5.0));

                if (message.getFrom().equals(service.getLoggedUser())) {
                    userMessageContainer.getChildren().add(messageBox);
                    friendMessageContainer.getChildren().add(spacer);
                } else {
                    friendMessageContainer.getChildren().add(messageBox);
                    userMessageContainer.getChildren().add(spacer);
                }
            }

        }
        messageContainer.setAlignment(Pos.CENTER);
        messageContainer.getChildren().setAll(friendMessageContainer, userMessageContainer);
        chatScrollPane.setContent(messageContainer);
    }


    private HBox createMessageBox(Message message) {
        HBox messageBox = new HBox();
        Button replyButton = new Button();
        ImageView replyImage = new ImageView(new Image("com/example/lab7/gui/icons/Reply.png"));
        replyImage.setFitHeight(39.0);
        replyImage.setFitWidth(39.0);
        replyButton.setGraphic(replyImage);
        replyButton.getStyleClass().add("transparent-background");
        replyButton.setOnAction(event -> handleReply(message));

        Label messageLabel = new Label(message.getMessage());
        ImageView imageView = new ImageView(new Image("com/example/lab7/gui/icons/Profile.png"));
        imageView.setFitHeight(39.0);
        imageView.setFitWidth(39.0);

        if (message.getFrom().equals(service.getLoggedUser())) {
            messageBox.getStyleClass().add("message-bubble");
            messageBox.getChildren().addAll(imageView, messageLabel, replyButton);
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(messageBox, Priority.ALWAYS);
            messageBox.setMaxWidth(Double.MAX_VALUE);
        } else {
            messageBox.getStyleClass().add("friend-message-bubble");
            messageBox.getChildren().addAll(replyButton, messageLabel, imageView);
            messageBox.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(messageBox, Priority.ALWAYS);
            messageBox.setMaxWidth(Double.MAX_VALUE);
        }
        HBox.setMargin(messageBox, new Insets(5.0));
        messageBox.setMinHeight(50);
        messageBox.setPrefHeight(50);
        messageBox.setMaxHeight(50);

        return messageBox;
    }

    private void handleReply(Message repliedMessage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reply to message");
        dialog.setHeaderText("Enter your reply: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(replyText -> addReplyMessage(repliedMessage, replyText));
    }

    private void addReplyMessage(Message repliedMessage, String replyText) {
        List<Utilizator> to = new ArrayList<>();
        to.add(repliedMessage.getFrom());
        ReplyMessage replyMessage = service.replyMessage(Session.getLoggedUser(), to, replyText, repliedMessage);

        Label repliedMessageLabel = new Label("<Reply: " + repliedMessage.getMessage() + "> ");
        repliedMessageLabel.getStyleClass().add("transparent-background");
        HBox replyMessageBox = createMessageBox(replyMessage);

        HBox combinedMessageBox = new HBox();
        combinedMessageBox.setAlignment(Pos.CENTER_LEFT);
        combinedMessageBox.setSpacing(10.0);
        combinedMessageBox.getChildren().addAll(repliedMessageLabel, replyMessageBox);

        userMessageContainer.getChildren().add(combinedMessageBox);
    }

    @FXML
    private void handleExit() {
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
    }
}