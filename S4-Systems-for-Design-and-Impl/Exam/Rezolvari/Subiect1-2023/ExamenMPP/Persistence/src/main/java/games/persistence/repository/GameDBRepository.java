package games.persistence.repository;

import games.persistence.IGameRepository;
import games.model.Game;
import games.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

public class GameDBRepository implements IGameRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public GameDBRepository(Properties props) {
        logger.info("Initializing GameDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }


    @Override
    public Iterable<Game> findAllByPlayer(Player player) {
        return null;
    }

    @Override
    public Optional<Game> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Game> findAll() {
        return null;
    }

    @Override
    public Optional<Game> save(Game entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Game> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Game> update(Game entity) {
        return Optional.empty();
    }
}
