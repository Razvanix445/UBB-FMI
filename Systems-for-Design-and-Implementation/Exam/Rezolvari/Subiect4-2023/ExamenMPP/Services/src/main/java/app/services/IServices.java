package app.services;

import app.model.*;

import java.util.List;

public interface IServices {
    Player login(Player player, IObserver client) throws AppException;
    Game addGame(Game game) throws AppException;
    Position addPosition(Position position) throws AppException;
    Player findPlayerByAlias(String alias) throws AppException;
    List<Game> getAllGames() throws AppException;
    Player logout(Player player, IObserver client) throws AppException;
}
