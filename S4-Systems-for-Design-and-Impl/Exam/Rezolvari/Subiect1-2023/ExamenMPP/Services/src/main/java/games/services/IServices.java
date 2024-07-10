package games.services;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;

import java.util.List;

public interface IServices {
    Player login(Player player, IObserver client) throws AppException;
    Configuration addConfiguration(Configuration configuration) throws AppException;
    Position addPosition(Position position) throws AppException;
    Game addGame(Game game) throws AppException;
    List<Configuration> getAllConfigurations() throws AppException;
    List<Game> getAllGamesForPlayer(Player player) throws AppException;
    List<Game> getAllGames() throws AppException;
    Player findPlayerByAlias(String alias) throws AppException;
    Player logout(Player player, IObserver client) throws AppException;
}
