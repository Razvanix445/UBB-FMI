package app.client.gui;

import app.model.*;
import app.services.AppException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import app.services.IObserver;
import app.services.IServices;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static java.lang.String.valueOf;

public class MainController implements Initializable, IObserver {

    @FXML
    private TableView<Game> clasamentTableView;

    @FXML
    private TableColumn<Game, String> aliasColumn;

    @FXML
    private TableColumn<Game, Long> scoreColumn;

    @FXML
    private TableColumn<Game, LocalDateTime> startingTimeColumn;

    @FXML
    private Button tryButton;

    @FXML
    private TextField enteredText;

    @FXML
    private Text literePosibile;

    @FXML
    private Text word1;

    @FXML
    private Text word2;

    @FXML
    private Text word3;

    @FXML
    private Text word4;

    @FXML
    private Text cautaCuvantul;

    private Long noOfGuessedWords;
    private Long noOfTries;
    private IServices server;
    private Player loggedUser;
    private Stage stage;

    private LocalDateTime startTime;
    private Game game;
    private Configuration configuration;

    public MainController() {
        System.out.println("MainController constructor");
    }

    public MainController(IServices server) {
        this.server = server;
        System.out.println("MainController constructor with server parameters");
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoggedUser(Player loggedUser) {
        this.loggedUser = loggedUser;
        initModel();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        aliasColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> game) {
                return new SimpleStringProperty(game.getValue().getPlayer() != null ? game.getValue().getPlayer().getAlias() : null);
            }
        });

        startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

    }

    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {
        try {
            server.logout(loggedUser, this);
            System.out.println("Logout successful (MainController).");
        } catch (AppException e) {
            System.out.println("Logout failed (MainController). " + e);
        }
    }

    private void initModel() {
        try {
            List<Configuration> configurations = server.getAllConfigurations();
            Collections.shuffle(configurations);
            configuration = configurations.get(0);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < configuration.getLetters().length(); i++) {
            letters.append(configuration.getLetters().charAt(i));
            letters.append(" ");
        }
        literePosibile.setText(String.valueOf(letters));

        word1.setText("");
        word2.setText("");
        word3.setText("");
        word4.setText("");
        noOfGuessedWords = 0L;
        noOfTries = 0L;
        startTime = LocalDateTime.now();
        game = new Game(loggedUser, configuration, LocalDateTime.now(), 0L, 0L);

        loadLeaderboard();
    }

    public void gameAdded(Game addedGame) throws AppException {
        Platform.runLater(() -> {
            System.out.println("Game added: " + addedGame);
            loadLeaderboard();
        });
    }

    private void loadLeaderboard() {
        try {
            List<Game> finishedGames = server.getAllGames();
            ObservableList<Game> gameObservableList = FXCollections.observableArrayList(finishedGames);
            clasamentTableView.setItems(gameObservableList);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonClick(MouseEvent event) {
        System.out.println("Button clicked.");

        noOfTries ++;

        String cuvant = enteredText.getText().trim();
        if (cuvant.equals(configuration.getWord1())) {
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word1.setText(configuration.getWord1());
            noOfGuessedWords ++;
        }
        else if (cuvant.equals(configuration.getWord2())) {
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word2.setText(configuration.getWord2());
            noOfGuessedWords ++;
        }
        else if (cuvant.equals(configuration.getWord3())) {
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word3.setText(configuration.getWord3());
            noOfGuessedWords ++;
        }
        else if (cuvant.equals(configuration.getWord4())) {
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word4.setText(configuration.getWord4());
            noOfGuessedWords ++;
        }
        else {
            Long word1Match = 0L;
            Long word2Match = 0L;
            Long word3Match = 0L;
            Long word4Match = 0L;
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord1().charAt(i)) {
                    word1Match ++;
                }
                else
                    break;
            }
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord2().charAt(i)) {
                    word2Match ++;
                }
                else
                    break;
            }
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord3().charAt(i)) {
                    word3Match ++;
                }
                else
                    break;
            }
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord4().charAt(i)) {
                    word4Match ++;
                }
                else
                    break;
            }
            Long maximum = 0L;
            if (word1Match >= word2Match)
                maximum = word1Match;
            else maximum = word2Match;
            if (word3Match >= maximum)
                maximum = word3Match;
            else if (word4Match >= maximum)
                maximum = word3Match;

            game.setScore(game.getScore() + maximum);
        }

        if (noOfTries >= 4)
            endGame("Game Over!");
    }

    private Game endGame(String message) {
        try {
            Player player = server.findPlayerByAlias(loggedUser.getAlias());
            game.setPlayer(player);
            game.setNoOfGuessedWords(noOfGuessedWords);
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                showStatisticsAfterTheGame(message);
                initModel();
            });
            return addedGame;
        } catch (AppException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showStatisticsAfterTheGame(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(message);

        Long totalScore = game.getScore();

        int playerPositionOnLeaderboard = 1;
        try {
            for (Game gameFromServer : server.getAllGames()) {
                if (gameFromServer.getScore() > game.getScore()) {
                    playerPositionOnLeaderboard++;
                }
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        String content = "Total Score: " + totalScore + "\n" +
                "Your Position in Leaderboard: " + playerPositionOnLeaderboard;

        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) tryButton.getScene().getWindow();
        stage.close();
    }
}