package app.services.rest;

// TODO 1: ADD ALL MODELS
import app.model.Configuration;
import app.model.Game;
import app.model.Player;
import app.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.StreamSupport.stream;

@CrossOrigin
@RestController
@RequestMapping("/app")
public class Controller {

    private static final String template = "Hello, %s!";

    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IConfigurationRepository configurationRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/games/{alias}")
    public List<Game> getGamesByPlayer(@PathVariable String alias) {
        Player foundPlayer = null;
        Iterable<Player> players = playerRepository.findAll();
        for (Player player : players) {
            if (player.getAlias().equals(alias)) {
                foundPlayer = player;
                break;
            }
        }
        if (foundPlayer == null)
            throw new RepositoryException("Player not found");

        List<Game> games = StreamSupport.stream(gameRepository.findAllByPlayer(foundPlayer).spliterator(), false).toList();
        List<Game> gamesWithAtLeast2Guesses = new ArrayList<>();

        for (Game game: games)
            if (game.getNoOfGuessedWords() >= 2)
                gamesWithAtLeast2Guesses.add(game);

        return gamesWithAtLeast2Guesses;
    }

    @PostMapping("/configurations")
    public ResponseEntity<Configuration> addConfiguration(@RequestBody Configuration configuration) {
        Configuration savedConfiguration = configurationRepository.save(configuration).orElseThrow(() -> new RepositoryException("Configuration could not be saved"));
        return new ResponseEntity<>(savedConfiguration, HttpStatus.CREATED);
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
