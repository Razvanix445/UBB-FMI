package app.server;

import app.model.*;
import app.persistence.*;
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
    private IPositionRepository positionRepository;
    private IWordRepository wordRepository;
    private IConfigurationWordRepository configurationWordRepository;

    public ServicesImpl(IPlayerRepository playerRepository, IGameRepository gameRepository, IConfigurationRepository configurationRepository, IPositionRepository positionRepository, IWordRepository wordRepository, IConfigurationWordRepository configurationWordRepository) {
        logger.info("Initializing ServicesImpl");
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.configurationRepository = configurationRepository;
        this.positionRepository = positionRepository;
        this.wordRepository = wordRepository;
        this.configurationWordRepository = configurationWordRepository;
        loggedClients = new ConcurrentHashMap<>();
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
    public synchronized Position addPosition(Position position) throws AppException {
        logger.info("Adding Position: " + position + " ...");
        return positionRepository.save(position).orElseThrow(() -> new AppException("Error adding the position."));
    }

    @Override
    public synchronized Configuration addConfiguration(Configuration configuration) throws AppException {
        logger.info("Adding Configuration: " + configuration + " ...");
        return configurationRepository.save(configuration).orElseThrow(() -> new AppException("Error adding the configuration."));
    }

    @Override
    public synchronized ConfigurationWord addConfigurationWord(ConfigurationWord configurationWord) throws AppException {
        logger.info("Adding ConfigurationWord: " + configurationWord + " ...");
        return configurationWordRepository.save(configurationWord).orElseThrow(() -> new AppException("Error adding the configurationWord."));
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

    @Override
    public synchronized List<Word> getAllWords() throws AppException {
        logger.info("Getting all words...");
        Iterable<Word> words = wordRepository.findAll();
        return StreamSupport.stream(words.spliterator(), false).toList();
    }

    @Override
    public synchronized Player findPlayerByAlias(String alias) throws AppException {
        Iterable<Player> players = playerRepository.findAll();
        for (Player player : players) {
            if (player.getAlias().equals(alias)) {
                return player;
            }
        }
        return null;
    }

    public synchronized Player logout(Player player, IObserver client) throws AppException {
        IObserver localClient = loggedClients.remove(player.getAlias());
        if (localClient == null)
            throw new AppException("Player " + player.getAlias() + " is not logged in.");
        return player;
    }
}
