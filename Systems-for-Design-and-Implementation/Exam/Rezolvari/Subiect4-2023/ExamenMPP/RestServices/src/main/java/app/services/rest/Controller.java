package app.services.rest;

import app.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

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
    private IPositionRepository positionRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameRepository.findOne(id).orElseThrow(() -> new RepositoryException("Game not found"));
    }

    @GetMapping("/positions/{game_id}")
    public List<Position> getPositionsByGame(@PathVariable Long game_id) {
        Game game = gameRepository.findOne(game_id).orElseThrow(() -> new RepositoryException("Game not found"));
        System.out.println("Game found: " + game);
        return StreamSupport.stream(positionRepository.findAllByGame(game).spliterator(), false).toList();
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

        return StreamSupport.stream(gameRepository.findAllByPlayer(foundPlayer).spliterator(), false)
                .sorted(Comparator.comparing(Game::getNoOfSeconds).reversed())
                .toList();
    }

    @PostMapping("/position")
    public ResponseEntity<Position> addPosition(@RequestBody Position position) {
        Position savedPosition = positionRepository.save(position).orElseThrow(() -> new RepositoryException("Position could not be saved"));
        return new ResponseEntity<>(savedPosition, HttpStatus.CREATED);
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
