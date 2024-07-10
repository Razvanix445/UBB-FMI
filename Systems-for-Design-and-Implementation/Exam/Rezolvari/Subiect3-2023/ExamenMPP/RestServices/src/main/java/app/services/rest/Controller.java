package app.services.rest;

import app.persistence.*;
import app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private IConfigurationRepository configurationRepository;
    @Autowired
    private IPositionRepository positionRepository;
    @Autowired
    private IConfigurationWordRepository configurationWordRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/games/details/{id}")
    public GameDetailsDTO getGameDetails(@PathVariable Long id) {
        Game game = gameRepository.findOne(id).orElseThrow(() -> new RepositoryException("Game not found"));
        List<Position> positions = StreamSupport.stream(positionRepository.findAllByGame(game).spliterator(), false).toList();
        List<PositionDTO> positionDTOs = positions.stream().map(p -> {
            return new PositionDTO(p.getCoordinateX(), p.getCoordinateY(), p.getPositionIndex());
        }).collect(Collectors.toList());
        String playerAlias = game.getPlayer().getAlias();
        List<Word> words = StreamSupport.stream(configurationWordRepository.findWordsByConfiguration(game.getConfiguration()).spliterator(), false).toList();
        Long score = game.getScore();

        return new GameDetailsDTO(id, positionDTOs, playerAlias, words, score);
    }

    @GetMapping("/games/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameRepository.findOne(id).orElseThrow(() -> new RepositoryException("Game not found"));
    }

    @GetMapping("/words/{configuration_id}")
    public List<Word> getWordsByConfiguration(@PathVariable Long configuration_id) {
        Configuration configuration = configurationRepository.findOne(configuration_id).orElseThrow(() -> new RepositoryException("Configuration not found"));
        return StreamSupport.stream(configurationWordRepository.findWordsByConfiguration(configuration).spliterator(), false).toList();
    }

    @GetMapping("/configurationwords/{configuration_id}")
    public List<ConfigurationWord> getConfigurationWordsByConfiguration(@PathVariable Long configuration_id) {
        Configuration configuration = configurationRepository.findOne(configuration_id).orElseThrow(() -> new RepositoryException("Configuration not found"));
        return StreamSupport.stream(configurationWordRepository.findConfigurationWordsByConfiguration(configuration).spliterator(), false).toList();
    }

    @GetMapping("/positions/{game_id}")
    public List<Position> getPositionsByGame(@PathVariable Long game_id) {
        Game game = gameRepository.findOne(game_id).orElseThrow(() -> new RepositoryException("Game not found"));
        return StreamSupport.stream(positionRepository.findAllByGame(game).spliterator(), false).toList();
    }

    @GetMapping("/configurations/{id}")
    public Configuration getConfigurationById(@PathVariable Long id) {
        return configurationRepository.findOne(id).orElseThrow(() -> new RepositoryException("Configuration not found"));
    }

    @PutMapping("/configurations/{id}")
    public ResponseEntity<?> updateConfigurationWord(@PathVariable Long id, @RequestBody ConfigurationWord configurationWord) {
        if (!id.equals(configurationWord.getId())) {
            return new ResponseEntity<String>("Path Id and Object Id do not match!", HttpStatus.BAD_REQUEST);
        }
        try {
            ConfigurationWord existingConfigurationWord = configurationWordRepository.findOne(id).orElse(null);
            if (existingConfigurationWord == null) {
                return new ResponseEntity<String>("ConfigurationWord not found", HttpStatus.NOT_FOUND);
            }
            else {
                configurationWordRepository.update(configurationWord);
                System.out.println("ConfigurationWord updated ..." + configurationWord);
                return new ResponseEntity<ConfigurationWord>(configurationWord, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping(value = "/configurations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateConfigurationWords(@PathVariable Long id, @RequestBody ConfigurationWord[] configurationWords) {
//        if (!id.equals(configurationWords[0].getId())) {
//            return new ResponseEntity<String>("Path Id and Object Id do not match!", HttpStatus.BAD_REQUEST);
//        }
//        try {
//            for (ConfigurationWord configurationWord : configurationWords) {
//                configurationWordRepository.update(configurationWord);
//            }
//            return ResponseEntity.ok("ConfigurationWords updated successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update ConfigurationWords: " + e.getMessage());
//        }
//    }

//    @PutMapping("/configurations/{id}")
//    public ResponseEntity<?> updateConfiguration(@PathVariable Long id, @RequestBody Configuration configuration) {
//        if (!id.equals(configuration.getId())) {
//            return new ResponseEntity<String>("Path Id and Object Id do not match!", HttpStatus.BAD_REQUEST);
//        }
//        try {
//            Configuration existingConfiguration = configurationRepository.findOne(id).orElse(null);
//            if (existingConfiguration == null) {
//                return new ResponseEntity<String>("Configuration not found", HttpStatus.NOT_FOUND);
//            }
//            else {
//                configurationRepository.update(configuration);
//                System.out.println("Configuration updated ..." + configuration);
//                return new ResponseEntity<Configuration>(configuration, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
