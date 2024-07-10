package app.services;

import app.model.Configuration;
import app.model.Game;
import app.model.Player;

import java.util.List;

public interface IServices {
    Player login(Player player, IObserver client) throws AppException;
    Game addGame(Game game) throws AppException;
    Player findPlayerByAlias(String alias) throws AppException;
    List<Game> getAllGames() throws AppException;
    List<Configuration> getAllConfigurations() throws AppException;
    Player logout(Player player, IObserver client) throws AppException;
}
