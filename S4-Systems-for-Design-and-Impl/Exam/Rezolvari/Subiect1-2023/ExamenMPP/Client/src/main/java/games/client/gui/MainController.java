package games.client.gui;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import games.services.IObserver;
import games.services.IServices;
import games.services.AppException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class MainController implements Initializable, IObserver {

    @FXML
    private GridPane gameTable;

    @FXML
    private TableView<Game> clasamentTableView;

    @FXML
    private TableColumn<Game, String> aliasColumn;

    @FXML
    private TableColumn<Game, String> startingTimeColumn;

    @FXML
    private TableColumn<Game, Integer> triesColumn;

    @FXML
    private TableColumn<Game, String> hintColumn;

    private IServices server;
    private Player loggedUser;
    private Stage stage;

    private Game game;
    private Configuration configuration;
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

        startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
        triesColumn.setCellValueFactory(new PropertyValueFactory<>("NoOfTries"));

        hintColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> game) {
                return new SimpleStringProperty(game.getValue().getConfiguration() != null ? game.getValue().getConfiguration().getHint() : null);
            }
        });
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
            Random random = new Random();
            List<Configuration> configurations = server.getAllConfigurations();
            int randomIndex = random.nextInt(configurations.size());
            configuration = configurations.get(randomIndex);

            positions.clear();
            game = new Game(loggedUser, false, 0L, LocalDateTime.now(), configuration);

            loadLeaderboard();

        } catch (AppException e) {
            e.printStackTrace();
        }
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
            finishedGames.sort(Comparator.comparingLong(Game::getNoOfTries)
                    .thenComparing(Game::getStartingTime)
                    .thenComparing(game -> game.getPlayer().getAlias()));
            ObservableList<Game> gameObservableList = FXCollections.observableArrayList(finishedGames);
            clasamentTableView.setItems(gameObservableList);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCellClick(MouseEvent event) {
        System.out.println("GridPane clicked.");

        double cellWidth = gameTable.getWidth() / 4;
        double cellHeight = gameTable.getHeight() / 4;

        int colIndex = (int) Math.floor(event.getX() / cellWidth);
        int rowIndex = (int) Math.floor(event.getY() / cellHeight);

        System.out.println("Row: " + rowIndex + ", Col: " + colIndex);

        Text clickedCell = null;
        for (Node node : gameTable.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == rowIndex &&
                    GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == colIndex) {
                clickedCell = (Text) node;
            }
        }

        if (clickedCell != null) {
            double euclideanDistance = Math.sqrt(Math.pow(configuration.getCoordinateX() - colIndex, 2) +
                    Math.pow(configuration.getCoordinateY() - rowIndex, 2));

            clickedCell.setText(String.format("Distance: %.2f", euclideanDistance));

            game.setNoOfTries(game.getNoOfTries() + 1);
            positions.add(new Position((long) rowIndex, (long) colIndex, game.getNoOfTries()));

            Game addedGame = null;
            if (configuration.getCoordinateX() == colIndex && configuration.getCoordinateY() == rowIndex) {
                clickedCell.setText("Hint: " + configuration.getHint());
                game.setIsWon(true);
                addedGame = endGame();
            } else if (game.getNoOfTries() >= 4) {
                game.setIsWon(false);
                addedGame = endGame();
            }
            System.out.println(addedGame);
            if (addedGame != null) {
                for (Position position : positions) {
                    position.setGame(game);
                    try {
                        server.addPosition(position);
                    } catch (AppException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        } else {
            System.out.println("No cell found at Row: " + rowIndex + ", Col: " + colIndex);
        }
    }

    private Game endGame() {
        try {
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                for (Node node : gameTable.getChildren()) {
                    if (node instanceof Text) {
                        ((Text) node).setText("");
                    }
                }
                showStatisticsAfterTheGame();
                initModel();
            });
            return addedGame;
        } catch (AppException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showStatisticsAfterTheGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);

        String hintPosition = configuration.getCoordinateX() + ", " + configuration.getCoordinateY();
        String numberOfTries = String.valueOf(game.getNoOfTries());

        int playerPositionOnLeaderboard = 1;
        try {
            for (Game gameFromServer : server.getAllGames()) {
                if (gameFromServer.getNoOfTries() < game.getNoOfTries()) {
                    playerPositionOnLeaderboard++;
                }
            }
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        String content = "Hint Position: " + hintPosition + "\n" +
                "Number of Tries: " + numberOfTries + "\n" +
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