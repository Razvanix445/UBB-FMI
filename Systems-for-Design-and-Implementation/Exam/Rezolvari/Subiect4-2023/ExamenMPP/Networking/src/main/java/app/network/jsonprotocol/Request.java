package app.network.jsonprotocol;

import app.model.Game;
import app.model.Player;
import app.model.Position;

import java.util.List;

public class Request {

    private RequestType type;
    private String stringR;
    private Long aLong;
    private Player player;
    private Game game;
    private Position position;
    private List<Player> players;
    private List<Game> games;
    private List<Position> positions;

    public Request() {}

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getStringR() {
        return stringR;
    }

    public void setStringR(String stringR) {
        this.stringR = stringR;
    }

    public Long getLong() {
        return aLong;
    }

    public void setLong(Long aLong) {
        this.aLong = aLong;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", stringR='" + stringR + '\'' +
                ", aLong=" + aLong +
                ", player=" + player +
                ", game=" + game +
                ", position=" + position +
                ", players=" + players +
                ", games=" + games +
                ", positions=" + positions +
                '}';
    }
}
