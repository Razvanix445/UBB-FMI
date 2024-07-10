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
    private GridPane gameTable;

    @FXML
    private TableView<Game> clasamentTableView;

    @FXML
    private TableColumn<Game, String> aliasColumn;

    @FXML
    private TableColumn<Game, Long> scoreColumn;

    @FXML
    private TableColumn<Game, Long> noOfSecondsColumn;

    private IServices server;
    private Player loggedUser;
    private Stage stage;

    private int cellClickCount = 0;
    private GameCell[][] gameCells = new GameCell[3][3];
    private LocalDateTime startTime;
    private Game game;
    private List<Position> positions = new ArrayList<>();

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

        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        noOfSecondsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfSeconds"));
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
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : gameTable.getChildren()) {
            if (node instanceof Label) {
                nodesToRemove.add(node);
            }
        }
        gameTable.getChildren().removeAll(nodesToRemove);

        gameTable.getColumnConstraints().clear();
        gameTable.getRowConstraints().clear();
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(33.33);
            gameTable.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(33.33);
            gameTable.getRowConstraints().add(row);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setMinSize(100, 100);

                GameCell cell = new GameCell("", label);
                gameCells[i][j] = cell;
                gameTable.add(label, j, i);
                label.setOnMouseClicked(this::handleCellClick);

                GridPane.setFillWidth(label, true);
                GridPane.setFillHeight(label, true);
            }
        }

        cellClickCount = 0;
        positions.clear();
        startTime = LocalDateTime.now();
        game = new Game(loggedUser, 0L, 0L);

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
            finishedGames.sort(Comparator.comparingLong(Game::getScore)
                    .thenComparingLong(Game::getNoOfSeconds));
            ObservableList<Game> gameObservableList = FXCollections.observableArrayList(finishedGames);
            clasamentTableView.setItems(gameObservableList);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCellClick(MouseEvent event) {
        System.out.println("GameCell clicked.");

        Label clickedLabelNode = (Label) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(clickedLabelNode);
        Integer rowIndex = GridPane.getRowIndex(clickedLabelNode);

        if (colIndex == null || rowIndex == null) {
            System.out.println("Invalid cell index.");
            return;
        }

        if (gameCells[rowIndex][colIndex].getMark().isEmpty()) {
            gameCells[rowIndex][colIndex].setMark("X");
            clickedLabelNode.setText("X");

            cellClickCount ++;

            Position position = new Position(game, Long.parseLong(valueOf(rowIndex)), Long.parseLong(valueOf(colIndex)), Long.parseLong(valueOf(cellClickCount)));
            positions.add(position);

            if (checkWin("X")) {
                game.setScore(10L);
                game.setNoOfSeconds(ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
                endGame("Player wins!");
                return;
            } else if (cellClickCount == 9) {
                game.setScore(5L);
                game.setNoOfSeconds(ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
                endGame("It's a draw!");
                return;
            }

            computerMove();
        } else {
            System.out.println("Cell already marked.");
        }
    }

    private void computerMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!gameCells[row][col].getMark().isEmpty());

        gameCells[row][col].setMark("O");
        Label labelNode = gameCells[row][col].getLabelNode();
        labelNode.setText("O");

        cellClickCount ++;

        if (checkWin("O")) {
            game.setScore(-10L);
            game.setNoOfSeconds(ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
            endGame("Computer wins!");
        } else if (cellClickCount == 9) {
            game.setScore(5L);
            game.setNoOfSeconds(ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
            endGame("It's a draw!");
        }
    }

    private boolean checkWin(String mark) {
        for (int i = 0; i < 3; i++) {
            if (gameCells[i][0].getMark().equals(mark) && gameCells[i][1].getMark().equals(mark) && gameCells[i][2].getMark().equals(mark))
                return true;
            if (gameCells[0][i].getMark().equals(mark) && gameCells[1][i].getMark().equals(mark) && gameCells[2][i].getMark().equals(mark))
                return true;
        }
        if (gameCells[0][0].getMark().equals(mark) && gameCells[1][1].getMark().equals(mark) && gameCells[2][2].getMark().equals(mark))
            return true;
        if (gameCells[0][2].getMark().equals(mark) && gameCells[1][1].getMark().equals(mark) && gameCells[2][0].getMark().equals(mark))
            return true;
        return false;
    }

    private Game endGame(String message) {
        try {
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                showStatisticsAfterTheGame(message);
                initModel();
            });
            addPositions();
            return addedGame;
        } catch (AppException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addPositions() {
        List<Game> games;
        try {
            games = server.getAllGames();
        } catch (AppException e) {
            e.printStackTrace();
            return;
        }

        if (games.isEmpty()) {
            return;
        }

        Game gameWithHighestId = games.get(0);
        for (Game game : games) {
            if (game.getId() > gameWithHighestId.getId()) {
                gameWithHighestId = game;
            }
        }

        try {
            for (Position position: positions) {
                position.setGame(gameWithHighestId);
                server.addPosition(position);
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
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
                else if (gameFromServer.getScore().equals(game.getScore()) && gameFromServer.getNoOfSeconds() < game.getNoOfSeconds()) {
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
        Stage stage = (Stage) gameTable.getScene().getWindow();
        stage.close();
    }
}

class GameCell {
    private String mark;
    private Label labelNode;

    public GameCell(String mark, Label labelNode) {
        this.mark = mark;
        this.labelNode = labelNode;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Label getLabelNode() {
        return labelNode;
    }

    public void setLabelNode(Label labelNode) {
        this.labelNode = labelNode;
    }
}