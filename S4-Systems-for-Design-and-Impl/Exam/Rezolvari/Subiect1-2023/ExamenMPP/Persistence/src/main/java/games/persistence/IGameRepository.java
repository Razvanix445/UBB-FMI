package games.persistence;

import games.model.Game;
import games.model.Player;

public interface IGameRepository extends Repository<Long, Game> {

    Iterable<Game> findAllByPlayer(Player player);
}
