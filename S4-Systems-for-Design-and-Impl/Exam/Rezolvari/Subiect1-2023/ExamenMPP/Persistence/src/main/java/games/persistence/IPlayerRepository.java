package games.persistence;

import games.model.Player;

import java.util.Optional;

public interface IPlayerRepository extends Repository<Long, Player> {

    Optional<Player> findOneByAlias(String alias);
}
