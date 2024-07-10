package app.services.rest;

// TODO 1: ADD ALL MODELS
import app.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
// TODO 2: Change "something" with the specific term from the problem
@RequestMapping("/app/something")
public class Controller {

    private static final String template = "Hello, %s!";

//    TODO 3: Add ALL the repositories needed
//    @Autowired
//    private IGameRepository gameRepository;
//    @Autowired
//    private IPlayerRepository playerRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

//    TODO 4: Add the method to get all the "somethings" by a specific "something"
//    @GetMapping("/{game_id}/positions")
//    public List<Position> getPositionsByGame(@PathVariable Long game_id) {
//        Game game = gameRepository.findOne(game_id).orElseThrow(() -> new RepositoryException("Game not found"));
//        return StreamSupport.stream(positionRepository.findAllForGame(game).spliterator(), false).toList();
//    }

//    TODO 5: Add the method to add a new "something"
//    @PostMapping("/configurations")
//    public ResponseEntity<Configuration> addConfiguration(@RequestBody Configuration configuration) {
//        Configuration savedConfiguration = configurationRepository.save(configuration).orElseThrow(() -> new RepositoryException("Configuration could not be saved"));
//        return new ResponseEntity<>(savedConfiguration, HttpStatus.CREATED);
//    }

//    TODO 6: Add the method to update a "something"
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

//    TODO 7: Add the method to delete a "something"
//    @DeleteMapping("/configurations/{id}")
//    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
//        configurationRepository.delete(id).orElseThrow(() -> new RepositoryException("Configuration could not be deleted"));
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
