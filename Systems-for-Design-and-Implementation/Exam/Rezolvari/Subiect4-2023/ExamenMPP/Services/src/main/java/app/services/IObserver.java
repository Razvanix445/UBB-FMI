package app.services;

import app.model.*;

public interface IObserver {

    void gameAdded(Game game) throws AppException;
}
