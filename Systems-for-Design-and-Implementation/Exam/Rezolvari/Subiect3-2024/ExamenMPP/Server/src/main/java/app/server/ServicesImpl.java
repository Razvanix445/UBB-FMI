package app.server;

// TODO 1: IMPORT USED MODELS AND REPOSITORIES
import app.model.Configuration;
import app.model.Game;
import app.model.Player;
import app.persistence.IConfigurationRepository;
import app.persistence.IGameRepository;
import app.persistence.IPlayerRepository;
import app.services.AppException;
import app.services.IObserver;
import app.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

public class ServicesImpl implements IServices {

    private static final Logger logger = LogManager.getLogger(ServicesImpl.class);

    private Map<String, IObserver> loggedClients;

    private IPlayerRepository playerRepository;
    private IGameRepository gameRepository;
    private IConfigurationRepository configurationRepository;

    public ServicesImpl(IPlayerRepository playerRepository, IGameRepository gameRepository, IConfigurationRepository configurationRepository) {
        logger.info("Initializing ServicesImpl");
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.configurationRepository = configurationRepository;
        loggedClients = new ConcurrentHashMap<>();
    }

    public synchronized Player login(Player player, IObserver client) throws AppException {
        logger.info("Player initialized: " + player);
        Player foundPlayer = findPlayerByAlias(player.getAlias());
        if (foundPlayer != null){
            if(loggedClients.get(player.getAlias()) != null)
                throw new AppException("Player already logged in.");
            loggedClients.put(player.getAlias(), client);
        } else
            throw new AppException("Authentication failed.");
        return player;
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

    public synchronized Player findPlayerByAlias(String alias) throws AppException {
        Iterable<Player> players = playerRepository.findAll();
        for (Player player : players) {
            if (player.getAlias().equals(alias)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public synchronized List<Game> getAllGames() throws AppException {
        logger.info("Getting all games...");
        Iterable<Game> games = gameRepository.findAll();
        return StreamSupport.stream(games.spliterator(), false).toList();
    }

    @Override
    public synchronized List<Configuration> getAllConfigurations() throws AppException {
        logger.info("Getting all configurations...");
        Iterable<Configuration> configurations = configurationRepository.findAll();
        return StreamSupport.stream(configurations.spliterator(), false).toList();
    }

    public synchronized Player logout(Player player, IObserver client) throws AppException {
        IObserver localClient = loggedClients.remove(player.getAlias());
        if (localClient == null)
            throw new AppException("Player " + player.getAlias() + " is not logged in.");
        return player;
    }
}
