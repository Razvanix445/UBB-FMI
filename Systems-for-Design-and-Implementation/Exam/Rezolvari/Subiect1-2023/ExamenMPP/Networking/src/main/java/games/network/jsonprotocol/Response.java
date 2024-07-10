package games.network.jsonprotocol;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;

import java.util.List;

public class Response {

    private ResponseType type;
    private String errorMessage;
    private Player player;
    private Game game;
    private Configuration configuration;
    private Position position;
    private List<Game> games;
    private List<Configuration> configurations;

    public Response(){}

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                ", player=" + player +
                ", game=" + game +
                ", configuration=" + configuration +
                ", position=" + position +
                ", games=" + games +
                ", configurations=" + configurations +
                '}';
    }
}
