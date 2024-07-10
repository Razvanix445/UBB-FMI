package games.services.rest;

import games.model.Configuration;
import games.model.Game;
import games.model.Player;
import games.model.Position;
import games.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/games/players")
public class PlayerController {

    private static final String template = "Hello, %s!";

    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IPositionRepository positionRepository;
    @Autowired
    private IConfigurationRepository configurationRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/{alias}/games")
    public List<Game> getGamesByPlayer(@PathVariable String alias) {
        Player player = playerRepository.findOneByAlias(alias).orElseThrow(() -> new RepositoryException("Player not found"));
        return StreamSupport.stream(gameRepository.findAllByPlayer(player).spliterator(), false).toList();
    }

    @GetMapping("/{game_id}/positions")
    public List<Position> getPositionsByGame(@PathVariable Long game_id) {
        Game game = gameRepository.findOne(game_id).orElseThrow(() -> new RepositoryException("Game not found"));
        return StreamSupport.stream(positionRepository.findAllForGame(game).spliterator(), false).toList();
    }

    @PostMapping("/configurations")
    public ResponseEntity<Configuration> addConfiguration(@RequestBody Configuration configuration) {
        Configuration savedConfiguration = configurationRepository.save(configuration).orElseThrow(() -> new RepositoryException("Configuration could not be saved"));
        return new ResponseEntity<>(savedConfiguration, HttpStatus.CREATED);
    }

    @PutMapping("/configurations/{id}")
    public ResponseEntity<?> updateConfiguration(@PathVariable Long id, @RequestBody Configuration configuration) {
        if (!id.equals(configuration.getId())) {
            return new ResponseEntity<String>("Path Id and Object Id do not match!", HttpStatus.BAD_REQUEST);
        }
        try {
            Configuration existingConfiguration = configurationRepository.findOne(id).orElse(null);
            if (existingConfiguration == null) {
                return new ResponseEntity<String>("Configuration not found", HttpStatus.NOT_FOUND);
            }
            else {
                configurationRepository.update(configuration);
                System.out.println("Configuration updated ..." + configuration);
                return new ResponseEntity<Configuration>(configuration, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/configurations/{id}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        configurationRepository.delete(id).orElseThrow(() -> new RepositoryException("Configuration could not be deleted"));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
