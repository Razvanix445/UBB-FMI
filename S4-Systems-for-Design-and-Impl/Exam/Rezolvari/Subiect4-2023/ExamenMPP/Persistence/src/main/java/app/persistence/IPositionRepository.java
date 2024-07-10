package app.persistence;

import app.model.Game;
import app.model.Position;

public interface IPositionRepository extends Repository<Long, Position> {

    Iterable<Position> findAllByGame(Game game);
}
