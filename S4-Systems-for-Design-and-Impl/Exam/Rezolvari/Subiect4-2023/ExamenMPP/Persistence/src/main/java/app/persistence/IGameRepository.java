package app.persistence;

import app.model.Game;
import app.model.Player;

public interface IGameRepository extends Repository<Long, Game> {

    Iterable<Game> findAllByPlayer(Player player);
}
