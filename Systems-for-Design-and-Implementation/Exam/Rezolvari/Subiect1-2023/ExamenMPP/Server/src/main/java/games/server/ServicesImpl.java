package games.server;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;
import games.persistence.IConfigurationRepository;
import games.persistence.IGameRepository;
import games.persistence.IPlayerRepository;
import games.persistence.IPositionRepository;
import games.services.IObserver;
import games.services.IServices;
import games.services.AppException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

public class ServicesImpl implements IServices {

    private static final Logger logger = LogManager.getLogger();

    private IPlayerRepository playerRepository;
    private IGameRepository gameRepository;
    private IConfigurationRepository configurationRepository;
    private IPositionRepository positionRepository;

    private Map<String, IObserver> loggedClients;

    public ServicesImpl(IPlayerRepository playerRepository, IGameRepository gameRepository, IConfigurationRepository configurationRepository, IPositionRepository positionRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.configurationRepository = configurationRepository;
        this.positionRepository = positionRepository;
        loggedClients = new ConcurrentHashMap<>();
        logger.info("Initializing ServiceImpl");
    }

    public synchronized Player login(Player player, IObserver client) throws AppException {
        logger.info("Player initialized: " + player);
        Player foundPlayer = findPlayerByAlias(player.getAlias());
        if (foundPlayer != null) {
            if(loggedClients.get(player.getAlias()) != null)
                throw new AppException("Player already logged in.");
            loggedClients.put(player.getAlias(), client);
        } else
            throw new AppException("Authentication failed.");
        return player;
    }

    @Override
    public synchronized Configuration addConfiguration(Configuration configuration) throws AppException {
        logger.info("Adding Configuration: " + configuration + " ...");
        return configurationRepository.save(configuration).orElseThrow(() -> new AppException("Error adding the configuration."));
    }

    @Override
    public synchronized Position addPosition(Position position) throws AppException {
        logger.info("Adding Position: " + position + " ...");
        return positionRepository.save(position).orElseThrow(() -> new AppException("Error adding the position."));
    }

    @Override
    public synchronized Game addGame(Game game) throws AppException {
        logger.info("Adding Game: " + game + " ...");
        Game addedGame = gameRepository.save(game).orElseThrow(() -> new AppException("Error adding the game."));
        // notify all clients that a new game was added
        for (IObserver client: loggedClients.values()) {
            try {
                client.gameAdded(addedGame);
            } catch (AppException e) {
                System.err.println("Error notifying client: " + e);
            }
        }
        return addedGame;
    }

    @Override
    public synchronized List<Configuration> getAllConfigurations() throws AppException {
        logger.info("Getting all configurations...");
        Iterable<Configuration> configurations = configurationRepository.findAll();
        return StreamSupport.stream(configurations.spliterator(), false).toList();
    }

    @Override
    public synchronized List<Game> getAllGamesForPlayer(Player player) throws AppException {
        logger.info("Getting all games for player: " + player.getAlias() + " ...");
        Iterable<Game> games = gameRepository.findAllByPlayer(player);
        return StreamSupport.stream(games.spliterator(), false).toList();
    }

    @Override
    public synchronized List<Game> getAllGames() throws AppException {
        logger.info("Getting all games...");
        Iterable<Game> games = gameRepository.findAll();
        return StreamSupport.stream(games.spliterator(), false).toList();
    }

    public synchronized Player findPlayerByAlias(String alias) throws AppException {
        logger.info("Finding player by alias: " + alias + " ...");
        return playerRepository.findOneByAlias(alias).orElseThrow(() -> new AppException("Player not found."));
    }

    public synchronized Player logout(Player player, IObserver client) throws AppException {
        logger.info("Logging out player: " + player.getAlias() + " ...");
        IObserver localClient = loggedClients.remove(player.getAlias());
        if (localClient == null)
            throw new AppException("Player " + player.getAlias() + " is not logged in.");
        return player;
    }
}
