package games.services;

import games.model.Game;

public interface IObserver {
    void gameAdded(Game game) throws AppException;
}
