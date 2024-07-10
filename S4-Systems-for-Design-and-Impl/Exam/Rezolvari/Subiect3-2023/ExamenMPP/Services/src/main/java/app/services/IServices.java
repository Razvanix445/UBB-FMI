package app.services;

import app.model.*;

import java.util.List;

public interface IServices {
    Player login(Player player, IObserver client) throws AppException;
    Position addPosition(Position position) throws AppException;
    ConfigurationWord addConfigurationWord(ConfigurationWord configurationWord) throws AppException;
    Configuration addConfiguration(Configuration configuration) throws AppException;
    Game addGame(Game game) throws AppException;
    Player findPlayerByAlias(String alias) throws AppException;
    List<Game> getAllGames() throws AppException;
    List<Configuration> getAllConfigurations() throws AppException;
    List<Word> getAllWords() throws AppException;
    Player logout(Player player, IObserver client) throws AppException;
}
