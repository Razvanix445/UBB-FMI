package games.persistence;

import games.model.Game;
import games.model.Player;
import games.model.Position;

import java.util.Optional;

public interface IPositionRepository extends Repository<Long, Position> {

    Iterable<Position> findAllForGame(Game game);
}
