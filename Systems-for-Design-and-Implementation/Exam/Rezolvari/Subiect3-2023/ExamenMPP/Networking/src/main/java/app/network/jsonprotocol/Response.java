package app.network.jsonprotocol;

import app.model.*;

import java.util.List;

public class Response {

    private ResponseType type;
    private String errorMessage;
    private Long aLong;
    private Game game;
    private Position position;
    private Configuration configuration;
    private Player player;
    private Word word;
    private ConfigurationWord configurationWord;
    private List<Game> games;
    private List<Position> positions;
    private List<Configuration> configurations;
    private List<Player> players;
    private List<Word> words;
    private List<ConfigurationWord> configurationWords;

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

    public Long getLong() {
        return aLong;
    }

    public void setLong(Long aLong) {
        this.aLong = aLong;
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

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public ConfigurationWord getConfigurationWord() {
        return configurationWord;
    }

    public void setConfigurationWord(ConfigurationWord configurationWord) {
        this.configurationWord = configurationWord;
    }

    public List<ConfigurationWord> getConfigurationWords() {
        return configurationWords;
    }

    public void setConfigurationWords(List<ConfigurationWord> configurationWords) {
        this.configurationWords = configurationWords;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                ", aLong=" + aLong +
                ", game=" + game +
                ", position=" + position +
                ", configuration=" + configuration +
                ", player=" + player +
                ", word=" + word +
                ", configurationWord=" + configurationWord +
                ", games=" + games +
                ", positions=" + positions +
                ", configurations=" + configurations +
                ", players=" + players +
                ", words=" + words +
                ", configurationWords=" + configurationWords +
                '}';
    }
}
