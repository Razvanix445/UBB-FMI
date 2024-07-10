package app.services;

import app.model.Game;

public interface IObserver {
    void gameAdded(Game game) throws AppException;
}
